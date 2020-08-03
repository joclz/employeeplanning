package com.cegeka.employeeplanning;

import com.cegeka.employeeplanning.data.EinsatzRepository;
import com.cegeka.employeeplanning.data.Mitarbeiter;
import com.cegeka.employeeplanning.data.MitarbeiterRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private EinsatzRepository einsatzRepository ;

    @Test
    public void test001_listMitarbeiter_Expected_CorrectValuesAndNumber5() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/listMitarbeiter");
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isOk());
        perform.andExpect(content().string(
                "[{\"id\":1,\"mitarbeiterStatus\":\"ANGESTELLT\",\"stundensatzEK\":30.0,\"name\":\"Mustermann\",\"vorname\":\"Max\",\"mitarbeiterUnit\":\"FACTORY_MUENCHEN\"}," +
                        "{\"id\":2,\"mitarbeiterStatus\":\"SUBUNTERNEHMER\",\"stundensatzEK\":50.0,\"name\":\"Stoteles\",\"vorname\":\"Ari\",\"mitarbeiterUnit\":\"FACTORY_MUENCHEN\"}," +
                        "{\"id\":3,\"mitarbeiterStatus\":\"ANGESTELLT\",\"stundensatzEK\":36.0,\"name\":\"LÃ¼hse\",\"vorname\":\"Anna\",\"mitarbeiterUnit\":\"FACTORY_MUENCHEN\"}," +
                        "{\"id\":4,\"mitarbeiterStatus\":\"ANGESTELLT\",\"stundensatzEK\":35.0,\"name\":\"MÃ¼ller\",\"vorname\":\"Werner\",\"mitarbeiterUnit\":\"FACTORY_NUERNBERG\"}," +
                        "{\"id\":5,\"mitarbeiterStatus\":\"SUBUNTERNEHMER\",\"stundensatzEK\":55.0,\"name\":\"Schulz\",\"vorname\":\"Renate\",\"mitarbeiterUnit\":\"FACTORY_NUERNBERG\"}]"));
        assertThat(mitarbeiterRepository.count()).isEqualTo(5);
    }

    @Test
    public void test002_addMitarbeiterAndlistMitarbeiter_Expected_CorrectValuesAndNumber6() throws Exception {
        Mitarbeiter mitarbeiter = new Mitarbeiter();
        mitarbeiterRepository.save(mitarbeiter);
        MockHttpServletRequestBuilder requestBuilder = get("/listMitarbeiter");
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isOk());
        assertThat(mitarbeiterRepository.count()).isEqualTo(6);
    }

    @Test
    public void test002_listEinsaetze_Expected_CorrectValuesAndNumber2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/listEinsaetze");
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isOk());
        perform.andExpect(content().string(
                "[{\"id\":1,\"" +
                        "mitarbeiter\":{\"id\":1,\"mitarbeiterStatus\":\"ANGESTELLT\",\"stundensatzEK\":30.0,\"name\":\"Mustermann\",\"vorname\":\"Max\",\"mitarbeiterUnit\":\"FACTORY_MUENCHEN\"},\"" +
                        "mitarbeiterVertrieb\":{\"id\":1,\"name\":\"GÃ¼nzkofer\",\"vorname\":\"Werner\"},\"" +
                        "einsatzStatus\":\"ANGEBOTEN\",\"beginn\":\"2020-08-31T22:00:00.000+00:00\",\"ende\":\"2020-12-30T23:00:00.000+00:00\",\"wahrscheinlichkeit\":50,\"zusatzkostenReise\"" + ":35.0,\"" +
                        "stundensatzVK\":26.0,\"projektnummerNettime\":\"ProjektNr1\",\"beauftragungsnummer\":\"BeaufNr1\",\"deckungsbeitrag\":10.0,\"marge\":11.0}," +
                        "{\"id\":2,\"" +
                        "mitarbeiter\":{\"id\":2,\"mitarbeiterStatus\":\"SUBUNTERNEHMER\",\"stundensatzEK\":50.0,\"name\":\"Stoteles\",\"vorname\":\"Ari\",\"mitarbeiterUnit\":\"FACTORY_MUENCHEN\"},\"" +
                        "mitarbeiterVertrieb\":{\"id\":2,\"name\":\"WÃ¼st\",\"vorname\":\"JÃ¼rgen\"},\"" +
                        "einsatzStatus\":\"ANGEBOTEN\",\"beginn\":\"2020-10-01T22:00:00.000+00:00\",\"ende\":\"2021-03-30T22:00:00.000+00:00\",\"wahrscheinlichkeit\":75,\"zusatzkostenReise\"" + ":36.0,\"" +
                        "stundensatzVK\":27.0,\"projektnummerNettime\":\"ProjektNr2\",\"beauftragungsnummer\":\"BeaufNr2\",\"deckungsbeitrag\":12.0,\"marge\":13.0}]"));
        assertThat(einsatzRepository.count()).isEqualTo(2);
    }
}
