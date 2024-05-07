package helpers;

import com.microsoft.playwright.APIResponse;

public class GlobalVariables {

    private GlobalVariables(){

    }
    private static APIResponse apiResponse;

    public static APIResponse getApiResponse() {
        return apiResponse;
    }

    public static void setApiResponse(APIResponse apiResponse) {
        GlobalVariables.apiResponse = apiResponse;
    }
}
