package com.qa.api.tests;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.HttpHeader;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class APIResponseHeadersTest {
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
    public void getHeadersTest() {

        APIResponse apiResponse = requestContext.get("https://reqres.in/api/users");


        // using map:
        Map<String, String> headersMap = apiResponse.headers();

        headersMap.forEach((k, v) -> System.out.println(k + ": " + v));
        System.out.println("Headers size: "+headersMap.size());

        System.out.println("===================================================");

        // using list:
        List<HttpHeader> headersList = apiResponse.headersArray();
        for (HttpHeader e : headersList){
            System.out.println(e.name + ": " + e.value);
        }

        Assert.assertEquals(apiResponse.status(), 200);
        Assert.assertEquals(headersMap.get("content-type"), "application/json; charset=utf-8");
        Assert.assertTrue(apiResponse.ok());

    }

    @AfterTest
    public void closePlaywright(){
        playwright.close();
    }

}
