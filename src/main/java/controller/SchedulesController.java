package controller;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Clazz;
import pojo.Employee;
import pojo.Schedules;
import pojo.ViewObject;
import service.ClazzService;
import service.DepartmentService;
import service.EmployeeService;
import service.SchedulesService;

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


            /*
            List<Boolean> canBeEdited = new ArrayList<>();
            viewObject.set("canBeEdited",canBeEdited);
            List<Schedules> schedules = new ArrayList<>();
            viewObject.set("schedules",schedules);
            List<Schedules> clazzes = new ArrayList<>();
            viewObject.set("clazzes",schedules);
            */
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

}
