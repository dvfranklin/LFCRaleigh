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
    NewsRepository newsRepo;

    @Autowired
    EventRepository eventRepo;

    @Autowired
    AdminRepository adminRepo;

    @Autowired
    MemberService memberService;

    @Autowired
    TweetService tweetService;


    @RequestMapping(path = "/membership", method = RequestMethod.POST)
    public String addMember(Member member, CreditCard creditCard, String mailingList) {

        Map<String, String> sdkConfig = new HashMap<String, String>();
        sdkConfig.put("mode", "live");

        try {
            String accessToken = memberService.getAccessToken(sdkConfig);
            Payment createdPayment = memberService.submitPayment(sdkConfig, accessToken, creditCard);

            memberService.saveMember(member, createdPayment);

            return "redirect:/confirmation";
        } catch (PayPalRESTException e){
            // add flash attribute
            return "redirect:/membership";
        }
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


    @RequestMapping(path = "/tweet", method = RequestMethod.POST)
    public String postTweet(String status) {

        tweetService.postTweet(status);

        return "redirect:/tweet";
    }

    public static class MergeVars extends MailChimpObject {

        @Field
        public String EMAIL, FNAME, LNAME;

        public MergeVars() {
        }

        public MergeVars(String email, String fname, String lname) {
            this.EMAIL = email;
            this.FNAME = fname;
            this.LNAME = lname;
        }
    }

    @RequestMapping(path = "/subscribe", method = RequestMethod.POST)
    public String addToMailingList(Member member) throws IOException, MailChimpException {

        MailChimpClient mailChimpClient = new MailChimpClient();

        SubscribeMethod subscribeMethod = new SubscribeMethod();
        subscribeMethod.apikey = System.getenv().get("MAIL_API_KEY");
        subscribeMethod.id = System.getenv().get("MAIL_LIST_ID");
        subscribeMethod.email = new Email();
        subscribeMethod.email.email = member.getEmail();
        subscribeMethod.update_existing = true;
        subscribeMethod.merge_vars = new MergeVars(member.getEmail(), member.getFirstName(), member.getLastName());
        mailChimpClient.execute(subscribeMethod);


        mailChimpClient.close();

        return "redirect:/";
    }
}
