package com.task;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ALogin extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
	{
		
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		String uname=request.getParameter("username");
		String pwd=request.getParameter("password");
		
		System.out.println(uname+" "+pwd);
		
			if(uname.equals("admin") && pwd.equals("Admin")) 
			{
			
				RequestDispatcher rd=request.getRequestDispatcher("admin.html");
				rd.include(request, response); 
				System.out.println("jsp page ");
				
				ListCenters l = new ListCenters();
				l.doPost(request, response);
				Listdoctors ld = new Listdoctors();
				ld.doPost(request, response);
				ListApps la = new ListApps();
				la.doPost(request, response);
				ListUsers lu = new ListUsers();
				lu.doPost(request, response);
				
			}
			else {
				pw.println("Login failed & Please check your credentials");
				RequestDispatcher rd=request.getRequestDispatcher("index.html");
				rd.include(request, response);
			}
		
		
	}
}
