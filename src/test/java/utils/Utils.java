package utils;

import com.opencsv.CSVReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.jupiter.api.Assertions;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Utils {

    public static File fileFromClasspathResource(final String resource) throws URISyntaxException {
        return Path.of(ClassLoader.getSystemResource(resource).toURI()).toFile();
    }

    public static File unzip(final File archive) throws IOException {
        File targetFolder = Path.of("build", UUID.randomUUID().toString()).toFile();
        Assertions.assertTrue(targetFolder.mkdirs());

        byte[] buffer = new byte[1024];
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(archive))) {
            ZipEntry zipEntry;

            while ((zipEntry = zis.getNextEntry()) != null) {

                File newFile = new File(targetFolder, zipEntry.getName());
                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
            }

        }

        return targetFolder;
    }

    public static List<String> cellsInRange(Sheet sheet, String from, String to) {
        List<String> cells = new ArrayList<>();

        int columnFrom = charToIndex(from.charAt(0));
        int columnTo = charToIndex(to.charAt(0));
        int rowFrom = Integer.parseInt(from.substring(1));
        int rowTo = Integer.parseInt(to.substring(1));

        assertThat(columnTo).isGreaterThanOrEqualTo(columnFrom);
        assertThat(rowTo).isGreaterThanOrEqualTo(rowFrom);

        for(int rowIndex = rowFrom - 1; rowIndex < rowTo ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);

            for(int column = columnFrom; column <= columnTo; column++) {
                Cell cell = row.getCell(column);
                switch(cell.getCellType()) {
                    case NUMERIC -> cells.add(String.valueOf(cell.getNumericCellValue()));
                    case STRING -> cells.add(String.valueOf(cell.getStringCellValue()));
                }
            }
        }

        return cells;
    }

    private static int charToIndex(char column) {
        return column - 'A';
    }

    public static List<String[]> readCsv(Path filePath) throws Exception {
        try (Reader reader = Files.newBufferedReader(filePath)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                return csvReader.readAll();
            }
        }
    }

}
