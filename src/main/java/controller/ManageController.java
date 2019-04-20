package controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.DepartmentService;

import java.util.*;

@Controller
public class ManageController {

    @Autowired
    DepartmentService departmentService;

    @RequestMapping("manage")
    public String manage(Model model)
    {

        int eid=1;
        model.addAttribute("tree",departmentService.getTreeJson(eid));
        return "manage";
    }





    @RequestMapping(value="department/{did}/manage",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String manageJson(@PathVariable("did") int did , int page, int limit) throws Exception
    {
        JSONArray jsonArray = new JSONArray();
        Map<String,Object > map = new HashMap<>();
        map.put("employeenumber","a0002");
        map.put("employeename","张五");
        map.put("departmentname","人事部");
        map.put("level","管理员");
        jsonArray.add(map);


        map = new HashMap<>();
        map.put("employeenumber","a0003");
        map.put("employeename","张六");
        map.put("departmentname","人事部");
        map.put("level","管理员");
        jsonArray.add(map);

        map = new HashMap<>();
        map.put("employeenumber","a0004");
        map.put("employeename","张琪");
        map.put("departmentname","人事部");
        map.put("level","管理员");
        jsonArray.add(map);

        map = new HashMap<>();
        map.put("employeenumber","a0005");
        map.put("employeename","张八");
        map.put("departmentname","人事部");
        map.put("level","管理员");
        jsonArray.add(map);




        JSONObject json = new JSONObject();
        json.put("code",0);
        json.put("msg","");
        json.put("count",4);
        json.put("data", jsonArray);
        System.out.println(json.toJSONString());
        return json.toJSONString();
    }
}
