package com.lfcraleigh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class GetController {

    @Autowired
    NewsRepository newsRepo;

    @Autowired
    EventRepository eventRepo;

    @Autowired
    AdminRepository adminRepo;

    @Autowired
    AdminService adminService;


    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String getHome(Model model){

        List<NewsItem> news = newsRepo.findAllByOrderByIdDesc();

        for(NewsItem n : news){
            n.dateTimeString = n.dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
        }

        model.addAttribute("news", news);


        return "home";
    }

    @RequestMapping(path = "/about", method = RequestMethod.GET)
    public String getAbout(){
        return "about";
    }

    @RequestMapping(path = "/membership", method = RequestMethod.GET)
    public String getMembership(){
        return "redirect:https://lfcraleigh.herokuapp.com/membership";
    }

    @RequestMapping(path = "/calendar", method = RequestMethod.GET)
    public String getEvents(Model model){

        List<Event> events = eventRepo.findAllByOrderByIdDesc();

        model.addAttribute("events", events);

        return "calendar";
    }

    @RequestMapping(path = "/admin-login", method = RequestMethod.GET)
    public String getAdminLogin(HttpSession session, Model model){
        model.addAttribute("badInfo", session.getAttribute("badInfo"));

        return "admin-login";
    }

    @RequestMapping (path = "/logout", method = RequestMethod.GET)
    public String adminLogout(HttpSession session){
        session.invalidate();

        return "redirect:/";
    }

    @RequestMapping(path = "/confirmation", method = RequestMethod.GET)
    public String paymentConfirm(){
        return "confirmation";
    }

    @RequestMapping(path = "/admin-signup", method = RequestMethod.GET)
    public String getAdminSignup(){
        return "admin-signup";
    }

}
