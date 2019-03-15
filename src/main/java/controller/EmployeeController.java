package controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Department;
import pojo.Employee;
import pojo.EmployeeExample;
import pojo.ViewObject;
import service.DepartmentService;
import service.EmployeeService;


import java.util.ArrayList;
import java.util.List;
@RequestMapping("employee")
@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    DepartmentService departmentService;

    @RequestMapping(value="")
    public String list(Model model)
    {
        List<Department> departments = departmentService.list();
        model.addAttribute("departments",departments);
        List<ViewObject> vos = new ArrayList<>();
        List<Employee> employees = employeeService.list();
        for(Employee e : employees)
        {
            ViewObject vo = new ViewObject();
            vo.set("employee",e);
            vo.set("department",departmentService.get(e.getDepartmentId()));
            vos.add(vo);
        }
        model.addAttribute("vos",vos);
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

    @RequestMapping(value="json/{id}")
    @ResponseBody
    public String getOneJson(@PathVariable("id") int id)
    {
        JSONObject json = new JSONObject();
        json.put("employee", JSONObject.toJSON(employeeService.get(id)));
        return json.toJSONString();
    }
}
