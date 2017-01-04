package org.shelan; /**
 * Created by shelan on 1/4/17.
 */

import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.*;
import org.shelan.model.AccessLog;
import org.shelan.util.PostDataUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Router {

    Controller controller;

    public Router() {
        this.controller = new Controller();
        registerRoutes();
    }

    private void registerRoutes() {

        post("/register", (request, response) -> {
            Map<String, String> values = PostDataUtil.getPostData(request.body());
            Status status = controller.addNewUser(values.get("username"), values.get("password"));

            switch (status) {
                case FAILED:
                    return "User registraion failed";
                case NOT_VALID:
                    return "Not valid username";
                case SUCCESS:
                    return "Successfully registered user";
                case USER_EXISTS:
                    return "User already exist";
                default:
                    return "Unknown Error occured";
            }

        });

        post("/authenticate", ((request, response) -> {
            Map<String, String> values = PostDataUtil.getPostData(request.body());
            Status status = controller.authenticate(values.get("username"), values.get("password"));

            switch (status) {
                case SUCCESS:
                    response.status(200);
                    return "successfully authenticated";
                case FAILED:
                    response.status(403);
                    return "Authentication Failure";
                default:
                    response.status(403);
                    return "Authentication Failure";
            }
        }));

        get("/getLogins/:username", ((request, response) -> {
            Map<String, String> values = PostDataUtil.getPostData(request.body());
            List<AccessLog> logItems = controller.getLastLoginAttempts(request.params(":username"));
            JsonArray jsonArray = new JsonArray();
            for (AccessLog logItem : logItems) {
                jsonArray.add(logItem.getTimestamp().toString());
            }
            return jsonArray;
        }));
    }


}

