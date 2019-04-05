package service;


import mapper.SchedulesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Schedules;
import pojo.SchedulesExample;

import java.util.Date;
import java.util.List;

@Service
public class SchedulesService {

    @Autowired
    SchedulesMapper schedulesMapper;

    public List<Schedules> getByEidAndStartDate(int eid, Date s)
    {
        SchedulesExample schedulesExample = new SchedulesExample();
        schedulesExample.createCriteria().andClazzDateEqualTo(s).andEmployeeIdEqualTo(eid);
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
}
