package com.example.awesomeprject;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;

import org.json.JSONTokener;
import org.json.JSONObject;

import java.net.*;  
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

@Configuration
public class DataSourceConfig {
    public static Logger logger = Logger.getLogger("global");

    @Value("${db.host}")
    private String Host;

    @Value("${db.user}")
    private String User;

    @Value("${db.name}")
    private String Database;

    @Value("${client_id}")
    private String ClientId;

    @Bean
    @RefreshScope
    public DataSource getDataSource() {

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

        try {
            URL url = new URL("http://169.254.169.254/metadata/identity/oauth2/token?api-version=2018-02-01&resource=https%3A%2F%2Fossrdbms-aad.database.windows.net&client_id=" + ClientId);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Metadata", "true");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            JSONTokener tokener = new JSONTokener(in);
            JSONObject json = new JSONObject(tokener);
            String accessToken = json.getString("access_token");

            logger.info("accessToken: " +  accessToken);

            dataSourceBuilder.url(this.Host);
            dataSourceBuilder.username(this.User);
            dataSourceBuilder.password(accessToken);
            
            in.close();
            con.disconnect();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return dataSourceBuilder.build();
    }
}