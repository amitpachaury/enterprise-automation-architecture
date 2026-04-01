package com.qa.stepdefs.api;

import com.qa.api.endpoints.UserApiEndpoints;
import com.qa.models.User;
import com.qa.utils.FixtureLoader;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.io.IOException;

public class UserApiSteps {
    private static final Logger log = LogManager.getLogger(UserApiSteps.class);
    private Response response;

    @Given("API is available")
    public void apiIsAvailable() {
        System.out.println("API is available");
    }

    @When("I send GET request to get user with id {string}")
    public void iSendGETRequestToGetUserWithId(String userId) {
        int id = Integer.parseInt(userId);
        response = UserApiEndpoints.getUser(id);
    }

    @Then("response status code should be {int}")
    public void responseStatusCodeShouldBe(int statusCode) {
        Assert.assertEquals(response.getStatusCode(), statusCode);
    }

    @And("response should contain user with email {string}")
    public void responseShouldContainUserWithEmail(String expectedEmail) {
        String email = response.jsonPath().getString("data.email");
        Assert.assertEquals(email, expectedEmail);
    }

    @When("I send GET request to get all users on page {string}")
    public void iSendGETRequestToGetAllUsersOnPage(String pageNo) {
       int page = Integer.parseInt(pageNo);
       response = UserApiEndpoints.getUserByPage(page);
    }

    @And("response should contain {int} users")
    public void responseShouldContainUsers(int expectedCount) {
        int count = response.jsonPath().getList("data").size();
        Assert.assertEquals(count, expectedCount);
    }

    @When("I send POST request to create a user with valid data")
    public void iSendPOSTRequestToCreateAUserWithValidData(){
        User user = FixtureLoader.loadAs("users/create-user.json", User.class);
        response = UserApiEndpoints.createUser(user);
        System.out.println("RESPONSE BODY: " + response.getBody().asString());
    }

    @And("response should contain name {string}")
    public void responseShouldContainName(String expectedName) {
        String name = response.jsonPath().getString("name");
        Assert.assertEquals(name, expectedName);
    }

    @And("response should contain job {string}")
    public void responseShouldContainJob(String expectedJob) {
        String job = response.jsonPath().getString("job");
        Assert.assertEquals(job, expectedJob);
    }

    @When("I send PUT request to update user {string} with valid data")
    public void iSendPUTRequestToUpdateUserWithValidData(String userId){
        User user = FixtureLoader.loadAs("users/update-user.json", User.class);
        response = UserApiEndpoints.updateUser(Integer.parseInt(userId), user);
    }

    @When("I send DELETE request to delete user {string}")
    public void iSendDELETERequestToDeleteUser(String userId) {
        response = UserApiEndpoints.deleteUser(Integer.parseInt(userId));
    }

    @And("response should contain a valid user id")
    public void responseShouldContainAValidUserId() {
        String id = response.jsonPath().getString("id");
        log.info("Created user id: {}", id);
        Assert.assertNotNull(id, "User id should not be null");
        Assert.assertFalse(id.isEmpty(), "User id should not be empty");
    }

    @And("response should contain a created timestamp")
    public void responseShouldContainACreatedTimestamp() {
        String createdAt = response.jsonPath().getString("createdAt");
        log.info("Created at: {}", createdAt);
        Assert.assertNotNull(createdAt, "createdAt should not be null");
    }

    @And("response should contain an updated timestamp")
    public void responseShouldContainAnUpdatedTimestamp() {
        String updatedAt = response.jsonPath().getString("updatedAt");
        log.info("Updated at: {}", updatedAt);
        Assert.assertNotNull(updatedAt, "updatedAt should not be null");
    }
}
