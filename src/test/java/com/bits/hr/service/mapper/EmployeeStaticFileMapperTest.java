package com.bits.hr.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeeStaticFileMapperTest {

    private EmployeeStaticFileMapper employeeStaticFileMapper;

    @BeforeEach
    public void setUp() {
        employeeStaticFileMapper = new EmployeeStaticFileMapperImpl();
    }
}