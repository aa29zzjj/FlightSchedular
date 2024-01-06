/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class WaitList {
    private static Connection connection;
    private static PreparedStatement insertNewBookings;
    private static PreparedStatement getAllCustomers;
    private static PreparedStatement getAllFlights;
    private static ResultSet rs;
    private static ArrayList<String> customers2;
    private static ArrayList<String> customers;
    private static ArrayList<String> flights;
    private static ArrayList<Date> statusDates;
    private static ArrayList<String> statusFlights;
    public static void addWaitList(String customer, String flight, Date date, Timestamp timestamp){

         try{   
        connection = DBConnection.getConnection();
        insertNewBookings = connection.prepareStatement("insert into waitlist (customer, flight, day, timestamp) values(?,?,?,?)");
        insertNewBookings.setString(1, customer);
        insertNewBookings.setString(2, flight);
        insertNewBookings.setDate(3, date);
        insertNewBookings.setTimestamp(4, timestamp);
        insertNewBookings.executeUpdate();

    
         }
         catch(SQLException ex){
            ex.printStackTrace();
         }
}
    public static ArrayList<String> statusbyFlightDate(Date date, String flight){
          try
        {
            connection = DBConnection.getConnection();
            
            getAllCustomers = connection.prepareStatement("select customer from waitlist where flight = ? and day = ? ");
            
            getAllCustomers.setString(1, flight);
            getAllCustomers.setDate(2, date);
            customers = new ArrayList();
           
          
            rs = getAllCustomers.executeQuery();
            while (rs.next())
            {
                customers.add(rs.getString(1));
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return customers;
     }
    public static void statusByDate(Date date){
            try
        {
            connection = DBConnection.getConnection();
            
            getAllFlights = connection.prepareStatement("select * from waitlist where day = ? ");      
            getAllFlights.setDate(1, date);       
            flights = new ArrayList();
           customers2 = new ArrayList();
            rs = getAllFlights.executeQuery();
            while (rs.next())
            {       
                customers2.add(rs.getString(1));
                flights.add(rs.getString(2));
            }
          
           
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
            
   }
   public static ArrayList<String> dateFlight(){
    return flights;
   }
   public static ArrayList<String> dateCustomers(){
    return customers2;
   }
   public static void setWaitList(String customer){
         try
        {
            connection = DBConnection.getConnection();
            
            getAllCustomers = connection.prepareStatement("select * from waitlist where customer = ? ");
            statusFlights = new ArrayList();
            statusDates = new ArrayList();
            getAllCustomers.setString(1, customer);           
            rs = getAllCustomers.executeQuery();
            
            while (rs.next())
            {
                statusFlights.add(rs.getString(2));
                statusDates.add(rs.getDate(3));
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
   }
   public static ArrayList<String> getStatusFlights(){
       return statusFlights;
   }
   public static ArrayList<Date> getStatusDates(){
       return statusDates;
   }
}

