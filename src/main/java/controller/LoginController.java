package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Employee;
import service.EmployeeService;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    EmployeeService employeeService;

    @RequestMapping("login")
    public String loginPage()
    {
        return "login/login";
    }
    @RequestMapping("logint")
    public String logint(Employee employee, HttpSession session)
    {
        Employee employee1=employeeService.login(employee.getNumber(),employee.getPassword());
        if(employee1==null)
            return "redirect:login";
        session.setAttribute("user",employee1);
        return "redirect:/";
    }
    @RequestMapping("loginJson")
    @ResponseBody
    public String loginJson(Employee employee)
    {
        if(employeeService.login(employee.getNumber(),employee.getPassword())!=null)
            return "success";

        return "fail";
    }
    @RequestMapping("password")
    public String forgetPassword()
    {
        return "login/forgetPassword";
    }

    @RequestMapping("logout")
    public String logout(HttpSession session)
    {
        session.removeAttribute("user");
        return "redirect:login";
    }
}
