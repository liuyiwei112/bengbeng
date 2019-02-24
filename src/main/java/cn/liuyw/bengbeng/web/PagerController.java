package cn.liuyw.bengbeng.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by liuyw on 19/2/18.
 */
@Controller
public class PagerController {

    @RequestMapping("/index")
    public String toIndex(){
        return "/index";
    }


}
