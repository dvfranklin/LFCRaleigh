package com.lfcraleigh;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public String getAccessToken(Map<String, String> sdkConfig) throws PayPalRESTException {

        String accessToken = new OAuthTokenCredential(System.getenv().get("CLIENT_ID"), System.getenv().get("SECRET"), sdkConfig).getAccessToken();

        return accessToken;
    }

    public Payment submitPayment(Map<String, String> sdkConfig, String accessToken, CreditCard creditCard) throws PayPalRESTException {
        APIContext apiContext = new APIContext(accessToken);
        apiContext.setConfigurationMap(sdkConfig);

        /*creditCard.setType("discover");
        creditCard.setNumber("6011587807416524");
        creditCard.setExpireMonth(11);
        creditCard.setExpireYear(2016);
        creditCard.setCvv2(572);
        creditCard.setFirstName("First");
        creditCard.setLastName("Last");*/

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

    public void saveMember(Member member, Payment createdPayment){
        if(createdPayment.getState().equals("approved")){
            member.setCurrentDues(true);
            memberRepo.save(member);
        }
    }

}
