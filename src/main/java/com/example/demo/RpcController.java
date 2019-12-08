package com.example.demo;

import org.apache.http.HttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.toilelibre.libe.curl.Curl.curl;

@RestController
public class RpcController {

    private final static String RPC_USER = "devprod";
    private final static String RPC_PASSWORD = "pass123";

    private final static String HEADER_CONTENT_TYPE = "'Content-Type: application/json'";

    private final static String HOST = "http://172.31.3.31";
    private final static String PORT = "8888";

    private final static String GET_REQUEST = "GET";
    private final static String POST_REQUEST = "POST";

    @GetMapping
    public String rpcTestGetMethod(@RequestBody String requestBody) throws IOException {
        return sendCurlCall(GET_REQUEST, requestBody);
    }

    @PostMapping
    public String rpcTestPostMethod(@RequestBody String requestBody) throws IOException {
        return sendCurlCall(POST_REQUEST, requestBody);
    }

    private String sendCurlCall(String type, String requestBody) throws IOException {
        char[] body = requestBody.toCharArray();

        StringBuilder bodyString = new StringBuilder();
        bodyString.append("'");
        for(char ch: body){
            bodyString.append(ch);
        }
        bodyString.append("'");

        String url = HOST + ":" + PORT;

        HttpResponse response = curl()
                .u(RPC_USER + ":" + RPC_PASSWORD)
                .header(HEADER_CONTENT_TYPE)
                .xUpperCase(type)
                .d(bodyString.toString())
                .run(url);

        String result = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()))
                .lines()
                .collect(Collectors.joining("\n"));

        System.out.println("Method 'sendCurlCall'. RequestBody:" + requestBody + " | Curl: " + response + " | Result: " + result);

        return result;
    }
}
