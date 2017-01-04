package org.shelan; /**
 * Created by shelan on 1/4/17.
 */

import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.shelan.util.PostDataUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;

public class Router {

    public Router(){
        registerRoutes();
    }

    private void registerRoutes(){

        post("/register" ,(request,response) ->{
            Map<String,String> values = PostDataUtil.getPostData(request.body());
            return "good";
        });
    }
}

