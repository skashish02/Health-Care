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

public class registerServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String full_name = request.getParameter("name");  
		String phone_number = request.getParameter("phone");
		String email = request.getParameter("email");      
		String dob  = request.getParameter("dob");       
		String gender = request.getParameter("gender");      
		String username = request.getParameter("signup-username");            
		String password = request.getParameter("signup-password");
		String password2 = request.getParameter("signup-cpassword");
		if(!(password.equals(password2)) || username.equals(" ")) {
			pw.println("check password and username");
			RequestDispatcher rd=request.getRequestDispatcher("signup.html");
			rd.include(request, response); 
		}
		else {
			try {
				
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection(
						"jdbc:mysql://@localhost:3306/hcs",
						"root","6704kash");
				
				PreparedStatement pst=con.prepareStatement(
						"insert into users(full_name, phone_number,email ,dob ,gender ,username, password) values(?,?,?,?,?,?,?)");
				pst.setString(1, full_name);
				pst.setString(2, phone_number);
				pst.setString(3, email);
				pst.setString(4, dob);
				pst.setString(5, gender);
				pst.setString(6, username);
				pst.setString(7, password);
				pst.executeUpdate();
				pst.close();
				pw.println("<h1 style='margin-bottom:-2.5%'>Registered Successfully </h1>");
				RequestDispatcher rd=request.getRequestDispatcher("index.html");
				rd.include(request, response);
			}
			catch(Exception e) {
				e.printStackTrace();
				pw.println(e);
				pw.println("failed to register, select other username");
				RequestDispatcher rd=request.getRequestDispatcher("signup.html");
				rd.include(request, response);
			}
			
		}
		
		
		
	}

}
