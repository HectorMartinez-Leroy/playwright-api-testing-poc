package com.qa.api.tests;

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
import java.util.Map;

public class GetApiCallTest {

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
    public void getUsersApiTest() throws IOException {

        APIResponse apiResponse = requestContext.get("https://reqres.in/api/users?page=2");
        System.out.println(apiResponse.status()+"\n");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(apiResponse.body());

        String prettyResponse = jsonResponse.toPrettyString();

        System.out.println(prettyResponse);

        Map<String, String> headersMap = apiResponse.headers();

        Assert.assertEquals(apiResponse.status(), 200);
        Assert.assertEquals(headersMap.get("content-type"), "application/json; charset=utf-8");
        Assert.assertTrue(apiResponse.ok());

    }

    @Test
    public void getSpecificUserApiTest() throws IOException {
        APIResponse apiResponse = requestContext.get("https://reqres.in/api/users", RequestOptions.create()
                .setQueryParam("gender", "male")
                .setQueryParam("status", "active")
        );

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(apiResponse.body());
        String prettyResponse = jsonResponse.toPrettyString();
        System.out.println(prettyResponse);

        Assert.assertEquals(apiResponse.status(), 200);
        Assert.assertTrue(apiResponse.ok());

    }

    @AfterTest
    public void closePlaywright(){
        playwright.close();
    }
}
