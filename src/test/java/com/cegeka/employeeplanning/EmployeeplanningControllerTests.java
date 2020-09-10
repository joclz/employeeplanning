package com.cegeka.employeeplanning;

import static com.cegeka.employeeplanning.util.EmployeeplanningUtil.TEST_IMPORT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cegeka.employeeplanning.data.Einsatz;
import com.cegeka.employeeplanning.data.Mitarbeiter;
import com.cegeka.employeeplanning.data.MitarbeiterVertrieb;
import com.cegeka.employeeplanning.data.dto.EinsatzDTO;
import com.cegeka.employeeplanning.data.enums.Enums;
import com.cegeka.employeeplanning.repositories.EinsatzRepository;
import com.cegeka.employeeplanning.repositories.MitarbeiterRepository;
import com.cegeka.employeeplanning.repositories.MitarbeiterVertriebRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(TEST_IMPORT)
public class EmployeeplanningControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;
    @Autowired
    private EinsatzRepository einsatzRepository;
    @Autowired
    private MitarbeiterVertriebRepository mitarbeiterVertriebRepository;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // @Test
    public void test_listMitarbeiter_Expected_CorrectValuesAndNumber7() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/listMitarbeiter");
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isOk());
        perform.andExpect(content().string("[{\"id\":1,\"mitarbeiterStatus\":\"ANGESTELLT\",\"stundensatzEK\":30.0,\"name\":\"Mustermann\",\"vorname\":\"Max\",\"mitarbeiterUnit\":\"FACTORY_MUENCHEN\"}," +
                "{\"id\":2,\"mitarbeiterStatus\":\"SUBUNTERNEHMER\",\"stundensatzEK\":50.0,\"name\":\"Stoteles\",\"vorname\":\"Ari\",\"mitarbeiterUnit\":\"FACTORY_MUENCHEN\"}," +
                "{\"id\":3,\"mitarbeiterStatus\":\"ANGESTELLT\",\"stundensatzEK\":36.0,\"name\":\"Lühse\",\"vorname\":\"Anna\",\"mitarbeiterUnit\":\"FACTORY_MUENCHEN\"}," +
                "{\"id\":4,\"mitarbeiterStatus\":\"ANGESTELLT\",\"stundensatzEK\":35.0,\"name\":\"Müller\",\"vorname\":\"Werner\",\"mitarbeiterUnit\":\"FACTORY_NUERNBERG\"}," +
                "{\"id\":5,\"mitarbeiterStatus\":\"SUBUNTERNEHMER\",\"stundensatzEK\":55.0,\"name\":\"Schulz\",\"vorname\":\"Renate\",\"mitarbeiterUnit\":\"FACTORY_NUERNBERG\"}," +
                "{\"id\":6,\"mitarbeiterStatus\":\"SUBUNTERNEHMER\",\"stundensatzEK\":60.0,\"name\":\"Schmidt\",\"vorname\":\"Wolfgang\",\"mitarbeiterUnit\":\"FACTORY_MUENCHEN\"}," +
                "{\"id\":7,\"mitarbeiterStatus\":\"ANGESTELLT\",\"stundensatzEK\":29.5,\"name\":\"Lange\",\"vorname\":\"Yvonne\",\"mitarbeiterUnit\":\"FACTORY_MUENCHEN\"}]"));
        assertThat(mitarbeiterRepository.count()).isEqualTo(7);
    }

    @Test
    public void test_addMitarbeiterMitEinzelnenWerten_Expected_CorrectValuesAndNumber8() throws Exception {
        MultiValueMap multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("vorname", "Hannes");
        multiValueMap.add("name", "Wolf");
        multiValueMap.add("status", Enums.MitarbeiterStatus.ANGESTELLT.name());
        multiValueMap.add("unit", Enums.MitarbeiterUnit.FACTORY_MUENCHEN.name());
        multiValueMap.add("stundensatzEK", "10.1");

        MockHttpServletRequestBuilder requestBuilder = post("/addMitarbeiterMitEinzelnenWerten").params(multiValueMap);
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isOk());
        assertThat(mitarbeiterRepository.count()).isEqualTo(8);
    }

    @Test
    public void test_addMitarbeiter_Expected_CorrectNamesAndNumber() throws Exception {
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
    public void test_listEinsaetze_Expected_CorrectValuesAndNumber9() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/listEinsaetze");
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isOk());
        assertThat(einsatzRepository.count()).isEqualTo(9);
    }

    @Test
    public void test_findEinsaetzeByEinsatzStatus_Expected_CorrectNumber3() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/findEinsaetzeByEinsatzStatus").param("status", "ANGEBOTEN");
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isOk());
        perform.andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void test_updateMitarbeiter_Expected_newValue() throws Exception {
        Mitarbeiter mitarbeiter = mitarbeiterRepository.findById(Integer.valueOf(1)).get();
        double neuerStundensatz = mitarbeiter.getStundensatzEK()*2;
        mitarbeiter.setStundensatzEK(neuerStundensatz);

        MockHttpServletRequestBuilder requestBuilder = post("/updateMitarbeiter")
                .content(asJsonString(mitarbeiter)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isOk());

        Mitarbeiter mitarbeiterNeu = mitarbeiterRepository.findById(Integer.valueOf(1)).get();
        assertThat(mitarbeiterNeu.getStundensatzEK()).isEqualTo(neuerStundensatz);
    }

    @Test
    public void test_updateMitarbeiterId_Expected_Status404() throws Exception {
        Mitarbeiter mitarbeiter = mitarbeiterRepository.findById(Integer.valueOf(1)).get();
        mitarbeiter.setId(Integer.valueOf(99));

        MockHttpServletRequestBuilder requestBuilder = post("/updateMitarbeiter")
                .content(asJsonString(mitarbeiter)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isNotFound());
    }

    @Test
    public void test_updateMitarbeiterVertrieb_Expected_newValue() throws Exception {
        MitarbeiterVertrieb mitarbeiterVertrieb = mitarbeiterVertriebRepository.findById(Integer.valueOf(1)).get();
        String doppelname = mitarbeiterVertrieb.getVorname() + " Hans";
        mitarbeiterVertrieb.setVorname(doppelname);

        MockHttpServletRequestBuilder requestBuilder = post("/updateMitarbeiterVertrieb")
                .content(asJsonString(mitarbeiterVertrieb)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isOk());

        MitarbeiterVertrieb mitarbeiterVertriebNeu = mitarbeiterVertriebRepository.findById(Integer.valueOf(1)).get();
        assertThat(mitarbeiterVertriebNeu.getVorname()).isEqualTo(doppelname);
    }

    @Test
    public void test_updateMitarbeiterVertriebId_Expected_Status404() throws Exception {
        MitarbeiterVertrieb mitarbeiterVertrieb = mitarbeiterVertriebRepository.findById(Integer.valueOf(1)).get();
        mitarbeiterVertrieb.setId(Integer.valueOf(99));

        MockHttpServletRequestBuilder requestBuilder = post("/updateMitarbeiterVertrieb")
                .content(asJsonString(mitarbeiterVertrieb)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isNotFound());
    }

    public EinsatzDTO convertFromEntity(Einsatz einsatz) {
        ModelMapper modelMapper = new ModelMapper();
        EinsatzDTO einsatzDTO = modelMapper.map(einsatz, EinsatzDTO.class);
        if (einsatz.getMitarbeiter() != null)
        {
            einsatzDTO.setMitarbeiterId(einsatz.getMitarbeiter().getId());
        }
        if (einsatz.getMitarbeiterVertrieb() != null)
        {
            einsatzDTO.setMitarbeiterVertriebId(einsatz.getMitarbeiterVertrieb().getId());
        }
        return einsatzDTO;
    }

    @Test
    public void test_updateEinsatz_Expected_newValue() throws Exception {
        Einsatz einsatz = einsatzRepository.findById(Integer.valueOf(1)).get();
        double neuerStundensatzVK = einsatz.getStundensatzVK()*2;
        EinsatzDTO neuerEinsatz = convertFromEntity(einsatz);
        neuerEinsatz.setStundensatzVK(neuerStundensatzVK);

        MockHttpServletRequestBuilder requestBuilder = post("/updateEinsatz")
                .content(asJsonString(neuerEinsatz)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isOk());

        Einsatz einsatzNeu = einsatzRepository.findById(Integer.valueOf(1)).get();
        assertThat(einsatzNeu.getStundensatzVK()).isEqualTo(neuerStundensatzVK);
    }

    @Test
    public void test_updateEinsatzId_Expected_Status404() throws Exception {
        Einsatz einsatz = einsatzRepository.findById(Integer.valueOf(1)).get();
        EinsatzDTO neuerEinsatz = convertFromEntity(einsatz);
        neuerEinsatz.setId(Integer.valueOf(99));

        MockHttpServletRequestBuilder requestBuilder = post("/updateEinsatz")
                .content(asJsonString(neuerEinsatz)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isNotFound());
    }
}
