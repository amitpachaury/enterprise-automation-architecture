package com.qa.api.endpoints;

import com.qa.api.ApiClient;
import io.restassured.response.Response;
import org.apache.logging.log4j.*;

public class UserApiEndpoints {
    private static final Logger log = LogManager.getLogger(UserApiEndpoints.class);
    private static final String USER_ENDPOINT = "/users";

    public static Response getAllUsers(){
        log.info("Getting all users");
        return ApiClient.get(USER_ENDPOINT);

    }
    public static Response getUser(int userId){
        log.info("Getting user with id: {}", userId);
        return ApiClient.get(USER_ENDPOINT + "/" + userId);
    }
    public static Response deleteUser(int userId){
        log.info("Delete user with id: {}", userId);
        return ApiClient.delete(USER_ENDPOINT + "/" + userId);
    }
    public static Response createUser(Object body){
        log.info("Create user");
        return ApiClient.post(USER_ENDPOINT , body);
    }
    public static Response updateUser(int userId, Object body){
        log.info("Update user");
        return ApiClient.put(USER_ENDPOINT +"/"+ userId, body);
    }

    public static Response getUserByPage(int page){
        log.info("Get user by page");
        return ApiClient.get(USER_ENDPOINT +"?page="+ page);
    }

}
