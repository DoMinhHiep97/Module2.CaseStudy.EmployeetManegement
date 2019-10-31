package com.judge.service;

import com.judge.model.Department;
import com.judge.model.Employee;
import com.judge.model.EmployeeForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

    Page<Employee> findAll(Pageable pageable);

    Page<Employee> findAllByFirstNameContaining(String firstName, Pageable pageable);



    Employee findById(Long id);

    void save(Employee employee);

    void remove(Long id);

    Iterable<Employee> findALlByDepartment(Department department);

    void edit(EmployeeForm employeeForm , String image);
}
