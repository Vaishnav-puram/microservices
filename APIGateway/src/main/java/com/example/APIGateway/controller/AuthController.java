package com.example.APIGateway.controller;

import com.example.APIGateway.model.AuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private Logger logger= LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/login")
    public ResponseEntity<AuthResponse> login(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient oAuth2AuthorizedClient , @AuthenticationPrincipal OidcUser oidcUser, Model model){
        logger.info("user email id"+oidcUser.getEmail());
        AuthResponse authResponse= AuthResponse.builder()
                .userId(oidcUser.getEmail())
                .accessToken(oAuth2AuthorizedClient.getAccessToken().getTokenValue())
                .refreshToken(oAuth2AuthorizedClient.getRefreshToken().getTokenValue())
                .expireAt(oAuth2AuthorizedClient.getAccessToken().getExpiresAt().getEpochSecond())
                .authorities(oidcUser.getAuthorities().stream().map(grantedAuthority -> grantedAuthority.getAuthority()).collect(Collectors.toList()))
                .build();
        return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.OK);
    }
}
