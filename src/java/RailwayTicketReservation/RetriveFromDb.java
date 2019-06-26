package RailwayTicketReservation;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

public class RetriveFromDb {
public ResultSet rs;
    public void ShowAvailableTrain(HttpServletResponse response,HttpServletRequest request,  String Origin, String Destination,String Date) throws SQLException, IOException, ServletException{
        JSONObject AvilableTrainJson=new JSONObject();
        JSONObject errorMessage=new JSONObject();
        try {
    	Class.forName("com.mysql.jdbc.Driver");
    	ArrayList<String> TrainIdList=new ArrayList<>();
    	DBClass obj=new DBClass();
        ResultSet rs=obj.getTrainForDate(Date);
        int flag=0;
    	if(rs.next()){
                rs.previous();
//                Logger logger=Logger.getLogger(RetriveFromDb.class.getName());
//                logger.warning("rs"+rs.getString(1));

                String sourceTrainId="";
                int OrderNumber=0;
                while(rs.next()){
                    String src=rs.getString(2);
                    if(flag!=2){
                        if(src.equals(Origin)){
                            sourceTrainId=rs.getString(1);
                            OrderNumber=rs.getInt("OrderinNumber");
                            System.out.println(OrderNumber);
                            flag=1;
                        }
                        if(sourceTrainId.equals(rs.getString(1)) && (Destination.equals(rs.getString(2))) && OrderNumber<rs.getInt("OrderinNumber")){
                            TrainIdList.add(sourceTrainId);
                        }
                    }
                    if(flag!=1){
                        if(src.equals(Destination)){
                            sourceTrainId=rs.getString(1);
                            flag=2;
                        }
                        if(sourceTrainId.equals(rs.getString(1)) && (Origin.equals(rs.getString(2)))){
                            TrainIdList.add(sourceTrainId);
                        }
                    }
                }
                if(TrainIdList.isEmpty()){
                    errorMessage.put("error","No Servies Available For Selected Region");
                    response.getWriter().println(errorMessage);		
                }
                else{
                    String trainTrack="";
                    for(int i=0;i<TrainIdList.size();i++){
                        trainTrack+=obj.getTrack(TrainIdList.get(i),Date);
                    }
                    for(int i=0;i<TrainIdList.size();i++){
                        JSONObject jsonObj=new JSONObject();
                        ResultSet rset=obj.getTrainId(TrainIdList.get(i));    		        
    		        rset.next();
                        ResultSet rs2=obj.getStationId(TrainIdList.get(i),Origin);
    		        rs2.next();			
    		        int seatsinactier=rs2.getInt(4);
                        int seatsingeneraltier=rs2.getInt(5);
    		        String showTrain=rs2.getString(1)+"/"+rset.getString(2)+"/"+Origin+"/"+Destination+"/"+rs2.getString(3)+"/"+seatsinactier+"/"+seatsingeneraltier+"/"+rs2.getString(7)+"/"+rs2.getString(8)+"/"+rs2.getString(9);
    		        showTrain =showTrain.replaceAll("\\s", "");	
    		        jsonObj.put("TrainId",rs2.getString(1)); 
    		        jsonObj.put("TrainName", rset.getString(2));
    		        jsonObj.put("Origin",Origin); 
    		        jsonObj.put("Destination",Destination); 
    		        jsonObj.put("DepartureTime",rs2.getString(3)); 
    		        jsonObj.put("SeatsInAcTier",seatsinactier); 
    		        jsonObj.put("SeatsInGeneralTier",seatsingeneraltier);
    		        jsonObj.put("AcFare",rs2.getString(7));
    		        jsonObj.put("GeneralFare",rs2.getString(8));
    		        jsonObj.put("Date", rs2.getString(9));
                        jsonObj.put("selecttrain",showTrain);
    		        AvilableTrainJson.put("key"+i, jsonObj);
                    }
                    AvilableTrainJson.put("size",TrainIdList.size());
                    AvilableTrainJson.put("trainTrack",trainTrack);
                    response.getWriter().println(AvilableTrainJson);
                }
            }
            else{
                errorMessage.put("error","No Servies Available For Selected Region");
                response.getWriter().println(errorMessage);
            }
        }
        catch (ClassNotFoundException e) 
        {
            e.printStackTrace();
        }
    }
}
