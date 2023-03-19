package com.ATM.transactionService.utils;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WebfluxRequestBuilder {
    WebClient webClient;
    public WebfluxRequestBuilder(){

        webClient =  WebClient.create("http://localhost:8000/api");
    }
//    @Async("asyncRequestExecutor")
    // make this async
    public JSONObject makePostRequest(String url, JSONObject body) {
        try {
            System.out.println(body);
//            Thread.sleep(10000);
            JSONObject res = webClient.post()
                    .uri(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(body)
                    .retrieve()
                    .toEntity(JSONObject.class)
                    .toFuture()
                    .get();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject errorRes =  new JSONObject();
            errorRes.put("error", "verification email seindg failed");
            return errorRes;
        }
    }
}
