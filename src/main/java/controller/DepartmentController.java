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

import javax.servlet.http.HttpSession;
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

    @RequestMapping("admin")
    public String index()
    {
        return "test";
    }

    @RequestMapping("department")
    public String list(Model model,HttpSession session)
    {
        int eid =(int)session.getAttribute("user");
        model.addAttribute("tree",departmentService.getTreeJson(eid));
        return "department";
    }

    @RequestMapping("departmentAdd")
    @ResponseBody
    public String add(Model model,HttpSession session,Department department)
    {
        departmentService.add(department);
        return "true";
    }
    @RequestMapping("departmentDelete")
    @ResponseBody
    public String delete(int id)
    {
        departmentService.delete(id);
        return "true";
    }

}
