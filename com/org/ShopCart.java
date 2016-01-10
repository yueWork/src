package com.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.ResultSet;

public class ShopCart extends HttpServlet {
	protected void doGet (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();		
		String bid = request.getParameter("bid");
		String uid=request.getParameter("uid");
		String num=request.getParameter("num");
		int number=Integer.parseInt(num);
		String sql = "select counter from bookstore.shop_cart where uid='"+uid+"' and bid='"+bid+"'";
		
		System.out.println(sql);
		ConnectDatabase con_data = new ConnectDatabase();
		con_data.connect();
		
		try {
			con_data.pst = con_data.connection
			.prepareStatement(sql);// 准备执行SQL语句
			con_data.ret = (ResultSet) con_data.pst.executeQuery();
			
			if(con_data.ret.next()){
				int counter=con_data.ret.getInt(1);
				counter+=number;
				String sqlupdate="update bookstore.shop_cart set counter="+counter+" where uid='"+uid+"' and bid='"+bid+"'";
				con_data.pst=con_data.connection.prepareStatement(sqlupdate);
				con_data.pst.executeUpdate();
				System.out.println("update"+sqlupdate);
			}
			else{
				String sqlinsert="insert into bookstore.shop_cart values('"+uid+"','"+bid+"',"+number+") ";
				con_data.pst=con_data.connection.prepareStatement(sqlinsert);
				con_data.pst.executeUpdate();
				System.out.println("insert:"+sqlinsert);
			}
			out.print("{\"result\":\"success\"}");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
