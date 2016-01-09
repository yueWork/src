package com.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.ResultSet;

public class UpdateUserInfo extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String uid = request.getParameter("uid");
        String uname = request.getParameter("uname");
        
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String sex = request.getParameter("sex");
        String age = request.getParameter("age");
        String portait=request.getParameter("portait");
        String sql="update bookstore.use_info set uname='"+uname+"'";
        if(!phone.equals("")){
        	sql+=",phone='"+phone+"'";
        }
        if(!sex.equals("")){
        	sql+=",sex='"+sex+"'";
        }
        if(!age.equals("")){
        	sql+=",age='"+age+"'";
        }
        if(!password.equals("")){
        	sql+=",password='"+password+"'";
        }
        if(!portait.equals("")){
        	sql+=",portait='"+portait+"'";
        }
//        sql+=" where uid='"+uid+"'";
//		System.out.println(sql);
        
		ConnectDatabase connect=new ConnectDatabase();
		connect.connect();
		try{
			String sqlQuery="select portait from bookstore.use_info where uid='"+uid+"'";
			connect.pst=connect.connection.prepareStatement(sqlQuery);
			connect.ret = (ResultSet) connect.pst.executeQuery();
			if (connect.ret.next()) {
				String portaitPre=connect.ret.getString("portait");
				if(!portait.equals(portaitPre)){
					
				}
				connect.ret.close();
			} else {
				out.print("[{\"msg\":\"用户名不存在\"},{\"state\":\"1\"}]");
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("数据库查询失败");
		}
		try {
			sql+=" where uid='"+uid+"'";
			System.out.println(sql);
			connect.pst = connect.connection.prepareStatement(sql);
			connect.pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("数据库查询失败");
		}
	}
}
