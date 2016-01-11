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

public class Confirm extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();

		String oid = UUID.randomUUID().toString().replaceAll("-", "");
		String addressid = UUID.randomUUID().toString().replaceAll("-", "");
		String uid = request.getParameter("uid");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String zip_code = request.getParameter("zip_code");
		String state = "1";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String time=df.format(new Date());
		System.out.println(time);
		String state0="1";
		
		//address表中插入数据
		String sql2 = "insert into bookstore.address (address_id,address,name,phone,zip_code) values (\"" + addressid + "\",\""+ address + "\",\""+ name 
		+ "\",\""+ phone + "\",\""+ zip_code + "\");";
		System.out.println(sql2);
		//user_address表中插入数据
		String sql3 = "insert into bookstore.user_address (uid,address_id) values (\"" + uid + "\",\""+ addressid + "\");";
		System.out.println(sql3);
	
		ConnectDatabase con_data = new ConnectDatabase();
		con_data.connect();
		if(state0 == "1"){
			try {
				con_data.pst = con_data.connection.prepareStatement(sql2);// 准备执行SQL语句
				con_data.pst.executeUpdate();
				con_data.pst.close();
				
				System.out.println("address插入成功！");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				state0="0";
				System.out.println("address插入失败");
			}
		}
		
		if(state0 == "1"){
			try {
				con_data.pst = con_data.connection.prepareStatement(sql3);// 准备执行SQL语句
				con_data.pst.executeUpdate();
				con_data.pst.close();
				
				con_data.close();
				System.out.println("user_address插入成功！");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				state0="0";
				System.out.println("user_address插入失败");
			}
		}
		System.out.println(state0);
		out.print("{\"state0\":\""+state0+"\"}");
}
}
