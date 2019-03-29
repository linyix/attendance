package service;

import mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Employee;
import pojo.EmployeeExample;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    public List<Employee> list()
    {
        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andIsDeletedNotEqualTo(true);
        return employeeMapper.selectByExample(employeeExample);

    }

    public void delete()
    {

    }

    public int add(Employee employee)
    {
        return employeeMapper.insertSelective(employee);

    }

    public void update(Employee employee)
    {
        employeeMapper.updateByPrimaryKey(employee);
    }

    public Employee get(int id)
    {
        return employeeMapper.selectByPrimaryKey(id);
    }

    public List<Employee> getByNumber(String number)
    {
        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.createCriteria().andNumberEqualTo(number).andIsDeletedEqualTo(false);
        return employeeMapper.selectByExample(employeeExample);
    }

    public List<Employee> listByDepartmentId(int did)
    {
        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.createCriteria().andIsDeletedEqualTo(false).andDepartmentIdEqualTo(did);
        return employeeMapper.selectByExample(employeeExample);
    }
}



