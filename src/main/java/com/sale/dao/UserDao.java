package com.sale.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sale.configuration.Connections;
import com.sale.model.Users;


public class UserDao {
	  public ArrayList<Users> showUser(){
		ArrayList<Users> arr = new ArrayList<Users>();
		Users user = null;
		String sql = "SELECT * FROM user_tbl";
		try{
			PreparedStatement pst = Connections.open().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				user = new Users();
				user.setId(rs.getLong("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				arr.add(user);
				System.out.println(arr);
			}
			Connections.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}
	
	public Users createUser(Users user){		
		try {
			String sql = "INSERT INTO user_tbl (username, password) VALUES (?, ?)";
			PreparedStatement pst = Connections.open().prepareStatement(sql);			
				pst.setString(1, user.getUsername());
				pst.setString(2, user.getPassword());
			if(pst.executeUpdate()>0){
				Connections.close();
				System.out.println("Your user have been created..! ");
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public Users updateUser(Users user) {
		try {
			String sql = "UPDATE user_tbl SET username=?, password=? WHERE id=?";
			PreparedStatement pst = Connections.open().prepareStatement(sql);
			pst.setString(1, user.getUsername());
			pst.setString(2, user.getPassword());
			pst.setLong(3, user.getId());
			if(pst.executeUpdate() > 0){
				Connections.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	public Users deleteUser(Users user){
		try{
			String sql = "DELETE FROM user_tbl WHERE id=?";
			PreparedStatement pst = Connections.open().prepareStatement(sql);
			pst.setLong(1, user.getId());
			if(pst.executeUpdate()>0){
				Connections.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
}
