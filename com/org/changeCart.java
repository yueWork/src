package com.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.ResultSet;

public class changeCart extends HttpServlet {
	protected void doGet (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();		
		String bid = request.getParameter("bid");
		String uid=request.getParameter("uid");
		String num=request.getParameter("num");
		int number=Integer.parseInt(num);
		
		ConnectDatabase con_data = new ConnectDatabase();
		con_data.connect();
		
		try {
			String sqlupdate = "update bookstore.shop_cart set counter=" + number + " where uid='" + uid
					+ "' and bid='" + bid + "'";
			con_data.pst = con_data.connection.prepareStatement(sqlupdate);
			con_data.pst.executeUpdate();
			System.out.println("update:"+sqlupdate);			
			out.print("{\"result\":\"success\"}");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
