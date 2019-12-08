package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RestController
public class RpcController {

    private final static String RPC_USER = "devprod";
    private final static String RPC_PASSWORD = "ZcergOfTzoSbDEFNdcxaEBRDRJh3VOOs-anma1viJPk=";

    private final static String HEADER_CONTENT_TYPE = "'Content-Type: application/json'";

    private final static String HOST = "http://localhost";
    private final static String PORT = "8080/test";

    private final static String GET_REQUEST = "GET";
    private final static String POST_REQUEST = "POST";

    @GetMapping
    public String rpcTestGetMethod(@RequestBody String requestBody) throws IOException {
        return sendCurlCall(GET_REQUEST, requestBody);
    }

    @GetMapping("/test")
    public String rpcTestGetMethod1(@RequestBody String requestBody) throws IOException {
        return "success";
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

        String userAuth = "-u " + RPC_USER + ":" + RPC_PASSWORD;
        String headerContentType = "-H " + HEADER_CONTENT_TYPE;
        String typeRequest = "-X " + type;
        String data = "-d " + bodyString;
        String url = HOST + ":" + PORT;

        String command = "curl " + userAuth + " " + headerContentType + " " + typeRequest + " " + data + " " + url;

        Process process = Runtime.getRuntime().exec(command);
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder responseStrBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            responseStrBuilder.append(line);
        }

        System.out.println("Method 'sendCurlCall'. RequestBody:" + requestBody + " | Curl: " + command + " | Result: " + line);

        return line;
    }
}
