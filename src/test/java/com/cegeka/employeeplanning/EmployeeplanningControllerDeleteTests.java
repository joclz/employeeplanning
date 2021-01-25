package com.cegeka.employeeplanning;

import static com.cegeka.employeeplanning.util.EmployeeplanningUtil.TEST_IMPORT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cegeka.employeeplanning.repositories.EinsatzRepository;
import com.cegeka.employeeplanning.repositories.MitarbeiterRepository;
import com.cegeka.employeeplanning.repositories.MitarbeiterVertriebRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Sql(TEST_IMPORT)
public class EmployeeplanningControllerDeleteTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;
    @Autowired
    private MitarbeiterVertriebRepository mitarbeiterVertriebRepository;
    @Autowired
    private EinsatzRepository einsatzRepository;

    @Test
    public void test_deleteMitarbeiter1_Expected_MitarbeiterCount6_reduziert_EinsatzCount8() throws Exception {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("mitarbeiterId", "1");
        MockHttpServletRequestBuilder requestBuilder = post("/deleteMitarbeiter").params(multiValueMap);
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isOk());
        assertThat(mitarbeiterRepository.count()).isEqualTo(6);
        assertThat(einsatzRepository.count()).isEqualTo(8);
    }

    @Test
    public void test_deleteMitarbeiter99_Expected_Status404() throws Exception {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("mitarbeiterId", "99");
        MockHttpServletRequestBuilder requestBuilder = post("/deleteMitarbeiter").params(multiValueMap);
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isNotFound());
    }

    @Test
    public void test_deleteMitarbeiterVertrieb1_Expected_MitarbeiterVertriebCount2_EinsatzCount4() throws Exception {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("mitarbeiterVertriebId", "1");
        MockHttpServletRequestBuilder requestBuilder = post("/deleteMitarbeiterVertrieb").params(multiValueMap);
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isOk());
        assertThat(mitarbeiterVertriebRepository.count()).isEqualTo(2);
        assertThat(einsatzRepository.count()).isEqualTo(4);
    }

    @Test
    public void test_deleteMitarbeiterVertrieb99_Expected_Status404() throws Exception {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("mitarbeiterVertriebId", "99");
        MockHttpServletRequestBuilder requestBuilder = post("/deleteMitarbeiterVertrieb").params(multiValueMap);
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isNotFound());
    }

    @Test
    public void test_deleteEinsatz2_Expected_EinsatzCount8() throws Exception {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("einsatzId", "2");
        MockHttpServletRequestBuilder requestBuilder = post("/deleteEinsatz").params(multiValueMap);
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isOk());
        assertThat(einsatzRepository.count()).isEqualTo(8);
    }

    @Test
    public void test_deleteEinsatz99_Expected_Status404() throws Exception {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("einsatzId", "99");
        MockHttpServletRequestBuilder requestBuilder = post("/deleteEinsatz").params(multiValueMap);
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isNotFound());
    }
}
