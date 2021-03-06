package com.cegeka.employeeplanning;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import com.cegeka.employeeplanning.controller.EmployeeplanningController;

@SpringBootTest
class EmployeeplanningApplicationTests {

    @Autowired
    EmployeeplanningController employeeplanningController;

    @Test
    void contextLoads() {
        assertThat(employeeplanningController).isNotNull();
    }
}
