import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.Utils.cellsInRange;

class ParsingFilesFromZipTest {

    private static final String ARCHIVE = "data/files.zip";

    @Test
    void parsingFilesTest() throws Exception {
        File archive = Utils.fileFromClasspathResource(ARCHIVE);
        File tempDirectory = Utils.unzip(archive);
        File[] files = Objects.requireNonNull(tempDirectory.listFiles());

        for (File file : files) {
            switch (file.getName()) {
                case "heat-map.xlsx" -> parseXlsx(file);
                case "sample.pdf" -> parsePdf(file);
                case "students_data.csv" -> parseCsv(file);
            }
        }
    }

    void parseXlsx(File xlsx) {
        XLS workbook = new XLS(xlsx);
        Sheet sheet = workbook.excel.getSheet("Sheet1");

        assertThat(sheet.getRow(0).getCell(0).getStringCellValue()).
                contains("Average Monthly Temperatures at Central Park, New York");

        assertThat(sheet.getRow(1).getCell(1).getStringCellValue()).contains("Jan");
        assertThat(sheet.getRow(2).getCell(0).getNumericCellValue()).isEqualTo(2009);
        assertThat(sheet.getRow(2).getCell(1).getNumericCellValue()).isEqualTo(27.9);

        List<Integer> years = cellsInRange(sheet, "A3", "A11")
                .stream()
                .map(it -> it.replace(".0", ""))
                .map(Integer::parseInt)
                .toList();

        assertThat(years).contains(2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017);

        assertThat(cellsInRange(sheet, "B2", "M2"))
                .contains("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

        List<Double> temperature = cellsInRange(sheet, "B3", "M11")
                .stream()
                .map(Double::parseDouble)
                .toList();

        temperature.forEach(it -> assertThat(it).isBetween(0.0, 100.0));
    }

    void parsePdf(File file) throws Exception {
        PDF pdf = new PDF(file);
        assertThat(pdf.title).contains("sample");
        assertThat(pdf.numberOfPages).isEqualTo(1);
        assertThat(pdf.author).isEqualTo("Philip Hutchison");
        assertThat(pdf.text).contains("Lorem ipsum dolor sit amet, consectetuer adipiscing elit");
    }

    void parseCsv(File csv) throws Exception {
        List<String[]> data = Utils.readCsv(csv.toPath());
        assertThat(data).hasSize(10);

        List<String> names = data.stream()
                                 .map(row -> row[0])
                                 .toList();

        assertThat(names)
                .containsAll(List.of(
                        "Lady",
                        "Felton",
                        "Leslie",
                        "Kenna",
                        "Kareem",
                        "Aron",
                        "Beaulah",
                        "Heather",
                        "Moses",
                        "Salena"
                ));
    }
}
