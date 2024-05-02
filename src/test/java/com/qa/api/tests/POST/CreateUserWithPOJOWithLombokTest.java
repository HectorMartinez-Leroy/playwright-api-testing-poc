package com.qa.api.tests.POST;

import com.api.data.UserLombok;
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

public class CreateUserWithPOJOWithLombokTest {
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

        UserLombok userLombok = UserLombok.builder()
                .name("morpheus")
                .job("leader")
                .id(999).build();

        APIResponse createApiResponse = requestContext.post("https://reqres.in/api/users",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setData(userLombok)

        );

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode postJsonResponse = objectMapper.readTree(createApiResponse.body());
        String postPrettyResponse = postJsonResponse.toPrettyString();
        System.out.println(postPrettyResponse);

        UserLombok responseUser = objectMapper.readValue(createApiResponse.text(), UserLombok.class);

        System.out.println(responseUser);

        Assert.assertEquals(createApiResponse.status(), 201);

    }

    @AfterTest
    public void closePlaywright(){
        playwright.close();
    }
}
