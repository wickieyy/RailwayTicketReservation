
package RailwayTicketReservation;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/validateLogin")
public class validateLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public validateLogin() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String uname =request.getParameter("firstname");
		session.setAttribute("email", email);
		DBClass obj=new DBClass();
		try {
			boolean b=obj.check(email,password,response,request);
			if(b==true){
                                Cookie userName = new Cookie("email",email);
                                userName.setMaxAge(100000);
                                response.addCookie(userName);
				response.sendRedirect("SelectOrgDes.jsp");
			}
			else{
				RequestDispatcher rd= request.getRequestDispatcher("Login.jsp");
				rd.include(request, response);
				rd=request.getRequestDispatcher("IncorrectLogin.html");
                                rd.include(request, response);
//                                out.println("1234567890987654321");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
