package com.judge.service;


import com.judge.model.Department;
import com.judge.model.Employee;

public interface DepartmentService {
   Iterable<Department> findAll();

   Department findById(Long id);

   void save(Department department);

   void remove(Long id);
}
