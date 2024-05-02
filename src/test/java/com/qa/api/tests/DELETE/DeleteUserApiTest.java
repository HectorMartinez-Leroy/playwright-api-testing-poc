package com.qa.api.tests.DELETE;

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

public class DeleteUserApiTest {
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
    public void getSpecificUserApiTest() throws IOException {
        // create user first to delete it after
        UserLombok userLombok = UserLombok.builder()
                .name("morpheus")
                .job("leader")
                .id(999).build();

        APIResponse createApiResponse = requestContext.post("https://reqres.in/api/users",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setData(userLombok)

        );

        Assert.assertEquals(createApiResponse.status(), 201);

        ObjectMapper objectMapper = new ObjectMapper();
        UserLombok responseUser = objectMapper.readValue(createApiResponse.text(), UserLombok.class);

        System.out.println(responseUser);

        Assert.assertEquals(userLombok.getId(), 999);

        // delete user created
        APIResponse deleteApiResponse = requestContext.delete("https://reqres.in/api/users/"+responseUser.getId());
        Assert.assertEquals(deleteApiResponse.status(), 204);
        Assert.assertTrue(deleteApiResponse.ok());

    }

    @AfterTest
    public void closePlaywright(){
        playwright.close();
    }
}
