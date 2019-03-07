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
/*
    public Employee get(int id)
    {

    }*/
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
}
