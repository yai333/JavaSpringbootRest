package com.example.awesomeprject;

import java.util.LinkedList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.microsoft.graph.logger.DefaultLogger;
import com.microsoft.graph.logger.LoggerLevel;
import com.microsoft.graph.models.extensions.DateTimeTimeZone;
import com.microsoft.graph.models.extensions.IGraphServiceClient;
import com.microsoft.graph.requests.extensions.GraphServiceClient;
import com.microsoft.graph.requests.extensions.ICalendarGetScheduleCollectionPage;
import org.apache.log4j.Logger;

@RestController
public class GraphController {

    private IGraphServiceClient graphClient = null;
    private SimpleAuthProvider authProvider = null;
    private String accessToken;
    private String timeZone = "AUS Eastern Standard Time";


    @Value("${appId}")
    private String appId;

    @Value("${scopes}")
    private String scopes;

    @Value("${appSecret}")
    private String appSecret;

    @Value("${authority}")
    private String authority;

    static Logger log = Logger.getLogger(GraphController.class.getName());

    @PostConstruct  
    public void GraphController() {
        Authentication.initialize(appId, authority, appSecret);
        accessToken = Authentication.getUserAccessToken(scopes);   
    }

    private void ensureGraphClient() {
        // Create the auth provider
        authProvider = new SimpleAuthProvider(accessToken);
        // Create default logger to only log errors
        DefaultLogger logger = new DefaultLogger();
        logger.setLoggingLevel(LoggerLevel.DEBUG);
        // Build a Graph client
        graphClient = GraphServiceClient.builder()
            .authenticationProvider(authProvider)
            .logger(logger)
            .buildClient();   
    }

    private ICalendarGetScheduleCollectionPage getFreeCalendarSchedule(){
        ensureGraphClient();
        LinkedList<String> scheduleList = new LinkedList<String>();
        scheduleList.add("dbadmin@aiyihackhotmail.onmicrosoft.com");
        scheduleList.add("aiyi_hack_hotmail.com#EXT#@aiyihackhotmail.onmicrosoft.com");
        scheduleList.add("yanyan@aiyihackhotmail.onmicrosoft.com");

        DateTimeTimeZone startTime = new DateTimeTimeZone();
        startTime.dateTime="2021-03-16T09:00";
        startTime.timeZone=timeZone;

        DateTimeTimeZone endTime = new DateTimeTimeZone();
        endTime.dateTime="2021-03-16T19:00";
        endTime.timeZone=timeZone;

        try {
            return graphClient
                .users("dbadmin@aiyihackhotmail.onmicrosoft.com")
                .calendar()
                .getSchedule(scheduleList, endTime, startTime, 30)
                .buildRequest()
                .post();
            
        } catch (Exception ex){
            log.info(ex);
            return null;
        }
    }

    @GetMapping("/free-schedule")
    JsonObject getSchedule() {
        ICalendarGetScheduleCollectionPage schedule = getFreeCalendarSchedule();
        return schedule.getRawObject();
	}
}
