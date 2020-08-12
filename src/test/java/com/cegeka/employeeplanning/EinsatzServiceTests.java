package com.cegeka.employeeplanning;

import com.cegeka.employeeplanning.data.Einsatz;
import com.cegeka.employeeplanning.data.EinsatzRepository;
import com.cegeka.employeeplanning.data.EinsatzService;
import com.cegeka.employeeplanning.data.enums.Enums;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EinsatzServiceTests {
    @Autowired
    private EinsatzRepository einsatzRepository;

    @Autowired
    private EinsatzService einsatzService;

    @Test
    public void test_001_calcEinsatzWerteWithEinsatz1() throws Exception {
        Optional<Einsatz> einsatzId1 = einsatzRepository.findById(1);
        Einsatz einsatz = einsatzService.calcEinsatzWerte(einsatzId1.get());

        assertThat(einsatz.getDeckungsbeitrag()).isEqualTo(50);
        assertThat(einsatz.getMarge()).isEqualTo(0.5555555555555556);
    }

    @Test
    public void test_001_calcEinsatzWerteWithEinsatz2() throws Exception {
        Optional<Einsatz> einsatzId1 = einsatzRepository.findById(2);
        Einsatz einsatz = einsatzService.calcEinsatzWerte(einsatzId1.get());

        assertThat(einsatz.getDeckungsbeitrag()).isEqualTo(25);
        assertThat(einsatz.getMarge()).isEqualTo(0.25);
    }

    @Test
    public void test_010_findEinsaetzeByMitarbeiterVertriebId1_expected4() throws Exception {
        Iterable<Einsatz> einsaetzeByMitarbeiterVertriebId = einsatzService.findEinsaetzeByMitarbeiterVertriebId(1);
        assertThat(einsaetzeByMitarbeiterVertriebId.spliterator().getExactSizeIfKnown()).isEqualTo(4);
    }

    @Test
    public void test_015_findEinsaetzeByEinsatzStatus_AND_findEinsaetzeByMitarbeiterVertriebId() throws Exception {
        Set<Integer> einsatzId1 = new HashSet<>();
        einsatzRepository.findEinsaetzeByEinsatzStatus(Enums.EinsatzStatus.BEAUFTRAGT).forEach(id -> einsatzId1.add(id.getId()));

        Set<Integer> einsatzId2 = new HashSet<>();
        einsatzService.findEinsaetzeByMitarbeiterVertriebId(1).forEach(id -> einsatzId2.add(id.getId()));

        einsatzId1.retainAll(einsatzId2);

        Iterable<Einsatz> allById = einsatzRepository.findAllById(einsatzId1);
        assertThat(allById.spliterator().getExactSizeIfKnown()).isEqualTo(1);
    }
}
