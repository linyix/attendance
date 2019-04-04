package controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Clazz;
import pojo.Employee;
import service.ClazzService;
import service.DepartmentService;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class ClazzController {

    @Autowired
    ClazzService clazzService;
    @Autowired
    DepartmentService departmentService;

    /*
    @RequestMapping("department/{did}/clazz")
    public String list(Model model,@PathVariable("did") int did)
    {
        List<Clazz> clazzes= clazzService.list(did);
        model.addAttribute("clazzes",clazzes);
        return "clazz2";
    }
    */
    @RequestMapping("clazz")
    public String tree(Model model )
    {
        int eid=1;
        model.addAttribute("tree",departmentService.getTreeJson(eid));
        return "clazz";
    }

    @RequestMapping(value ="department/{did}/clazz" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getClazzByDepartment(@PathVariable("did") int did ,int page, int limit) throws Exception
    {
        if(limit ==0)
            limit=10;
        PageHelper.offsetPage(0, limit);

        JSONArray jsonArray = new JSONArray();
        List<Clazz> clazzes = clazzService.list(did);
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("HH:mm");
        for(Clazz clazz:clazzes)
        {
            Map<String,Object > map = new HashMap<>();
            map.put("id",clazz.getId());
            map.put("name",clazz.getName());
            map.put("starttime",simpleDateFormat.format(clazz.getStartTime()));
            map.put("endtime",simpleDateFormat.format(clazz.getEndTime()));
            map.put("startbf",clazz.getBfStart());
            map.put("endaf",clazz.getAfEnd());
            jsonArray.add(map);
        }
        JSONObject json = new JSONObject();
        json.put("code",0);
        json.put("msg","");
        json.put("count",(int) new PageInfo<>(clazzes).getTotal());
        json.put("data", jsonArray);
        System.out.println(json.toJSONString());
        return json.toJSONString();
    }
}
