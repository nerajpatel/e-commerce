package net.javaguides.departmentservice.service;

import net.javaguides.departmentservice.dto.DepartmentDto;

public interface DepartmentService {
    DepartmentDto create(DepartmentDto departmentDto);

    DepartmentDto findByDepartmentCode(String departmentCode);
}
