package com.conceal.plaid;

import com.conceal.plaid.requests.LinkTokenRequest;
import com.plaid.client.ApiClient;
import com.plaid.client.model.*;
import com.plaid.client.request.PlaidApi;
import com.conceal.plaid.respones.*;
import com.conceal.plaid.requests.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.Response;


import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

@RestController
@RequestMapping("/api/plaid")
@CrossOrigin(origins = "http://localhost:3000")
public class PlaidController {

    private final PlaidApi plaidClient;

    @Value("${plaid.clientId}")
    private String clientId;

    @Value("${plaid.secret}")
    private String secret;

    //Should be stored in persisted DB
    private String accessToken;

    //Should be stored in persisted DB
    private String itemId;

    public PlaidController(@Value("${plaid.clientId}") String clientId, @Value("${plaid.secret}") String secret) {
        this.clientId = clientId;
        this.secret = secret;

        HashMap<String, String> apiKeys = new HashMap<String, String>();
        apiKeys.put("clientId", clientId);
        apiKeys.put("secret", secret);
        ApiClient apiClient = new ApiClient(apiKeys);
        apiClient.setPlaidAdapter(ApiClient.Sandbox);
        plaidClient = apiClient.createService(PlaidApi.class);
    }


    @PostMapping ("/create-link-token")
    public ResponseEntity<LinkTokenResponse> createLinkToken(@RequestBody LinkTokenRequest tokenRequest) throws IOException {
        LinkTokenCreateRequestUser user = new LinkTokenCreateRequestUser()
                .clientUserId(this.clientId)
                .legalName(tokenRequest.getLegalName())
                .phoneNumber(tokenRequest.getPhoneNumber())
                .emailAddress(tokenRequest.getEmailAddress());

        LinkTokenCreateRequest request = new LinkTokenCreateRequest()
                .user(user)
                .clientName("Conceal")
                .language(tokenRequest.getLanguage())
                .products(Arrays.asList(Products.AUTH))
                .countryCodes(Arrays.asList(CountryCode.US, CountryCode.CA));

        Response<LinkTokenCreateResponse> response  = plaidClient.linkTokenCreate(request).execute();
        if(!response.isSuccessful()) {
            System.out.println(response.errorBody().string());
        }

        String linkToken = response.body().getLinkToken();
        LinkTokenResponse linkTokenResponse = new LinkTokenResponse(linkToken);

        return ResponseEntity.ok(linkTokenResponse);
    }

    @PostMapping("/set-access-token")
    public ResponseEntity<SetAccessTokenResponse> setAccessToken(@RequestBody SetAccessTokenRequest accessTokenRequest) throws IOException {
        ItemPublicTokenExchangeRequest request = new ItemPublicTokenExchangeRequest()
                .publicToken(accessTokenRequest.getPublicToken());
        System.out.println(accessTokenRequest.getPublicToken());
        Response<ItemPublicTokenExchangeResponse> response = plaidClient
                .itemPublicTokenExchange(request)
                .execute();
        if(!response.isSuccessful()) {
            String errorMessage = "An error occurred: " + response.errorBody().string();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SetAccessTokenResponse(errorMessage));
        }
        String accessToken = response.body().getAccessToken();
        System.out.println(accessToken);
        this.accessToken = accessToken;
        this.itemId = response.body().getItemId();
        return ResponseEntity.ok(new SetAccessTokenResponse(this.accessToken, this.itemId));
    }
}
