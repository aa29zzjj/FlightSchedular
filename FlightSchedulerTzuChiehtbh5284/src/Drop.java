/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class Drop {
     private static Connection connection;
     private static PreparedStatement getAllFlights;
     private static PreparedStatement insertNewBookings;
     private static PreparedStatement getCustomerDate;
     private static PreparedStatement getFlightSeats;
     private static PreparedStatement getWaiters;
     private static PreparedStatement dropFlight;
     private static ResultSet rs;
     private static int flightSeats;
     private static int seatsBooked;
     private static ArrayList<String> waiters;
     private static String customers;
     private static String emptyFlight;
     private static Date dates;
     private static Timestamp timestamp;
     
     public static void dropFlight(String flight){
      try{   
       
        connection = DBConnection.getConnection();
        dropFlight = connection.prepareStatement("delete from flight where name= ? ");
        dropFlight.setString(1, flight);
        dropFlight.executeUpdate();
    
         }
         catch(SQLException ex){
            ex.printStackTrace();
         }
     }
     public static void cancelBooking(String customer, String flight, Date date){
      try{   
       
        connection = DBConnection.getConnection();
        dropFlight = connection.prepareStatement("delete from bookings where flight= ? and customer =? and day=? ");
        dropFlight.setString(1, flight);
        dropFlight.setString(2, customer);
        dropFlight.setDate(3, date);
        dropFlight.executeUpdate();
    
         }
         catch(SQLException ex){
            ex.printStackTrace();
         }
     }
      public static void dropWaitListFlight(String flight){
      try{   
        connection = DBConnection.getConnection();
        dropFlight = connection.prepareStatement("delete from waitlist where flight= ? ");
        dropFlight.setString(1, flight);
        dropFlight.executeUpdate();
        
         }
         catch(SQLException ex){
            ex.printStackTrace();
         }
     }      
     public static void setCustomerDate(String flight){
         try
        {
            connection = DBConnection.getConnection();
            getCustomerDate = connection.prepareStatement("select * from bookings where flight = ?  ");
            
            getCustomerDate.setString(1, flight);           
            rs = getCustomerDate.executeQuery();
            if(rs.next()){
            customers =rs.getString(1);
            dates = rs.getDate(3);
            timestamp = rs.getTimestamp(4);
            }
            else{
            customers =null;
            dates = null;
            timestamp = null;
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
     }
     public static String getCustomer(){
         return customers;
     }
     public static Date getDate(){
         return dates;
     }
      public static Timestamp getTimestamp(){
         return timestamp;
     }
    public static Boolean checkFlightSeats(Date date, String flight){
        try{   
       
        connection = DBConnection.getConnection();
        getAllFlights = connection.prepareStatement("select count(flight) from bookings where flight = ? and day = ?");
        getAllFlights.setString(1, flight);
        getAllFlights.setDate(2, date);
        rs = getAllFlights.executeQuery();
        rs.next();
        seatsBooked = rs.getInt(1);
        getFlightSeats = connection.prepareStatement("select seats from flight where name =?");     
        getFlightSeats.setString(1, flight);
        rs = getFlightSeats.executeQuery(); 
        rs.next();
        flightSeats = rs.getInt(1);
        
         }
         catch(SQLException ex){
            ex.printStackTrace();
         }
        if(seatsBooked >= flightSeats){
            emptyFlight = null;
           return false;
           }
           else{
            emptyFlight = flight;
           return true;
           }
    }
   public static Boolean checkCustomerDate(Date date){
   if(date == dates){
       return true;
   }
   else{
       return false;
   }
   }
   
   public static Boolean checkCustomerFlight(){
   if(customers==null || emptyFlight ==null){
   return false;
   }
   else{
   return true;
   }
   
   }
    public static Boolean checkBookingCustomers(){
    if(customers == null){
        return false;
    }
    else{
        return true;
    }
    }
     public static void addBookings(String customer, String flight, Date date, Timestamp timestamp){

         try{   
        connection = DBConnection.getConnection();
        insertNewBookings = connection.prepareStatement("insert into bookings (customer, flight, day, timestamp) values(?,?,?,?)");
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
    
     public static String getEmptyFlight(){
         return emptyFlight;
     }
     public static ArrayList<String>  getAllFlightWaiter(String flight){ 
        try
        {
            connection = DBConnection.getConnection();
            
            getWaiters = connection.prepareStatement("select customer from waitlist where flight = ?  "); 
            getWaiters.setString(1, flight);
     
            waiters = new ArrayList();
                   
            rs = getWaiters.executeQuery();
            while (rs.next())
            {
               waiters.add(rs.getString(1));
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return waiters;
     }
}


