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
import pojo.Goout;
import pojo.GooutCheck;
import service.DepartmentService;
import service.EmployeeService;
import service.GooutCheckService;
import service.GooutService;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class GooutController {
    @Autowired
    DepartmentService departmentService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    GooutCheckService gooutCheckService;
    @Autowired
    GooutService gooutService;
    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    //树
    @RequestMapping(value = "/goout")
    public String goout(Model model)
    {
        /*
        int eid =1;
        model.addAttribute("tree",departmentService.getTreeJson(eid));*/
        return "goout";
    }
    @RequestMapping(value = "/goouts/{checked}/json",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String gooutJson(@PathVariable("checked") boolean check,int page,int limit) {
        int eid = 1;

        System.out.println("check:" + check);
        List<Department> departments = departmentService.listAllManageByEmployeeId(eid);

        List<Goout> Goouts = gooutService.list();
        for (Iterator<Goout> iterator = Goouts.iterator(); iterator.hasNext(); ) {
            Goout Goout = iterator.next();
            if ( (gooutCheckService.getByGooutId(Goout.getId()) != null) ^ (check)) {
                iterator.remove();
                continue;
            }
            if (!departmentService.isDidInDepartmentList(employeeService.get(Goout.getEmployeeId()).getDepartmentId(), departments)) {
                iterator.remove();
                continue;
            }
        }
        int retSize = Goouts.size();
        Goouts.subList( (page-1)*limit, ((page)*limit)>Goouts.size()?(Goouts.size()-1):((page)*limit-1));
        JSONArray jsonArray = new JSONArray();
        for(Goout Goout:Goouts)
        {
            Map<String,Object > map = new HashMap<>();
            map.put("id",Goout.getId());
            map.put("starttime",simpleDateFormat.format(Goout.getStartTime()));
            map.put("endtime",simpleDateFormat.format(Goout.getEndTime()));
            Employee employee =employeeService.get(Goout.getEmployeeId());
            map.put("departmentname",departmentService.get(employee.getDepartmentId()).getName());
            map.put("employeeName",employee.getName());
            map.put("employeeNumber",employee.getNumber());
            GooutCheck GooutCheck = gooutCheckService.getByGooutId(Goout.getId());
            if(GooutCheck==null)
            {
                map.put("passed","未审批" );
            }
            else
            {
                map.put("passed",GooutCheck.getPass()?"通过":"未通过");
            }

            jsonArray.add(map);
        }

        JSONObject json = new JSONObject();
        json.put("code",0);
        json.put("msg","");
        json.put("count",retSize);
        json.put("data", jsonArray);
        return json.toJSONString();

    }
    @RequestMapping(value = "/Goout/{lid}/json")
    @ResponseBody
    public String GooutJson ( @PathVariable("lid") int lid)
    {
        Goout Goout = gooutService.get(lid);
        Employee employee = employeeService.get(Goout.getEmployeeId());
        String start = simpleDateFormat.format(Goout.getStartTime());
        String end = simpleDateFormat.format(Goout.getEndTime());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("employee", JSONObject.toJSON(employee));
        jsonObject.put("Goout", JSONObject.toJSON(Goout));
        jsonObject.put("start", start);
        jsonObject.put("end", end);
        return jsonObject.toJSONString();
    }



    //个人页面
    @RequestMapping(value ="/myGoout",method = RequestMethod.GET)
    public String myGoout(Model model)
    {
        return "mygoout";
    }

    //个人添加
    @RequestMapping(value ="/myGoout",method = RequestMethod.POST)
    @ResponseBody
    public String myGooutAdd(String daterange,String notes) throws Exception
    {
        int eid=2;
        String[] strings = daterange.split(" - ");
        Goout Goout = new Goout();
        Goout.setEmployeeId(2);
        Goout.setStartTime(simpleDateFormat.parse(strings[0]));
        Goout.setEndTime(simpleDateFormat.parse(strings[1]));
        if(gooutService.isBetween(Goout))
            return "请假范围重复";
        gooutService.add(Goout);
        return "success";
    }

    //个人列表
    @RequestMapping(value ="/myGoout/json",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String myGooutJsonGet(int page, int limit) throws Exception
    {
        if(limit ==0)
            limit=10;
        PageHelper.offsetPage(page-1, limit);
        int eid =2;
        List<Goout> Goouts = gooutService.listByEid(eid);
        JSONArray jsonArray = new JSONArray();
        for(Goout Goout:Goouts)
        {
            Map<String,Object > map = new HashMap<>();
            map.put("id",Goout.getId());
            map.put("starttime",simpleDateFormat.format(Goout.getStartTime()));
            map.put("endtime",simpleDateFormat.format(Goout.getEndTime()));
            map.put("employeeNumber",Goout.getEmployeeId());
            map.put("employeeName",employeeService.get(Goout.getEmployeeId()).getName());
            GooutCheck GooutCheck = gooutCheckService.getByGooutId(Goout.getId());
            if(GooutCheck==null)
            {
                map.put("passed","未审批" );
            }
            else
            {
                map.put("passed",GooutCheck.getPass()?"通过":"未通过");
            }

            jsonArray.add(map);
        }
        JSONObject json = new JSONObject();
        json.put("code",0);
        json.put("msg","");
        json.put("count",(int) new PageInfo<>(Goouts).getTotal());
        json.put("data", jsonArray);
        return json.toJSONString();

    }


}
