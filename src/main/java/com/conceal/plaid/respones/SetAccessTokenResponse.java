package com.conceal.plaid.respones;

public class SetAccessTokenResponse extends ResponseBase {
    private String accessToken;
    private String itemId;

    public SetAccessTokenResponse(String error) {
        this.setErrorMessage(error);
    }

    public SetAccessTokenResponse(String accessToken, String itemId) {
        this.accessToken = accessToken;
        this.itemId = itemId;
    }


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
