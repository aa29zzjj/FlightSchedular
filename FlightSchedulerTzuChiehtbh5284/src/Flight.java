/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author user
 */
public class Flight {
    private static Connection connection;
    private static PreparedStatement getAllFlights;
    private static PreparedStatement getFlightSeats;
    private static PreparedStatement insertNewFlights;
    private static ResultSet rs;
    private static int [] flightSeats;
    private static ArrayList<String> flights;
    
    public static ArrayList<String> getAllFlights(){
        try{
         connection = DBConnection.getConnection();
            flights = new ArrayList();
            getAllFlights = connection.prepareStatement("select name from flight");
            rs = getAllFlights.executeQuery();
            while (rs.next())
            {
                flights.add(rs.getString(1));
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return flights;
    }
    public static int [] getFlightSeats(){
       try{
         connection = DBConnection.getConnection();
            getAllFlights = connection.prepareStatement("select seats from flight");
            ResultSetMetaData metaData = rs.getMetaData();
            int count = metaData.getColumnCount();     
            rs = getFlightSeats.executeQuery();
           for(int i = 0; i <= count ; i++){
           flightSeats[i]= (int) rs.getObject(i);
           }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return flightSeats;
    }
    public static void addNewFlight(String flight, int seats){
         try
        {
            connection = DBConnection.getConnection();
            insertNewFlights = connection.prepareStatement("insert into flight (name, seats) values(?,?)");
            insertNewFlights.setString(1, flight);
            insertNewFlights.setInt(2, seats);
            insertNewFlights.executeUpdate();
        
        }  
         catch (SQLException ex)
        {
            ex.printStackTrace();
        }

    }    
}
     
    
      

