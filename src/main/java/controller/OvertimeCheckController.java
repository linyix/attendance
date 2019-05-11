package controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Employee;
import pojo.GooutCheck;
import pojo.Overtime;
import pojo.OvertimeCheck;
import service.EmployeeService;
import service.OvertimeCheckService;
import service.OvertimeService;

@Controller
public class OvertimeCheckController {

    @Autowired
    OvertimeService overtimeService;
    @Autowired
    OvertimeCheckService overtimeCheckService;
    @Autowired
    EmployeeService employeeService;
    @RequestMapping("Goout/{lid}/Gooutcheck/json")
    @ResponseBody
    public String getGooutCheckJson(@PathVariable("lid") int lid)
    {
        Overtime overtime = overtimeService.get(lid);
        if(overtime==null)
            return "notexist";
        Employee employee = employeeService.get(overtime.getEmployeeId());
        JSONObject json = new JSONObject();
        json.put("Gooutcheck", JSONObject.toJSON(overtimeCheckService));
        json.put("employee", JSONObject.toJSON(employee));
        return json.toJSONString();
    }
}
