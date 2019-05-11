package controller;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.*;
import service.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class SchedulesController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    SchedulesService schedulesService;
    @Autowired
    ClazzService clazzService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    LeaveeService leaveeService;
    @Autowired
    LeaveeCheckService leaveeCheckService;
    @Autowired
    GooutService gooutService;
    @Autowired
    GooutCheckService gooutCheckService;
    @Autowired
    OvertimeService overtimeService;
    @Autowired
    OvertimeCheckService ovettimeCheckService;

    String[] weekDays = { "日", "一", "二", "三", "四", "五", "六" };

    //初始Tree
    @RequestMapping("schedule")
    public String tree(Model model)
    {
        int eid=1;
        model.addAttribute("tree",departmentService.getTreeJson(eid));
        return "treeWithSchedule";

    }

    @RequestMapping(value = "department/{did}/schedule",method = RequestMethod.GET)
    public String list(@PathVariable("did") int did, String date, Model model) throws Exception
    {
       Department departmentstatic = departmentService.get(did);

       if(departmentstatic.getType()==1)
       {
           return "staticschedules";

       }



        List<String> dateAndWeek = new ArrayList<>();
        model.addAttribute("dateAndWeek",dateAndWeek);
        List<Clazz> clazzes = clazzService.list(did);
        model.addAttribute("clazzes",clazzes);

        Date today = new Date();
        Date checkDay;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat mad = new SimpleDateFormat("MM-dd");
        if(date==null)
            checkDay=sdf.parse(sdf.format(today));
        else
        {
            checkDay = sdf.parse(date);
        }
        List<Date> dates = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(checkDay);
        for(int i=0;i<7;i++)
        {
            dates.add(calendar.getTime());
            dateAndWeek.add(mad.format(calendar.getTime())+"<br/>"+getWeekOfDate(calendar));
            calendar.add(Calendar.DATE,1);

        }



        List<Employee> employees = employeeService.listByDepartmentId(did);
        List<ViewObject> vos = new ArrayList<>();
        for(Employee e :employees)
        {
            ViewObject viewObject = new ViewObject();
            viewObject.set("employee",e);
            List<ViewObject> vos2 = new ArrayList<>();
            viewObject.set("vos2",vos2);
            vos.add(viewObject);

            for(Date dd:dates)
            {
                ViewObject viewObject2 = new ViewObject();
                vos2.add(viewObject2);

                Calendar calendar1 = new GregorianCalendar();
                calendar1.setTime(dd);
                List<Schedules> ss = schedulesService.getByEidAndStartDate(e.getId(),dd);

                if(ss.size()==0)
                {
                    viewObject2.set("schedule",null);
                }
                else
                {
                    viewObject2.set("schedule",ss.get(0));
                    viewObject2.set("clazz",clazzService.get(ss.get(0).getClazzId()));
                }
                if(dd.compareTo(today)>=0)
                {
                    System.out.println(dd+"compare"+checkDay+">");
                    viewObject2.set("can",true);
                }
                else
                {
                    System.out.println(dd+"compare"+checkDay+"<");
                    viewObject2.set("can",false);
                }

            }
        }
        model.addAttribute("vos",vos);

        Calendar calendar2 = new GregorianCalendar();
        calendar2.setTime(checkDay);

        calendar2.add(Calendar.DATE,-7);
        model.addAttribute("last",sdf.format(calendar2.getTime()));
        calendar2.add(Calendar.DATE,14);
        model.addAttribute(("next"),sdf.format(calendar2.getTime()));
        model.addAttribute("checkday",sdf.format(checkDay));
        return "schedules";
    }

    @RequestMapping(value = "department/{did}/schedule", method=RequestMethod.POST)
    @ResponseBody
    public String change(@PathVariable("did") int did,String date,int eid,int index,int cid) throws Exception
    {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d= sdf.parse(date);
        System.out.println(d);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(d);
        calendar.add(Calendar.DATE,index);
        d=calendar.getTime();
        System.out.println(index);
        System.out.println(date);
        System.out.println(d);
        schedulesService.changeSchedules(d,eid,cid);

        return "success";
    }

    public String getWeekOfDate(Calendar cal) {
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    @RequestMapping(value="/myschedule",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String myschedule(String start, String end, HttpSession session) throws Exception
    {
        int eid =((Employee)session.getAttribute("user")).getId();
        start =StringUtils.substringBefore(start,"T");
        end =StringUtils.substringBefore(end,"T");
        System.out.println(start+"///"+end);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM-dd HH:mm");
        Date startDate = simpleDateFormat.parse(start);
        Date endDate = simpleDateFormat.parse(end);
        JSONArray jsonArray = new JSONArray();
        List<Schedules> schedules = schedulesService.getByEidAndStartAndEnd(eid,startDate,endDate);
        for(Schedules schedules1 :schedules)
        {
            Clazz clazz = clazzService.get(schedules1.getClazzId());
            Map<String,Object> map = new HashMap<>();
            map.put("title",clazz.getName());
            map.put("start",simpleDateFormat.format(schedules1.getClazzDate()));
            jsonArray.add(map);
        }
        List<Leavee> leavees = leaveeService.listByEid(eid);
        for(Leavee leavee:leavees)
        {
            LeaveeCheck leaveeCheck =leaveeCheckService.getByLeaveeId(leavee.getId());
            if(leaveeCheck!=null&&leaveeCheck.getPass())
            {
                Map<String,Object> map = new HashMap<>();
                map.put("title","请假 "+simpleDateFormat2.format(leavee.getStartTime())+"至"+simpleDateFormat2.format(leavee.getEndTime()));
                map.put("start",simpleDateFormat.format(leavee.getStartTime()));

                Date temp = leavee.getEndTime();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(temp);
                calendar.add(Calendar.DATE,1);

                map.put("end",simpleDateFormat.format(calendar.getTime()));
                map.put("color","red");
                jsonArray.add(map);
            }
        }

        List<Goout> goouts = gooutService.listByEid(eid);
        for(Goout goout:goouts)
        {
            GooutCheck gooutCheck =gooutCheckService.getByGooutId(goout.getId());
            if(gooutCheck!=null&&gooutCheck.getPass())
            {
                Map<String,Object> map = new HashMap<>();
                map.put("title","外出 "+simpleDateFormat2.format(goout.getStartTime())+"至"+simpleDateFormat2.format(goout.getEndTime()));
                map.put("start",simpleDateFormat.format(goout.getStartTime()));

                Date temp = goout.getEndTime();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(temp);
                calendar.add(Calendar.DATE,1);

                map.put("end",simpleDateFormat.format(calendar.getTime()));
                map.put("color","green");
                jsonArray.add(map);
            }
        }

        List<Overtime> overtimes = overtimeService.listByEid(eid);
        for(Overtime overtime:overtimes)
        {
            OvertimeCheck overtimeCheck =ovettimeCheckService.getByOvertimeId(overtime.getId());
            if(overtimeCheck!=null&&overtimeCheck.getPass())
            {
                Map<String,Object> map = new HashMap<>();
                map.put("title","加班 "+overtime.getMinutes()+"分钟");
                map.put("start",simpleDateFormat.format(overtime.getStartTime()));

                map.put("color","#9933FF");
                jsonArray.add(map);
            }
        }
        return jsonArray.toJSONString();

    }

}
