package service;

import com.alibaba.fastjson.JSONArray;
import mapper.ManageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Department;
import pojo.Manage;
import pojo.ManageExample;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManageService {
    @Autowired
    ManageMapper manageMapper;

    public List<Manage> getByEid(int eid)
    {
        ManageExample manageExample = new ManageExample();
        manageExample.createCriteria().andEmployeeIdEqualTo(eid);
        return manageMapper.selectByExample(manageExample);
    }


}
