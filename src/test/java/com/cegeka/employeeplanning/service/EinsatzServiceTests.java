package com.cegeka.employeeplanning.service;

import static com.cegeka.employeeplanning.util.EmployeeplanningUtil.TEST_IMPORT;
import static com.cegeka.employeeplanning.util.EmployeeplanningUtil.round;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.cegeka.employeeplanning.data.Einsatz;
import com.cegeka.employeeplanning.data.EinsatzDTO;
import com.cegeka.employeeplanning.data.enums.Enums;
import com.cegeka.employeeplanning.data.enums.Enums.EinsatzStatus;
import com.cegeka.employeeplanning.data.enums.Enums.MitarbeiterStatus;
import com.cegeka.employeeplanning.repositories.EinsatzRepository;
import com.cegeka.employeeplanning.util.EmployeeplanningUtil;

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

        assertThat(einsatz.getDeckungsbeitrag()).isEqualTo(45);
        assertThat(einsatz.getMarge()).isEqualTo(0.5);
    }

    @Test
    public void test_calcEinsatzWerteWithEinsatz2() {
        Optional<Einsatz> einsatzId1 = einsatzRepository.findById(2);
        Einsatz einsatz = einsatzService.calcEinsatzWerte(einsatzId1.get());

        assertThat(einsatz.getDeckungsbeitrag()).isEqualTo(25);
        assertThat(einsatz.getMarge()).isEqualTo(0.25);
    }

    @Test
    public void test_calcEinsatzWerteWithEinsatz4() {
        Optional<Einsatz> einsatzId1 = einsatzRepository.findById(4);
        Einsatz einsatz = einsatzService.calcEinsatzWerte(einsatzId1.get());

        assertThat(einsatz.getDeckungsbeitrag()).isEqualTo(28);
        assertThat(round(einsatz.getMarge(), 2)).isEqualTo(0.33);
    }

    @Test
    public void test_findEinsaetzeByMitarbeiterVertriebId1_expected5() {
        Iterable<Einsatz> einsaetzeByMitarbeiterVertriebId = einsatzService.findEinsaetzeByMitarbeiterVertriebId(1);
        assertThat(einsaetzeByMitarbeiterVertriebId.spliterator().getExactSizeIfKnown()).isEqualTo(5);
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
    public void test_findEinsaetzeBySuchkriterien_given_keine_expected_9() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, null,
                null, null, null, null, null);
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(9);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_BeginnVon20201002_expected_4() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, null,
                null, EmployeeplanningUtil.parseDate("2020-10-02"), null, null, null);
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(4);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_BeginnBis20201002_expected_6() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, null,
                null, null, EmployeeplanningUtil.parseDate("2020-10-02"), null, null);
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(6);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_mAStatusSUBUNTERNEHMER_BeginnBis20201002_expected_2() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, MitarbeiterStatus.SUBUNTERNEHMER,
                null, null, EmployeeplanningUtil.parseDate("2020-10-02"), null, null);
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(2);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_mAStatusSUBUNTERNEHMER_StatusBEAUFTRAGT_BeginnBis20201002_expected_1() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, MitarbeiterStatus.SUBUNTERNEHMER,
                EinsatzStatus.BEAUFTRAGT, null, EmployeeplanningUtil.parseDate("2020-10-02"), null, null);
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(1);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_mAStatusANGESTELLT_BeginnBis20201002_expected_4() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, MitarbeiterStatus.ANGESTELLT,
                null, null, EmployeeplanningUtil.parseDate("2020-10-02"), null, null);
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(4);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_BeginnVon20201002_BeginnBis20201002_expected_1() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, null,
                null, EmployeeplanningUtil.parseDate("2020-10-02"), EmployeeplanningUtil.parseDate("2020-10-02"), null, null);
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(1);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_EndeBis20201231_expected_4() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, null,
                null, null, null, null, EmployeeplanningUtil.parseDate("2020-12-31"));
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(4);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_EndeVon20201231_expected_7() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, null,
                null, null, null, EmployeeplanningUtil.parseDate("2020-12-31"), null);
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(7);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_EndeVon20201231_EndeBis20201231_expected_2() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, null,
                null, null, null, EmployeeplanningUtil.parseDate("2020-12-31"), EmployeeplanningUtil.parseDate("2020-12-31"));
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(2);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_vertriebMa1_EndeBis20201231_expected_3() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(1, null, null,
                null, null, null, null, EmployeeplanningUtil.parseDate("2020-12-31"));
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(3);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_vertriebMa1_statusANGEBOTEN_EndeBis20201231_expected_1() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(1, null, null,
                EinsatzStatus.ANGEBOTEN, null, null, null, EmployeeplanningUtil.parseDate("2020-12-31"));
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(1);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_vertriebMa1_statusANGEBOTEN_expected_2() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(1, null, null,
                EinsatzStatus.ANGEBOTEN, null, null, null, null);
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(2);
    }

    @Test
    public void test_findEinsaetzeBySuchkriterien_given_vertriebMa1_Ma5_statusANGEBOTEN_expected_1() {
        EinsatzSuche einsatzSuche = new EinsatzSuche(1, 5, null,
                EinsatzStatus.ANGEBOTEN, null, null, null, null);
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
        assertThat(einsaetze.spliterator().getExactSizeIfKnown()).isEqualTo(1);
    }

    @Test
    public void Test_findEinsaetzeByMitarbeiter_MitarbeiterStatus_given_ANGESTELLT_expected_4() {
        Iterable<Einsatz> einsaetzeByMaStatus = einsatzRepository.findEinsaetzeByMitarbeiter_MitarbeiterStatus(Enums.MitarbeiterStatus.ANGESTELLT);
        assertThat(einsaetzeByMaStatus.spliterator().getExactSizeIfKnown()).isEqualTo(4);
    }

    @Test
    public void Test_findEinsaetzeByMitarbeiter_MitarbeiterStatus_given_SUBUNTERNEHMER_expected_5() {
        Iterable<Einsatz> einsaetzeByMaStatus = einsatzRepository.findEinsaetzeByMitarbeiter_MitarbeiterStatus(Enums.MitarbeiterStatus.SUBUNTERNEHMER);
        assertThat(einsaetzeByMaStatus.spliterator().getExactSizeIfKnown()).isEqualTo(5);
    }

    @Test
    public void Test_getDeckungsbeitrag_given_Date_20200814_expected_50_0() {
        double deckungsbeitrag = einsatzService.getDeckungsbeitrag(EmployeeplanningUtil.parseDate("2020-08-14"));
        assertThat(deckungsbeitrag).isEqualTo(50.0);
    }

    @Test
    public void Test_getDeckungsbeitrag_given_Date_20201002_expected_53_0() {
        double deckungsbeitrag = einsatzService.getDeckungsbeitrag(EmployeeplanningUtil.parseDate("2020-10-02"));
        assertThat(deckungsbeitrag).isEqualTo(53.0);
    }

    @Test
    public void Test_getDeckungsbeitrag_given_Date_20201003_expected_71_5() {
        double deckungsbeitrag = einsatzService.getDeckungsbeitrag(EmployeeplanningUtil.parseDate("2020-10-03"));
        assertThat(deckungsbeitrag).isEqualTo(71.5);
    }

    @Test
    public void test_convertToEntity_givenMA1_MAV1_expected_correctValues() {
        EinsatzDTO einsatzDTO = new EinsatzDTO(1,1, EinsatzStatus.ANGEBOTEN,
                EmployeeplanningUtil.parseDate("2020-09-01"), EmployeeplanningUtil.parseDate("2021-08-31"),
                50, 20, 100,
                "projektnummerNettime", "beauftragungsnummer");
        Einsatz einsatz = einsatzService.convertToEntity(einsatzDTO);
        assertThat(einsatz.getMitarbeiter().getName()).isEqualTo("Mustermann");
        assertThat(einsatz.getMitarbeiter().getId()).isEqualTo(1);
        assertThat(einsatz.getMitarbeiterVertrieb().getName()).isEqualTo("Günzkofer");
        assertThat(einsatz.getMitarbeiterVertrieb().getId()).isEqualTo(1);
        assertThat(einsatz.getBeginn()).isEqualTo("2020-09-01");
        assertThat(einsatz.getEnde()).isEqualTo("2021-08-31");
        assertThat(einsatz.getEinsatzStatus()).isEqualTo(EinsatzStatus.ANGEBOTEN);
        assertThat(einsatz.getWahrscheinlichkeit()).isEqualTo(50);
        assertThat(einsatz.getZusatzkostenReise()).isEqualTo(20);
        assertThat(einsatz.getStundensatzVK()).isEqualTo(100);
        assertThat(einsatz.getProjektnummerNettime()).isEqualTo("projektnummerNettime");
        assertThat(einsatz.getBeauftragungsnummer()).isEqualTo("beauftragungsnummer");
    }

    @Test
    public void test_convertToEntity_givenMA0_MAV0_expected_MaNullAndMavNull_But_remainingValuesCorrect() {
        EinsatzDTO einsatzDTO = new EinsatzDTO(0,0, EinsatzStatus.ANGEBOTEN,
                EmployeeplanningUtil.parseDate("2020-09-01"), EmployeeplanningUtil.parseDate("2021-08-31"),
                50, 20, 100,
                "projektnummerNettime", "beauftragungsnummer");
        Einsatz einsatz = einsatzService.convertToEntity(einsatzDTO);
        assertThat(einsatz.getMitarbeiter()).isEqualTo(null);
        assertThat(einsatz.getMitarbeiterVertrieb()).isEqualTo(null);
        assertThat(einsatz.getBeginn()).isEqualTo("2020-09-01");
        assertThat(einsatz.getEnde()).isEqualTo("2021-08-31");
        assertThat(einsatz.getEinsatzStatus()).isEqualTo(EinsatzStatus.ANGEBOTEN);
        assertThat(einsatz.getWahrscheinlichkeit()).isEqualTo(50);
        assertThat(einsatz.getZusatzkostenReise()).isEqualTo(20);
        assertThat(einsatz.getStundensatzVK()).isEqualTo(100);
        assertThat(einsatz.getProjektnummerNettime()).isEqualTo("projektnummerNettime");
        assertThat(einsatz.getBeauftragungsnummer()).isEqualTo("beauftragungsnummer");
    }
}
