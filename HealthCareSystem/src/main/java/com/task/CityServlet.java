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

public class CityServlet extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		String city=request.getParameter("city");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://@localhost:3306/hcs",
					"root","6704kash");
			PreparedStatement pst=con.prepareStatement(
					"select cname from cnts where city = '"+city+"'");
			ResultSet rs = pst.executeQuery();
			String result = "";
			while(rs.next()) {
				 result+=rs.getString(1)+"<br>";
			}	
			rs.close();
			pst.close();	
			

			RequestDispatcher rd=request.getRequestDispatcher("bookv.html");
			rd.include(request, response);
			    pw.println("<script>");
			    pw.println("var result = '" + result + "';");
		        pw.println("var centers = result.split('<br>');");
		        pw.println("var select = document.getElementById('center');");
//		        pw.println("select.innerHTML = '<option selected disabled>select center</option>';");
		        pw.println("for (var i = 0; i < centers.length; i++) {");
		        pw.println("    var center = centers[i].trim();");
		        pw.println("    if (center !== '') {");
		        pw.println("        var option = document.createElement('option');");
		        pw.println("        option.text = center;");
		        pw.println("        option.value = center;");
		        pw.println("        select.appendChild(option);");
		        pw.println("    }");
		        pw.println("}");
		        pw.println("</script>");
	
		}
		catch(Exception e) {
			pw.println(e);
			pw.println("operation failed, try again.");
			RequestDispatcher rd=request.getRequestDispatcher("cust_list.html");
			rd.include(request, response);
         }
		
	}
}
