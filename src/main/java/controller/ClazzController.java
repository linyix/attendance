package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Clazz;
import service.ClazzService;

@RequestMapping("Clsss")
@Controller
public class ClazzController {

    @Autowired
    ClazzService clazzService;
    @RequestMapping("")
    @ResponseBody
    public String test()
    {
        Clazz clazz= clazzService.test();
        System.out.println(clazz.getStartTime());
        return "";
    }
}
