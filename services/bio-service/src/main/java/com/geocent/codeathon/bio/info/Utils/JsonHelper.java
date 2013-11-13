/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geocent.codeathon.bio.info.Utils;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

/**
 *
 * @author jtbruce
 */

@Component
public class JsonHelper {
    
    public String convertToJson(Object o){
        Gson gson = new Gson();
        
        return gson.toJson(o);
        
    }
    
    public Object convertFromJson(String json, Class clazz){
        
        Gson gson = new Gson();
        
        return gson.fromJson(json, clazz);
        
    }
    
}
