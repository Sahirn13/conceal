package com.conceal.plaid.respones;

public class LinkTokenResponse extends ResponseBase {
        private String linkToken;

        public LinkTokenResponse(String linkToken) {
            this.linkToken = linkToken;
        }

        public String getLinkToken() {
            return linkToken;
        }

        public void setLinkToken(String linkToken) {
            this.linkToken = linkToken;
        }
}
