package com.example.APIGateway.model;

import lombok.*;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String userId;
    private String accessToken;
    private String refreshToken;
    private long expireAt;
    private Collection<String> authorities;
}
