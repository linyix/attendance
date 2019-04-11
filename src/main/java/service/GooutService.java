package service;

import mapper.GooutMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Employee;
import pojo.Goout;
import pojo.GooutCheck;
import pojo.GooutExample;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GooutService {
    @Autowired
    GooutMapper GooutMapper;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    GooutCheckService GooutCheckService;

    public List<Goout> listByEid(int eid)
    {
        GooutExample GooutExample = new GooutExample();
        GooutExample.createCriteria().andEmployeeIdEqualTo(eid);
        GooutExample.setOrderByClause("id desc");
        return GooutMapper.selectByExample(GooutExample);
    }
    public List<Goout> listByDid(int did)
    {
        List<Employee> employees = employeeService.listByDepartmentId(did);
        List<Goout> Goouts = new ArrayList<>();
        for(Employee employee :employees)
        {
            Goouts.addAll(listByEid(employee.getId()));
        }
        return Goouts;
    }
    public List<Goout> list()
    {
        GooutExample GooutExample = new GooutExample();
        GooutExample.setOrderByClause("id desc");
        return GooutMapper.selectByExample(GooutExample);
    }

    public void add(Goout Goout)
    {
        GooutMapper.insert(Goout);
    }

    public List<Goout> listByStartBefore(Goout Goout)
    {
        GooutExample GooutExample = new GooutExample();
        GooutExample.createCriteria().andEmployeeIdEqualTo(Goout.getEmployeeId()).andStartTimeLessThanOrEqualTo(Goout.getStartTime()).andEndTimeGreaterThanOrEqualTo(Goout.getStartTime());
        return GooutMapper.selectByExample(GooutExample);
    }
    public List<Goout> listByEndAfter(Goout Goout)
    {
        GooutExample GooutExample = new GooutExample();
        GooutExample.createCriteria().andEmployeeIdEqualTo(Goout.getEmployeeId()).andEndTimeGreaterThanOrEqualTo(Goout.getEndTime()).andStartTimeLessThanOrEqualTo(Goout.getEndTime());
        return GooutMapper.selectByExample(GooutExample);
    }

    public boolean isBetween(Goout Goout)
    {
        List<Goout> Goouts =listByEndAfter(Goout);
        for(Goout Goout1:Goouts)
        {
            GooutCheck GooutCheck = GooutCheckService.getByGooutId(Goout1.getId());
            if(GooutCheck==null||GooutCheck.getPass()==true)
            {
                return false;
            }
        }
        Goouts = listByStartBefore(Goout);
        for(Goout Goout1:Goouts)
        {
            GooutCheck GooutCheck = GooutCheckService.getByGooutId(Goout1.getId());
            if(GooutCheck==null||GooutCheck.getPass()==true)
            {
                return false;
            }
        }
        return true;
    }

    public Goout get(int lid)
    {
        return GooutMapper.selectByPrimaryKey(lid);
    }

    public Goout getByBetweenDateStartAndEnd(int eid, Date date)
    {
        GooutExample GooutExample = new GooutExample();
        GooutExample.createCriteria().andEmployeeIdEqualTo(eid).andStartTimeLessThanOrEqualTo(date).andEndTimeGreaterThanOrEqualTo(date);
        List<Goout> Goouts= GooutMapper.selectByExample(GooutExample);
        if (Goouts.size()==0)
            return null;
        return Goouts.get(0);
    }
}
