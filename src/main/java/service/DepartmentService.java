package service;

import com.alibaba.fastjson.JSONArray;
import mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Department;
import pojo.DepartmentExample;
import pojo.Manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    ManageService   manageService;
    @Autowired
    EmployeeService employeeService;

    public Department get(int id)
    {
        return departmentMapper.selectByPrimaryKey(id);
    }

    public List<Department> list()
    {
        DepartmentExample departmentExample = new DepartmentExample();
        departmentExample.createCriteria().andIsDeletedEqualTo(false);
        return departmentMapper.selectByExample(departmentExample);
    }
    public List<Department> listByParent(int pid)
    {
        DepartmentExample departmentExample = new DepartmentExample();
        departmentExample.createCriteria().andParentIdEqualTo(pid).andIsDeletedEqualTo(false);
        return departmentMapper.selectByExample(departmentExample);
    }

    public JSONArray getTree(List<Department> list)
    {
        JSONArray jsonArray = new JSONArray();
        for(Department department:list)
        {
            Map<String,Object> map = new HashMap<>();
            map.put("did",department.getId());
            map.put("name",department.getName());
            map.put("open",true);
            map.put("children",getTree(listByParent(department.getId())));
            jsonArray.add(map);
        }
        return jsonArray;
    }

    public String getTreeJson(int eid)
    {
        List<Manage> manages = manageService.getByEid(eid);
        List<Department> departments = new ArrayList<>();
        for(Manage manage:manages)
        {
            departments.add(get(manage.getDepartmentId()));
        }
        JSONArray jsonArray = getTree(departments);
        return jsonArray.toJSONString();
    }

    public List<Department> listAllManageByEmployeeId(int eid)
    {
        List<Manage> manages = manageService.getByEid(eid);
        List<Department> departments = new ArrayList<>();
        for(Manage manage:manages)
        {
            departments.add(get(manage.getDepartmentId()));
            List<Department>departments1 = listAllManageByDepartmentId(manage.getDepartmentId());
            departments.addAll(departments1);
        }
        return departments;
    }
    public List<Department> listAllManageByDepartmentId(int did)
    {
        List<Department> departments=listByParent(did);
        List<Department> departments1 = new ArrayList<>();
        for(Department department:departments)
        {
            departments1.addAll(departments1);
        }
        departments.addAll(departments1);
        return departments;
    }
    public boolean isDidInDepartmentList(int did,List<Department> departments)
    {
        for(Department department:departments)
        {
            if(department.getId()==did)
                return true;
        }
        return false;
    }

    public void add(Department department)
    {
        departmentMapper.insert(department);
    }
    public void  delete(int id)
    {
        departmentMapper.deleteByPrimaryKey(id);
    }
}
