package com.example.userService.configuration;

import com.example.userService.configuration.interceptors.RestTemplateInterceptor;
import org.apache.hc.core5.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpRequestInterceptorList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyConfig {
    @Autowired
    ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        RestTemplate restTemplate= new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptorList=new ArrayList<>();
        interceptorList.add(new RestTemplateInterceptor(oAuth2AuthorizedClientManager(clientRegistrationRepository,oAuth2AuthorizedClientRepository)));
        restTemplate.setInterceptors(interceptorList);
        return restTemplate;

    }

    //declaring bean ofOAuth2AuthorizedClientManager
    @Bean
    public OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager(ClientRegistrationRepository registrationRepository, OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository){
        OAuth2AuthorizedClientProvider provider= OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build();
        DefaultOAuth2AuthorizedClientManager defaultOAuth2AuthorizedClientManager=new DefaultOAuth2AuthorizedClientManager(registrationRepository,oAuth2AuthorizedClientRepository);
        defaultOAuth2AuthorizedClientManager.setAuthorizedClientProvider(provider);
        return defaultOAuth2AuthorizedClientManager;

    }
}
