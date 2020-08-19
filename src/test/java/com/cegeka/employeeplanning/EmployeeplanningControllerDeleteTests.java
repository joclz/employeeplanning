package com.cegeka.employeeplanning;

import com.cegeka.employeeplanning.data.EinsatzRepository;
import com.cegeka.employeeplanning.data.MitarbeiterRepository;
import com.cegeka.employeeplanning.data.MitarbeiterVertriebRepository;
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

import static com.cegeka.employeeplanning.util.EmployeeplanningUtil.TEST_IMPORT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
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
    public void test_deleteMitarbeiter1_Expected_MitarbeiterCount6_reduziert_EinsatzCount7() throws Exception {
        MultiValueMap multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("mitarbeiterId", "1");
        MockHttpServletRequestBuilder requestBuilder = post("/deleteMitarbeiter").params(multiValueMap);
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isOk());
        assertThat(mitarbeiterRepository.count()).isEqualTo(6);
        assertThat(einsatzRepository.count()).isEqualTo(7);
    }

    @Test
    public void test_deleteMitarbeiterVertrieb1_Expected_MitarbeiterVertriebCount1_EinsatzCount4() throws Exception {
        MultiValueMap multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("mitarbeiterVertriebId", "1");
        MockHttpServletRequestBuilder requestBuilder = post("/deleteMitarbeiterVertrieb").params(multiValueMap);
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isOk());
        assertThat(mitarbeiterVertriebRepository.count()).isEqualTo(1);
        assertThat(einsatzRepository.count()).isEqualTo(4);
    }

    @Test
    public void test_deleteEinsatz2_Expected_EinsatzCount7() throws Exception {
        MultiValueMap multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("einsatzId", "2");
        MockHttpServletRequestBuilder requestBuilder = post("/deleteEinsatz").params(multiValueMap);
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isOk());
        assertThat(einsatzRepository.count()).isEqualTo(7);
    }
}
