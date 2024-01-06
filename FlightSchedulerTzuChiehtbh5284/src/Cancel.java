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
public class Cancel {
    private static Connection connection;
    private static PreparedStatement getWaiter;
    private static PreparedStatement cancelBookings;
    private static PreparedStatement getBookingFlight;
    private static PreparedStatement insertNewBookings;
    private static ResultSet rs;
    private static String flight;
    private static String nextCustomer;
    private static Timestamp timestamp;
    
   public static void setBookingFlight(Date day, String customer){
      try{   
        connection = DBConnection.getConnection();
        getBookingFlight = connection.prepareStatement("select flight from bookings where customer = ? and day = ?"); 
        getBookingFlight.setString(1, customer);
        getBookingFlight.setDate(2, day); 
        rs = getBookingFlight.executeQuery(); 
        if(rs.next()){
        flight = rs.getString(1);
        }
        else{
        flight =null;
        }
          }
         catch(SQLException ex){
            ex.printStackTrace();
         }
   }
   public static String getBookingFlight(){
        return flight;
   }
   public static void setNextBooker(String flight, Date day){
    try{   
        connection = DBConnection.getConnection();
        getWaiter = connection.prepareStatement("select * from waitlist where flight=? and day =?"); 
        getWaiter.setString(1, flight);
        getWaiter.setDate(2, day); 
        rs = getWaiter.executeQuery(); 
        if(rs.next()){
        nextCustomer = rs.getString(1);
        timestamp = rs.getTimestamp(4);
        }
        else{
        nextCustomer = null;
        }
          }
         catch(SQLException ex){
            ex.printStackTrace();
         }
   }
   public static String getNextBooker(){
        return nextCustomer;
   }
   public static Timestamp getNextTimestamp(){
        return timestamp;
   }
    public static void cancelBooking(Date day, String customer){
        try{   
        connection = DBConnection.getConnection();
        cancelBookings = connection.prepareStatement("delete from bookings where customer = ? and day = ? ");
        cancelBookings.setString(1, customer);
        cancelBookings.setDate(2, day);
        cancelBookings.executeUpdate();
    
         }
         catch(SQLException ex){
            ex.printStackTrace();
         }
    }
     public static void cancelWaiter(Date day, String flight, String customer){
        try{   
        connection = DBConnection.getConnection();
        cancelBookings = connection.prepareStatement("delete from waitlist where flight = ? and day = ? and customer =?");
        cancelBookings.setString(1, flight);
        cancelBookings.setDate(2, day);
        cancelBookings.setString(3, customer);
        cancelBookings.executeUpdate();
       

    
         }
         catch(SQLException ex){
            ex.printStackTrace();
         }
    }
    public static void addBooker(String customer, String flight, Date date, Timestamp timestamp){

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
  

   public static Boolean checkBooking(){
      
       if(flight==null){
       return false;
       }
       else{
       return true;
       }
   }
   public static Boolean checkWaiter(){
       
       if(nextCustomer == null){
       return false;
       }
       else{
       return true;
       }
   }
}