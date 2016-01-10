package com.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.ResultSet;

public class checkShopCart extends HttpServlet {
	protected void doGet (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String uid = request.getParameter("uid");
        String bid=request.getParameter("bid");
        String num=request.getParameter("num");
        String oid=UUID.randomUUID().toString().replaceAll("-", "");
        int number=Integer.parseInt(num);
		ConnectDatabase connect=new ConnectDatabase();
		connect.connect();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date=df.format(new Date());
		System.out.println("date:"+date);
		try {
			String sql="select S.counter from bookstore.shop_cart S where S.bid="+bid+" and "+
					" S.uid='"+uid+"'";
			System.out.println(sql);
			connect.pst = connect.connection.prepareStatement(sql);
			connect.ret = (ResultSet) connect.pst.executeQuery();
			int counter;
			
			if(connect.ret.next()){
				counter=connect.ret.getInt(1);
				System.out.println("num"+number+"coun:"+counter);
				if(counter>number){
					counter-=number;
					String sqlupdate="update bookstore.shop_cart set counter="+counter+" where uid='"+uid+"' and bid='"+bid+"'";
					
					connect.pst=connect.connection.prepareStatement(sqlupdate);
					connect.pst.executeUpdate();
					
					sqlupdate="insert into bookstore.order values( '"+oid+"','"+uid+"','1','"+date+"')";
					connect.pst=connect.connection.prepareStatement(sqlupdate);
					connect.pst.executeUpdate();
					
					System.out.println(sqlupdate);
				}
				else{
					System.out.println("begin");
					String sqldelete="delete from bookstore.shop_cart where uid='"+uid+"' and bid='"+bid+"'";
					System.out.println(sqldelete);
					connect.pst=connect.connection.prepareStatement(sqldelete);
					connect.pst.execute();
					
					sqldelete="insert into bookstore.order values( '"+oid+"','"+uid+"','1','"+date+"')";
					connect.pst=connect.connection.prepareStatement(sqldelete);
					connect.pst.execute();
					
					System.out.println(sqldelete);
				}
				
			}
			out.println("{\"result\":\"success\"}");
			connect.ret.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("数据库查询失败");
		}
	}
}
