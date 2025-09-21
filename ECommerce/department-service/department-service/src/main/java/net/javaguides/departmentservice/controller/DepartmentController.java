package net.javaguides.departmentservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.javaguides.departmentservice.dto.DepartmentDto;
import net.javaguides.departmentservice.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Department Controller",
        description = "Department Controller exposes Rest APIs for Department Service"
)
@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Operation(
            summary = "Create Department Rest API",
            description = "Create Department API is used to save department object in database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 created"
    )
    @PostMapping("create")
    public ResponseEntity<DepartmentDto> create(@RequestBody DepartmentDto departmentDto) {
        DepartmentDto response = departmentService.create(departmentDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Department Rest API",
            description = "Get Department API is used to get department object from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success"
    )
    @GetMapping("findByDepartmentCode")
    public ResponseEntity<DepartmentDto> findByDepartmentCode(@RequestParam String departmentCode) {
        DepartmentDto response = departmentService.findByDepartmentCode(departmentCode);
        return ResponseEntity.ok(response);
    }
}
