package cn.neu.alpha.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontEndController {
    @RequestMapping("")
    public String index(){
        return "index";
    }
}
