package stepdefinitions;

import com.api.data.UserListLombok;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.io.IOException;
import java.util.Arrays;

public class GetUsers {

    Playwright playwright = Playwright.create();
    APIRequest request = playwright.request();
    APIRequestContext requestContext = request.newContext();

    APIResponse apiResponse;

    String url = "";

    @Given("^reqres API (.*)")
    public void setApiURL(String url){
        this.url = url;
    }

    @When("I do request")
    public void getRequest(){
        apiResponse = requestContext.get(url);
    }

    @Then("I receive list of users")
    public void getListUsers() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        String arrayUsers = objectMapper.readTree(apiResponse.body()).get("data").toString();

        Assert.assertFalse(arrayUsers.isEmpty());

        UserListLombok[] listResponseUser = objectMapper.readValue(arrayUsers, UserListLombok[].class);

        Assert.assertTrue(listResponseUser.length > 0);
        System.out.println(Arrays.toString(listResponseUser));
    }

    @And("response request code is 200")
    public void verifyResponseCode(){
        Assert.assertEquals(200, apiResponse.status());
    }
}
