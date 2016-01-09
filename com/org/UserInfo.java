package com.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.ResultSet;

public class UserInfo extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String uid = request.getParameter("uid");
		ConnectDatabase connect=new ConnectDatabase();
		connect.connect();
		try {
			connect.pst = connect.connection.prepareStatement("select * from use_info where uid=\"" + uid + "\";");
			connect.ret = (ResultSet) connect.pst.executeQuery();
			if (connect.ret.next()) {
				String uname=connect.ret.getString("uname");
				String age = connect.ret.getString("age");
				String sex=connect.ret.getString("sex");
				String portait=connect.ret.getString("portait");
				out.print("{\"age\":\""+age+"\",\"sex\":\""+sex+"\",\"uname\":\""+uname+"\",\"portait\":\""+portait+"\"}");
				connect.ret.close();
			} else {
				out.print("[{\"msg\":\"用户名不存在\"},{\"state\":\"1\"}]");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("数据库查询失败");
		}
	}
}
