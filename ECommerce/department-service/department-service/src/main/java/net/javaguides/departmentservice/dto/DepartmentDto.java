package net.javaguides.departmentservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "DepartmentDto Model Information"
)
public record DepartmentDto(Long id, String departmentName, String departmentDescription, String departmentCode) {
}
