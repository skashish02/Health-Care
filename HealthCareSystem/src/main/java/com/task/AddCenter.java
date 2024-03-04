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


public class AddCenter extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String cname = request.getParameter("cname");
		String dname = request.getParameter("dname");
		String city = request.getParameter("city");
		String[] options = request.getParameterValues("options[]");
		String tests = String.join(", ", options);
		
		
try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://@localhost:3306/hcs",
					"root","6704kash");
			
			PreparedStatement pst=con.prepareStatement(
					"insert into cnts values(null,?,?,?,?)");
			pst.setString(1, cname);
			pst.setString(2, dname);
			pst.setString(3, city);
			pst.setString(4, tests);
			pst.executeUpdate();
			pst.close();
			
			
			
			pw.println("center: "+cname+" added successfully.");
			RequestDispatcher rd=request.getRequestDispatcher("admin.html");
			rd.include(request, response);
			ListCenters l = new ListCenters();
			l.doPost(request, response);
			Listdoctors ld = new Listdoctors();
			ld.doPost(request, response);
			ListApps la = new ListApps();
			la.doPost(request, response);
			ListUsers lu = new ListUsers();
			lu.doPost(request, response);
			
			
			
		}
		catch(Exception e) {
			pw.println(e);
			pw.println("Failed to add, Try again");
			RequestDispatcher rd=request.getRequestDispatcher("admin.html");
			rd.include(request, response);
		}
		
		
	}
}
