package controller;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Employee;
import pojo.LeaveeCheck;
import service.EmployeeService;
import service.LeaveeCheckService;
import service.LeaveeService;

@Controller
public class LeaveeCheckController {

    @Autowired
    LeaveeCheckService leaveeCheckService;
    @Autowired
    LeaveeService leaveeService;
    @Autowired
    EmployeeService employeeService;
    @RequestMapping("leavee/{lid}/leaveecheck/json")
    @ResponseBody
    public String getLeaveeCheckJson(@PathVariable("lid") int lid)
    {
        LeaveeCheck leaveeCheck = leaveeCheckService.getByLeaveeId(lid);
        if(leaveeCheck==null)
            return "notexist";
        Employee employee = employeeService.get(leaveeCheck.getEmployeeId());
        JSONObject json = new JSONObject();
        json.put("leaveecheck", JSONObject.toJSON(leaveeCheck));
        json.put("employee", JSONObject.toJSON(employee));
        return json.toJSONString();
    }
}
