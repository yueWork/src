package com.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchByType extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();

		String pageNum = request.getParameter("pageNum");
		String type = request.getParameter("type");
		System.out.println(pageNum);
		System.out.println(type);
		int index = Integer.parseInt(pageNum);
		index = index*6;

		ConnectDatabase con_data = new ConnectDatabase();
		con_data.connect();
		String sql;
		String tid = null;
		// 返回符合条件的书籍数量
		sql = "select tid from book_sort where tname=\"" + type + "\";";
		try {
			con_data.pst = con_data.connection.prepareStatement(sql);
			con_data.ret = (com.mysql.jdbc.ResultSet) con_data.pst.executeQuery();
			con_data.ret.next();
			tid = con_data.ret.getString("tid");
			System.out.println(tid);
			con_data.ret.close();
			con_data.pst.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int num = 0;
		sql = "select count(*) as num from book_info where tid=\"" + tid + "\" order by tid limit "+index+",6;";
		try {
			con_data.pst = con_data.connection.prepareStatement(sql);
			con_data.ret = (com.mysql.jdbc.ResultSet) con_data.pst.executeQuery();
			con_data.ret.next();
			num = con_data.ret.getInt("num");
			System.out.println(num);
			con_data.ret.close();
			con_data.pst.close();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		// 返回符合条件的书籍的信息
		sql = "select * from book_info where tid=\"" + tid + "\" order by tid limit "+index+",6;";
		System.out.println(sql);
		try {
			con_data.pst = con_data.connection.prepareStatement(sql);
			con_data.ret = (com.mysql.jdbc.ResultSet) con_data.pst.executeQuery();
			HashMap<String,String>[] books = new HashMap[num];

			String bid = null, bname = null, price = null, cover = null;
			int n = 0;
			while (con_data.ret.next()) {
				bid = con_data.ret.getString("bid");
				bname = con_data.ret.getString("bname");
				price = con_data.ret.getString("price");
				cover = con_data.ret.getString("cover");
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
			if (num != 0 ) {
				 String result =
				 "{\"msg\":\"查询成功\",\"state\":\"0\",\"count\":\"" + num +
				 "\""+ ",\"books\":[";
				for (int i = 0; i < num; i++) {
					result = result + "{\"bid\":\"" + books[i].get("bid") + "\"," + "\"bname\":\""
							+ books[i].get("bname") + "\"," + "\"price\":\"" + books[i].get("price") + "\","
							+ "\"cover\":\"" + books[i].get("cover") + "\"},";
				}
				System.out.println(result);
				System.out.println(result.length());
				System.out.println(result.length() - 2);
				result = result.substring(0, result.length() - 1);
				System.out.println(result);
				result = result + "]}";
				System.out.println(result);
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
