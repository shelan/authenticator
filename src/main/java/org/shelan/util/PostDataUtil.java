package org.shelan.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for postData
 */
public class PostDataUtil {

    /**
     * generating keyvalue map pair from post body format
     * Post data format is "key=value&key2=value2"
     * @param body
     * @return
     */
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
