package com.cegeka.employeeplanning;

import com.cegeka.employeeplanning.service.MitarbeiterService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MitarbeiterServiceTests {
    @Autowired
    MitarbeiterService mitarbeiterService;

    @Test
    public void test_001_getLastEndDateForMitarbeiter_given_Ma3_expected_2020_12_31() {
        Date lastEndDateForMitarbeiter = mitarbeiterService.getLastEndDateForMitarbeiter(3);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(lastEndDateForMitarbeiter);

        assertThat(calendar.get(Calendar.YEAR)).isEqualTo(2020);
        assertThat(calendar.get(Calendar.MONTH)).isEqualTo(Calendar.DECEMBER);
        assertThat(calendar.get(Calendar.DAY_OF_MONTH)).isEqualTo(31);
    }
}
