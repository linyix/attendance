package service;

import mapper.LeaveeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Employee;
import pojo.Leavee;
import pojo.LeaveeExample;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeaveeService {
    @Autowired
    LeaveeMapper leaveeMapper;
    @Autowired
    EmployeeService employeeService;
    public List<Leavee> listByEid(int eid)
    {
        LeaveeExample leaveeExample = new LeaveeExample();
        leaveeExample.createCriteria().andEmployeeIdEqualTo(eid);
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
        leaveeExample.createCriteria().andStartTimeLessThanOrEqualTo(leavee.getStartTime());
        return leaveeMapper.selectByExample(leaveeExample);
    }
    public List<Leavee> listByEndAfter(Leavee leavee)
    {
        LeaveeExample leaveeExample = new LeaveeExample();
        leaveeExample.createCriteria().andEndTimeGreaterThanOrEqualTo(leavee.getEndTime());
        return leaveeMapper.selectByExample(leaveeExample);
    }

    public boolean isBetween(Leavee leavee)
    {
        if(listByEndAfter(leavee).size()==0 && listByStartBefore(leavee).size()==0)
            return false;
        return true;
    }
}
