package service;


import mapper.AttendancesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Attendances;
import pojo.AttendancesExample;

import java.util.Date;
import java.util.List;

@Service
public class AttendancesService {

    @Autowired
    AttendancesMapper attendancesMapper;


    public Attendances getBySid(int sid)
    {
        AttendancesExample attendancesExample = new AttendancesExample();
        attendancesExample.createCriteria().andSchedulesIdEqualTo(sid);
        List<Attendances> attendances = attendancesMapper.selectByExample(attendancesExample);
        if(attendances.size()==0)
            return null;
        return attendances.get(0);
    }

    public void add(Attendances attendances)
    {
        attendancesMapper.insert(attendances);
    }

    public void update(Attendances attendances)
    {
        attendancesMapper.updateByPrimaryKeySelective(attendances);
    }
    public List<Attendances> listByEid(int eid)
    {
        AttendancesExample attendancesExample = new AttendancesExample();
        attendancesExample.createCriteria().andEmployeeIdEqualTo(eid);
        attendancesExample.setOrderByClause("id desc");
        return attendancesMapper.selectByExample(attendancesExample);
    }
}
