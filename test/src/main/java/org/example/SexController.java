package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SexController {


        @GetMapping("sex")

        public String index(){
            return "sex";
        }
}