package controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Attendances;
import pojo.Clazz;
import pojo.Employee;
import pojo.Schedules;
import service.AttendancesService;
import service.ClazzService;
import service.EmployeeService;
import service.SchedulesService;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AttendanceController {

    @Autowired
    SchedulesService schedulesService;
    @Autowired
    AttendancesService attendancesService;
    @Autowired
    ClazzService clazzService;
    @Autowired
    EmployeeService employeeService;


    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    @RequestMapping(value = "myattendance",method = RequestMethod.GET)
    public String myAttendance()
    {
        return "myattendance";
    }

    @RequestMapping(value = "myattendance/json",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String myAttendanceJson(int page, int limit, HttpSession session) throws Exception
    {
        int eid =((Employee)session.getAttribute("user")).getId();
        if(limit ==0)
            limit=10;
        PageHelper.startPage(page-1, limit);

        JSONArray jsonArray = new JSONArray();
        List<Schedules> scheduleses = schedulesService.listByeid(eid);
        for(Schedules schedules:scheduleses)
        {
            Attendances attendances = attendancesService.getBySid(schedules.getId());
            Clazz clazz = clazzService.get(schedules.getClazzId());
            Employee employee = employeeService.get(schedules.getEmployeeId());
            Map<String,Object > map = new HashMap<>();
            map.put("id",schedules.getId());
            map.put("employeeNumber",employee.getNumber());
            map.put("employeeName",employee.getName());
            Map<String,Object> s =  schedulesService.getStartAndEnd(schedules);
            map.put("starttime",simpleDateFormat2.format((Date)s.get("start")));
            map.put("endtime",simpleDateFormat2.format((Date)s.get("end")));
            map.put("clazzName",clazz.getName());
            map.put("checkin",attendances==null?"":simpleDateFormat2.format(attendances.getCheckIn()));
            if(attendances!=null&&attendances.getCheckOut()!=null)
            {
                map.put("checkout",simpleDateFormat2.format(attendances.getCheckOut()));
            }
            else
            {
                map.put("checkout","");
            }

            if(attendances==null)
            {
                map.put("type","未打卡");
            }
            else
            {
                map.put("type",attendances.getType()+(attendances.getCheckOut()==null?"|未退卡":""));
            }
            jsonArray.add(map);
        }

        JSONObject json = new JSONObject();
        json.put("code",0);
        json.put("msg","");
        json.put("count",(int) new PageInfo<>(scheduleses).getTotal());
        json.put("data", jsonArray);
        System.out.println(json.toJSONString());
        return json.toJSONString();

    }

    //打卡
    @RequestMapping(value = "myattendance/on")
    @ResponseBody
    public String myAttendanceOn(HttpSession session) throws Exception
    {
        int eid =((Employee)session.getAttribute("user")).getId();


        List<Schedules> scheduleses = schedulesService.getThreeDaySchedules(eid);
        Date date = new Date();
        Map<String,Object> map;
        for(Schedules schedules :scheduleses)
        {
            map = schedulesService.getStartAndEnd(schedules);
            if( ((Date)map.get("bfstart")).before(date) && ((Date)map.get("end")).after(date))
            {
                Schedules schedules1 =(Schedules) map.get("schedules");
                Attendances attendances = attendancesService.getBySid(schedules1.getId());
                if(attendances==null)
                {
                    Attendances attendances1 = new Attendances();
                    attendances1.setCheckIn(new Date());
                    attendances1.setEmployeeId(eid);
                    attendances1.setSchedulesId(schedules.getId());
                    attendances1.setType(new Date().before( (Date) map.get("start"))?"正常打卡":"迟到" );
                    attendancesService.add(attendances1);
                    return "success";
                }
            }
        }
        return "fail";
    }

    //退卡
    @RequestMapping(value = "myattendance/off")
    @ResponseBody
    public String myAttendanceOff(HttpSession session) throws Exception
    {

        int eid =((Employee)session.getAttribute("user")).getId();
        List<Schedules> scheduleses = schedulesService.getThreeDaySchedules(eid);
        Date date = new Date();
        Map<String,Object> map;
        for(Schedules schedules :scheduleses)
        {
            map = schedulesService.getStartAndEnd(schedules);
            if( ((Date)map.get("start")).before(date) && ((Date)map.get("afend")).after(date))
            {
                Schedules schedules1 =(Schedules) map.get("schedules");
                Attendances attendances = attendancesService.getBySid(schedules1.getId());
                if(attendances!=null && attendances.getCheckOut()==null)
                {
                    attendances.setCheckOut(new Date());
                    attendances.setType(attendances.getType()+"|"+(new Date().before( (Date) map.get("end"))?"早退":"正常退卡" ));
                    attendancesService.update(attendances);
                    return "success";
                }
            }
        }
        return "fail";
    }
}
