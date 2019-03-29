package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import service.DepartmentService;

@Controller
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;


    @RequestMapping("test")
    public String test()
    {
        return "test";
    }
    @RequestMapping("department")
    public String list(Model model)
    {
        model.addAttribute("department",departmentService.list());
        return "department";
    }


}
