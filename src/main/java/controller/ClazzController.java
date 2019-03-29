package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Clazz;
import service.ClazzService;

import java.util.List;


@Controller
public class ClazzController {

    @Autowired
    ClazzService clazzService;
    @RequestMapping("department/{did}/clazz")
    public String list(Model model,@PathVariable("did") int did)
    {
        List<Clazz> clazzes= clazzService.list(did);
        model.addAttribute("clazzes",clazzes);
        return "clazz";
    }
}
