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
}
