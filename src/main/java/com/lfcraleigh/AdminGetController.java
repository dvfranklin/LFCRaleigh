package com.lfcraleigh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class AdminGetController {

    @Autowired
    AdminService adminService;

    @Autowired
    NewsService newsService;

    @Autowired
    EventService eventService;

    @Autowired
    MemberService memberService;


    @RequestMapping(path = "/admin-landing", method = RequestMethod.GET)
    public String getAdminlanding(Model model, HttpSession session){

        Administrator admin = adminService.getCurrentAdmin((String)session.getAttribute("username"));

        if(admin != null) {
            model.addAttribute("admin", admin);
            return "admin-landing";
        }

        return "redirect:/admin-login";
    }




    @RequestMapping(path = "/member-lookup", method = RequestMethod.GET)
    public String getMemberLookup(Model model, HttpSession session){
        Administrator admin = adminService.getCurrentAdmin((String)session.getAttribute("username"));
        List<Member> members = memberService.getAllMembers();


        if(admin != null) {
            model.addAttribute("admin", admin);
            model.addAttribute("members", members);
            return "member-lookup";
        }

        return "redirect:/admin-login";
    }

    @RequestMapping(path = "/manage-news", method = RequestMethod.GET)
    public String getManageNews(Model model, HttpSession session){
        Administrator admin = adminService.getCurrentAdmin((String)session.getAttribute("username"));


        List<NewsItem> news = newsService.getAllNews();

        model.addAttribute("news", news);
        model.addAttribute("now", LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));

        if(admin != null) {
            model.addAttribute("admin", admin);
            return "manage-news";
        }

        return "redirect:/admin-login";
    }

    @RequestMapping(path = "delete-news", method = RequestMethod.GET)
    public String deleteNews(int id){
        newsService.deleteSelectedNews(id);
        return "redirect:/manage-news";
    }

    @RequestMapping(path = "/edit-news", method = RequestMethod.GET)
    public String getEditNews(int id, Model model){
        NewsItem news = newsService.getSelectedNews(id);

        model.addAttribute("news", news);
        model.addAttribute("now", LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));


        return "edit-news";
    }

    @RequestMapping(path = "/manage-events", method = RequestMethod.GET)
    public String getManageEvents(Model model, HttpSession session){
        Administrator admin = adminService.getCurrentAdmin((String)session.getAttribute("username"));

        List<Event> events = eventService.getAllEvents();

        model.addAttribute("events", events);
        if(admin != null) {
            model.addAttribute("admin", admin);
            return "manage-events";
        }
        return "redirect:/admin-login";
    }

    @RequestMapping(path = "/delete-event", method = RequestMethod.GET)
    public String deleteEvent(int id, Model model, HttpSession session){
        Administrator admin = adminService.getCurrentAdmin((String)session.getAttribute("username"));

        if(admin != null) {
            model.addAttribute("admin", admin);
            eventService.deleteSelectedEvent(id);

            return "redirect:/manage-events";
        }

        return "/admin-login";
    }

    @RequestMapping(path = "/edit-event", method = RequestMethod.GET)
    public String getEditEvent(int id, Model model, HttpSession session){
        Event event = eventService.getSelectedEvent(id);
        model.addAttribute("event", event);

        Administrator admin = adminService.getCurrentAdmin((String)session.getAttribute("username"));

        if(admin != null) {
            model.addAttribute("admin", admin);
            return "edit-event";
        }

        return "redirect:/admin-login";
    }

    @RequestMapping(path = "/tweet", method = RequestMethod.GET)
    public String getTweetPage(Model model, HttpSession session){

        Administrator admin = adminService.getCurrentAdmin((String)session.getAttribute("username"));

        if(admin != null) {
            model.addAttribute("admin", admin);
            return "tweet";
        }

        return "redirect:/admin-login";
    }

    @RequestMapping(path = "/add-member", method = RequestMethod.GET)
    public String getAddMemberPage(Model model, HttpSession session){
        Administrator admin = adminService.getCurrentAdmin((String)session.getAttribute("username"));

        if(admin != null) {
            model.addAttribute("admin", admin);
            return "add-member";
        }

        return "redirect:/admin-login";

    }

    @RequestMapping(path = "/edit-member", method=RequestMethod.GET)
    public String getEditMemberPage(int id, Model model, HttpSession session){
        Administrator admin = adminService.getCurrentAdmin((String)session.getAttribute("username"));

        if(admin != null){
            model.addAttribute("admin", admin);
            Member member = memberService.getMemberById(id);
            model.addAttribute("member", member);
            return "edit-member";
        }

        return "redirect:/admin-login";
    }
}
