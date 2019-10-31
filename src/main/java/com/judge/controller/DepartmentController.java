package com.judge.controller;

import com.judge.model.Department;
import com.judge.model.Employee;
import com.judge.service.DepartmentService;
import com.judge.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DepartmentController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/view-department/{id}")
    public ModelAndView viewDepartment(@PathVariable("id") Long id){
        Department department=departmentService.findById(id);
        Iterable<Employee> employees=employeeService.findALlByDepartment(department);
        ModelAndView modelAndView=new ModelAndView("/department/view");
        modelAndView.addObject("department",department);
        modelAndView.addObject("employee",employees);
        return modelAndView;
    }

    @GetMapping("/department")
    public ModelAndView listDepartment(){
        Iterable<Department> departments=departmentService.findAll();
        ModelAndView modelAndView=new ModelAndView("/department/list");
        modelAndView.addObject("department",departments);
        return modelAndView;
    }

    @GetMapping("/create-department")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView=new ModelAndView("/department/create");
        modelAndView.addObject("department", new Department());
        return modelAndView;
    }

    @PostMapping("/create-department")
    public ModelAndView saveDepartment(@ModelAttribute("department") Department department){
        departmentService.save(department);
        ModelAndView modelAndView=new ModelAndView("/department/create");
        modelAndView.addObject("department",new Department());
        modelAndView.addObject("message","New added");
        return modelAndView;
    }

    @GetMapping("/edit-department/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Department department=departmentService.findById(id);
        ModelAndView modelAndView=new ModelAndView("/department/edit");
        modelAndView.addObject("department",department);
        return modelAndView;
    }

    @PostMapping("/edit-department")
    public ModelAndView updateDepartment(@ModelAttribute("department") Department department){
        departmentService.save(department);
        ModelAndView modelAndView=new ModelAndView("/department/edit");
        modelAndView.addObject("department",department);
        modelAndView.addObject("message","new edited");
        return modelAndView;
    }
    @GetMapping("/delete-department/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Department department=departmentService.findById(id);
        ModelAndView modelAndView=new ModelAndView("/department/delete");
        modelAndView.addObject("department",department);
        return modelAndView;
    }
    @PostMapping("/delete-department")
    public String deleteDepartment(@ModelAttribute("department") Department department){
        departmentService.remove(department.getId());
        return "redirect:department";
    }


}
