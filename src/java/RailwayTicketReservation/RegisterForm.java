package RailwayTicketReservation;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class RegisterForm extends HttpServlet {
    static boolean alreadyRegistered=false;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                putInDb pdb=new putInDb();
		PrintWriter out=response.getWriter();
		String fname=request.getParameter("firstname");
		String lname=request.getParameter("lastname");
		String age = request.getParameter("age");
		String email= request.getParameter("email");
		String password =request.getParameter("password");
		String gender=request.getParameter("Gender");
		String MobileNumber=request.getParameter("MobileNumber");
		String City=request.getParameter("City");
		try {
                        alreadyRegistered=isRegistered(email);
//                        System.out.println(alreadyRegistered);
			if(alreadyRegistered==false)
			pdb.putData(fname,lname,age,email,password,gender,MobileNumber,City);
			else{
				RequestDispatcher rd=request.getRequestDispatcher("Login.jsp");
				rd.include(request, response);
				rd=request.getRequestDispatcher("alreadyRegistered.html");
                                rd.include(request, response);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	protected boolean isRegistered(String email) {
		boolean bool=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwayTicketReservation", "wickie", "wickie");
			Statement stmt = connection.createStatement();
			PreparedStatement pstmt=connection.prepareStatement("select * from registration where email=?");
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			bool=rs.next();
     		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                processRequest(request,response);
		PrintWriter out=response.getWriter();
		if(alreadyRegistered==false){
			RequestDispatcher rd=request.getRequestDispatcher("Login.jsp");
			rd.include(request, response);
			rd=request.getRequestDispatcher("resgistrationSuccess.html");
			rd.include(request, response);
		}
		else{
			
		}
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
