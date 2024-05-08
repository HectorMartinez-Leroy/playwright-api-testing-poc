package stepdefinitions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import enums.HttpRequestEnum;
import helpers.GlobalVariables;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class CommonStepDefinitions {
    Playwright playwright;
    APIRequest request;
    APIRequestContext requestContext;
    APIResponse apiResponse;
    RequestOptions requestOptions;
    String url = "";

    @Before
    public void beforeTest(Scenario scenario){
        playwright = Playwright.create();
        request = playwright.request();
        requestContext = request.newContext();
        requestOptions = RequestOptions.create();
    }

    @After
    public void afterTest(Scenario scenario){
        playwright.close();
    }

    @Given("^a request to (.*)")
    public void setApiURL(String url){
        this.url = url;
    }

    @And("^a request header (.*) with value (.*)")
    public void addHeader(String key, String value) {
        requestOptions.setHeader(key, value);
    }

    @And("^a request parameter (.*) with value (.*)")
    public void addParameter(String key, String value) {
        requestOptions.setQueryParam(key, value);
    }

    @And("^a request body from file (.*)")
    public void setBodyFromFile(String srcFile) {
        File file = new File(srcFile);
        try {
            requestOptions.setData(Files.readAllBytes(file.toPath()));
        } catch (Exception e) {
            System.err.println("Error reading json body file.\nFile path: "+srcFile);
        }
    }

    @When("^I do (.*) request")
    public void getRequest(HttpRequestEnum requestType) {
        switch (requestType) {
            case GET:
                apiResponse = requestContext.get(url, requestOptions);
                break;
            case POST:
                apiResponse = requestContext.post(url, requestOptions);
                break;
            case PUT:
                apiResponse = requestContext.put(url, requestOptions);
                break;
            case DELETE:
                apiResponse = requestContext.delete(url, requestOptions);
                break;
            default:
                System.err.println("No http method introduced");
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode postJsonResponse = objectMapper.readTree(apiResponse.body());
            String postPrettyResponse = postJsonResponse.toPrettyString();
            System.out.println(postPrettyResponse);
        } catch (IOException e){
            System.err.println("Error printing api response:\n"+e);
        }




        // Save api response to recover it in specific step definitions
        GlobalVariables.setApiResponse(apiResponse);
    }

    @Then("^response code is (.[0-9]*)")
    public void verifyResponseCode(int statusCodeExpected){
        Assertions.assertEquals(statusCodeExpected, apiResponse.status());
    }

}
