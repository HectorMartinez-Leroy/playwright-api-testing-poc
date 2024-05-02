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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class CreateUserWithJsonFileTest {

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

        File file = new File("src/test/data/user.json");

        APIResponse createApiResponse = requestContext.post("https://reqres.in/api/users",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        // you can send Bytes to the method setData
                        .setData(Files.readAllBytes(file.toPath()))
        );


        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode postJsonResponse = objectMapper.readTree(createApiResponse.body());
        String postPrettyResponse = postJsonResponse.toPrettyString();
        System.out.println(postPrettyResponse);

        Assert.assertEquals(createApiResponse.status(), 201);

    }

    @AfterTest
    public void closePlaywright(){
        playwright.close();
    }
}
