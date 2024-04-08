import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import datamodel.Order;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ParsingJsonDataTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void parsingJsonTest() throws Exception {
        List<Order> orders = objectMapper.readValue(Utils.fileFromClasspathResource("data/data.json"), new TypeReference<>() {});

        assertThat(orders).hasSize(3);

        assertThat(orders.get(0).getId()).isEqualTo("0001");
        assertThat(orders.get(0).getType()).isEqualTo("donut");
        assertThat(orders.get(0).getBatters()).hasSize(4);
        assertThat(orders.get(0).getToppings()).hasSize(7);
        assertThat(orders.get(1).getBatters()).hasSize(1);
        assertThat(orders.get(1).getToppings().get(4).getType()).isEqualTo("Maple");

    }
}
