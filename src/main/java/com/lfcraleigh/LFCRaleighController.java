package com.lfcraleigh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LFCRaleighController {

    @Autowired
    NewsRepository newsRepo;

    @Autowired
    EventRepository eventRepo;

    @Autowired
    AdminRepository adminRepo;


    @RequestMapping(path = "/membership", method = RequestMethod.POST)
    public String addMember(){
        return "redirect:/confirmation";
    }

    @RequestMapping(path = "/admin-signup", method = RequestMethod.POST)
    public String addAdmin(String username, String password) throws PasswordStorage.CannotPerformOperationException {


        String hashedPass = PasswordStorage.createHash(password);

        Administrator newAdmin = new Administrator(username, hashedPass);

        adminRepo.save(newAdmin);

        return "redirect:/admin-login";
    }

    @RequestMapping(path = "/admin-login", method = RequestMethod.POST)
    public String adminLogin(String username, String password, HttpSession session, Model model) throws PasswordStorage.InvalidHashException, PasswordStorage.CannotPerformOperationException {

        Administrator admin = adminRepo.findAdministratorByUsername(username);

        if(admin != null) {
            if (PasswordStorage.verifyPassword(password, admin.getPassword())) {
                session.setAttribute("username", username);
                session.removeAttribute("badInfo");
                return "redirect:/admin-landing";
            } else {
                session.setAttribute("badInfo", "badInfo");
            }
        } else {
            session.setAttribute("badInfo", "badInfo");
        }
        return "redirect:/admin-login";
    }

    @RequestMapping(path = "manage-news", method = RequestMethod.POST)
    public String addNews(String title, String body){
        NewsItem news = new NewsItem(title, body);

        newsRepo.save(news);

        return "redirect:/manage-news";
    }

    @RequestMapping (path = "/logout", method = RequestMethod.GET)
    public String adminLogout(HttpSession session){
        session.invalidate();

        return "redirect:/";
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String getHome(Model model){

        List<NewsItem> news = newsRepo.findAllByOrderByIdDesc();
        model.addAttribute("news", news);


        return "home";
    }

    @RequestMapping(path = "/about", method = RequestMethod.GET)
    public String getAbout(){
        return "about";
    }

    @RequestMapping(path = "/membership", method = RequestMethod.GET)
    public String getMembership(){
        return "membership";
    }

    @RequestMapping(path = "/calendar", method = RequestMethod.GET)
    public String getEvents(){
        return "calendar";
    }

    @RequestMapping(path = "/admin-login", method = RequestMethod.GET)
    public String getAdminLogin(HttpSession session, Model model){
        model.addAttribute("badInfo", session.getAttribute("badInfo"));

        return "admin-login";
    }

    @RequestMapping(path = "/confirmation", method = RequestMethod.GET)
    public String paymentConfirm(){
        return "confirmation";
    }

    @RequestMapping(path = "/admin-signup", method = RequestMethod.GET)
    public String getAdminSignup(){
        return "admin-signup";
    }

    @RequestMapping(path = "/admin-landing", method = RequestMethod.GET)
    public String getAdminlanding(Model model, HttpSession session){

        Administrator admin = adminRepo.findAdministratorByUsername((String)session.getAttribute("username"));

        model.addAttribute("admin", admin);

        return "admin-landing";
    }

    @RequestMapping(path = "/member-lookup", method = RequestMethod.GET)
    public String getMemberLookup(){
        return "member-lookup";
    }

    @RequestMapping(path = "/manage-news", method = RequestMethod.GET)
    public String getManageNews(){
        return "manage-news";
    }

    @RequestMapping(path = "/manage-events", method = RequestMethod.GET)
    public String getManageEvents(){
        return "manage-events";
    }

    @RequestMapping(path = "/tweet", method = RequestMethod.GET)
    public String getTweetPage(){
        return "tweet";
    }

}
