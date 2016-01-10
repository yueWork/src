package com.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.ResultSet;

public class OrderInfo extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
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
			String sql="select O.time ,B.bname,B.cover ,O.oid ,B.price,BS1.tname as children,BS2.tname as parent from bookstore.order O,bookstore.book_info B, bookstore.shop S,"
					+"bookstore.book_sort BS1 ,bookstore.book_sort BS2 "
					+" where uid='"+uid+"' and O.oid=S.oid and S.bid=B.bid "
					+" and BS1.tid=B.tid and BS1._id=BS2.tid order by O.time DESC limit  "+index+",3";
//			System.out.println(sql);
			connect.pst = connect.connection.prepareStatement(sql);
			connect.ret = (ResultSet) connect.pst.executeQuery();
			String bname;
			String time;
			String cover;
			String oid;
			String price;
			String childType;
			String parentType;
			if(!connect.ret.next()){
//				System.out.println("false");
				out.print(0+"\",\"next\":\"0\"}");
				
			}else{
				out.print(1+"\",\"result\":[");
				bname=connect.ret.getString("bname");
				cover=connect.ret.getString("cover");
				time=connect.ret.getString("time");
				oid=connect.ret.getString("oid");
				price=connect.ret.getString("price");
				childType=connect.ret.getString("children");
				parentType=connect.ret.getString("parent");
				out.print("{\"bname\":\""+bname+"\",\"cover\":\""+cover+"\",\"time\":\""+time+"\",\"oid\":\""+oid+"\",\"price\":\""+price+"\",\"children\":\""+childType+"\",\"parent\":\""+parentType+"\"}");
				while (connect.ret.next()) {
					bname=connect.ret.getString("bname");
					cover=connect.ret.getString("cover");
					time=connect.ret.getString("time");
					oid=connect.ret.getString("oid");
					price=connect.ret.getString("price");
					childType=connect.ret.getString("children");
					parentType=connect.ret.getString("parent");
					out.print(",{\"bname\":\""+bname+"\",\"cover\":\""+cover+"\",\"time\":\""+time+"\",\"oid\":\""+oid+"\",\"price\":\""+price+"\",\"children\":\""+childType+"\",\"parent\":\""+parentType+"\"}");
				} 
				out.print("],");
				index+=3;
				sql="select O.time ,B.bname ,B.cover ,O.oid ,B.price,BS1.tname ,BS2.tname from bookstore.order O,bookstore.book_info B, bookstore.shop S,"
						+"bookstore.book_sort BS1 ,bookstore.book_sort BS2 "
						+" where uid='"+uid+"' and O.oid=S.oid and S.bid=B.bid "
						+" and BS1.tid=B.tid and BS1._id=BS2.tid order by O.time DESC limit  "+index+",3";
//				sql="select O.time ,B.bname ,O.oid ,B.price from bookstore.order O,bookstore.book_info B, bookstore.shop S "+
//						" where uid='"+uid+"' and O.oid=S.oid and S.bid=B.bid order by O.time DESC limit "+index+",3";
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
