package com.csu.edu;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewRecords extends HttpServlet {
	private static final long serialVersionUID = 8564080796092325815L;
	Connection conn;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ResultSet set = null;
		PrintWriter out = resp.getWriter();
		try {
			set = conn.createStatement().executeQuery("select * from records");
			StringBuffer sb = new StringBuffer();
			while(set.next()){
				String name = set.getString(1);
				String sessionId = set.getString(2);
				String phone = set.getString(3);
				String email = set.getString(4);
				Date registerDate = set.getDate(5);
				sb.append("name = " + name + ", sessionId = " + sessionId + ", phone = " + phone + ", email = " + email + "registerDate = " + registerDate + "\r\n");
			}
			out.write(sb.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				set.close();
				out.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(req, resp);
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		System.out.println("ViewRecords servlet inited!...");
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			conn = DriverManager.getConnection("jdbc:hsqldb:file:testdb", "SA", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		System.out.println("ViewRecords servlet destroyed!...");
		if(null != conn){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}
