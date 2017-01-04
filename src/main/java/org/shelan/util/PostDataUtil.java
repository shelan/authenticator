package org.shelan.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shelan on 1/4/17.
 */
public class PostDataUtil {


    public static Map<String,String> getPostData(String body){
        HashMap map = new HashMap();
        String [] kvPairs = body.split("&");

        //TODO: handle = sign if it present in the params
        for (String kvPair : kvPairs) {
             String [] keyAndValue = kvPair.split("=");
             if(keyAndValue.length == 2){
                 map.put(keyAndValue[0],keyAndValue[1]);
             }
            //map.put()
        }
        return map;
    }
}
