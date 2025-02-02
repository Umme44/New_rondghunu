package com.bits.hr.service.mapper;

import com.bits.hr.domain.*;
import com.bits.hr.service.dto.PfArrearDTO;
import org.mapstruct.*;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity {@link PfArrear} and its DTO {@link PfArrearDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { EmployeeMapper.class })
public interface PfArrearMapper extends EntityMapper<PfArrearDTO, PfArrear> {
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.pin", target = "pin")
    @Mapping(source = "employee.fullName", target = "fullName")
    @Mapping(source = "employee.designation.designationName", target = "designationName")
    @Mapping(source = "employee.department.departmentName", target = "departmentName")
    @Mapping(source = "employee.unit.unitName", target = "unitName")
    PfArrearDTO toDto(PfArrear pfArrear);

    @Mapping(source = "employeeId", target = "employee")
    PfArrear toEntity(PfArrearDTO pfArrearDTO);

    default PfArrear fromId(Long id) {
        if (id == null) {
            return null;
        }
        PfArrear pfArrear = new PfArrear();
        pfArrear.setId(id);
        return pfArrear;
    }
}
