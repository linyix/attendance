package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pojo.*;
import service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    SchedulesService schedulesService;
    @Autowired
    AttendancesService attendancesService;
    @Autowired
    ClazzService clazzService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    EmployeeService employeeService;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @RequestMapping("home")
    public String home(Model model, HttpSession session) throws Exception
    {
        int eid =((Employee)session.getAttribute("user")).getId();


        List<Schedules> scheduleses = schedulesService.getThreeDaySchedules(eid);
        Date date = new Date();
        Map<String,Object> map;
        model.addAttribute("cancheckon",false);
        model.addAttribute("cancheckout",false);
        for(Schedules schedules :scheduleses)
        {
            map = schedulesService.getStartAndEnd(schedules);
            if( ((Date)map.get("bfstart")).before(date) && ((Date)map.get("afend")).after(date))
            {
                Schedules schedules1 =(Schedules) map.get("schedules");
                Attendances attendances = attendancesService.getBySid(schedules1.getId());
                Clazz clazz = clazzService.get(schedules1.getClazzId());
                model.addAttribute("clazz",clazz);
                if(attendances==null&&( ((Date)map.get("bfstart")).before(date) && ((Date)map.get("end")).after(date)))
                {
                    model.addAttribute("cancheckon",true);
                }
                else
                {
                    model.addAttribute("attendances",attendances);
                }
            }
            if( ((Date)map.get("start")).before(date) && ((Date)map.get("afend")).after(date))
            {
                Schedules schedules1 =(Schedules) map.get("schedules");
                Attendances attendances = attendancesService.getBySid(schedules1.getId());
                model.addAttribute("attendancesOut",attendances);
                if(attendances!=null&&attendances.getCheckOut()==null)
                {
                    model.addAttribute("cancheckout",true);
                }
            }
        }

        Department department = departmentService.get(employeeService.get(eid).getDepartmentId());
        model.addAttribute("department",department);

        List<Clazz> clazzes = clazzService.list(department.getId());
        model.addAttribute("clazzes",clazzes);
        model.addAttribute("today",simpleDateFormat.format(new Date()));
        return "home";
    }


    @RequestMapping("")
    public String index(HttpServletRequest request)
    {
        System.out.println(request.getSession().getServletContext().getRealPath(""));
        return "/front/home";
    }
}
