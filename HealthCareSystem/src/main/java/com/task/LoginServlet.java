package com.task;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
	{
		System.out.println("hello");
		
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		String uname=request.getParameter("username");
		String pwd=request.getParameter("password");
		String type = request.getParameter("login-type");
		String cuname ="";
		String cpwd = "";
		System.out.println(type);
	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://@localhost:3306/hcs",
					"root","6704kash");
			PreparedStatement pst=con.prepareStatement(
					"select username,password from "+type+" where username = '"+uname+"'");
			ResultSet rs = pst.executeQuery();
			System.out.println(type);
			
			while(rs.next()) {
				cuname = rs.getString(1);
				cpwd = rs.getString(2);
			}
			pst.close();
			if(cuname.equals(uname)&& cpwd.equals(pwd)) {
				
				request.setAttribute("name", uname);
				request.setAttribute("initial", uname.charAt(0));
				HttpSession session = request.getSession();
				session.setAttribute("initial", uname.charAt(0));
				session.setAttribute("name", uname);
				pw.println("<p style='color:red'>Login Success : "+uname+"  "+type+"<p>");
				
				   
				if(type.equals("admins")) {
					
					String name = session.getAttribute("name").toString();
					request.setAttribute("name", name);
					RequestDispatcher rd=request.getRequestDispatcher("admin.html");
					rd.include(request, response); 	
					ListCenters l = new ListCenters();
					l.doPost(request, response);
					ListApps la = new ListApps();
					la.doPost(request, response);
					ListUsers lu = new ListUsers();
					lu.doPost(request, response);

					
				}
				else if(type.equals("users")){
					RequestDispatcher rd=request.getRequestDispatcher("cust_list.html");
					rd.include(request, response); 
					
				}
				else{
					
					RequestDispatcher rd=request.getRequestDispatcher("index.html");
					rd.include(request, response);
				}
			}  
			
			else {
				pw.println("Login failed & Please check your credentials");
				RequestDispatcher rd=request.getRequestDispatcher("index.html");
				rd.include(request, response);
			}

		}
		catch(Exception e) {
			pw.println(e);
			pw.println("Login is failed, try again");
			RequestDispatcher rd=request.getRequestDispatcher("index.html");
			rd.include(request, response);
		}
		
		
	}


}
