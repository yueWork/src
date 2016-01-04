package com.org;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class Register extends HttpServlet {
//	protected void doGet(HttpServletRequest request,
//			HttpServletResponse response) throws ServletException, IOException {
//		response.setContentType("text/json;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        out.write("hello");
//	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String userName = request.getParameter("userName");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String password0 = request.getParameter("password0");
		String portail = "/Users/yue/Documents/workspace/java/BookStore/WebContent/images/close_1.png";
		
		System.out.println("insert into use_info (uname,phone,email,password,portail) values (\""
				+userName+"\",\""+phone+"\",\""+email+"\",\""+password0+"\",\""+portail+"\");");
		String sql = "insert into use_info (uname,phone,email,password,portail) values (\""
				+userName+"\",\""+phone+"\",\""+email+"\",\""+password0+"\",\""+portail+"\");";
		ConnectDatabase con_data = new ConnectDatabase();
		con_data.connect();
		try {
			con_data.pst = con_data.connection.prepareStatement(sql);
			out.print("");
			System.out.println("成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("插入失败");
		}
		try {
			con_data.pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		try {
			con_data.pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}

