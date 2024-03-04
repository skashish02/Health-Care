package com.task;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Approve extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String id = request.getParameter("a_id");
		int a_id = Integer.parseInt(id);
		
try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://@localhost:3306/hcs",
					"root","6704kash");
			
			PreparedStatement pst=con.prepareStatement(
					"update appts set approved = 1 where a_id = ?");
			pst.setInt(1, a_id);
			
			pst.executeUpdate();
			pst.close();
			
			
			pw.println("appointment: "+a_id+" approved successfully.");
			RequestDispatcher rd=request.getRequestDispatcher("admin.html");
			rd.include(request, response);
			
			ListCenters l = new ListCenters();
			l.doPost(request, response);
			
			ListUsers lu = new ListUsers();
			lu.doPost(request, response);
			
			ListApps la = new ListApps();
			la.doPost(request, response);
			
			
			
		}
		catch(Exception e) {
			pw.println(e);
			pw.println("Failed to add, Try again");
			RequestDispatcher rd=request.getRequestDispatcher("admin.html");
			rd.include(request, response);
		}
		
		
	}
}
