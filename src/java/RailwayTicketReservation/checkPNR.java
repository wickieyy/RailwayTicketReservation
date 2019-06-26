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
public class checkPNR extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session=request.getSession(false);
            String pnrString=request.getParameter("pnr");
            session.setAttribute("pnr", pnrString);
//            System.out.println(pnrString+"zZz");
            int pnrInt=Integer.valueOf(pnrString);
            DBClass dbobj=new DBClass();
	    ResultSet rs=dbobj.getPNRDetails(pnrInt);
            JSONObject obj=new JSONObject();
            JSONObject objArr=new JSONObject();
            if(rs.next()){
                obj.put("pnr", pnrInt);
                obj.put("nos", rs.getString(3));
                obj.put("Origin",rs.getString(6));
                String Origin=rs.getString(6);
                String TrainId=rs.getString(4);
                obj.put("Destination", rs.getString(7));
                obj.put("Date", rs.getString(8));
                obj.put("top",rs.getString(9));
                rs.previous();
                int i=0;
                while(rs.next()){
                    objArr.put("cancelPassenger"+i,rs.getString(11));
                    objArr.put("passengerName"+i, rs.getString(13));
                    objArr.put("passengerAge"+i,rs.getString(14));
                    objArr.put("seatNo"+i,rs.getString(15));
                    objArr.put("status"+i,rs.getString(16));
                    objArr.put("fare"+i,rs.getString(17));
                    i++;
                }
                String DepartureTime=dbobj.getDepartureTime(TrainId,Origin);
                obj.put("passengerInfo",objArr);
                obj.put("DepartureTime",DepartureTime);
                response.getWriter().println(obj);
            }       
            else{
                JSONObject errorMessage=new JSONObject();
                errorMessage.put("error","Enter a valid PNR Number");
                response.getWriter().println(errorMessage);
            }
        }
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
            Logger.getLogger(checkPNR.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(checkPNR.class.getName()).log(Level.SEVERE, null, ex);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(checkPNR.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(checkPNR.class.getName()).log(Level.SEVERE, null, ex);
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
