package com.cegeka.employeeplanning.service;

import static com.cegeka.employeeplanning.util.EmployeeplanningUtil.TEST_IMPORT;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.cegeka.employeeplanning.data.Einsatz;
import com.cegeka.employeeplanning.data.EinsatzRepository;
import com.cegeka.employeeplanning.data.enums.Enums;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(TEST_IMPORT)
public class EinsatzServiceTests {
    @Autowired
    private EinsatzRepository einsatzRepository;

    @Autowired
    private EinsatzService einsatzService;

    @Test
    public void test_calcEinsatzWerteWithEinsatz1() {
        Optional<Einsatz> einsatzId1 = einsatzRepository.findById(1);
        Einsatz einsatz = einsatzService.calcEinsatzWerte(einsatzId1.get());

        assertThat(einsatz.getDeckungsbeitrag()).isEqualTo(50);
        assertThat(einsatz.getMarge()).isEqualTo(0.5555555555555556);
    }

    @Test
    public void test_calcEinsatzWerteWithEinsatz2() {
        Optional<Einsatz> einsatzId1 = einsatzRepository.findById(2);
        Einsatz einsatz = einsatzService.calcEinsatzWerte(einsatzId1.get());

        assertThat(einsatz.getDeckungsbeitrag()).isEqualTo(25);
        assertThat(einsatz.getMarge()).isEqualTo(0.25);
    }

    @Test
    public void test_findEinsaetzeByMitarbeiterVertriebId1_expected4() {
        Iterable<Einsatz> einsaetzeByMitarbeiterVertriebId = einsatzService.findEinsaetzeByMitarbeiterVertriebId(1);
        assertThat(einsaetzeByMitarbeiterVertriebId.spliterator().getExactSizeIfKnown()).isEqualTo(4);
    }

    @Test
    public void test_findEinsaetzeByEinsatzStatus_AND_findEinsaetzeByMitarbeiterVertriebId() {
        Set<Integer> einsatzId1 = new HashSet<>();
        einsatzRepository.findEinsaetzeByEinsatzStatus(Enums.EinsatzStatus.BEAUFTRAGT).forEach(id -> einsatzId1.add(id.getId()));

        Set<Integer> einsatzId2 = new HashSet<>();
        einsatzService.findEinsaetzeByMitarbeiterVertriebId(1).forEach(id -> einsatzId2.add(id.getId()));

        einsatzId1.retainAll(einsatzId2);

        Iterable<Einsatz> allById = einsatzRepository.findAllById(einsatzId1);
        assertThat(allById.spliterator().getExactSizeIfKnown()).isEqualTo(1);
    }


    @Test
    public void test_findEinsaetzeBySuchkriterien_given_keine_expected_8() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, null, null, null, null, null, null);
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(8);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_BeginnVon20201002_expected_4() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, null, null, "2020-10-02", null, null, null);
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(4);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_BeginnBis20201002_expected_5() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, null, null, null, "2020-10-02", null, null);
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(5);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_mAStatusSUBUNTERNEHMER_BeginnBis20201002_expected_1() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, "SUBUNTERNEHMER", null, null, "2020-10-02", null, null);
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(1);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_mAStatusANGESTELLT_BeginnBis20201002_expected_4() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, "ANGESTELLT", null, null, "2020-10-02", null, null);
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(4);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_BeginnVon20201002_BeginnBis20201002_expected_1() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, null, null, "2020-10-02", "2020-10-02", null, null);
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(1);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_EndeBis20201231_expected_3() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, null, null, null, null, null, "2020-12-31");
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(3);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_EndeVon20201231_expected_7() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, null, null, null, null, "2020-12-31", null);
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(7);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_EndeVon20201231_EndeBis20201231_expected_2() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, null, null, null, null, "2020-12-31", "2020-12-31");
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(2);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_vertriebMa1_EndeBis20201231_expected_2() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(1, null, null, null, null, null, null, "2020-12-31");
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(2);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_vertriebMa1_statusANGEBOTEN_EndeBis20201231_expected_1() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(1, null, null, "ANGEBOTEN", null, null, null, "2020-12-31");
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(1);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_vertriebMa1_statusANGEBOTEN_expected_2() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(1, null, null, "ANGEBOTEN", null, null, null, null);
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(2);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_vertriebMa1_Ma5_statusANGEBOTEN_expected_1() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(1, 5, null, "ANGEBOTEN", null, null, null, null);
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(1);
    }

    @Test
    public void Test_findEinsaetzeByMitarbeiter_MitarbeiterStatus_given_ANGESTELLT_expected_4() {
        Iterable<Einsatz> einsaetzeByMaStatus = einsatzRepository.findEinsaetzeByMitarbeiter_MitarbeiterStatus(Enums.MitarbeiterStatus.ANGESTELLT);
        assertThat(einsaetzeByMaStatus.spliterator().getExactSizeIfKnown()).isEqualTo(4);
    }

    @Test
    public void Test_findEinsaetzeByMitarbeiter_MitarbeiterStatus_given_SUBUNTERNEHMER_expected_4() {
        Iterable<Einsatz> einsaetzeByMaStatus = einsatzRepository.findEinsaetzeByMitarbeiter_MitarbeiterStatus(Enums.MitarbeiterStatus.SUBUNTERNEHMER);
        assertThat(einsaetzeByMaStatus.spliterator().getExactSizeIfKnown()).isEqualTo(4);
    }

    @Test
    public void Test_getDeckungsbeitrag_given_Date_20200814_expected_21_0() {
        double deckungsbeitrag = einsatzService.getDeckungsbeitrag("2020-08-14");
        assertThat(deckungsbeitrag).isEqualTo(21.0);
    }

    @Test
    public void Test_getDeckungsbeitrag_given_Date_20201002_expected_33_0() {
        double deckungsbeitrag = einsatzService.getDeckungsbeitrag("2020-10-02");
        assertThat(deckungsbeitrag).isEqualTo(33.0);
    }

    @Test
    public void Test_getDeckungsbeitrag_given_Date_20201003_expected_54_0() {
        double deckungsbeitrag = einsatzService.getDeckungsbeitrag("2020-10-03");
        assertThat(deckungsbeitrag).isEqualTo(54.0);
    }
}
