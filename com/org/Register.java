package com.org;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class Register extends HttpServlet {
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String userName = request.getParameter("userName");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String password0 = request.getParameter("password0");
		String portail = "/Users/yue/Documents/workspace/java/BookStore/WebContent/portait/default_portait.jpg";
		String uid = UUID.randomUUID().toString().replaceAll("-", "");;
		System.out.println(uid);
		
		String sql = "insert into use_info (uid,uname,phone,email,password,portail) values (\""
				+uid+"\",\""+userName+"\",\""+phone+"\",\""+email+"\",\""+password0+"\",\""+portail+"\");";
		ConnectDatabase con_data = new ConnectDatabase();
		con_data.connect();
		
		try {
			con_data.pst = con_data.connection.prepareStatement("select * from use_info where phone=\""+phone+"\";");//准备执行SQL语句
			con_data.ret = (ResultSet) con_data.pst.executeQuery();
			if(!con_data.ret.next())
			{
				con_data.ret.close();
				try {
					con_data.pst = con_data.connection.prepareStatement(sql);
					con_data.pst.executeUpdate();
					con_data.pst.close();
					out.print("{\"msg\":\"注册成功！\"}");
					System.out.println("数据库插入成功！");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					out.print("{\"msg\":\"注册失败！\"}");
					System.out.println("数据库插入失败！");
				}
			}
			else {
				out.print("{\"msg\":\"手机号已注册！\"}");
				System.out.println("手机号已注册!");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("数据库查询失败！");
		}
		
		
	}
	
}

