package com.lfcraleigh;

import com.ecwid.mailchimp.MailChimpClient;
import com.ecwid.mailchimp.MailChimpException;
import com.ecwid.mailchimp.MailChimpObject;
import com.ecwid.mailchimp.method.v2_0.lists.Email;
import com.ecwid.mailchimp.method.v2_0.lists.SubscribeMethod;
import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Controller
public class PostController {

    @Autowired
    MemberService memberService;

    @Autowired
    TweetService tweetService;

    @Autowired
    NewsService newsService;

    @Autowired
    EventService eventService;

    @Autowired
    AdminService adminService;


    @RequestMapping(path = "/membership", method = RequestMethod.POST)
    public String addMember(Member member, CreditCard creditCard, String mailingList) throws IOException, MailChimpException {

        Map<String, String> sdkConfig = new HashMap<String, String>();
        sdkConfig.put("mode", "live");

        try {
            String accessToken = memberService.getAccessToken(sdkConfig);
            Payment createdPayment = memberService.submitPayment(sdkConfig, accessToken, creditCard);

            memberService.saveMemberPaypal(member, createdPayment);

            return "redirect:/confirmation";
        } catch (PayPalRESTException e){
            // add flash attribute
            return "redirect:/membership";
        }
    }

    @RequestMapping(path = "/add-member", method = RequestMethod.POST)
    public String addMemberManually(Member member){
        memberService.saveMember(member);
        return "redirect:/member-lookup";
    }

    @RequestMapping(path = "/admin-signup", method = RequestMethod.POST)
    public String addAdmin(String username, String password) throws PasswordStorage.CannotPerformOperationException {


        String hashedPass = PasswordStorage.createHash(password);

        Administrator newAdmin = new Administrator(username, hashedPass);

        adminService.saveAdmin(newAdmin);

        return "redirect:/admin-login";
    }

    @RequestMapping(path = "/admin-login", method = RequestMethod.POST)
    public String adminLogin(String username, String password, HttpSession session, Model model) throws PasswordStorage.InvalidHashException, PasswordStorage.CannotPerformOperationException {

        Administrator admin = adminService.getCurrentAdmin(username);

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

        newsService.saveNews(news);

        return "redirect:/manage-news";
    }

    @RequestMapping(path = "/add-event", method = RequestMethod.POST)
    public String addEvent(String title, String start){
        Event event = new Event(title, start);

        eventService.saveEvent(event);

        return "redirect:/manage-events";
    }

    @RequestMapping(path = "/edit-event", method = RequestMethod.POST)
    public String editEvent(int id, String title, String start){
        Event event = eventService.getSelectedEvent(id);
        event.setTitle(title);
        event.setStart(start);

        eventService.saveEvent(event);

        return "redirect:/manage-events";
    }

    @RequestMapping(path = "/edit-news", method = RequestMethod.POST)
    public String editNews(int id, String title, String body, String dateTime){
        NewsItem news = newsService.getSelectedNews(id);
        news.setTitle(title);
        news.setBody(body);
        news.setDateTime(LocalDateTime.parse(dateTime));

        newsService.saveNews(news);

        return "redirect:/manage-news";
    }

    @RequestMapping(path = "/edit-member", method = RequestMethod.POST)
    public String editMember(int id, String firstName, String lastName, String email, boolean currentDues, boolean joinMailingList, boolean receivedSwag){
        Member member = memberService.getMemberById(id);
        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setEmail(email);
        member.setCurrentDues(currentDues);
        member.setJoinMailingList(joinMailingList);
        member.setReceivedSwag(receivedSwag);

        memberService.saveMember(member);

        return "redirect:/member-lookup";
    }


    @RequestMapping(path = "/tweet", method = RequestMethod.POST)
    public String postTweet(String status) {

        tweetService.postTweet(status);

        return "redirect:/tweet";
    }


    @RequestMapping(path = "/subscribe", method = RequestMethod.POST)
    public String addToMailingList(Member member) throws IOException, MailChimpException {

        memberService.subscribeMailingList(member);

        return "redirect:/";
    }
}
