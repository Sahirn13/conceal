package com.conceal.plaid.requests;

public class SetAccessTokenRequest {
    private String publicToken;

    public String getPublicToken() {
        return this.publicToken;
    }

    public void setPublicToken(String publicToken) {
        this.publicToken = publicToken;
    }
}
