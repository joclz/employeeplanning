package com.cegeka.employeeplanning;

import com.cegeka.employeeplanning.data.EinsatzRepository;
import com.cegeka.employeeplanning.data.MitarbeiterRepository;
import com.cegeka.employeeplanning.data.MitarbeiterVertriebRepository;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    public void test_200_deleteMitarbeiter1_Expected_MitarbeiterCount5_EinsatzCount7() throws Exception {
        MultiValueMap multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("mitarbeiterId", "1");
        MockHttpServletRequestBuilder requestBuilder = post("/deleteMitarbeiter").params(multiValueMap);
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isOk());
        assertThat(mitarbeiterRepository.count()).isEqualTo(5);
        assertThat(einsatzRepository.count()).isEqualTo(7);
    }

    @Test
    public void test_210_deleteMitarbeiterVertrieb1_Expected_MitarbeiterVertriebCount1_EinsatzCount4() throws Exception {
        MultiValueMap multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("mitarbeiterVertriebId", "1");
        MockHttpServletRequestBuilder requestBuilder = post("/deleteMitarbeiterVertrieb").params(multiValueMap);
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isOk());
        assertThat(mitarbeiterVertriebRepository.count()).isEqualTo(1);
        assertThat(einsatzRepository.count()).isEqualTo(4);
    }

    @Test
    public void test_220_deleteEinsatz2_Expected_EinsatzCount3() throws Exception {
        MultiValueMap multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("einsatzId", "2");
        MockHttpServletRequestBuilder requestBuilder = post("/deleteEinsatz").params(multiValueMap);
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        perform.andExpect(status().isOk());
        assertThat(einsatzRepository.count()).isEqualTo(3);
    }
}
