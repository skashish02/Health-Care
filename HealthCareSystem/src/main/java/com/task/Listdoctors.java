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

public class Listdoctors extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection(
						"jdbc:mysql://@localhost:3306/hcs",
						"root","6704kash");
				PreparedStatement pst=con.prepareStatement(
						"select * from doctors");
				ResultSet rs = pst.executeQuery();
				String result = "";
				while(rs.next()) {
					
					 result+=rs.getInt(1)+": "+rs.getString(2)+": "+rs.getString(4)+": "+rs.getString(5)+": "+rs.getString(7)+rs.getInt(8)+": "+rs.getString(11)+": "+rs.getString(12)+"<br>";
					 System.out.println(result);
				}	
				rs.close();
				pst.close();	
				
				ptable( pw, result);
			}
			catch(Exception e) {
				pw.println(e);
				pw.println("operation failed, try again.");
				RequestDispatcher rd=request.getRequestDispatcher("admin.html");
				rd.include(request, response);
	         }
	}
	public void ptable(PrintWriter pw, String result) {
		String id="doc";

		pw.println("<script>");
		pw.println("var result = '" + result + "';");
		pw.println("var movies = result.split('<br>');");
		pw.println("var tableHtml = \"\"");
		pw.println("for (var i = 0; i < movies.length-1; i++) {");
		pw.println("    var movie = movies[i].trim().split(': ');"); 
		pw.println("        tableHtml += '<tr>';");
		pw.println("        tableHtml += '<td style=\"border: 1px solid black; padding: 8px;\">' + movie[0] + '</td>';"); // Movie cell
		pw.println("        tableHtml += '<td style=\"border: 1px solid black; padding: 8px;\">' + movie[1] + '</td>';"); // Price cell
		pw.println("        tableHtml += '<td style=\"border: 1px solid black; padding: 8px;\">' + movie[3] + '</td>';"); // language cell
		pw.println("        tableHtml += '<td style=\"border: 1px solid black; padding: 8px;\">' + movie[2] + '</td>';"); // location cell
		pw.println("        tableHtml += '<td style=\"border: 1px solid black; padding: 8px;\">' + movie[4] + '</td>';");
		pw.println("        tableHtml += '<td style=\"border: 1px solid black; padding: 8px;\">' + movie[5] + '</td>';");
		pw.println("        tableHtml += '<td style=\"border: 1px solid black; padding: 8px;\">' + movie[6] + '</td>';");
		pw.println("        tableHtml += '<td style=\"border: 1px solid black; padding: 8px;\">' + movie[7] + '</td>';");
		pw.println("        tableHtml += '</tr>';");
		pw.println("}");
		pw.println("document.getElementById('"+id+"').innerHTML = tableHtml;");
		pw.println("</script>");
	}
}

