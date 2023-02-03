package com.test.services;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import com.test.exceptions.RestTemplateResponseErrorHandler;
import lombok.Data;


@Component
public class ServiceTestService {

    @Value("${service.service-test.url}")
    private String urlApi;
    @Value("${service.service-test.clientId}")
    private String clientId;
	@Value("${service.service-test.clientSecret}")
	private String clientSecret;
	@Value("${service.service-test.maxTimeInMillis}")
	private Long maxTimeInMillis;
	@Value("${service.service-test.path.auth}")
	private String auth;
	
	@Value("${service.service-test.path.customer}")
	private String customer;

	
	private String token;
	private LocalDateTime tokenDate;
	
	
	public String getToken() {
		if (token == null) {
			generateToken();
		} else {
            long tokenMillis = tokenDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            long currMillis = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
			Long timeLive = currMillis - tokenMillis;
			if (timeLive > maxTimeInMillis) {
				generateToken();
			}
		}	
		return token;
		
	}
	
	private void generateToken() {
		token = postAccessToken();
		tokenDate = LocalDateTime.now();
	}
	
	
		public String postAccessToken() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(urlApi+auth);

        HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("client_id",this.clientId);
		map.add("client_secret",this.clientSecret);
		
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<Token> result = restTemplate.exchange(uri.toUriString(), HttpMethod.POST, entity, Token.class);
        
        return result.getBody().getAccess_token();
    }
	

	public String customer(String requestEntity){
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
		UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(urlApi+customer);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		headers.add("Authorization", "Bearer " + this.getToken());
		HttpEntity<String> request = new HttpEntity<>(requestEntity, headers);
		ResponseEntity<String> response = restTemplate.exchange(uri.toUriString(), HttpMethod.POST, request, String.class);
		return response.getBody();		
	}	

	@Data
	public static class Token {	
	    private String access_token;
	}
	
}

