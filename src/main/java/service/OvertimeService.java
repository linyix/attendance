package service;

import mapper.OvertimeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Employee;
import pojo.Overtime;
import pojo.OvertimeCheck;
import pojo.OvertimeExample;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OvertimeService {
    @Autowired
    OvertimeMapper overtimeMapper;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    OvertimeCheckService overtimeCheckService;

    public List<Overtime> listByEid(int eid)
    {
        OvertimeExample overtimeExample = new OvertimeExample();
        overtimeExample.createCriteria().andEmployeeIdEqualTo(eid);
        overtimeExample.setOrderByClause("id desc");
        return overtimeMapper.selectByExample(overtimeExample);
    }
    public List<Overtime> listByDid(int did)
    {
        List<Employee> employees = employeeService.listByDepartmentId(did);
        List<Overtime> overtimes = new ArrayList<>();
        for(Employee employee :employees)
        {
            overtimes.addAll(listByEid(employee.getId()));
        }
        return overtimes;
    }
    public List<Overtime> list()
    {
        OvertimeExample overtimeExample = new OvertimeExample();
        overtimeExample.setOrderByClause("id desc");
        return overtimeMapper.selectByExample(overtimeExample);
    }

    public void add(Overtime overtime)
    {
        overtimeMapper.insert(overtime);
    }


    public Overtime get(int lid)
    {
        return overtimeMapper.selectByPrimaryKey(lid);
    }


    //todo
    public Overtime getByEmployeeIdAndDateRange(int eid, Date start,Date end)
    {
        OvertimeExample overtimeExample = new OvertimeExample();
        overtimeExample.createCriteria().andEmployeeIdEqualTo(eid);
        List<Overtime> overtimes= overtimeMapper.selectByExample(overtimeExample);
        if (overtimes.size()==0)
            return null;
        return overtimes.get(0);
    }
}
