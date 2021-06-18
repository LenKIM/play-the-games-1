package org.slipp.masil.games.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping(value = "/")
    public String indexPage(Model model){
        return "index";
    }

    @GetMapping(value = "/name")
    public String inputName(Model model){
        return "name";
    }
}
