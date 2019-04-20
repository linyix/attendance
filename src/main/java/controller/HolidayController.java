package controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class HolidayController {

    @RequestMapping(value="department/{did}/holiday",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String departmentCount(@PathVariable("did") int did , int page, int limit) throws Exception
    {
        JSONArray jsonArray = new JSONArray();
        Map<String,Object > map = new HashMap<>();
        map.put("id","4");
        map.put("name","五一");
        map.put("starttime","2019-05-01");
        map.put("endtime","2019-05-04");
        jsonArray.add(map);


        map = new HashMap<>();
        map.put("id","3");
        map.put("name","清明节");
        map.put("starttime","2019-04-05");
        map.put("endtime","2019-04-05");
        jsonArray.add(map);

        map = new HashMap<>();
        map.put("id","2");
        map.put("name","春节");
        map.put("starttime","2019-02-01");
        map.put("endtime","2019-02-10");
        jsonArray.add(map);

        map = new HashMap<>();
        map.put("id","1");
        map.put("name","元旦");
        map.put("starttime","2019-01-01");
        map.put("endtime","2019-01-02");
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
