package com.example.demo.oauth;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OAuthToken {
    private String token_type;
    private String access_token;
    private Integer expires_in;
    private String refresh_token;
    private Integer refresh_token_expires_in;
    private String scope;

}
