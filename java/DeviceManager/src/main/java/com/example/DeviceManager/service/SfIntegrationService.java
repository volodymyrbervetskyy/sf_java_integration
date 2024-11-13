package com.example.DeviceManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

@Service
public class SfIntegrationService {

    private final String sfOAuth2ClientRegistrationId = "sf";

    @Value("${SF_BASE_URL}")
    private String baseUrl;

    private final WebClient webClient;

    @Autowired
    public SfIntegrationService(WebClient webClient){
        this.webClient = webClient;
    }

    public ResponseEntity<String> upsertRecord(String objectName, String externalIdFieldName, String externalIdValue, String body){
        ResponseEntity<String> response = webClient
            .patch()
            .uri(baseUrl + "/services/data/v58.0/sobjects/{0}/{1}/{2}", objectName, externalIdFieldName, externalIdValue)
            .attributes(clientRegistrationId(sfOAuth2ClientRegistrationId))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .retrieve()
            .toEntity(String.class)
            .block();

        if(!response.getStatusCode().is2xxSuccessful()){
            System.out.println("status code: " + response.getStatusCode().toString());
            System.out.println("response: " + response.toString());
            throw new RuntimeException("Error during executing request.");
        }

        return response;
    }

}
