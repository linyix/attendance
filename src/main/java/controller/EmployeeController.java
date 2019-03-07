package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Employee;
import pojo.EmployeeExample;
import service.EmployeeService;

import java.util.List;
@RequestMapping("employee")
@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


    @RequestMapping(value="")
    public String list(Model model)
    {
        List<Employee> employees = employeeService.list();
        model.addAttribute("employees",employees);
        return "employees";

    }


    //todo 权限 isdeleted
    @RequestMapping(value="",method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(Model model)
    {
        List<Employee> employees = employeeService.list();
        model.addAttribute("employees",employees);
        return "success";

    }


    //todo
    @RequestMapping(value = "search")
    public String search(Model model)
    {
        List<Employee> employees = employeeService.list();
        model.addAttribute("employees",employees);
        return "employees";
    }
}
