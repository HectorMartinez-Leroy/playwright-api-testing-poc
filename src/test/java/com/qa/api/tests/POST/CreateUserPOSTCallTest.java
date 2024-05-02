package com.qa.api.tests.POST;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreateUserPOSTCallTest {
    Playwright playwright;
    APIRequest request;
    APIRequestContext requestContext;

    @BeforeTest
    public void setup() {
        playwright = Playwright.create();
        request = playwright.request();
        requestContext = request.newContext();
    }

    @Test
    public void createUserTest() throws IOException {

        Map<String, Object> data = new HashMap<>();

        data.put("name", "morpheus");
        data.put("job", "leader");

        APIResponse createApiResponse = requestContext.post("https://reqres.in/api/users",
                RequestOptions.create()
                    .setHeader("Content-Type", "application/json")
                        .setData(data)

        );


        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode postJsonResponse = objectMapper.readTree(createApiResponse.body());
        String postPrettyResponse = postJsonResponse.toPrettyString();
        System.out.println(postPrettyResponse);

        Assert.assertEquals(createApiResponse.status(), 201);

        // capture id from the post json response:
        String strUserId = postJsonResponse.get("id").asText();

        // if search for userId created, api does not find user because it actually doesn't create the user
        APIResponse getApiResponse = requestContext.get("https://reqres.in/api/users/"+2);

        System.out.println(getApiResponse.text());

        Assert.assertEquals(getApiResponse.status(), 200);


    }

    @AfterTest
    public void closePlaywright(){
        playwright.close();
    }
}
