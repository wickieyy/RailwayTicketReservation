/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RailwayTicketReservation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author test
 */
public class DBClass {
    protected boolean check(String email,String password,HttpServletResponse response,HttpServletRequest request) throws SQLException, IOException, ServletException{
		boolean bool=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwayTicketReservation", "wickie", "wickie");
			Statement stmt = connection.createStatement();
			PreparedStatement pstmt=connection.prepareStatement("select * from registration where email=? and password=?");
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			bool=rs.next();
     		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return bool;
	}
    protected ResultSet getTrainForDate(String Date) throws SQLException{
        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwayTicketReservation","wickie","wickie");
    	PreparedStatement ps=connection.prepareStatement("Select * from route where Date=?");
    	ps.setString(1, Date);
    	ResultSet rs=ps.executeQuery();
        return rs;
    }
    protected ResultSet getTrainId(String id) throws SQLException{
         Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwayTicketReservation","wickie","wickie");
         PreparedStatement ps1=connection.prepareStatement("Select * from traindetails where TrainId=?");
         ps1.setString(1,id);
         ResultSet rs=ps1.executeQuery();
         return rs;
    }
    protected ResultSet getStationId(String id,String Origin) throws SQLException{
          Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwayTicketReservation","wickie","wickie");
          PreparedStatement ps2=connection.prepareStatement("Select * from route where TrainId=? and StationName=?");
          ps2.setString(1,id);
          ps2.setString(2, Origin);
          ResultSet rs2=ps2.executeQuery();
          return rs2;
    }
    protected ResultSet getPNRDetails(int pnrInt) throws SQLException{
          Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwayTicketReservation","wickie","wickie");
          PreparedStatement ps2 = connection.prepareStatement("select * from ticketinfo INNER JOIN passengerdetails ON ticketinfo.pnr=passengerdetails.pnr where ticketinfo.PNR=?");
	  ps2.setInt(1, pnrInt);
          ResultSet rs2=ps2.executeQuery();
          return rs2;
    }
    protected String getDepartureTime(String TrainId,String StationName) throws SQLException{
          Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwayTicketReservation","wickie","wickie");
          PreparedStatement ps2 = connection.prepareStatement("select DepartureTime from route where StationName=? and TrainId=?");
	  ps2.setString(1, StationName);
          ps2.setString(2, TrainId);
          ResultSet rs2=ps2.executeQuery();
          rs2.next();
          return rs2.getString(1);
    }
     protected void updateCanceledPassenger(String[] cancelArr) throws SQLException{
          Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwayTicketReservation","wickie","wickie");
          PreparedStatement ps2=connection.prepareStatement("update passengerdetails set Status=? where pid=?");
          ps2.setString(1,"Canceled");
          for(int i=0;i<cancelArr.length;i++){
              ps2.setString(2,cancelArr[i]);
              ps2.executeUpdate();
          }
    }
    protected ResultSet getCardDetails(String[] cancelArr,int pnrInt) throws SQLException{
          Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwayTicketReservation","wickie","wickie");
          PreparedStatement ps2=connection.prepareStatement("select * from passengerdetails INNER JOIN paymentcarddetails ON paymentcarddetails.pnr=passengerdetails.pnr where passengerdetails.pnr=?");
          ps2.setInt(1,pnrInt);
          ResultSet rs=ps2.executeQuery();
          return rs;
    }
    protected void updateSeatCountInAcTier(String[] seatsArr,int start,int end,int nos,String TrainId) throws SQLException{
          Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwayTicketReservation","wickie","wickie");
          PreparedStatement ps411=connection.prepareStatement("update route set seats_in_ac_tier=seats_in_ac_tier+? where TrainId=? and OrderinNumber>=? and OrderinNumber<?");
          for(int i=0;i<seatsArr.length;i++){
              int temp=Integer.valueOf(seatsArr[i])+nos;
          }
              ps411.setInt(1, nos);
              ps411.setString(2,TrainId);
              ps411.setInt(3, start);
              ps411.setInt(4, end);
              ps411.executeUpdate();
              start=start+100;      
    }
    protected void updateSeatCountInGeneralTier(String[] seatsArr,int start,int end,int nos,String TrainId) throws SQLException{
          Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwayTicketReservation","wickie","wickie");
          PreparedStatement ps411=connection.prepareStatement("update route set seats_in_general_tier=? where TrainId=? and OrderinNumber>=? and OrderinNumber<?");
          for(int i=0;i<seatsArr.length;i++){
              int temp=Integer.valueOf(seatsArr[i])+nos;
          }
              ps411.setInt(1, nos);
              ps411.setString(2,TrainId);
              ps411.setInt(3, start);
              ps411.setInt(4, end);
              ps411.executeUpdate();
              start=start+100;
//              if(start>=end) break;
    }
    protected ResultSet getCancelPassengerDetails(String cancelArr,int pnrInt) throws SQLException{
        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwayTicketReservation","wickie","wickie");
        PreparedStatement ps1=connection.prepareStatement("select * from ticketinfo INNER JOIN passengerdetails on ticketinfo.pnr=passengerdetails. pnr where ticketinfo.PNR=? and pid=?");
        ps1.setInt(1, pnrInt);
        ps1.setInt(2, Integer.valueOf(cancelArr));
        ResultSet rs1=ps1.executeQuery();
        return rs1;
    }
     protected int getStationNumber(String TrainId,String doj,String Origin) throws SQLException{
        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwayTicketReservation","wickie","wickie");
        PreparedStatement ps4=connection.prepareStatement("select OrderinNumber from route where TrainId=? and Date=? and StationName=?");
        ps4.setString(1, TrainId);
        ps4.setString(2, doj);
        ps4.setString(3, Origin);
        ResultSet rs4=ps4.executeQuery();
        rs4.next();
        int start=rs4.getInt(1);
        return start;
    }
    protected ResultSet getSeatCancelCount(String TrainId,String doj) throws SQLException{
        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwayTicketReservation","wickie","wickie");
        PreparedStatement ps3=connection.prepareStatement("select * from route where TrainId=? and Date=?");
        ps3.setString(1, TrainId);
        ps3.setString(2, doj);
        ResultSet rs3=ps3.executeQuery();
        return rs3;
    }
    protected String getTrack(String TrainId,String Date) throws SQLException{
        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwayTicketReservation","wickie","wickie");
        PreparedStatement ps3=connection.prepareStatement("select * from route where TrainId=? and Date=? order by OrderinNumber ASC ");
        ps3.setString(1, TrainId);
        ps3.setString(2,Date);
        ResultSet rs3=ps3.executeQuery();
        String track="";
        while(rs3.next()){
            track=track+rs3.getString(2)+"/";
        }
        track=track+"!";
        return track;
    }
    protected boolean checkSeatAvailability(int seatNumber,String Origin,String Destination,String tot) throws SQLException{
        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwayTicketReservation","wickie","wickie");
        System.out.println(seatNumber+"inMethod");
        PreparedStatement ps3=connection.prepareStatement("select passengerdetails.seatno,passengerdetails.pnr,ticketinfo.Origin,ticketinfo.Destination,passengerdetails.status from passengerdetails INNER JOIN ticketinfo ON ticketinfo.pnr=passengerdetails.pnr where passengerdetails.seatno=? and ticketinfo.Type_of_tier=?");
        ps3.setInt(1,seatNumber);
        ps3.setString(2,tot);
        ResultSet rs=ps3.executeQuery();
        if(rs.next()){
            while(rs.next()){}
            rs.previous();
            if(rs.getInt(1)==seatNumber){
                if(rs.getString(4).equals(Origin) || rs.getString(3).equals(Destination)){
                    return true;
                }
                else{
                    return false;                    
                }
            }
        }
        return true;
    }
    protected void insertPassenegerDetails(int pnrInt,String namesArr,String agesArr,int seatInt,String Status,String tot,String acfare,String generalfare) throws SQLException{
        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwayTicketReservation","wickie","wickie");
        PreparedStatement p11=connection.prepareStatement("insert into passengerdetails(pnr,name,age,seatno,Status,Fare) values(?,?,?,?,?,?)");
        p11.setInt(1, pnrInt);
        p11.setString(2, namesArr);
        p11.setString(3, agesArr);
        p11.setInt(4,seatInt);
        p11.setString(5, Status);
        if(tot.equals("ac_tier")){
            p11.setInt(6,Integer.valueOf(acfare));
        }
        if(tot.equals("general_tier")){
            p11.setInt(6,Integer.valueOf(generalfare));
        }
        p11.executeUpdate();
    }
    protected int getMaxSeatInAcTier(String TrainId,String doj,int strt,int end) throws SQLException{
        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwayTicketReservation","wickie","wickie");
        PreparedStatement pp1=connection.prepareStatement("select max(seats_in_ac_tier) from route where TrainId=? and Date=? and OrderinNumber>=? and OrderinNumber<?");
	pp1.setString(1, TrainId);
	pp1.setString(2, doj);
        pp1.setInt(3,strt);
        pp1.setInt(4, end);
        ResultSet r=pp1.executeQuery();
        r.next();
        return r.getInt(1);
    }
     protected int getMaxSeatInGeneralTier(String TrainId,String doj,int strt,int end) throws SQLException{
        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwayTicketReservation","wickie","wickie");
        PreparedStatement pp1=connection.prepareStatement("select max(seats_in_general_tier) from route where TrainId=? and Date=? and OrderinNumber>=? and OrderinNumber<?");
	pp1.setString(1, TrainId);
	pp1.setString(2, doj);
        pp1.setInt(3,strt);
        pp1.setInt(4, end);
        ResultSet r=pp1.executeQuery();
        r.next();
        return r.getInt(1);
    }
}
