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
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.*;
import service.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class CountController {

    @Autowired
    DepartmentService departmentService;
    @Autowired
    SchedulesService schedulesService;
    @Autowired
    AttendancesService attendancesService;
    @Autowired
    LeaveeService leaveeService;
    @Autowired
    LeaveeCheckService leaveeCheckService;
    @Autowired
    GooutService gooutService;
    @Autowired
    GooutCheckService gooutCheckService;
            
    SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @RequestMapping("mycount")
    public String employeeCount(String month, Model model, HttpSession session) throws Exception
    {
        int eid =((Employee)session.getAttribute("user")).getId();

        Date monthStart = new Date();
        if(month==null)
        {
            monthStart= monthFormat.parse( monthFormat.format(monthStart) );
        }
        else
        {
            monthStart = monthFormat.parse(month);
        }
        model.addAttribute("thisMonth",monthFormat.format(monthStart));
        Calendar calendar =new GregorianCalendar();
        calendar.setTime(monthStart);
        //int dayOfMonth = calendar.getActualMaximum(Calendar.MONTH);
        calendar.add(Calendar.MONTH,1);
        //calendar.add(Calendar.DATE,-1);
        Date monthEnd = calendar.getTime();

        System.out.println(monthStart);
        System.out.println(monthEnd);
        List<Schedules> scheduleses = schedulesService.getByEidAndStartAndEnd(eid,monthStart,monthEnd);

        List<Leavee> leavees =new ArrayList<>();
        List<Goout> goouts= new ArrayList<>();
        List<Overtime> overtimes= new ArrayList<>();
        List<Map<String,Object>> lates= new ArrayList<>();
        List<Map<String,Object>> leaveEarly= new ArrayList<>();
        List<Map<String,Object>> unCheckon = new ArrayList<>();
        List<Map<String,Object>> unCheckOut= new ArrayList<>();
        List<Map<String,Object>> schedulesWithClazz= new ArrayList<>();
        for(Schedules schedules:scheduleses)
        {
            Map<String,Object> map = schedulesService.getStartAndEnd(schedules);
            schedulesWithClazz.add(map);
            Attendances attendances = attendancesService.getBySid(schedules.getId());
            Date scheduleStart =(Date) map.get("start");
            Date scheduleend =(Date) map.get("end");

            //请假外出加班
            Leavee leavee = leaveeService.getByBetweenDateStartAndEnd(eid,scheduleStart);

            if(leavee!=null)
            {
                LeaveeCheck leaveeCheck = leaveeCheckService.getByLeaveeId(leavee.getId());
                if(leaveeCheck!=null&&leaveeCheck.getPass())
                    leavees.add(leavee);
            }
            Leavee leavee1 = leaveeService.getByBetweenDateStartAndEnd(eid,scheduleend);
            if(leavee1!=null)
            {
                if(leavee!=null && leavee1.getId()!=leavee.getId())
                {
                    LeaveeCheck leaveeCheck = leaveeCheckService.getByLeaveeId(leavee1.getId());
                    if(leaveeCheck!=null&&leaveeCheck.getPass())
                        leavees.add(leavee1);
                }
            }

            Goout goout = gooutService.getByBetweenDateStartAndEnd(eid,scheduleStart);

            if(goout!=null)
            {
                GooutCheck gooutCheck = gooutCheckService.getByGooutId(goout.getId());
                if(gooutCheck!=null&&gooutCheck.getPass())
                    goouts.add(goout);
            }
            Goout goout1 = gooutService.getByBetweenDateStartAndEnd(eid,scheduleend);
            if(goout1!=null)
            {
                if(goout!=null && goout1.getId()!=goout.getId())
                {
                    GooutCheck gooutCheck = gooutCheckService.getByGooutId(goout1.getId());
                    if(gooutCheck!=null&&gooutCheck.getPass())
                        goouts.add(goout1);
                }
            }
            //迟到早退
            if(attendances==null)
            {
                map.put("type","未打卡");
                unCheckon.add(map);
            }
            else
            {
                map.put("attendance",attendances);
                map.put("hasCheckOut",true);
                if(StringUtils.contains(attendances.getType(),"迟到"))
                {

                    lates.add(map);
                }
                if(StringUtils.contains(attendances.getType(),"早退"))
                {
                    leaveEarly.add(map);
                }
                if(attendances.getCheckOut()==null)
                {
                    map.put("hasCheckOut",false);
                    unCheckOut.add(map);
                }
            }

        }


        model.addAttribute("scheduleWithClazz",schedulesWithClazz);
        model.addAttribute("scheduleWithClazzSize",schedulesWithClazz.size());
        model.addAttribute("leavees",leavees);
        model.addAttribute("leaveesSize",leavees.size());
        model.addAttribute("goouts",goouts);
        model.addAttribute("gooutsSize",goouts.size());
        model.addAttribute("lates",lates);
        model.addAttribute("latesSize",lates.size());
        model.addAttribute("leaveEarly",leaveEarly);
        model.addAttribute("leaveEarlySize",leaveEarly.size());
        model.addAttribute("unCheckon",unCheckon);
        model.addAttribute("unCheckonSize",unCheckon.size());
        model.addAttribute("unCheckOut",unCheckOut);
        model.addAttribute("unCheckOutSize",unCheckOut.size());
        return "mycount";

    }
    
    @RequestMapping("mycountfree")
    public String mycount(String daterange,Model model,HttpSession session) throws Exception
    {
        int eid =((Employee)session.getAttribute("user")).getId();

        Date countStart;
        Date countEnd;
        if(daterange==null)
        {
            String[] strings = daterange.split(" - ");
            countStart = dateFormat.parse(strings[0]);
            countEnd = dateFormat.parse(strings[1]);
        }
        else
        {
            countEnd=dateFormat.parse( dateFormat.format(new Date()));
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(countEnd);
            calendar.add(Calendar.DATE,-6);
            countStart=calendar.getTime();
        }
        model.addAttribute("daterange",dateFormat.format(countStart)+" - "+dateFormat.format(countEnd) );





        return "";
    }

    @RequestMapping("department/count")
    public String departmentCountTree(Model model) throws Exception
    {
        int eid=1;
        model.addAttribute("tree",departmentService.getTreeJson(eid));
        return "departmentCountEvery";
    }
    @RequestMapping(value="department/{did}/count",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String departmentCount(@PathVariable("did") int did ,int page, int limit) throws Exception
    {
        JSONArray jsonArray = new JSONArray();
        Map<String,Object > map = new HashMap<>();
        map.put("number","b0001");
        map.put("name","张三");
        map.put("schedule",16);
        map.put("late",1);
        map.put("leaveeearly",2);
        map.put("uncheckon",2);
        map.put("uncheckout",2);
        map.put("leavee",2);
        map.put("goout",2);
        map.put("overtime",60);
        jsonArray.add(map);

        map = new HashMap<>();
        map.put("number","b0003");
        map.put("name","赵一");
        map.put("schedule",16);
        map.put("late",0);
        map.put("leaveeearly",1);
        map.put("uncheckon",2);
        map.put("uncheckout",2);
        map.put("leavee",2);
        map.put("goout",2);
        map.put("overtime",120);
        jsonArray.add(map);

        map = new HashMap<>();
        map.put("number","b0004");
        map.put("name","赵二");
        map.put("schedule",16);
        map.put("late",0);
        map.put("leaveeearly",1);
        map.put("uncheckon",2);
        map.put("uncheckout",1);
        map.put("leavee",2);
        map.put("goout",2);
        map.put("overtime",240);
        jsonArray.add(map);

        map = new HashMap<>();
        map.put("number","b0005");
        map.put("name","赵五");
        map.put("schedule",16);
        map.put("late",0);
        map.put("leaveeearly",1);
        map.put("uncheckon",2);
        map.put("uncheckout",1);
        map.put("leavee",3);
        map.put("goout",0);
        map.put("overtime",240);
        jsonArray.add(map);

        map = new HashMap<>();
        map.put("number","b0006");
        map.put("name","赵六");
        map.put("schedule",16);
        map.put("late",0);
        map.put("leaveeearly",0);
        map.put("uncheckon",2);
        map.put("uncheckout",0);
        map.put("leavee",2);
        map.put("goout",2);
        map.put("overtime",300);
        jsonArray.add(map);

        JSONObject json = new JSONObject();
        json.put("code",0);
        json.put("msg","");
        json.put("count",5);
        json.put("data", jsonArray);
        System.out.println(json.toJSONString());
        return json.toJSONString();
    }

    @RequestMapping("department/{did}/countall")
    public String departmentCountAll() throws Exception
    {
        return "departmentCount";
    }
    @RequestMapping("department/countall")
    public String departmentCountAllTree(Model model) throws Exception
    {
        int eid=1;
        model.addAttribute("tree",departmentService.getTreeJson(eid));
        return "treeWithDepartmentCount";
    }
}
