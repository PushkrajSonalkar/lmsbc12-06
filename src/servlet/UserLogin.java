package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.DB;

@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet
{
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String email = req.getParameter("email");
		String psw = req.getParameter("psw");
		
		
		if(UserLogin.authenticate(email, psw)) 
		{
			HttpSession session= req.getSession();
			session.setAttribute("email", email);
			resp.sendRedirect("welcome.jsp");
		}
		else
		{
			resp.sendRedirect("index.jsp");
		}
	}
	public static boolean authenticate(String email, String pass)
	{
		boolean status = false;
		try
		{
			Connection con = DB.getCon();
			PreparedStatement ps = con.prepareStatement("select * from book_user where email=? and password=?");
			ps.setString(1, email);
			ps.setString(2, pass);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				status = true;
			}
			con.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return status;
	}
}
