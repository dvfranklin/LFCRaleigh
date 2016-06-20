package com.lfcraleigh;

import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PostController {

    @Autowired
    NewsRepository newsRepo;

    @Autowired
    EventRepository eventRepo;

    @Autowired
    AdminRepository adminRepo;

    @Autowired
    MemberService memberService;


    @RequestMapping(path = "/membership", method = RequestMethod.POST)
    public String addMember(Member member, CreditCard creditCard, String mailingList) throws PayPalRESTException {

        Map<String, String> sdkConfig = new HashMap<String, String>();
        sdkConfig.put("mode", "sandbox");

        String accessToken = memberService.getAccessToken(sdkConfig);
        Payment createdPayment = memberService.submitPayment(sdkConfig, accessToken, creditCard);

        memberService.saveMember(member, createdPayment);

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

    @RequestMapping(path = "add-news", method = RequestMethod.POST)
    public String addNews(String title, String body, String dateTime){
        NewsItem news = new NewsItem(title, body, LocalDateTime.parse(dateTime));

        newsRepo.save(news);

        return "redirect:/manage-news";
    }

    @RequestMapping(path = "/add-event", method = RequestMethod.POST)
    public String addEvent(String description, String dateTime, String location){
        Event event = new Event(description, dateTime, location);

        eventRepo.save(event);

        return "redirect:/manage-events";
    }

    @RequestMapping(path = "/edit-event", method = RequestMethod.POST)
    public String editEvent(int id, String dateTime, String location, String description){
        Event event = eventRepo.findOne(id);
        event.setDateTime(dateTime);
        event.setLocation(location);
        event.setDescription(description);

        eventRepo.save(event);

        return "redirect:/manage-events";
    }
}
