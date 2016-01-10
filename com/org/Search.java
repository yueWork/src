package com.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.javafx.collections.MappingChange.Map;

public class Search extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();

		String search_type = request.getParameter("search_type");
		String search_name = request.getParameter("search_name");
//		System.out.println(search_name);

		ConnectDatabase con_data = new ConnectDatabase();
		con_data.connect();
		String sql;
		int num = 0;
		// 返回符合条件的书籍数量
		if(search_type.equals("作者"))
			search_type = "author";
		else if (search_type.equals("书名")) {
			search_type = "bname";
		}else if (search_type.equals("出版社")) {
			search_type = "publisher";
		}else if (search_type.equals("书籍编号")) {
			search_type = "bid";
		}
		sql = "select count(*) as num from bookstore.book_info where " + search_type + "=\"" + search_name + "\";";
//		System.out.println(sql);
		try {
			con_data.pst = con_data.connection.prepareStatement(sql);
			con_data.ret = (com.mysql.jdbc.ResultSet) con_data.pst.executeQuery();
			con_data.ret.next();
			num = con_data.ret.getInt("num");
//			System.out.println(num);
			con_data.ret.close();
			con_data.pst.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 返回符合条件的书籍的信息
		sql = "select * from bookstore.book_info where " + search_type + "=\"" + search_name + "\" order by bid;";
//		System.out.println(sql);
		try {
			con_data.pst = con_data.connection.prepareStatement(sql);
			con_data.ret = (com.mysql.jdbc.ResultSet) con_data.pst.executeQuery();
			HashMap<String,String>[] books = new HashMap[num];

			String bid = null, bname = null, price = null, cover = null;
			int n = 0;
			while (con_data.ret.next()) {
//				System.out.println("有");
				bid = con_data.ret.getString("bid");
//				System.out.println(bid);
				bname = con_data.ret.getString("bname");
//				System.out.println(bname);
				price = con_data.ret.getString("price");
//				System.out.println(price);
				cover = con_data.ret.getString("cover");
//				System.out.println(cover);
//				System.out.println(n);
				books[n] = new HashMap();
				books[n].put("bid", bid);
				books[n].put("bname", bname);
				books[n].put("price", price);
				books[n].put("cover", cover);
				n++;
			}
			con_data.ret.close();
			con_data.pst.close();
			con_data.close();
//			System.out.println("num"+num);
			if (num != 0 ) {
				 String result =
				 "{\"msg\":\"查询成功\",\"state\":\"0\",\"count\":\"" + num +
				 "\""+ ",\"books\":[";
//				String result = "{\"books\":[";
				for (int i = 0; i < num; i++) {
					result = result + "{\"bid\":\"" + books[i].get("bid") + "\"," + "\"bname\":\""
							+ books[i].get("bname") + "\"," + "\"price\":\"" + books[i].get("price") + "\","
							+ "\"cover\":\"" + books[i].get("cover") + "\"},";
				}
//				System.out.println(result);
//				System.out.println(result.length());
//				System.out.println(result.length() - 2);
				result = result.substring(0, result.length() - 1);
//				System.out.println(result);
				result = result + "]}";
//				System.out.println(result);
				out.print(result);
			}
			else {
				String result = "{\"msg\":\"无符合条件书籍\",\"state\":\"1\",\"count\":\"" + num + "\"}";
				out.print(result);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			String result = "{\"msg\":\"查询失败\",\"state\":\"1\"}";
			out.print(result);
		}

	}

}
