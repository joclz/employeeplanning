package com.cegeka.employeeplanning.service;

import com.cegeka.employeeplanning.data.dto.MitarbeiterDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.cegeka.employeeplanning.util.EmployeeplanningUtil.TEST_IMPORT;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(TEST_IMPORT)
public class MitarbeiterVertriebServiceTests {
    @Autowired
    MitarbeiterVertriebService mitarbeiterVertriebService;

    @Test
    public void test_getMitarbeiterVertriebListOrderByName_expected_correctNumberAndCorrectOrder() {
        final List<MitarbeiterDTO> mitarbeiterListOrderByName = mitarbeiterVertriebService.getMitarbeiterVertriebListOrderByName();
        assertThat(mitarbeiterListOrderByName.size()).isEqualTo(3);
        assertThat(mitarbeiterListOrderByName.get(0).getName()).isEqualTo("Bolika, Anna");
    }
}
