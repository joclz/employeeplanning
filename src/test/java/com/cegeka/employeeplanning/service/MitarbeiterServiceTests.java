package com.cegeka.employeeplanning.service;

import static com.cegeka.employeeplanning.util.EmployeeplanningUtil.TEST_IMPORT;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.cegeka.employeeplanning.data.Mitarbeiter;
import com.cegeka.employeeplanning.data.enums.Enums.MitarbeiterStatus;
import com.cegeka.employeeplanning.util.EmployeeplanningUtil;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(TEST_IMPORT)
public class MitarbeiterServiceTests {
    @Autowired
    MitarbeiterService mitarbeiterService;

    @Test
    public void test_getLastEndDateForMitarbeiter_given_Ma3_expected_2020_12_31() {
        Date lastEndDateForMitarbeiter = mitarbeiterService.getLastEndDateForMitarbeiter(3);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(lastEndDateForMitarbeiter);

        assertThat(calendar.get(Calendar.YEAR)).isEqualTo(2020);
        assertThat(calendar.get(Calendar.MONTH)).isEqualTo(Calendar.DECEMBER);
        assertThat(calendar.get(Calendar.DAY_OF_MONTH)).isEqualTo(31);
    }

    @Test
    public void test_getLastEndDateForMitarbeiter_given_Ma5_expected_2021_01_30() {
        Date lastEndDateForMitarbeiter = mitarbeiterService.getLastEndDateForMitarbeiter(5);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(lastEndDateForMitarbeiter);

        assertThat(calendar.get(Calendar.YEAR)).isEqualTo(2021);
        assertThat(calendar.get(Calendar.MONTH)).isEqualTo(Calendar.JANUARY);
        assertThat(calendar.get(Calendar.DAY_OF_MONTH)).isEqualTo(30);
    }

    @Test
    public void test_getChanceForMitarbeiter_given_Ma5_expected_50() {
        Integer chanceForMitarbeiter = mitarbeiterService.getChanceForMitarbeiter(5);
        assertThat(chanceForMitarbeiter).isEqualTo(50);
    }

    @Test
    public void test_getMitarbeiterBank_given_Date_2020_08_14_expected_Ma_3() {
        Iterable<Mitarbeiter> mitarbeiterBank = mitarbeiterService.getMitarbeiterBank(EmployeeplanningUtil.parseDate("2020-08-14"));
        assertThat(mitarbeiterBank.spliterator().getExactSizeIfKnown()).isEqualTo(3);
        mitarbeiterBank.forEach(id -> assertThat(id.getId()).isIn(1, 6, 7));
    }

    @Test
    public void test_getMitarbeiterBank_given_Date_2020_09_01_expected_Ma_4() {
        Iterable<Mitarbeiter> mitarbeiterBank = mitarbeiterService.getMitarbeiterBank(EmployeeplanningUtil.parseDate("2020-09-01"));
        assertThat(mitarbeiterBank.spliterator().getExactSizeIfKnown()).isEqualTo(4);
        mitarbeiterBank.forEach(id -> assertThat(id.getId()).isIn(1, 4, 6, 7));
    }

    @Test
    public void test_getMitarbeiterInternBank_given_Date_2020_08_14_expected_Ma_2() {
        Iterable<Mitarbeiter> mitarbeiterBank = mitarbeiterService.getMitarbeiterBank(true, EmployeeplanningUtil.parseDate("2020-08-14"));
        assertThat(mitarbeiterBank.spliterator().getExactSizeIfKnown()).isEqualTo(2);
        mitarbeiterBank.forEach(id -> assertThat(id.getId()).isIn(1, 7));
    }

    @Test
    public void test_getMitarbeiterInternBank_given_Date_2020_09_01_expected_Ma_3() {
        Iterable<Mitarbeiter> mitarbeiterBank = mitarbeiterService.getMitarbeiterBank(true, EmployeeplanningUtil.parseDate("2020-09-01"));
        assertThat(mitarbeiterBank.spliterator().getExactSizeIfKnown()).isEqualTo(3);
        mitarbeiterBank.forEach(id -> assertThat(id.getId()).isIn(1, 4, 7));
    }

    @Test
    public void test_countMitarbeiterImEinsatz_given_Date2020_08_14_StatusANGESTELLT_expected_Ma_1() {
        int countMitarbeiter = mitarbeiterService.countMitarbeiterImEinsatz(MitarbeiterStatus.ANGESTELLT, EmployeeplanningUtil.parseDate("2020-08-14"));
        assertThat(countMitarbeiter).isEqualTo(1);
    }

    @Test
    public void test_countMitarbeiterImEinsatz_given_Date2020_08_14_StatusSUBUNTERNEHMER_expected_Ma_0() {
        int countMitarbeiter = mitarbeiterService.countMitarbeiterImEinsatz(MitarbeiterStatus.SUBUNTERNEHMER, EmployeeplanningUtil.parseDate("2020-08-14"));
        assertThat(countMitarbeiter).isEqualTo(0);
    }

    @Test
    public void test_countMitarbeiterImEinsatz_given_Date2020_10_04_expected_Ma_3() {
        int countMitarbeiter = mitarbeiterService.countMitarbeiterImEinsatz(null, EmployeeplanningUtil.parseDate("2020-10-04"));
        assertThat(countMitarbeiter).isEqualTo(3);
    }

    @Test
    public void test_countMitarbeiterImEinsatz_given_Date2020_10_04_StatusSUBUNTERNEHMER_expected_Ma_2() {
        int countMitarbeiter = mitarbeiterService.countMitarbeiterImEinsatz(MitarbeiterStatus.SUBUNTERNEHMER, EmployeeplanningUtil.parseDate("2020-10-04"));
        assertThat(countMitarbeiter).isEqualTo(2);
    }
}
