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
import pojo.Leavee;
import pojo.LeaveeCheck;
import service.DepartmentService;
import service.EmployeeService;
import service.LeaveeCheckService;
import service.LeaveeService;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
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
        /*
        int eid =1;
        model.addAttribute("tree",departmentService.getTreeJson(eid));*/
        return "leavee";
    }
    @RequestMapping(value = "/leavees/{checked}/json",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String leaveeJson(@PathVariable("checked") boolean check,int page,int limit) {
        int eid = 1;

        System.out.println("check:" + check);
        List<Department> departments = departmentService.listAllManageByEmployeeId(eid);

        List<Leavee> leavees = leaveeService.list();
        for (Iterator<Leavee> iterator = leavees.iterator(); iterator.hasNext(); ) {
            Leavee leavee = iterator.next();
            if ( (leaveeCheckService.getByLeaveeId(leavee.getId()) != null) ^ (check)) {
                iterator.remove();
                continue;
            }
            if (!departmentService.isDidInDepartmentList(employeeService.get(leavee.getEmployeeId()).getDepartmentId(), departments)) {
                iterator.remove();
                continue;
            }
        }
        int retSize = leavees.size();
        leavees.subList( (page-1)*limit, ((page)*limit)>leavees.size()?(leavees.size()-1):((page)*limit-1));
        JSONArray jsonArray = new JSONArray();
        for(Leavee leavee:leavees)
        {
            Map<String,Object > map = new HashMap<>();
            map.put("id",leavee.getId());
            map.put("starttime",simpleDateFormat.format(leavee.getStartTime()));
            map.put("endtime",simpleDateFormat.format(leavee.getEndTime()));

            Employee employee =employeeService.get(leavee.getEmployeeId());
            map.put("departmentname",departmentService.get(employee.getDepartmentId()).getName());
            map.put("employeeName",employee.getName());
            map.put("employeeNumber",employee.getNumber());
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
        json.put("count",retSize);
        json.put("data", jsonArray);
        return json.toJSONString();

    }
        @RequestMapping(value = "/leavee/{lid}/json")
        @ResponseBody
        public String leaveeJson ( @PathVariable("lid") int lid)
        {
            Leavee leavee = leaveeService.get(lid);
            Employee employee = employeeService.get(leavee.getEmployeeId());
            String start = simpleDateFormat.format(leavee.getStartTime());
            String end = simpleDateFormat.format(leavee.getEndTime());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("employee", JSONObject.toJSON(employee));
            jsonObject.put("leavee", JSONObject.toJSON(leavee));
            jsonObject.put("start", start);
            jsonObject.put("end", end);
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
    public String myleaveeAdd(String daterange, String notes, HttpSession session) throws Exception
    {
        int eid =((Employee)session.getAttribute("user")).getId();

        String[] strings = daterange.split(" - ");
        Leavee leavee = new Leavee();
        leavee.setEmployeeId(2);
        leavee.setStartTime(simpleDateFormat.parse(strings[0]));
        leavee.setEndTime(simpleDateFormat.parse(strings[1]));
        leavee.setNotes(notes);
        if(leaveeService.isBetween(leavee))
            return "请假范围重复";
        leaveeService.add(leavee);
        return "success";
    }

    //个人列表
    @RequestMapping(value ="/myleavee/json",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String myleaveeJsonGet(int page, int limit,HttpSession session) throws Exception
    {
        if(limit ==0)
            limit=10;
        PageHelper.offsetPage(page-1, limit);
        int eid =((Employee)session.getAttribute("user")).getId();
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


}
