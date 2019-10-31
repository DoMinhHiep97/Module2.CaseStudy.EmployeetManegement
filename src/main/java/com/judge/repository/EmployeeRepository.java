package com.judge.repository;

import com.judge.model.Department;
import com.judge.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;




public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
    Iterable<Employee> findAllByDepartment(Department department);

    Page<Employee> findAllByFirstNameContaining(String firstName, Pageable pageable);

}
