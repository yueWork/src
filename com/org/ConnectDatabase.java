package com.org;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.ResultSet;

public class ConnectDatabase {
	public String url = "jdbc:mysql://localhost:3307/bookstore";
	public String name = "com.mysql.jdbc.Driver";
	public String username = "root";
	public String password = "8387110";
	public java.sql.PreparedStatement pst = null;
	public Connection connection = null;
	public ResultSet ret = null;
	public java.sql.Statement statement = null;
	void connect(){
		try
		{
			Class.forName(name); // 制定连接类型
			connection = DriverManager.getConnection(url, username, password);// 获取连接
			System.out.println("mysql connection is ok");
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	void close()
	{
		try {
			connection.close();
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
