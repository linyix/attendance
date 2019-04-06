package service;


import mapper.SchedulesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Clazz;
import pojo.Schedules;
import pojo.SchedulesExample;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SchedulesService {

    @Autowired
    SchedulesMapper schedulesMapper;
    @Autowired
    ClazzService   clazzService;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm");
    SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public List<Schedules> getByEidAndStartDate(int eid, Date s)
    {
        SchedulesExample schedulesExample = new SchedulesExample();
        schedulesExample.createCriteria().andClazzDateEqualTo(s).andEmployeeIdEqualTo(eid);
        return schedulesMapper.selectByExample(schedulesExample);
    }
    public List<Schedules> listByeid(int eid)
    {
        SchedulesExample schedulesExample = new SchedulesExample();
        schedulesExample.createCriteria().andEmployeeIdEqualTo(eid);
        schedulesExample.setOrderByClause("id desc");
        return schedulesMapper.selectByExample(schedulesExample);
    }

    public void changeSchedules(Date date,int eid,int cid)
    {
        SchedulesExample schedulesExample = new SchedulesExample();
        schedulesExample.createCriteria().andClazzDateEqualTo(date).andEmployeeIdEqualTo(eid);
        List<Schedules> scheduleses = schedulesMapper.selectByExample(schedulesExample);
        for(Schedules schedules:scheduleses)
        {
            schedulesMapper.deleteByPrimaryKey(schedules.getId());
        }
        if(cid!=-1)
        {
            Schedules schedules = new Schedules();
            schedules.setClazzDate(date);
            schedules.setClazzId(cid);
            schedules.setEmployeeId(eid);
            schedulesMapper.insert(schedules);
        }

    }

    public List<Schedules> getByEidAndStartAndEnd(int eid,Date start,Date end)
    {
        SchedulesExample schedulesExample = new SchedulesExample();
        schedulesExample.createCriteria().andEmployeeIdEqualTo(eid).andClazzDateBetween(start,end);
        return schedulesMapper.selectByExample(schedulesExample);
    }


    public Map<String,Object> getStartAndEnd(Schedules schedules) throws Exception
    {
        Map<String,Object> map = new HashMap<>();
        Clazz clazz = clazzService.get(schedules.getClazzId());
        map.put("schedules",schedules);
        map.put("clazz",clazz);

        Date start =  schedules.getClazzDate();
        Date clazzend = clazz.getEndTime();
        Date clazzstart =clazz.getStartTime();

        Date startdate =simpleDateFormat3.parse(simpleDateFormat.format(start) +" "+ simpleDateFormat2.format(clazzstart));
        map.put("start",startdate) ;
        if(clazzend.before(clazzstart))
        {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(start);
            calendar.add(Calendar.DATE,1);
            start = calendar.getTime();
        }
        Date endDate =simpleDateFormat3.parse(simpleDateFormat.format(start) +" "+ simpleDateFormat2.format(clazzend));
        map.put("end",endDate);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);
        calendar.add(Calendar.MINUTE,0-clazz.getBfStart());
        map.put("bfstart",calendar.getTime());
        calendar.setTime(endDate);
        calendar.add(Calendar.MINUTE,clazz.getAfEnd());
        map.put("afend",calendar.getTime());

        System.out.println("bfstart"+map.get("bfstart"));
        System.out.println("start"+map.get("start"));
        System.out.println("end"+map.get("end"));
        System.out.println("afend"+map.get("afend"));
        return map;
    }


    public List<Schedules> getThreeDaySchedules(int eid) throws Exception
    {
        Date date = new Date();
        date = simpleDateFormat.parse(simpleDateFormat.format(date));
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,-1);
        Date yesterday = calendar.getTime();
        calendar.add(Calendar.DATE,2);
        Date tomorrow = calendar.getTime();

        List<Schedules> scheduleses = getByEidAndStartDate(eid,yesterday);
        scheduleses.addAll(getByEidAndStartDate(eid,date));
        scheduleses.addAll(getByEidAndStartDate(eid,tomorrow));
        return scheduleses;
    }
}
