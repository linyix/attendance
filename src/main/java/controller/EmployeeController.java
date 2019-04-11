package controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Department;
import pojo.Employee;
import service.DepartmentService;
import service.EmployeeService;


import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    DepartmentService departmentService;
    @RequestMapping(value = "employee",method = RequestMethod.POST)
    @ResponseBody
    public String add(Employee employee)
    {
        List<Employee> employees = employeeService.getByNumber(employee.getNumber());
        if(employees.size()!=0)
            return "工号重复";
        employeeService.add(employee);
        return "success";
    }


    @RequestMapping(value = "employee/{eid}", method = RequestMethod.POST)
    @ResponseBody
    public String update(Employee employee)
    {
        List<Employee> employees = employeeService.getByNumber(employee.getNumber());
        if(employees.size()!=0)
        {
            if(employees.size()==1)
            {
                if(employees.get(0).getId()!=employee.getId())
                    return "该工号已被使用";
            }
            else
                return "该工号已被使用";
        }
        employeeService.update(employee);
        return "successf";
    }

    //todo 权限 isdeleted
    @RequestMapping(value="employee",method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(Model model)
    {
        List<Employee> employees = employeeService.list();
        model.addAttribute("employees",employees);
        return "success";

    }


    //todo
    @RequestMapping(value = "employee/search")
    public String search(Model model)
    {
        List<Employee> employees = employeeService.list();
        model.addAttribute("employees",employees);
        return "todelete/employees2";
    }

    @RequestMapping(value="employee/json/{id}")
    @ResponseBody
    public String getOneJson(@PathVariable("id") int id)
    {
        JSONObject json = new JSONObject();
        json.put("employee", JSONObject.toJSON(employeeService.get(id)));
        return json.toJSONString();
    }
    @RequestMapping(value ="department/{did}/employee" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getEmployeeByDepartment(@PathVariable("did") int did ,int page, int limit)
    {
        if(limit ==0)
            limit=10;
        PageHelper.offsetPage(page-1, limit);

        JSONArray jsonArray = new JSONArray();
        List<Employee> employees = employeeService.listByDepartmentId(did);
        for(Employee employee:employees)
        {
            Map<String,Object > map = new HashMap<>();
            map.put("id",employee.getId());
            map.put("number",employee.getNumber());
            map.put("name",employee.getName());
            map.put("sex",employee.getSex()==0?"男":"女");
            jsonArray.add(map);
        }


        JSONObject json = new JSONObject();
        json.put("code",0);
        json.put("msg","");
        json.put("count",(int) new PageInfo<>(employees).getTotal());
        json.put("data", jsonArray);
        System.out.println(json.toJSONString());
        return json.toJSONString();
    }

    @RequestMapping(value="numberalone")
    @ResponseBody
    public String numberalone(String number)
    {
        if(employeeService.getByNumber(number).size()==0)
            return "success";
        return "fail";
    }

    @RequestMapping(value="employee",method = RequestMethod.GET)
    public String tree(Model model)
    {
        //todo getuser
        int eid =1;
        //todo level

        model.addAttribute("tree",departmentService.getTreeJson(eid));


        return "employees";
    }

    //个人信息R
    @RequestMapping(value="myinfo",method = RequestMethod.GET)
    public String myinfo(Model model, HttpSession session)
    {
        int eid =((Employee)session.getAttribute("user")).getId();
        Employee e = employeeService.get(eid);
        Department department = departmentService.get(e.getDepartmentId());
        model.addAttribute("d",department);
        model.addAttribute("e",e);
        return "myinfo";
    }

    //个人信息A
    @RequestMapping(value="myinfo",method = RequestMethod.POST)
    @ResponseBody
    public String myinfoAdd(String email,String telephone,HttpSession session)
    {
        int eid =((Employee)session.getAttribute("user")).getId();
        Employee e = new Employee();
        e.setId(eid);

        e.setEmail(email);
        e.setTelephone(telephone);

        employeeService.updateSelective(e);
        return "success";
    }
}
