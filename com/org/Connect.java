package com.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.ResultSet;

public class Connect extends HttpServlet{
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("jfdkjdl");
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String cid = UUID.randomUUID().toString().replaceAll("-", "");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String info = request.getParameter("info");
		
		String sql = "insert into contact (cid,name,email,telephone,message) values (\"" + cid + "\",\""+
			name + "\",\"" + email + "\",\"" + phone + "\",\"" + info + "\");";
		System.out.println(sql);
		ConnectDatabase con_data = new ConnectDatabase();
		con_data.connect();
		
		try {
			con_data.pst = con_data.connection.prepareStatement(sql);// 准备执行SQL语句
			con_data.pst.executeUpdate();
			con_data.pst.close();
			con_data.close();
			
			out.print("{\"state\":\"0\"}");
			
			System.out.println("数据库插入成功！");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			out.print("{\"state\":\"1\"}");
			System.out.println("失败");
		}
		
	}
}
