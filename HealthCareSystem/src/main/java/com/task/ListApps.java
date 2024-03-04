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

public class ListApps extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection(
						"jdbc:mysql://@localhost:3306/hcs",
						"root","6704kash");
				PreparedStatement pst=con.prepareStatement(
						"select a_id, username, name, description,approved from appts");
				ResultSet rs = pst.executeQuery();
				String result = "";
				while(rs.next()) {
					 result+=rs.getInt(1)+"^ "+rs.getString(2)+"^ "+rs.getString(3)+"^ "+rs.getString(4)+"^ "+rs.getInt(5)+"<br>";
				}	
				rs.close();
				pst.close();	
				System.out.println("result is: "+result);
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
		String id="apps";

		pw.println("<script>");
		pw.println("var result = '" + result + "';");
		pw.println("var movies = result.split('<br>');");
		pw.println("var tableHtml = \"\"");
		pw.println("for (var i = 0; i < movies.length-1; i++) {");
		pw.println("    var movie = movies[i].trim().split('^ ');"); 
		pw.println("        tableHtml += '<tr>';");
		pw.println("        tableHtml += '<td style=\"border: 1px solid black; padding: 8px;\">' + movie[0] + '</td>';"); // Movie cell
		pw.println("        tableHtml += '<td style=\"border: 1px solid black; padding: 8px;\">' + movie[1] + '</td>';"); // Price cell
		pw.println("        tableHtml += '<td style=\"border: 1px solid black; padding: 8px;\">' + movie[2] + '</td>';"); // language cell
		pw.println("        tableHtml += '<td style=\"border: 1px solid black; padding: 8px;\">' + movie[3] + '</td>';"); // location cell
//		pw.println("        tableHtml += '<td style=\"border: 1px solid black; padding: 8px;\"><button class=\"addt\">Approve</button></td>';");
		pw.println("        if (movie[4] === '1') {"); // Checking if movie is approved
		pw.println("            tableHtml += '<td style=\"border: 1px solid black; padding: 8px;\">Approved</td>';"); // Approved cell
		pw.println("        } else {");
		pw.println("            tableHtml += '<td style=\"border: 1px solid black; padding: 8px;\"><button class=\"addt\" a_id =\"' + movie[0] + '\" >Approve</button></td>';");
		pw.println("        }");
		pw.println("        tableHtml += '</tr>';");
		pw.println("}");
		pw.println("document.getElementById('"+id+"').innerHTML = tableHtml;");
		pw.println("</script>");
	}
}

