package com.bits.hr.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SalaryGeneratorMasterMapperTest {

    private SalaryGeneratorMasterMapper salaryGeneratorMasterMapper;

    @BeforeEach
    public void setUp() {
        salaryGeneratorMasterMapper = new SalaryGeneratorMasterMapperImpl();
    }
}
