package com.ATM.AccountService.utils;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class webclientRequestHandler {
    WebClient webClient;
    public webclientRequestHandler(){

        webClient =  WebClient.create("http://localhost:8000/api");
    }
    @Async("asyncRequestExecutor")
    // make this async
    public Mono<JSONObject> makePostRequest(String url, JSONObject body) {
            System.out.println(body);
//            Thread.sleep(10000);
            Mono<JSONObject> res = webClient.post()
                    .uri(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .syncBody(body)
                    .retrieve()
                    .bodyToMono(JSONObject.class);w

            return res;
    }
}
