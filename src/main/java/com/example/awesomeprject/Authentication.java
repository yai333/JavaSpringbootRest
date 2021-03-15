package com.example.awesomeprject;

import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ClientCredentialParameters;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.IClientCredential;
import com.microsoft.aad.msal4j.SilentParameters;
import com.microsoft.aad.msal4j.MsalException;
import java.net.MalformedURLException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class Authentication {
    private static String applicationId;
    private static String authority;
    private static String clientSecret;

    public static void initialize(String applicationId, String authority, String clientSecret){
        Authentication.authority=authority;
        Authentication.applicationId=applicationId;
        Authentication.clientSecret=clientSecret;
    }

    public static String getUserAccessToken(String scopes){
        if (applicationId == null){
            return null;
        }
        final String[] appScopes = scopes.split(",");

        Set<String> scopeSet = new HashSet<>();
        Collections.addAll(scopeSet, appScopes);
        ConfidentialClientApplication app;  

        try{
            IClientCredential cred = ClientCredentialFactory.createFromSecret(clientSecret);            
            app =  ConfidentialClientApplication.builder(applicationId, cred).authority(authority).build();
        }
        catch (MalformedURLException e){
            return null;
        }

        IAuthenticationResult result; 

        try{
           
            SilentParameters silentParameters = SilentParameters.builder(scopeSet).build();
            result = app.acquireTokenSilently(silentParameters).join();

        }
        catch(Exception ex){
            if(ex.getCause() instanceof MsalException){
                ClientCredentialParameters parameters = ClientCredentialParameters.builder(scopeSet).build();
                result = app.acquireToken(parameters).join();
            }
            else{
                return null;
            }
        }

        if (result != null){
            return result.accessToken();
        }

        return null;
    }
}
