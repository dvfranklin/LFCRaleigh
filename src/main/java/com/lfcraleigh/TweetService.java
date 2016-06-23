package com.lfcraleigh;


import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import java.io.IOException;


@Service
public class TweetService {


    static String consumerKeyStr = System.getenv().get("TWT_CONSUMER_KEY");
    static String consumerSecretStr = System.getenv().get("TWT_CONSUMER_SECRET");
    static String accessTokenStr = System.getenv().get("TWT_ACCESS_TOKEN");
    static String accessTokenSecretStr = System.getenv().get("TWT_ACCESS_SECRET");


    public void postTweet(String status) {
        try {
            Twitter twitter = new TwitterFactory().getInstance();

            twitter.setOAuthConsumer(consumerKeyStr, consumerSecretStr);
            AccessToken accessToken = new AccessToken(accessTokenStr,
                    accessTokenSecretStr);

            twitter.setOAuthAccessToken(accessToken);

            twitter.updateStatus(status);

            System.out.println("Successfully updated the status in Twitter.");
        } catch (TwitterException te) {
            te.printStackTrace();
        }
    }
}
