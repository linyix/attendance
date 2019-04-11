package service;

import mapper.LeaveeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Employee;
import pojo.Leavee;
import pojo.LeaveeCheck;
import pojo.LeaveeExample;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LeaveeService {
    @Autowired
    LeaveeMapper leaveeMapper;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    LeaveeCheckService leaveeCheckService;

    public List<Leavee> listByEid(int eid)
    {
        LeaveeExample leaveeExample = new LeaveeExample();
        leaveeExample.createCriteria().andEmployeeIdEqualTo(eid);
        leaveeExample.setOrderByClause("id desc");
        return leaveeMapper.selectByExample(leaveeExample);
    }
    public List<Leavee> listByDid(int did)
    {
        List<Employee> employees = employeeService.listByDepartmentId(did);
        List<Leavee> leavees = new ArrayList<>();
        for(Employee employee :employees)
        {
            leavees.addAll(listByEid(employee.getId()));
        }
        return leavees;
    }
    public List<Leavee> list()
    {
        LeaveeExample leaveeExample = new LeaveeExample();
        leaveeExample.setOrderByClause("id desc");
        return leaveeMapper.selectByExample(leaveeExample);
    }

    public void add(Leavee leavee)
    {
        leaveeMapper.insert(leavee);
    }

    public List<Leavee> listByStartBefore(Leavee leavee)
    {
        LeaveeExample leaveeExample = new LeaveeExample();
        leaveeExample.createCriteria().andEmployeeIdEqualTo(leavee.getEmployeeId()).andStartTimeLessThanOrEqualTo(leavee.getStartTime()).andEndTimeGreaterThanOrEqualTo(leavee.getStartTime());
        return leaveeMapper.selectByExample(leaveeExample);
    }
    public List<Leavee> listByEndAfter(Leavee leavee)
    {
        LeaveeExample leaveeExample = new LeaveeExample();
        leaveeExample.createCriteria().andEmployeeIdEqualTo(leavee.getEmployeeId()).andEndTimeGreaterThanOrEqualTo(leavee.getEndTime()).andStartTimeLessThanOrEqualTo(leavee.getEndTime());
        return leaveeMapper.selectByExample(leaveeExample);
    }

    public boolean isBetween(Leavee leavee)
    {
        List<Leavee> leavees =listByEndAfter(leavee);
        for(Leavee leavee1:leavees)
        {
            LeaveeCheck leaveeCheck = leaveeCheckService.getByLeaveeId(leavee1.getId());
            if(leaveeCheck==null||leaveeCheck.getPass()==true)
            {
                return false;
            }
        }
        leavees = listByStartBefore(leavee);
        for(Leavee leavee1:leavees)
        {
            LeaveeCheck leaveeCheck = leaveeCheckService.getByLeaveeId(leavee1.getId());
            if(leaveeCheck==null||leaveeCheck.getPass()==true)
            {
                return false;
            }
        }
        return true;
    }

    public Leavee get(int lid)
    {
        return leaveeMapper.selectByPrimaryKey(lid);
    }

    public Leavee getByBetweenDateStartAndEnd(int eid, Date date)
    {
        LeaveeExample leaveeExample = new LeaveeExample();
        leaveeExample.createCriteria().andEmployeeIdEqualTo(eid).andStartTimeLessThanOrEqualTo(date).andEndTimeGreaterThanOrEqualTo(date);
        List<Leavee> leavees= leaveeMapper.selectByExample(leaveeExample);
        if (leavees.size()==0)
            return null;
        return leavees.get(0);
    }
    //todo
    public Leavee getByEmployeeIdAndDateRange(int eid, Date start,Date end)
    {
        LeaveeExample leaveeExample = new LeaveeExample();
        leaveeExample.createCriteria().andEmployeeIdEqualTo(eid);
        List<Leavee> leavees= leaveeMapper.selectByExample(leaveeExample);
        if (leavees.size()==0)
            return null;
        return leavees.get(0);
    }
}
