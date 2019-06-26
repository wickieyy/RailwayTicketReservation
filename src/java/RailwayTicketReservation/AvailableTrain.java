package RailwayTicketReservation;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jdk.nashorn.internal.runtime.Context;
import org.json.simple.JSONObject;

@WebServlet("/AvailableTrain")
public class AvailableTrain extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AvailableTrain() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject errorMessage=new JSONObject();
	HttpSession session=request.getSession(false);
	PrintWriter out=response.getWriter();
	String Origin=(String) request.getParameter("Origin");
	String Destination=(String) request.getParameter("Destination");
	if(Origin.equals(Destination)){
            errorMessage.put("error","Source and Destination Cannot be Same");
            out.println(errorMessage);
	}
	else{
            String date=(String) request.getParameter("Date");
            String splitDate[]=date.split("-");
            String dateString=splitDate[1]+"/"+splitDate[2]+"/"+splitDate[0];
            RetriveFromDb rdb=new RetriveFromDb();
            try {
		rdb.ShowAvailableTrain(response,request,Origin,Destination,dateString);
            } 
            catch (SQLException e) 
            {
                
            }
	}
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doGet(request, response);
    }
}
