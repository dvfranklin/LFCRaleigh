package com.lfcraleigh;

import com.ecwid.mailchimp.MailChimpClient;
import com.ecwid.mailchimp.MailChimpException;
import com.ecwid.mailchimp.MailChimpObject;
import com.ecwid.mailchimp.method.v2_0.lists.Email;
import com.ecwid.mailchimp.method.v2_0.lists.SubscribeMethod;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberService {


    @Autowired
    MemberRepository memberRepo;


    public List<Member> getAllMembers(){
        return memberRepo.findAllByOrderByIdAsc();
    }

    public Member getMemberById(int id){
        return memberRepo.findOne(id);
    }

    public String getAccessToken(Map<String, String> sdkConfig) throws PayPalRESTException {

        String accessToken = new OAuthTokenCredential(System.getenv().get("CLIENT_ID"), System.getenv().get("SECRET"), sdkConfig).getAccessToken();

        return accessToken;
    }

    public Payment submitPayment(Map<String, String> sdkConfig, String accessToken, CreditCard creditCard) throws PayPalRESTException {
        APIContext apiContext = new APIContext(accessToken);
        apiContext.setConfigurationMap(sdkConfig);


        FundingInstrument fundingInstrument = new FundingInstrument();
        fundingInstrument.setCreditCard(creditCard);

        List<FundingInstrument> fundingInstrumentList = new ArrayList<FundingInstrument>();
        fundingInstrumentList.add(fundingInstrument);

        Payer payer = new Payer();
        payer.setFundingInstruments(fundingInstrumentList);
        payer.setPaymentMethod("credit_card");

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal("20");

        Transaction transaction = new Transaction();
        transaction.setDescription("2016-17 Membership Renewal");
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        return payment.create(apiContext);

    }

    public void saveMemberPaypal(Member member, Payment createdPayment) throws IOException, MailChimpException {
        if(createdPayment.getState().equals("approved")){
            member.setCurrentDues(true);
            memberRepo.save(member);
        }

        if(member.joinMailingList ==true){
            subscribeMailingList(member);
        }
    }

    public void saveMember(Member member){
        memberRepo.save(member);
    }



    public void subscribeMailingList(Member member) throws IOException, MailChimpException {
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
    }

}
