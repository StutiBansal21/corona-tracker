package com.labproject.covidtracker.controllers;

import com.labproject.covidtracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/* to render the stats in html page and access the url. to make the page show up in frontend. controller. This is what
gets accessed when we load the home url. We are not making this class as restController as we do not want the response/
return as JSON value. We are returning a name which pts to a template.
we need to use model. When we call the controller we can do anything on the page. when rendering the html page we can
access things from model and construct html page accordingly.
Now if we want to show all the data like country region n confirmed cases we need to get that value from service class
and put that in the model.
 */
@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    //this method should be mapped to a html file called home.html
    @GetMapping("/")
    public String home(Model model)
    {
        model.addAttribute("LocationStats",coronaVirusDataService.getAllLocation());//giving the value TESt to testname. which we can access using thymeleaf
        return "home";
    }
}
