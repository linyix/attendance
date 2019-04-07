package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pojo.Attendances;
import pojo.Clazz;
import pojo.Department;
import pojo.Schedules;
import service.*;

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


    @RequestMapping("home")
    public String home(Model model) throws Exception
    {
        int eid=2;


        List<Schedules> scheduleses = schedulesService.getThreeDaySchedules(eid);
        Date date = new Date();
        Map<String,Object> map;
        model.addAttribute("cancheckon",false);
        model.addAttribute("cancheckout",false);
        for(Schedules schedules :scheduleses)
        {
            map = schedulesService.getStartAndEnd(schedules);
            if( ((Date)map.get("bfstart")).before(date) && ((Date)map.get("end")).after(date))
            {
                Schedules schedules1 =(Schedules) map.get("schedules");
                Attendances attendances = attendancesService.getBySid(schedules1.getId());
                Clazz clazz = clazzService.get(schedules1.getClazzId());
                model.addAttribute("clazz",clazz);
                if(attendances==null)
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
        return "home";
    }
}
