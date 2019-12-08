package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@RestController
public class RpcController {

    private final static String RPC_USER = "devprod";
    private final static String RPC_PASSWORD = "pass123";

    private final static String HEADER_CONTENT_TYPE = "Content-Type: application/json";

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
        for(char ch: body){
            if(ch == '"') {
                bodyString.append("\\").append(ch);
            } else {
                bodyString.append(ch);
            }
        }

        String userAuth = "-u " + RPC_USER + ":" + RPC_PASSWORD;
        String headerContentType = "-H " + HEADER_CONTENT_TYPE;
        String typeRequest = "-X " + type;
        String data = "--data-binary " + bodyString;
        String url = HOST + ":" + PORT;

        String command = "curl " + userAuth + " " + headerContentType + " " + typeRequest + " " + data + " " + url;
        String command1 = "curl -u devprod:pass123 -H 'Content-Type: application/json' -X GET -d '{\"jsonrpc\": 1, \"method\": \"getinfo\"}' http://172.31.3.31:8888";

        Process process = Runtime.getRuntime().exec(command1);
//Older version than java 8
        BufferedReader response = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder result = new StringBuilder();
        String s;
        while((s = response.readLine()) != null) {
            result.append(s);
        }
        System.out.println(result.toString());

//You then need to close the BufferedReader if not using Java 8
        response.close();

        System.out.println("Method 'sendCurlCall'. RequestBody:" + requestBody + " | Curl: " + command + " | Result: " + result.toString());

        return result.toString();
    }
}
