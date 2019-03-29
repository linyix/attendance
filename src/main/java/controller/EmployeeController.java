package controller;

import com.alibaba.fastjson.JSONArray;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("employee")
@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    DepartmentService departmentService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String list(Model model) {
        List<Department> departments = departmentService.list();
        model.addAttribute("departments", departments);
        List<ViewObject> vos = new ArrayList<>();
        List<Employee> employees = employeeService.list();
        for (Employee e : employees) {
            ViewObject vo = new ViewObject();
            vo.set("employee", e);
            vo.set("department", departmentService.get(e.getDepartmentId()));
            vos.add(vo);
        }
        model.addAttribute("vos", vos);
        return "employees";

    }
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public String add(Employee employee)
    {
        List<Employee> employees = employeeService.getByNumber(employee.getNumber());
        if(employees.size()!=0)
            return "工号重复";
        employeeService.add(employee);
        return "success";
    }


    @RequestMapping(value = "{eid}", method = RequestMethod.POST)
    @ResponseBody
    public String update(Employee employee)
    {
        List<Employee> employees = employeeService.getByNumber(employee.getNumber());
        if(employees.size()!=0)
        {
            if(employees.size()==1)
            {
                if(employees.get(0).getId()!=employee.getId())
                    return "工号重复";
            }
            else
                return "工号重复";
        }
        employeeService.update(employee);
        return "successf";
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

    @RequestMapping(value="jsona" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getOneJson(int page,int limit)
    {
        JSONArray jsonArray = new JSONArray();
        for(int i=0;i<10;i++)
        {
            Map<String,Object > map = new HashMap<>();
            map.put("id",i+1);
            map.put("number",i);
            map.put("username","啦啦啦");
            map.put("telephones","121231");
            jsonArray.add(map);
        }

        JSONObject json = new JSONObject();
        json.put("code",0);
        json.put("msg","");
        json.put("count",300);
        json.put("data", jsonArray);
        System.out.println(json.toJSONString());
        return json.toJSONString();
    }
}
