package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@ThreadSafe
@Controller
public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}