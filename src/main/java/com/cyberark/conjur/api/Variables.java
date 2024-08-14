package com.cyberark.conjur.api;

import javax.net.ssl.SSLContext;

import com.cyberark.conjur.api.clients.ResourceClient;

public class Variables {

    private ResourceClient resourceClient;

    public Variables(Credentials credentials,String source) {
        this(credentials, null,source);
    }

    public Variables(Credentials credentials, SSLContext sslContext,String source) {
        resourceClient =
                new ResourceClient(credentials, Endpoints.fromCredentials(credentials,source), sslContext);
    }

    public Variables(Token token) {
        this(token, null);
    }
    
    public Variables(Token token, SSLContext sslContext) {
        this(token, null, null);
    }

    public Variables(Token token, SSLContext sslContext,String source) {
        resourceClient = new ResourceClient(token, Endpoints.fromSystemProperties(), sslContext);
    }

    public String retrieveSecret(String variableId) {
        return resourceClient.retrieveSecret(variableId);
    }

    public void addSecret(String variableId, String secret){
        resourceClient.addSecret(variableId, secret);
    }
}
