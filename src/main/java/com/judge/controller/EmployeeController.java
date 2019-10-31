package com.judge.controller;

import com.judge.model.Department;
import com.judge.model.Employee;
import com.judge.model.EmployeeForm;
import com.judge.service.DepartmentService;
import com.judge.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
@PropertySource("classpath:global_config_app.properties")
public class EmployeeController {
    @Autowired
    Environment env;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @ModelAttribute("department")
    public Iterable<Department> departments(){
        return departmentService.findAll();
    }
    @GetMapping("/list")
    public ModelAndView listEmployee(@RequestParam("s")Optional<String> s,@PageableDefault(value = 3,sort = "salary") Pageable pageable){
        Page<Employee> employees;
        if(s.isPresent()){
            employees=employeeService.findAllByFirstNameContaining(s.get(), pageable);
        }else {
            employees=employeeService.findAll(pageable);
        }

        ModelAndView modelAndView=new ModelAndView("/employee/list");
        modelAndView.addObject("employee", employees);
        return modelAndView;
    }

    @GetMapping("/create-employee")
    public ModelAndView createForm(){
        ModelAndView modelAndView=new ModelAndView("/employee/create");
        modelAndView.addObject("employeeForm", new EmployeeForm());
        return modelAndView;
    }

    @PostMapping("/save-employee")
    public ModelAndView saveEmployee(@Validated @ModelAttribute("employeeForm") EmployeeForm employeeForm, BindingResult result){
        if (result.hasErrors()){
            ModelAndView modelAndView=new ModelAndView("/employee/create");
            modelAndView.addObject("employees", new EmployeeForm());
            return modelAndView;
        }

        MultipartFile multipartFile=employeeForm.getImage();
        String fileName= multipartFile.getOriginalFilename();
        String fileUpload=env.getProperty("file_upload").toString();

        try{
            FileCopyUtils.copy(employeeForm.getImage().getBytes(),new File(fileUpload+fileName));
        }catch (IOException ex){
            ex.printStackTrace();
        }
        Employee employeeObj=new Employee(employeeForm.getFirstName(),employeeForm.getLastName(),
                employeeForm.getBirthDate(),employeeForm.getAddress(),employeeForm.getSalary(),
                fileName,employeeForm.getDepartment());
        employeeService.save(employeeObj);

        ModelAndView modelAndView=new ModelAndView("/employee/create");
        modelAndView.addObject("employee", new EmployeeForm());
        modelAndView.addObject("message","ok");
        return modelAndView;
    }

    @GetMapping("/edit-employee/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Employee employee=employeeService.findById(id);
            ModelAndView modelAndView=new ModelAndView("/employee/edit");
            modelAndView.addObject("employees", employee);
        return modelAndView;
    }
    @PostMapping("/edit-employee")
    public ModelAndView updateEmployee(@ModelAttribute("employees") EmployeeForm employee){


        MultipartFile multipartFile=employee.getImage();
        String fileName= multipartFile.getOriginalFilename();
        String fileUpload=env.getProperty("file_upload").toString();


        try{
            FileCopyUtils.copy(employee.getImage().getBytes(),new File(fileUpload+fileName));
        }catch (IOException ex){
            ex.printStackTrace();
        }

//        Employee employeeObj=employeeService.findById(employee.getId());
//        employeeObj.setAddress(employee.getAddress());
//        employeeObj.setBirthDate(employee.getBirthDate());
//        employeeObj.setFirstName(employee.getFirstName());
//        employeeObj.setLastName(employee.getLastName());
//        employeeObj.setDepartment(employee.getDepartment());
//        employeeObj.setSalary(employee.getSalary());
//        employeeObj.setImage(fileName);
//        employeeObj.setId(employee.getId());
//        employeeService.save(employeeObj);

        employeeService.edit(employee,fileName);

        ModelAndView modelAndView=new ModelAndView("/employee/edit");
        modelAndView.addObject("employee", new EmployeeForm());
        modelAndView.addObject("message","ok");
        return modelAndView;
    }

    @GetMapping("/delete-employee/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Employee employee=employeeService.findById(id);
            ModelAndView modelAndView = new ModelAndView("/employee/delete");
            modelAndView.addObject("employee", employee);
            return modelAndView;

    }

    @PostMapping("/delete-employee")
    public String deleteDelete(@ModelAttribute("employee") Employee employee){
        employeeService.remove(employee.getId());
        return "redirect:list";
    }

}
