package com.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.ResultSet;

public class loadShopCart extends HttpServlet {
	protected void doGet (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String uid = request.getParameter("uid");
        String pagenum=request.getParameter("pagenum");
        int index=Integer.parseInt(pagenum);
        index*=3;
        out.print("{\"status\":\"");
		ConnectDatabase connect=new ConnectDatabase();
		connect.connect();
		try {
			String sql="select B.bid,B.bname ,S.counter ,B.author ,B.cover,B.price from bookstore.book_info B,bookstore.shop_cart S where B.bid=S.bid and "+
					" S.uid='"+uid+"'order by counter limit "+index+",3";
			connect.pst = connect.connection.prepareStatement(sql);
			connect.ret = (ResultSet) connect.pst.executeQuery();
			String bname;
			String counter;
			String author;
			String cover;
			String price;
			String bid;
			if(!connect.ret.next()){
//				System.out.println("false");
				out.print(0+"\",\"next\":\"0\"}");
				
			}else{
				out.print(1+"\",\"result\":[");
				bname=connect.ret.getString("bname");
				bid=connect.ret.getString("bid");
				counter=connect.ret.getString("counter");
				author=connect.ret.getString("author");
				cover=connect.ret.getString("cover");
				price=connect.ret.getString("price");
				out.print("{\"bname\":\""+bname+"\",\"bid\":\""+bid+"\",\"counter\":\""+counter+"\",\"author\":\""+author+"\",\"cover\":\""+cover+"\",\"price\":\""+price+"\"}");
				while (connect.ret.next()) {
					bname=connect.ret.getString("bname");
					bid=connect.ret.getString("bid");
					counter=connect.ret.getString("counter");
					author=connect.ret.getString("author");
					cover=connect.ret.getString("cover");
					price=connect.ret.getString("price");
					out.print(",{\"bname\":\""+bname+"\",\"bid\":\""+bid+"\",\"counter\":\""+counter+"\",\"author\":\""+author+"\",\"cover\":\""+cover+"\",\"price\":\""+price+"\"}");
				} 
				out.print("],");
				index+=3;
				sql="select B.bname ,S.counter ,B.author ,B.cover ,B.price from bookstore.book_info B,bookstore.shop_cart S where B.bid=S.bid and "+
					" S.uid='"+uid+"'order by counter limit "+index+",3";
				connect.pst = connect.connection.prepareStatement(sql);
				connect.ret = (ResultSet) connect.pst.executeQuery();
				if(!connect.ret.next()){
					out.print("\"next\":\"0\"}");
				}else{
					out.print("\"next\":\"1\"}");
				}
			}
			
			connect.ret.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("数据库查询失败");
		}
	}
}
