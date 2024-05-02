package com.qa.api.tests.PUT;

import com.api.data.UserLombok;
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

public class UpdateUserPUTWithPOJOLombokTest {

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
    public void createAndEditUserTest() throws IOException {

        // user to create
        UserLombok userLombok = UserLombok.builder()
                .name("morpheus")
                .job("leader")
                .id(999).build();

        // Create user
        APIResponse createApiResponse = requestContext.post("https://reqres.in/api/users",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setData(userLombok)

        );

        // Convert response to POJO
        ObjectMapper objectMapper = new ObjectMapper();
        UserLombok responseUser = objectMapper.readValue(createApiResponse.text(), UserLombok.class);

        System.out.println(responseUser);
        System.out.println("----------");
        Assert.assertEquals(createApiResponse.status(), 201);

        // Update user created
        userLombok.setName("name updated");
        userLombok.setId(827346786);
        APIResponse updateApiResponse = requestContext.put("https://reqres.in/api/users/"+responseUser.getId(),
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setData(userLombok)

        );

        UserLombok updatedResponseUser = objectMapper.readValue(updateApiResponse.text(), UserLombok.class);
        System.out.println(updatedResponseUser);
        Assert.assertEquals(updateApiResponse.status(), 200);

    }

    @AfterTest
    public void closePlaywright(){
        playwright.close();
    }

}
