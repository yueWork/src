package com.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class deleteAll extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String uid = request.getParameter("uid");

		ConnectDatabase connect = new ConnectDatabase();
		connect.connect();

		try {
			String sqldelete = "delete from bookstore.shop_cart where uid='" + uid + "'" ;
			System.out.println(sqldelete);
			connect.pst = connect.connection.prepareStatement(sqldelete);
			connect.pst.execute();
			
			out.println("{\"result\":\"success\"}");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("数据库查询失败");
		}
	}
}
