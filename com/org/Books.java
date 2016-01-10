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
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));

		if (pageNum <= (num / 6 + 1) && pageNum >= 0) {
			String bids[] = new String[num];
			for (int i = 0; i < num; i++) {
				String temp = "bid" + i;
				bids[i] = request.getParameter(temp);

			}
			String sql;
			ConnectDatabase con_data = new ConnectDatabase();
			con_data.connect();
			int start = pageNum*6;
			sql = "select * from book_info where bid=" + bids[start];
			int end = 0;
			if((pageNum*6+5) > (num-1)){
				end = num-1;
			}else
				end = pageNum*6+5;
			for (int j = start+1; j <= end; j++) {
				sql = sql + " or bid=" + bids[j];
			}
			sql = sql + ";";
			try {
				con_data.pst = con_data.connection.prepareStatement(sql);
				con_data.ret = (com.mysql.jdbc.ResultSet) con_data.pst.executeQuery();

				HashMap<String, String>[] books = new HashMap[end-start+1];


				String bid = null, bname = null, price = null, cover = null, counter = null;
				int n = 0;
				while (con_data.ret.next()) {

					bid = con_data.ret.getString("bid");
					bname = con_data.ret.getString("bname");
					price = con_data.ret.getString("price");
					cover = con_data.ret.getString("cover");
					counter = con_data.ret.getString("counter");
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
				String result = "{\"msg\":\"查询成功\",\"state\":\"0\",\"count\":\"" + (end-start+1) + "\"" + ",\"books\":[";
				for (int i = 0; i < (end-start+1); i++) {
					result = result + "{\"bid\":\"" + books[i].get("bid") + "\"," + "\"bname\":\""
							+ books[i].get("bname") + "\"," + "\"price\":\"" + books[i].get("price") + "\","
							+ "\"cover\":\"" + books[i].get("cover") + "\"," + "\"counter\":\""
							+ books[i].get("counter") + "\"},";
				}
				result = result.substring(0, result.length() - 1);
				result = result + "]}";

				out.print(result);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				String result = "{\"msg\":\"查询失败\",\"state\":\"1\"}";
				out.print(result);
			}
		} else {
			String result = "{\"msg\":\"查询失败\",\"state\":\"1\"}";
			out.print(result);
		}
	}

}
