package com.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;

import com.mysql.jdbc.ResultSet;

public class Login extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		ConnectDatabase con_data = new ConnectDatabase();
		con_data.connect();
		try {
			con_data.pst = con_data.connection
					.prepareStatement("select * from use_info where email=\"" + email + "\";");
			con_data.ret = (ResultSet) con_data.pst.executeQuery();
			if (con_data.ret.next()) {
				System.out.println("jdkl");
				String uid = con_data.ret.getString("uid");
				String name = con_data.ret.getString("uname");
				String psw = con_data.ret.getString("password");
				System.out.println(psw);
				con_data.ret.close();
				if (password == psw) {
					out.print("[{\"msg\":\"验证成功\"},{\"state\":\"0\"}]");
					System.out.println("chenggong");
				} else {
					out.print("[{\"msg\":\"用户名与密码不匹配\"},{\"state\":\"1\"}]");
				}
			} else {
				out.print("[{\"msg\":\"用户名不存在\"},{\"state\":\"1\"}]");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("数据库查询失败");
		} // 准备执行SQL语句

	}
}
