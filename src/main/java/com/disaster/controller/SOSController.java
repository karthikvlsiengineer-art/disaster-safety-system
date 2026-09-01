package com.disaster.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SOSController {

    @GetMapping("/sos")
    public String sosPage() {
        return "sos";
    }
}
