package service;

import mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Department;
import pojo.DepartmentExample;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;

    public Department get(int id)
    {
        return departmentMapper.selectByPrimaryKey(id);
    }

    public List<Department> list()
    {
        DepartmentExample departmentExample = new DepartmentExample();
        departmentExample.createCriteria().andIdNotEqualTo(0);
        return departmentMapper.selectByExample(departmentExample);
    }
}
