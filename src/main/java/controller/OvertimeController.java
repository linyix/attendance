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
import pojo.Department;
import pojo.Employee;
import pojo.Overtime;
import pojo.OvertimeCheck;
import service.DepartmentService;
import service.EmployeeService;
import service.OvertimeCheckService;
import service.OvertimeService;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class OvertimeController {
    @Autowired
    DepartmentService departmentService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    OvertimeCheckService overtimeCheckService;
    @Autowired
    OvertimeService overtimeService;
    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
    //树
    @RequestMapping(value = "/overtime")
    public String overtime(Model model)
    {
        /*
        int eid =1;
        model.addAttribute("tree",departmentService.getTreeJson(eid));*/
        return "overtime";
    }
    @RequestMapping(value = "/overtimes/{checked}/json",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String overtimeJson(@PathVariable("checked") boolean check,int page,int limit) {
        int eid = 1;

        System.out.println("check:" + check);
        List<Department> departments = departmentService.listAllManageByEmployeeId(eid);

        List<Overtime> overtimes = overtimeService.list();
        for (Iterator<Overtime> iterator = overtimes.iterator(); iterator.hasNext(); ) {
            Overtime overtime = iterator.next();
            if ( (overtimeCheckService.getByOvertimeId(overtime.getId()) != null) ^ (check)) {
                iterator.remove();
                continue;
            }
            if (!departmentService.isDidInDepartmentList(employeeService.get(overtime.getEmployeeId()).getDepartmentId(), departments)) {
                iterator.remove();
                continue;
            }
        }
        int retSize = overtimes.size();
        overtimes.subList( (page-1)*limit, ((page)*limit)>overtimes.size()?(overtimes.size()-1):((page)*limit-1));
        JSONArray jsonArray = new JSONArray();
        for(Overtime overtime:overtimes)
        {
            Map<String,Object > map = new HashMap<>();
            map.put("id",overtime.getId());
            map.put("starttime",simpleDateFormat2.format(overtime.getStartTime()));
            map.put("minutes",overtime.getMinutes());

            Employee employee =employeeService.get(overtime.getEmployeeId());
            map.put("departmentname",departmentService.get(employee.getDepartmentId()).getName());
            map.put("employeeName",employee.getName());
            map.put("employeeNumber",employee.getNumber());

            OvertimeCheck overtimeCheck = overtimeCheckService.getByOvertimeId(overtime.getId());
            if(overtimeCheck==null)
            {
                map.put("passed","未审批" );
            }
            else
            {
                map.put("passed",overtimeCheck.getPass()?"通过":"未通过");
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
    @RequestMapping(value = "/overtime/{lid}/json")
    @ResponseBody
    public String overtimeJson ( @PathVariable("lid") int lid)
    {
        Overtime overtime = overtimeService.get(lid);
        Employee employee = employeeService.get(overtime.getEmployeeId());
        String start = simpleDateFormat.format(overtime.getStartTime());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("employee", JSONObject.toJSON(employee));
        jsonObject.put("overtime", JSONObject.toJSON(overtime));
        jsonObject.put("start", start);
        return jsonObject.toJSONString();
    }



    //个人页面
    @RequestMapping(value ="/myovertime",method = RequestMethod.GET)
    public String myovertime(Model model)
    {
        return "myovertime";
    }
    /*
    //个人添加
    @RequestMapping(value ="/myovertime",method = RequestMethod.POST)
    @ResponseBody
    public String myovertimeAdd(String daterange, String notes, HttpSession session) throws Exception
    {
        int eid =((Employee)session.getAttribute("user")).getId();

        String[] strings = daterange.split(" - ");
        Overtime overtime = new Overtime();
        overtime.setEmployeeId(2);
        overtime.setStartTime(simpleDateFormat.parse(strings[0]));
        overtime.setNotes(notes);
        if(overtimeService.isBetween(overtime))
            return "请假范围重复";
        overtimeService.add(overtime);
        return "success";
    }
    */
    //个人列表
    @RequestMapping(value ="/myovertime/json",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String myovertimeJsonGet(int page, int limit,HttpSession session) throws Exception
    {
        if(limit ==0)
            limit=10;
        PageHelper.offsetPage(page-1, limit);
        int eid =((Employee)session.getAttribute("user")).getId();
        List<Overtime> overtimes = overtimeService.listByEid(eid);
        JSONArray jsonArray = new JSONArray();
        for(Overtime overtime:overtimes)
        {
            Map<String,Object > map = new HashMap<>();
            map.put("id",overtime.getId());
            map.put("starttime",simpleDateFormat2.format(overtime.getStartTime()));
            map.put("minutes",overtime.getMinutes());
            map.put("employeeNumber",overtime.getEmployeeId());
            map.put("employeeName",employeeService.get(overtime.getEmployeeId()).getName());
            OvertimeCheck overtimeCheck = overtimeCheckService.getByOvertimeId(overtime.getId());
            if(overtimeCheck==null)
            {
                map.put("passed","未审批" );
            }
            else
            {
                map.put("passed",overtimeCheck.getPass()?"通过":"未通过");
            }

            jsonArray.add(map);
        }
        JSONObject json = new JSONObject();
        json.put("code",0);
        json.put("msg","");
        json.put("count",(int) new PageInfo<>(overtimes).getTotal());
        json.put("data", jsonArray);
        return json.toJSONString();

    }


}
