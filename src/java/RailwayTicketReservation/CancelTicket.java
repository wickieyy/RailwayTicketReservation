package RailwayTicketReservation;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
public class CancelTicket extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
       HttpSession session=request.getSession(false);
		String cancelTicket=request.getParameter("cnclPas");
		String cancelArr[]=cancelTicket.split("/");
		String pnrCheck=(String)session.getAttribute("pnr");
		int pnrInt=Integer.valueOf(pnrCheck);
		String email=(String) session.getAttribute("email");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
                        DBClass dbobj=new DBClass();
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwayTicketReservation", "wickie", "wickie");
			for(int j=0;j<cancelArr.length;j++){
                            ResultSet rs1=dbobj.getCancelPassengerDetails(cancelArr[j],pnrInt);
                            rs1.next();
                            int nos=cancelArr.length;
                            String TrainId=rs1.getString(4);
                            String tot=rs1.getString(5);
                            String Origin=rs1.getString(6);
                            String Destination=rs1.getString(7);
                            String doj=rs1.getString(8);
                            String mop=rs1.getString(9);
                            int start=dbobj.getStationNumber(TrainId,doj,Origin);
                            int end=dbobj.getStationNumber(TrainId,doj,Destination);
                            int tierTypeSeatCount=0;
                            if(rs1.getString(16).equals("Confirmed") || rs1.getString(16).equals("Waiting List")){
                                ResultSet rs3=dbobj.getSeatCancelCount(TrainId, doj);
                                rs3.next();
                                if(tot.equals("ac_tier")){
                                    tierTypeSeatCount=rs3.getInt(4);
                                }
                                if(tot.equals("general_tier")){
                                   tierTypeSeatCount=rs3.getInt(5);
                                }
                                String seats="";
                                if(rs3.next()){
                                    rs3.previous();
                                    while(rs3.next()){
                                            seats=seats+tierTypeSeatCount+"/";
                                    }
                                String seatsArr[]=seats.split("/");
                                if(tot.equals("ac_tier")){
                                   dbobj.updateSeatCountInAcTier(seatsArr,start,end,nos,TrainId);
                                }
                                if(tot.equals("general_tier")){
                                    dbobj.updateSeatCountInGeneralTier(seatsArr,start,end,nos,TrainId);
                                }
                            }
                            dbobj.updateCanceledPassenger(cancelArr);
                            JSONObject canceledResponse=new JSONObject();
                            ResultSet cardDetails=dbobj.getCardDetails(cancelArr,pnrInt);
                            cardDetails.next();
                            canceledResponse.put("Fare",cardDetails.getInt(7)*nos);
                            canceledResponse.put("CardNo", cardDetails.getString(8));
                            canceledResponse.put("message", "Canceled");
                            canceledResponse.put("NoOfSeatsCanceled",nos);
                            response.getWriter().println(canceledResponse);
                        }
                    }
		}catch (SQLException e) {
			e.printStackTrace();
		}
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CancelTicket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CancelTicket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
