package com.cegeka.employeeplanning;

import com.cegeka.employeeplanning.data.EinsatzRepository;
import com.cegeka.employeeplanning.data.Mitarbeiter;
import com.cegeka.employeeplanning.data.MitarbeiterRepository;
import com.cegeka.employeeplanning.data.enums.Enums;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeplanningControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;
    @Autowired
    private EinsatzRepository einsatzRepository;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test_001_listMitarbeiter_Expected_CorrectValuesAndNumber6() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/listMitarbeiter");
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isOk());
        perform.andExpect(content().string(
                "[{\"id\":1,\"mitarbeiterStatus\":\"ANGESTELLT\",\"stundensatzEK\":30.0,\"name\":\"Mustermann\",\"vorname\":\"Max\",\"mitarbeiterUnit\":\"FACTORY_MUENCHEN\"}," +
                        "{\"id\":2,\"mitarbeiterStatus\":\"SUBUNTERNEHMER\",\"stundensatzEK\":50.0,\"name\":\"Stoteles\",\"vorname\":\"Ari\",\"mitarbeiterUnit\":\"FACTORY_MUENCHEN\"}," +
                        "{\"id\":3,\"mitarbeiterStatus\":\"ANGESTELLT\",\"stundensatzEK\":36.0,\"name\":\"LÃ¼hse\",\"vorname\":\"Anna\",\"mitarbeiterUnit\":\"FACTORY_MUENCHEN\"}," +
                        "{\"id\":4,\"mitarbeiterStatus\":\"ANGESTELLT\",\"stundensatzEK\":35.0,\"name\":\"MÃ¼ller\",\"vorname\":\"Werner\",\"mitarbeiterUnit\":\"FACTORY_NUERNBERG\"}," +
                        "{\"id\":5,\"mitarbeiterStatus\":\"SUBUNTERNEHMER\",\"stundensatzEK\":55.0,\"name\":\"Schulz\",\"vorname\":\"Renate\",\"mitarbeiterUnit\":\"FACTORY_NUERNBERG\"}," +
                        "{\"id\":6,\"mitarbeiterStatus\":\"SUBUNTERNEHMER\",\"stundensatzEK\":60.0,\"name\":\"Schmidt\",\"vorname\":\"Wolfgang\",\"mitarbeiterUnit\":\"FACTORY_MUENCHEN\"}]"));
        assertThat(mitarbeiterRepository.count()).isEqualTo(6);
    }

    @Test
    public void test_011_addMitarbeiterMitEinzelnenWerten_Expected_CorrectValuesAndNumber7() throws Exception {
        MultiValueMap multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("vorname", "Hannes");
        multiValueMap.add("name", "Wolf");
        multiValueMap.add("status", Enums.MitarbeiterStatus.ANGESTELLT.name());
        multiValueMap.add("unit", Enums.MitarbeiterUnit.FACTORY_MUENCHEN.name());
        multiValueMap.add("stundensatzEK", "10.1");

        MockHttpServletRequestBuilder requestBuilder = post("/addMitarbeiterMitEinzelnenWerten").params(multiValueMap);
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isOk());
        assertThat(mitarbeiterRepository.count()).isEqualTo(7);
    }

    // Todo - Dieser Testfall geht aktuell nicht.
    // Der Unit-Test benötigt offensichtlich @RequestBody beim Aufruf der Methode addMitarbeiter.
    // Beim Aufruf innerhalb der Anwendung stört dies aber.
    @Test
    public void test_012_addMitarbeiter_Expected_CorrectNamesAndNumber() throws Exception {
        Mitarbeiter mitarbeiter = new Mitarbeiter();
        mitarbeiter.setVorname("Hans-Neu");
        mitarbeiter.setName("Schmidt-Neu");
        mitarbeiter.setMitarbeiterStatus(Enums.MitarbeiterStatus.ANGESTELLT);
        mitarbeiter.setMitarbeiterUnit(Enums.MitarbeiterUnit.FACTORY_MUENCHEN);
        mitarbeiter.setStundensatzEK(12.1);

        long oldValue = mitarbeiterRepository.count();
        MockHttpServletRequestBuilder requestBuilder = post("/addMitarbeiter")
                .content(asJsonString(mitarbeiter)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isOk());
        assertThat(mitarbeiterRepository.count()).isEqualTo(oldValue + 1);

        Iterable<Mitarbeiter> all = mitarbeiterRepository.findAll();
        boolean treffer = false;
        for (Mitarbeiter ma : all) {
            if (ma.getName().equals("Schmidt-Neu") && ma.getVorname().equals("Hans-Neu")) {
                treffer = true;
                break;
            }
        }
        assertThat(treffer).isEqualTo(true);
    }

    @Test
    public void test_100_listEinsaetze_Expected_CorrectValuesAndNumber8() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/listEinsaetze");
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isOk());
        assertThat(einsatzRepository.count()).isEqualTo(8);
    }

    @Test
    public void test_111_findEinsaetzeByEinsatzStatus_Expected_CorrectNumber3() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/findEinsaetzeByEinsatzStatus").param("status", "ANGEBOTEN");
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isOk());
        perform.andExpect(jsonPath("$", hasSize(3)));
    }
}
