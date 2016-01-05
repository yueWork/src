package com.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;

import com.mysql.jdbc.ResultSet;

public class Login extends HttpServlet {

	// 利用cookie设置读取上次返回的时间：
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
	response.setContentType("text/html;charset=utf-8");
	PrintWriter out = response.getWriter();
	out.print("您上次访问的时间是：");
	//获取客户的cookie信息
	Cookie[] cookies = request.getCookies();
	for(int i=0;cookies!=null && i<cookies.length;i++){
	if(cookies[i].getName().equals("lastAccessTime")){
	long value = Long.parseLong(cookies[i].getValue());
	Date date = new Date(value);
	out.print(date.toLocaleString());
	}
	}
	//把新的访问时间写给客户，保存到cookie中
	        Cookie cookie =new Cookie("lastAccessTime",System.currentTimeMillis()+"");
	        cookie.setMaxAge(30*24*3600);
	        cookie.setPath("/CookieDemo");
	response.addCookie(cookie);
	}

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
				String uname = con_data.ret.getString("uname");
				String psw = con_data.ret.getString("password");
				System.out.println(psw);
				System.out.println(password);
				con_data.ret.close();
				if (password.equals(psw)) {
					// 在服务器端创建一个cookie
					Cookie cookie_uid = new Cookie("uid", uid);
					Cookie cookie_uname = new Cookie("uname", uname);
					Cookie cookie_email = new Cookie("email", email);
					Cookie cookie_password = new Cookie("password", psw);
					// 设置cookie的存活时间,如果不设置，cookie不保存。
					cookie_uid.setMaxAge(7 * 24 * 60 * 60);
					cookie_uname.setMaxAge(7 * 24 * 60 * 60);
					cookie_email.setMaxAge(7 * 24 * 60 * 60);
					cookie_password.setMaxAge(7 * 24 * 60 * 60);
					// 将cookie写到客户端
					response.addCookie(cookie_uid);
					response.addCookie(cookie_uname);
					response.addCookie(cookie_email);
					response.addCookie(cookie_password);
					out.print("[{\"msg\":\"验证成功\"},{\"state\":\"0\"}]");
					System.out.println("验证成功");
				} else {
					out.print("[{\"msg\":\"用户名与密码不匹配\"},{\"state\":\"1\"}]");
					System.out.println("验证失败");
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
