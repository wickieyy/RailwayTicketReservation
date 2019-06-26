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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;

public class ConfirmBooking extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        String names=request.getParameter("pname");
	String ages=request.getParameter("page");
        String selecttrain=request.getParameter("selecttrain");
        String tierValue=request.getParameter("tierValue");
        String cardNumber=request.getParameter("cardnumber");
        String cardMonth=request.getParameter("cardmonth");
        String cardYear=request.getParameter("cardyear");
        String cardChecked=request.getParameter("cardchecked");
        String upiId=request.getParameter("upiid");
        String[] selecttrainArray=selecttrain.split("/");
        HttpSession session=request.getSession(false);
        JSONObject objJson=new JSONObject();
	String Status="Confirmed";
	Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwayTicketReservation","wickie","wickie");
	String TypeOfPayment=request.getParameter("TypeOfPayment");
	response.setContentType("text/html");
	String TrainId=selecttrainArray[0];
	String TrainName=selecttrainArray[1];
	String Origin=selecttrainArray[2];
	String Destination=selecttrainArray[3];
	String DepartureTime=selecttrainArray[4];
        session.setAttribute("DepartureTime", DepartureTime);
        String acfare=selecttrainArray[7];
        String generalfare=selecttrainArray[8];
	String ns=(String) session.getAttribute("nos");
        int NoOfSeats=Integer.valueOf(ns);
	String tot="";
        if(tierValue.equals("1")) tot="ac_tier";
        if(tierValue.equals("2")) tot="general_tier";                        
        String doj=selecttrainArray[9]+"/"+selecttrainArray[10]+"/"+selecttrainArray[11];
	int seatCount=0;
        if(tot.equals("ac_tier")){
            seatCount=Integer.valueOf(selecttrainArray[5]);
        }
        else{
            seatCount=Integer.valueOf(selecttrainArray[6]);
	}
        if(NoOfSeats>seatCount){
            Status="Waiting List";
        }
        if(Status.equals("Waiting List")){
            //
        }
        Cookie[] cookies = request.getCookies();
                    String userId = null;
                    for(Cookie cookie : cookies){
                        if("email".equals(cookie.getName())){
                            userId = cookie.getValue();
                        }
                    }
        String email = userId;
	PreparedStatement ps=connection.prepareStatement("insert into ticketinfo(Email,No_of_seats_Booked,TrainId,Type_of_tier,Origin,Destination,Date_of_journey,Type_of_payment,SaveCard) values(?,?,?,?,?,?,?,?,?)");
	ps.setString(1, email);
	ps.setInt(2, NoOfSeats);
	ps.setString(3, TrainId);
	ps.setString(4, tot);
	ps.setString(5, Origin);
	ps.setString(6, Destination);
	ps.setString(7, doj);
	ps.setString(8, TypeOfPayment);
        ps.setString(9,cardChecked);
        ps.executeUpdate();
	int seatNumber=0;
	ps=connection.prepareStatement("select * from ticketinfo");
	ResultSet rs=ps.executeQuery();
        DBClass dbobj= new DBClass();
	while(rs.next()){};
            rs.previous();
            int pnrInt=rs.getInt(1);
            int strt=dbobj.getStationNumber(TrainId,doj,Origin);
            int end=dbobj.getStationNumber(TrainId,doj,Destination);
            String[] namesArr=names.split("/");
            String[] agesArr=ages.split("/");
            String seat="";
            if(tot.equals("ac_tier")){ 
                boolean seatAvailability=false;
                int nos=NoOfSeats;
                seatNumber=dbobj.getMaxSeatInAcTier(TrainId,doj,strt,end);
                int seatCopy=seatNumber;
                int i=0;
                while(nos>0){
                 seatNumber=seatCopy;
                if(seatNumber<=0){
                    Status="WaitingList";
                    dbobj.insertPassenegerDetails(pnrInt,namesArr[i],agesArr[i],seatCopy-1,Status,tot,acfare,generalfare);
                    seat=seat+(seatCopy-1)+",";
                }
                else{
                while(seatNumber>=0){
                    if(seatNumber==0){
                        Status="Confirmed";
                        dbobj.insertPassenegerDetails(pnrInt,namesArr[i],agesArr[i],seatCopy,Status,tot,acfare,generalfare);
                        seat=seat+seatCopy+",";
                    }
                    seatAvailability=dbobj.checkSeatAvailability(seatNumber,Origin,Destination,tot);
                    if(seatAvailability==true){
                        Status="Confirmed";
                        dbobj.insertPassenegerDetails(pnrInt,namesArr[i],agesArr[i],seatNumber,Status,tot,acfare,generalfare);
                        seat=seat+seatCopy+",";
                        seatAvailability=false;
                        break;
                    }
                    seatNumber--;
                }
                }
                nos--;
                seatCopy--;
                i++;
                }
                PreparedStatement p11=connection.prepareStatement("update route set seats_in_ac_tier=seats_in_ac_tier-? where TrainId=? and Date=? and OrderinNumber>=? and OrderinNumber <?");
		p11.setInt(1,NoOfSeats);
		p11.setString(2, TrainId);
		p11.setString(3, doj);
		p11.setInt(4, strt);
		p11.setInt(5, end);
		p11.executeUpdate();
            }
		if(tot.equals("general_tier")){
                        boolean seatAvailability=false;
                        int nos=NoOfSeats;
                        seatNumber=dbobj.getMaxSeatInGeneralTier(TrainId,doj,strt,end);
                        int seatCopy=seatNumber;
                        int i=0;
                        while(nos>0){
                         seatNumber=seatCopy;
                        if(seatNumber<=0){
                            Status="WaitingList";
                            dbobj.insertPassenegerDetails(pnrInt,namesArr[i],agesArr[i],seatCopy-1,Status,tot,acfare,generalfare);
                            seat=seat+(seatCopy-1)+",";
                        }
                        else{
                        while(seatNumber>=0){
                            if(seatNumber==0){
                                Status="Confirmed";
                                dbobj.insertPassenegerDetails(pnrInt,namesArr[i],agesArr[i],seatCopy,Status,tot,acfare,generalfare);
                                seat=seat+seatCopy+",";
                            }
                            seatAvailability=dbobj.checkSeatAvailability(seatNumber,Origin,Destination,tot);
                            if(seatAvailability==true){
                                Status="Confirmed";
                                dbobj.insertPassenegerDetails(pnrInt,namesArr[i],agesArr[i],seatNumber,Status,tot,acfare,generalfare);
                                seat=seat+seatNumber+",";
                                seatAvailability=false;
                                break;
                            }
                            seatNumber--;
                        }
                        }
                        nos--;
                        seatCopy--;
                        i++;
                        }
			PreparedStatement p11=connection.prepareStatement("update route set seats_in_general_tier=seats_in_general_tier-? where TrainId=? and Date=? and OrderinNumber>=? and OrderinNumber <?");
			p11.setInt(1, NoOfSeats);
			p11.setString(2, TrainId);
			p11.setString(3, doj);
			p11.setInt(4, strt);
			p11.setInt(5, end);
			p11.executeUpdate();
		}
			for(int i=0;i<namesArr.length;i++){
				PreparedStatement p11=connection.prepareStatement("select pid from passengerdetails where pnr=?");
				p11.setInt(1, pnrInt);
				ResultSet rrr=p11.executeQuery();
				String pid="";
				while(rrr.next()){
					pid=pid+rrr.getInt(1)+"/";
                                }
                            objJson.put("seat", seat);
                            objJson.put("pnr",rs.getString(1));
                            objJson.put("Origin",Origin);
                            objJson.put("Destination",Destination);
                            objJson.put("doj",doj);
                            objJson.put("DepartureTime",DepartureTime);
//                            objJson.put("pid", pid);
                        }
                if(TypeOfPayment.equals("DebitCard") || TypeOfPayment.equals("CreditCard")){ 
                     PreparedStatement pstmnt=connection.prepareStatement("insert into paymentcarddetails values(?,?,?,?,?)");
                     pstmnt.setString(1,cardNumber);
                     pstmnt.setInt(2,Integer.valueOf(cardMonth));
                     pstmnt.setInt(3,Integer.valueOf(cardYear));
                     pstmnt.setString(4,TypeOfPayment);
                     pstmnt.setInt(5,pnrInt);
                     pstmnt.executeUpdate();
                }
                response.getWriter().println(objJson);
        }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ConfirmBooking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ConfirmBooking.class.getName()).log(Level.SEVERE, null, ex);
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
