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


public class AddDoctor extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String d_name = request.getParameter("d_name");
		String email = request.getParameter("email");
		String phn = request.getParameter("phone_number");
		String Spl = request.getParameter("Sepecialist");
		int age = Integer.parseInt(request.getParameter("age"));
		int exp = Integer.parseInt(request.getParameter("exp"));
		int pin = Integer.parseInt(request.getParameter("pincode"));
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String gen = request.getParameter("gender");
		String add = request.getParameter("address");
		
		
		
try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://@localhost:3306/hcs",
					"root","6704kash");
			
			PreparedStatement pst=con.prepareStatement(
					"insert into doctors values(null,?,?,?,?,?,?,?,?,?,?,?)");
			pst.setString(1, d_name);
			pst.setString(2, email);
			pst.setString(3, phn);
			pst.setString(4, gen);
			pst.setInt(5, age);
			pst.setString(6, Spl);
			pst.setInt(7, exp);
			pst.setString(8, add);
			pst.setInt(9, pin);
			pst.setString(10,username);
			pst.setString(11, password);
			pst.executeUpdate();
			pst.close();
			
			
			
			pw.println("Doctor:  "+d_name+" added successfully.");
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
