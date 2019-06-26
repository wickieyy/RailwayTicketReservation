package RailwayTicketReservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class putInDb {
    public void putData(String fname,String lname,String age, String email,String password ,String Gender,String MobileNumber,String City) throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwayTicketReservation","wickie","wickie");
			PreparedStatement ps = connection.prepareStatement("insert into registration(FirstName,LastName,Email,Password,Gender,Age,MobileNumber,City) values(?,?,?,?,?,?,?,?)");
			ps.setString(1,fname);
			ps.setString(2, lname);
			int ageInt=Integer.parseInt(age);
			ps.setInt(6, ageInt);
			ps.setString(3, email);
			ps.setString(4, password);
			ps.setString(5, Gender);
			ps.setString(7, MobileNumber);
			ps.setString(8, City);
			ps.executeUpdate();
			} catch (SQLException e){
			e.printStackTrace();
                        }	
                    }
    
}