package com.labproject.covidtracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/* to render the stats in html page and access the url. to make the page show up in frontend. controller. This is what
gets accessed when we load the home url. We are not making this class as restController as we do not want the response/
return as JSON value. We are returning a name which pts to a template.
 */
@Controller
public class HomeController {

    //this method should be mapped to a html file called home.html
    @GetMapping("/")
    public String home()
    {
        return "home";
    }
}
