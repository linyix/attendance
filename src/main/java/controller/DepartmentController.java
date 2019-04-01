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
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Department;
import pojo.Employee;
import pojo.Manage;
import service.DepartmentService;
import service.EmployeeService;
import service.ManageService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @Autowired
    ManageService manageService;
    @Autowired
    EmployeeService employeeService;

    @RequestMapping("test")
    public String test()
    {
        return "test";
    }

    @RequestMapping("department")
    public String list(Model model)
    {
        //todo getuser
        int eid =1;
        //todo level
        List<Manage> manages = manageService.getByEid(eid);
        List<Department> departments = new ArrayList<>();
        for(Manage manage:manages)
        {
            departments.add(departmentService.get(manage.getDepartmentId()));
        }
        JSONArray jsonArray = departmentService.getTree(departments);
        model.addAttribute("tree",jsonArray.toJSONString());
        System.out.println(jsonArray.toJSONString());

        return "department";
    }
    @RequestMapping(value ="department/{did}/employee" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getEmployeeByDepartment(@PathVariable("did") int did ,int page, int limit)
    {
        if(limit ==0)
            limit=10;
        PageHelper.offsetPage(0, limit);

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

}
