package stepdefinitions;

import com.api.data.UserListLombok;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import helpers.GlobalVariables;
import io.cucumber.java.en.And;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.Arrays;

public class GetUsers {
    APIResponse apiResponse;

    @And("^I receive list of users$")
    public void getListUsers() throws IOException {
        apiResponse = GlobalVariables.getApiResponse();
        ObjectMapper objectMapper = new ObjectMapper();

        String arrayUsers = objectMapper.readTree(apiResponse.body()).get("data").toString();

        Assertions.assertFalse(arrayUsers.isEmpty());

        UserListLombok[] listResponseUser = objectMapper.readValue(arrayUsers, UserListLombok[].class);

        Assertions.assertTrue(listResponseUser.length > 0);
        System.out.println(Arrays.toString(listResponseUser));
    }

}
