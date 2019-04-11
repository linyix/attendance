package controller;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Employee;
import pojo.GooutCheck;
import service.EmployeeService;
import service.GooutCheckService;
import service.GooutService;

@Controller
public class GooutCheckController {

    @Autowired
    GooutCheckService GooutCheckService;
    @Autowired
    GooutService GooutService;
    @Autowired
    EmployeeService employeeService;
    @RequestMapping("Goout/{lid}/Gooutcheck/json")
    @ResponseBody
    public String getGooutCheckJson(@PathVariable("lid") int lid)
    {
        GooutCheck GooutCheck = GooutCheckService.getByGooutId(lid);
        if(GooutCheck==null)
            return "notexist";
        Employee employee = employeeService.get(GooutCheck.getEmployeeId());
        JSONObject json = new JSONObject();
        json.put("Gooutcheck", JSONObject.toJSON(GooutCheck));
        json.put("employee", JSONObject.toJSON(employee));
        return json.toJSONString();
    }
}
