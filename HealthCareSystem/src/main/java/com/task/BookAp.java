package com.task;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class BookAp extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession();
		String username = session.getAttribute("name").toString();
		String name = request.getParameter("patientName");
		int age = Integer.parseInt(request.getParameter("age"));
		String gender = request.getParameter("gender");
		String center = request.getParameter("center");
		String date = request.getParameter("appointmentDate");
		String time = request.getParameter("appointmentTime");
		String description = request.getParameter("content");
		
		LocalDate ldate = LocalDate.parse(date);
        LocalTime ltime = LocalTime.parse(time);
		
		Date adate = Date.valueOf(ldate);
		Time atime = Time.valueOf(ltime);
		
try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://@localhost:3306/hcs",
					"root","6704kash");
			
			PreparedStatement pst=con.prepareStatement(
					"insert into appts(username, name,age, gender, center, adate, atime, description) values(?,?,?,?,?,?,?,?)");
			pst.setString(1, username);
			pst.setString(2, name);
			pst.setInt(3, age);
			pst.setString(4, gender);
			pst.setString(5, center);
			pst.setDate(6, adate);
			pst.setTime(7, atime);
//			pst.setString(6, date);
//			pst.setString(7, time);
			pst.setString(8, description);
			pst.executeUpdate();
			pst.close();
			
			
			
			pw.println("<h1 style='color:red;'>appointment created successfully.</h1>");
			RequestDispatcher rd=request.getRequestDispatcher("cust_list.html");
			rd.include(request, response);

			
			
		}
		catch(Exception e) {
			pw.println(e);
			pw.println("Failed to create, Try again");
			RequestDispatcher rd=request.getRequestDispatcher("bookv.html");
			rd.include(request, response);
		}
		
		
	}
}
