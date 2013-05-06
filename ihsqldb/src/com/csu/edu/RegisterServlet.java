package com.csu.edu;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = -5899601345039334697L;
	Connection conn;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String sessionId = req.getSession().getId();
		String name = req.getParameter("name");
		String phone = req.getParameter("phone");
		String email = req.getParameter("email");
		PrintWriter out = resp.getWriter();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("insert into records(name, sessionId, phone, email, registerDate)" +
					"values(?, ?, ?, ?, ?)");
			pstmt.setString(1, name);
			pstmt.setString(2, sessionId);
			pstmt.setString(3, phone);
			pstmt.setString(4, email);
			pstmt.setDate(5, new Date(Calendar.getInstance().getTimeInMillis()));
			int i = pstmt.executeUpdate();
			out.write(i + " records inserted, <a href='ViewRecords'>ViewRecords</a>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				out.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		System.out.println("Register servlet inited!...");
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			conn = DriverManager.getConnection("jdbc:hsqldb:file:testdb", "SA", "");
			conn.createStatement().executeUpdate("create table records(name varchar(10), sessionId varchar(40), " +
					"phone varchar(20), email varchar(50), registerDate date)");
		} catch (SQLException e) {
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
		System.out.println("Register servlet destroyed!...");
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
