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
}
