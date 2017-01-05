package org.shelan; /**
 * Created by shelan on 1/4/17.
 */

import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.JsonArray;
import org.shelan.model.AccessLog;
import org.shelan.util.PostDataUtil;

import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class Router {

    private Controller controller;

    public Router(Controller controller, int port) {
        this.controller = controller;
        port(port);
    }

    public void registerRoutes() {

        before("/getLogins/*", (request, response) -> {
            // ... check if authenticated

            //TODO : move this to controller and handle corner cases;
            String bearer = request.headers("Authorization");
            if (bearer == null || bearer.split(" ").length != 2) {

                halt(401, "Unauthorized Access!");
            }
        });


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
            String username = values.get("username");
            String password = values.get("password");
            if (username == null || password == null) {
                return "username or password cannot be empty or null";
            }
            Status status = controller.authenticate(username, password);

            switch (status) {
                case SUCCESS:
                    response.status(200);
                    return controller.generateToken(username);
                case FAILED:
                    response.status(403);
                    return "Authentication Failure";
                default:
                    response.status(403);
                    return "Authentication Failure";
            }
        }));

        get("/getLogins", ((request, response) -> {
            String bearer = request.headers("Authorization");
            if (bearer == null) {
                halt(401, "Unauthorized Access!! Please pass valid Authorization header with JWT token");
            }
            String user = controller.getUserFromJwtToken(bearer);
            if (user == null || user.isEmpty()) {
                halt(401, "Unauthorized Access!");
            }
            Map<String, String> values = PostDataUtil.getPostData(request.body());
            List<AccessLog> logItems = controller.getLastLoginAttempts(user);
            JsonArray jsonArray = new JsonArray();
            for (AccessLog logItem : logItems) {
                jsonArray.add(logItem.getTimestamp().toString());
            }
            return jsonArray;
        }));
    }


}

