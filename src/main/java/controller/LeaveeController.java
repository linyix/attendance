package controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Employee;
import pojo.Leavee;
import pojo.LeaveeCheck;
import service.DepartmentService;
import service.EmployeeService;
import service.LeaveeCheckService;
import service.LeaveeService;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LeaveeController {
    @Autowired
    DepartmentService departmentService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    LeaveeCheckService leaveeCheckService;
    @Autowired
    LeaveeService leaveeService;
    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    //树
    @RequestMapping(value = "/leavee")
    public String leavee(Model model)
    {
        int eid =1;
        model.addAttribute("tree",departmentService.getTreeJson(eid));
        return "leavee";
    }
    @RequestMapping(value = "/leavee/{lid}/json")
    @ResponseBody
    public String leaveeJson(@PathVariable("lid") int lid)
    {
        Leavee leavee= leaveeService.get(lid);
        Employee employee = employeeService.get(leavee.getEmployeeId());
        String start = simpleDateFormat.format(leavee.getStartTime());
        String end = simpleDateFormat.format(leavee.getEndTime());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("employee",JSONObject.toJSON(employee));
        jsonObject.put("leavee",JSONObject.toJSON(leavee));
        jsonObject.put("start",start);
        jsonObject.put("end",end);
        return jsonObject.toJSONString();
    }


    //个人页面
    @RequestMapping(value ="/myleavee",method = RequestMethod.GET)
    public String myleavee(Model model)
    {
        return "myleavee";
    }

    //个人添加
    @RequestMapping(value ="/myleavee",method = RequestMethod.POST)
    @ResponseBody
    public String myleaveeAdd(String daterange,String notes) throws Exception
    {
        int eid=2;
        String[] strings = daterange.split(" - ");
        Leavee leavee = new Leavee();
        leavee.setEmployeeId(2);
        leavee.setStartTime(simpleDateFormat.parse(strings[0]));
        leavee.setEndTime(simpleDateFormat.parse(strings[1]));
        if(leaveeService.isBetween(leavee))
            return "请假范围重复";
        leaveeService.add(leavee);
        return "success";
    }

    //个人列表
    @RequestMapping(value ="/myleavee/json",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String myleaveeJsonGet(int page, int limit) throws Exception
    {
        if(limit ==0)
            limit=10;
        PageHelper.offsetPage(page-1, limit);
        int eid =2;
        List<Leavee> leavees = leaveeService.listByEid(eid);
        JSONArray jsonArray = new JSONArray();
        for(Leavee leavee:leavees)
        {
            Map<String,Object > map = new HashMap<>();
            map.put("id",leavee.getId());
            map.put("starttime",simpleDateFormat.format(leavee.getStartTime()));
            map.put("endtime",simpleDateFormat.format(leavee.getEndTime()));
            map.put("employeeNumber",leavee.getEmployeeId());
            map.put("employeeName",employeeService.get(leavee.getEmployeeId()).getName());
            LeaveeCheck leaveeCheck = leaveeCheckService.getByLeaveeId(leavee.getId());
            if(leaveeCheck==null)
            {
                map.put("passed","未审批" );
            }
            else
            {
                map.put("passed",leaveeCheck.getPass()?"通过":"未通过");
            }

            jsonArray.add(map);
        }
        JSONObject json = new JSONObject();
        json.put("code",0);
        json.put("msg","");
        json.put("count",(int) new PageInfo<>(leavees).getTotal());
        json.put("data", jsonArray);
        return json.toJSONString();

    }




/*
    @RequestMapping(value = "/department/{did}/leavee")
    @ResponseBody
    public String leaveeByDid(@PathVariable("did") int did , int page, int limit)
    {
        if(limit ==0)
            limit=10;
        PageHelper.offsetPage(0, limit);

        JSONArray jsonArray = new JSONArray();
        List<Leavee> leavees = leaveeService.listByDid(did);
        JSONObject json = new JSONObject();
        json.put("code",0);
        json.put("msg","");
        //json.put("count",(int) new PageInfo<>(employees).getTotal());
        json.put("data", jsonArray);
        System.out.println(json.toJSONString());
        return json.toJSONString();
    }

*/
}
