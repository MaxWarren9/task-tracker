package com.example.demo.client;

import com.example.demo.exception.TaskException;
import com.example.demo.model.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class UserClient {

    private final RestTemplate restTemplate;

    @Value("${integration.user-service.url}")
    private String userServiceUrl;

    public UserResponseDto getUserById(Long userId) {
        try {
            return restTemplate.getForObject(
                    userServiceUrl + "/users/" + userId,
                    UserResponseDto.class
            );
        } catch (HttpClientErrorException.NotFound ex) {
            throw new TaskException("User not found: " + userId, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            throw new TaskException("User service is unavailable", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
