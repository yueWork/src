package com.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Search extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ServletConfig config=null;
	public String url = "jdbc:mysql://localhost:3306/university";
	public String name = "com.mysql.jdbc.Driver";
	public String username = "root";
	public String password = "adminzyy22339456732";
	public java.sql.PreparedStatement pst = null;
	public Connection connection = null;
	public ResultSet ret = null;
	public java.sql.Statement statement = null;
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String selectSubject = request.getParameter("selectSubject");
		String search = request.getParameter("search");
		ConnectDatabase connect=new ConnectDatabase();
		connect.connect();
		out.print("[{\"result\":50}]");
	}
	
}
