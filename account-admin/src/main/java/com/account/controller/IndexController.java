package com.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author hope
 * @since 2022/5/4
 */
@Controller
public class IndexController {

    @GetMapping("index")
    public String index(){
        return "index";
    }
}
