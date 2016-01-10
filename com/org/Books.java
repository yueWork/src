package com.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Books extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();

		int num = Integer.parseInt(request.getParameter("num"));
		System.out.println(num);
		String bids[] = new String[num];
		for (int i = 0; i < num; i++) {
			String temp = "bid" + i;
			bids[i] = request.getParameter(temp);
			System.out.println(bids[i]);
		}
		String sql;
		ConnectDatabase con_data = new ConnectDatabase();
		con_data.connect();
		sql = "select * from book_info where bid=" + bids[0];
		for (int j = 1; j < num; j++) {
			sql = sql + " or bid=" + bids[j];
		}
		sql = sql + ";";
		System.out.println(sql);
		try {
			con_data.pst = con_data.connection.prepareStatement(sql);
			con_data.ret = (com.mysql.jdbc.ResultSet) con_data.pst.executeQuery();
			HashMap<String, String>[] books = new HashMap[num];

			String bid = null, bname = null, price = null, cover = null, counter = null;
			int n = 0;
			while (con_data.ret.next()) {
				System.out.println("有");
				bid = con_data.ret.getString("bid");
				System.out.println(bid);
				bname = con_data.ret.getString("bname");
				System.out.println(bname);
				price = con_data.ret.getString("price");
				System.out.println(price);
				cover = con_data.ret.getString("cover");
				System.out.println(cover);
				counter = con_data.ret.getString("counter");
				System.out.println(cover);
				System.out.println(n);
				books[n] = new HashMap();
				books[n].put("bid", bid);
				books[n].put("bname", bname);
				books[n].put("price", price);
				books[n].put("cover", cover);
				books[n].put("counter", counter);
				n++;
			}
			con_data.ret.close();
			con_data.pst.close();
			String result = "{\"msg\":\"查询成功\",\"state\":\"0\",\"count\":\"" + num + "\"" + ",\"books\":[";
			// String result = "{\"books\":[";
			for (int i = 0; i < num; i++) {
				result = result + "{\"bid\":\"" + books[i].get("bid") + "\"," + "\"bname\":\"" + books[i].get("bname")
						+ "\"," + "\"price\":\"" + books[i].get("price") + "\"," + "\"cover\":\""
						+ books[i].get("cover") + "\"," + "\"counter\":\"" + books[i].get("counter") + "\"},";
			}
				System.out.println(result);
				System.out.println(result.length());
				System.out.println(result.length() - 2);
				result = result.substring(0, result.length() - 1);
				System.out.println(result);
				result = result + "]}";
				System.out.println(result);

				out.print(result);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			String result = "{\"msg\":\"查询失败\",\"state\":\"1\"}";
			out.print(result);
		}
	}

}