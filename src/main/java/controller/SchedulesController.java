package controller;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pojo.Employee;
import pojo.Schedules;
import pojo.ViewObject;
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

    @RequestMapping("department/{did}/schedule")
    public String list(@PathVariable("did") int did, String date, Model model) throws Exception
    {

        Date today = new Date();
        Date checkDay;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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
            calendar.add(Calendar.DATE,1);
        }
        List<Employee> employees = employeeService.listByDepartmentId(did);
        List<ViewObject> vos = new ArrayList<>();
        for(Employee e :employees)
        {
            ViewObject viewObject = new ViewObject();
            viewObject.set("employee",e);
            List<Boolean> canBeEdited = new ArrayList<>();
            viewObject.set("canBeEdited",canBeEdited);
            List<Schedules> schedules = new ArrayList<>();
            viewObject.set("schedules",schedules);
            for(Date dd:dates)
            {
                Calendar calendar1 = new GregorianCalendar();
                calendar1.setTime(dd);
                List<Schedules> ss = schedulesService.getByEidAndStartDate(e.getId(),dd);

                if(ss.size()==0)
                {
                    schedules.add(null);
                }
                else
                {
                    schedules.add(ss.get(0));
                }
                if(dd.compareTo(today)>=0)
                {
                    canBeEdited.add(true);
                }
                else
                {
                    canBeEdited.add(false);
                }

            }
            vos.add(viewObject);
            model.addAttribute("vos",vos);
            return "schedules";
        }


        return "schedules";
    }
}
