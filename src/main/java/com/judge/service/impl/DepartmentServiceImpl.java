package com.judge.service.impl;

import com.judge.model.Department;
import com.judge.model.Employee;
import com.judge.repository.DepartmentRepository;
import com.judge.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;

public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Iterable<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department findById(Long id) {
        return departmentRepository.findOne(id);
    }

    @Override
    public void save(Department department) {
        departmentRepository.save(department);
    }

    @Override
    public void remove(Long id) {
        departmentRepository.delete(id);
    }
}
