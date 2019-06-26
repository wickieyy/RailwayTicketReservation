package RailwayTicketReservation;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import org.json.simple.JSONObject;

@WebServlet("/viewBookings")
public class viewBookings extends HttpServlet {
	static int count=0;
	private static final long serialVersionUID = 1L;
    public viewBookings() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
                    JSONObject viewBookings = new JSONObject();
                    JSONObject  mainJsonBookings= new JSONObject();
                    HttpSession session=request.getSession(false);
//                    String email=(String) session.getAttribute("email");
                    Cookie[] cookies = request.getCookies();
                    String userId = null;
                    for(Cookie cookie : cookies){
                        if("email".equals(cookie.getName())){
                            userId = cookie.getValue();
                        }
                    }
                    String email = userId;
                    Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwayTicketReservation","wickie","wickie");
                    PreparedStatement ps = connection.prepareStatement("select * from ticketinfo where email=?");
                    ps.setString(1,email);
                    ResultSet rs=ps.executeQuery();
                    if(rs.next()){
                    	rs.previous();
//                    	while(rs.next()){}
                        int i=0 ;
                        while(rs.next())
                        {
                            viewBookings.put("pnr"+i,rs.getInt(1));
                            viewBookings.put("nos"+i, rs.getInt(3));
                            viewBookings.put("Origin"+i,rs.getString(6));
                            viewBookings.put("Destination"+i,rs.getString(7));
                            viewBookings.put("doj"+i,rs.getString(8));
                            viewBookings.put("top"+i,rs.getString(9));
                            mainJsonBookings.put("Booking"+i,viewBookings);
                            i++;
                        }
                        mainJsonBookings.put("size",i);
			response.getWriter().println(mainJsonBookings);
			}
			else{
                            JSONObject errorJson = new JSONObject();
                            errorJson.put("error","No Bookings Made");
                            response.getWriter().println(errorJson);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
