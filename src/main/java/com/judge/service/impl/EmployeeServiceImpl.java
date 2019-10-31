package com.judge.service.impl;

import com.judge.model.Department;
import com.judge.model.Employee;
import com.judge.model.EmployeeForm;
import com.judge.repository.EmployeeRepository;
import com.judge.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Page<Employee> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public Page<Employee> findAllByFirstNameContaining(String firstName, Pageable pageable) {
        return employeeRepository.findAllByFirstNameContaining(firstName, pageable);
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findOne(id);
    }

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void remove(Long id) {
        employeeRepository.delete(id);
    }

    @Override
    public Iterable<Employee> findALlByDepartment(Department department) {
        return employeeRepository.findAllByDepartment(department);
    }

    @Override
    public void edit(EmployeeForm employeeForm , String image) {

        Employee employeeObj=employeeRepository.findOne(employeeForm.getId());
        employeeObj.setAddress(employeeForm.getAddress());
        employeeObj.setBirthDate(employeeForm.getBirthDate());
        employeeObj.setFirstName(employeeForm.getFirstName());
        employeeObj.setLastName(employeeForm.getLastName());
        employeeObj.setDepartment(employeeForm.getDepartment());
        employeeObj.setSalary(employeeForm.getSalary());
        employeeObj.setImage(image);
        //employeeObj.setId(employeeForm.getId());
        employeeRepository.save(employeeObj);
    }
}
