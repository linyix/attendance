package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Employee;
import pojo.EmployeeExample;
import service.EmployeeService;

import java.util.List;

@Controller("employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value="121")
    @ResponseBody
    public String test()
    {
        Employee employee = employeeService.get(1);
        System.out.println(employee.getName());

        return employee.getName();
    }

    @RequestMapping(value="")
    public String list(Model model)
    {
        List<Employee> employees = employeeService.list();
        model.addAttribute("employees",employees);
        return "employees";

    }
}
