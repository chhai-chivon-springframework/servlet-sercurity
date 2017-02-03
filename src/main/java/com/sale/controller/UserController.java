package com.sale.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sale.model.Users;
import com.sale.services.UserServices;

import flexjson.JSONSerializer;

@Path("users")
public class UserController {
	
	UserServices userService ;//= new UserServices();
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Users>showUser() {
    	return userService.showUser();
    }
    
    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
    	return "fuck";
    }
    
    @GET
    @Path("/data")
    @Produces(MediaType.TEXT_PLAIN)
    public String data() {
    	ArrayList<Users>users=new ArrayList<Users>();
    	users.add(new Users(1923l,"Bucky","122"));
    	users.add(new Users(3423l,"Channy","12222"));
    	users.add(new Users(245l,"Tony","122"));
    	Map<String,Object>map=new HashMap<String,Object>();
    	map.put("STATUS", true);
    	map.put("CODE", 200);
    	map.put("DATA",users);
    	return new JSONSerializer().deepSerialize(map);
    }
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createUsers(Users user){
    	userService.createUser(user);
    	return "User have been created...!";
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateUser(Users user){
    	userService.updateUser(user);
    	return "User have been updated...!";
    }
}

