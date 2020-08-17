package com.cegeka.employeeplanning.service;

import com.cegeka.employeeplanning.data.Mitarbeiter;
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

    @Test
    public void test_002_getLastEndDateForMitarbeiter_given_Ma5_expected_2021_01_30() {
        Date lastEndDateForMitarbeiter = mitarbeiterService.getLastEndDateForMitarbeiter(5);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(lastEndDateForMitarbeiter);

        assertThat(calendar.get(Calendar.YEAR)).isEqualTo(2021);
        assertThat(calendar.get(Calendar.MONTH)).isEqualTo(Calendar.JANUARY);
        assertThat(calendar.get(Calendar.DAY_OF_MONTH)).isEqualTo(30);
    }

    @Test
    public void test_010_getChanceForMitarbeiter_given_Ma5_expected_50() {
        Integer chanceForMitarbeiter = mitarbeiterService.getChanceForMitarbeiter(5);
        assertThat(chanceForMitarbeiter).isEqualTo(50);
    }

    @Test
    public void test_020_getMitarbeiterBank_given_Date_2020_08_14_expected_Ma2() {
        Iterable<Mitarbeiter> mitarbeiterBank = mitarbeiterService.getMitarbeiterBank("2020-08-14");
        assertThat(mitarbeiterBank.spliterator().getExactSizeIfKnown()).isEqualTo(2);
        mitarbeiterBank.forEach(id -> assertThat(id.getId()).isIn(1, 6));
    }

    @Test
    public void test_020_getMitarbeiterBank_given_Date_2020_09_01_expected_Ma3() {
        Iterable<Mitarbeiter> mitarbeiterBank = mitarbeiterService.getMitarbeiterBank("2020-09-01");
        assertThat(mitarbeiterBank.spliterator().getExactSizeIfKnown()).isEqualTo(3);
        mitarbeiterBank.forEach(id -> assertThat(id.getId()).isIn(1, 4, 6));
    }

    @Test
    public void test_020_getMitarbeiterInternBank_given_Date_2020_08_14_expected_Ma1() {
        Iterable<Mitarbeiter> mitarbeiterBank = mitarbeiterService.getMitarbeiterBank(true, "2020-08-14");
        assertThat(mitarbeiterBank.spliterator().getExactSizeIfKnown()).isEqualTo(1);
        mitarbeiterBank.forEach(id -> assertThat(id.getId()).isIn(1));
    }

    @Test
    public void test_020_getMitarbeiterInternBank_given_Date_2020_09_01_expected_Ma2() {
        Iterable<Mitarbeiter> mitarbeiterBank = mitarbeiterService.getMitarbeiterBank(true, "2020-09-01");
        assertThat(mitarbeiterBank.spliterator().getExactSizeIfKnown()).isEqualTo(2);
        mitarbeiterBank.forEach(id -> assertThat(id.getId()).isIn(1, 4));
    }
}
