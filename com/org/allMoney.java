package com.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.ResultSet;

public class allMoney extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String uid = request.getParameter("uid");

		ConnectDatabase connect = new ConnectDatabase();
		connect.connect();

		try {
			String sql = "select B.price*S.counter as allmoney from bookstore.book_info B ,bookstore.shop_cart S "+
					" where uid='"+uid+"' and S.bid=B.bid" ;
			System.out.println(sql);
			connect.pst = connect.connection.prepareStatement(sql);
			connect.ret = (ResultSet) connect.pst.executeQuery();
			int sum=0;
			int total;
 			while(connect.ret.next()){
				total=connect.ret.getInt(1);
				sum+=total;
			}
			out.print("{\"result\":\""+sum+"\",\"counter\":\"");
			sql="select S.counter from bookstore.shop_cart S  where uid='"+uid+"'" ;
			connect.pst = connect.connection.prepareStatement(sql);
			connect.ret = (ResultSet) connect.pst.executeQuery();
			int sumcounter=0;
			int total1;
 			while(connect.ret.next()){
				total1=connect.ret.getInt(1);
				sumcounter+=total1;
			}
 			out.print(sumcounter+"\"}");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("数据库查询失败");
		}
	}
}
