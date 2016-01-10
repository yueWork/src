package com.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.ResultSet;

public class Single extends HttpServlet{
	protected void doGet (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String bid = request.getParameter("bid");
		
		String sql = "select * from book_info where bid =\"" + bid + "\";";
		
		System.out.println(sql);
		ConnectDatabase con_data = new ConnectDatabase();
		con_data.connect();
		
		try {
			con_data.pst = con_data.connection
			.prepareStatement(sql);// 准备执行SQL语句
			con_data.ret = (ResultSet) con_data.pst.executeQuery();
			
			con_data.ret.next();
			String Bid = con_data.ret.getString("bid");
			String Bname = con_data.ret.getString("bname");
			String Author = con_data.ret.getString("author");
			String Publisher = con_data.ret.getString("publisher");
			String PublishData = con_data.ret.getString("publish_data");
			String Price = con_data.ret.getString("price");
			String Cover = con_data.ret.getString("cover");
			String Tid = con_data.ret.getString("tid");
			String Instruction = con_data.ret.getString("instruction");
			con_data.ret.close();
			con_data.pst.close();
			System.out.println(Bid);
			System.out.println(Bname);
			System.out.println(Author);
			System.out.println(Publisher);
			System.out.println(PublishData);
			System.out.println(Price);
			System.out.println(Cover);
			System.out.println(Tid);
			System.out.println(Instruction);
			
			String sql1 = "select tname from book_sort where tid =\"" + Tid + "\";";
			System.out.println(sql1);
			
			con_data.pst = con_data.connection
			.prepareStatement(sql1);// 准备执行SQL语句
			con_data.ret = (ResultSet) con_data.pst.executeQuery();
			
			con_data.ret.next();
			String Tname = con_data.ret.getString("tname");
			System.out.println(Tname);
			con_data.ret.close();
			con_data.pst.close();
			con_data.close();
			System.out.println("[{\"bid\":\""+ Bid + "\"},{\"bname\":\""+ Bname + "\"},{\"author\":\""+ Author + "\"},{\"publisher\":\""+ Publisher + 
					"\"},{\"publish_data\":\""+ PublishData + "\"},{\"price\":\""+ Price + "\"},{\"cover\":\""+ Cover + "\"},{\"tname\":\""+ Tname + "\"},{\"instruction\":\""+ Instruction + "\"}]");
			out.print("[{\"bid\":\""+ Bid + "\"},{\"bname\":\""+ Bname + "\"},{\"author\":\""+ Author + "\"},{\"publisher\":\""+ Publisher + 
					"\"},{\"publish_data\":\""+ PublishData + "\"},{\"price\":\""+ Price + "\"},{\"cover\":\""+ Cover + "\"},{\"tname\":\""+ Tname + "\"},{\"instruction\":\""+ Instruction + "\"}]");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
