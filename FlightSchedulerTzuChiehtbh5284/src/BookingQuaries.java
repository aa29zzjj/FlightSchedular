
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class BookingQuaries {
    
    private static Connection connection;
    private static PreparedStatement getFlightSeats;
    private static PreparedStatement insertNewBookings;
    private static PreparedStatement cancelSeats;
    private static PreparedStatement getAllCustomers;
    private static ResultSet rs;
    private static int flightSeats;
    private static int seatsBooked;
    private static ArrayList<String> customers;
    private static ArrayList<String> flights;
    private static ArrayList<Date> dates;
    
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
     public static Boolean checking( String flight, Date day){
          try{   
        connection = DBConnection.getConnection();
        getFlightSeats = connection.prepareStatement("select count(flight) from bookings where flight = ? and day = ?"); 
        getFlightSeats.setString(1, flight);
        getFlightSeats.setDate(2, day); 
        rs = getFlightSeats.executeQuery(); 
        rs.next();
        seatsBooked = rs.getInt(1);
    
        
          }
         catch(SQLException ex){
            ex.printStackTrace();
         }
           try{   
        connection = DBConnection.getConnection();
        getFlightSeats = connection.prepareStatement("select seats from flight where name =?");     
        getFlightSeats.setString(1, flight);
        rs = getFlightSeats.executeQuery(); 
        rs.next();
        flightSeats = rs.getInt(1);
        
        }
         catch(SQLException ex){
            ex.printStackTrace();
         }
           if(seatsBooked>flightSeats){
           return false;
           }
           else{
           return true;
           }
     }
     public static void cancelBooking(String customer, String flight, Date date){
     try{   
        connection = DBConnection.getConnection();
        cancelSeats = connection.prepareStatement("delete from bookings where customer =? and flight=? and day=?"); 
        cancelSeats.setString(1, customer);
        cancelSeats.setString(2, flight);
        cancelSeats.setDate(3, date);
        cancelSeats.executeUpdate();
       
  
        }
         catch(SQLException ex){
            ex.printStackTrace();
         }
     }
     public static ArrayList<String> statusbyDate(Date date, String flight){
          try
        {
            connection = DBConnection.getConnection();
            customers = new ArrayList();
            getAllCustomers = connection.prepareStatement("select customer from bookings where flight = ? and day = ? ");
            
            getAllCustomers.setString(1, flight);
            getAllCustomers.setDate(2, date);
           
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
     public static void setBookingList(String customer){
       try
        {
            connection = DBConnection.getConnection();
            flights = new ArrayList();
            dates  = new ArrayList();
            getAllCustomers = connection.prepareStatement("select * from bookings where customer = ? ");
            
            getAllCustomers.setString(1, customer);           
            rs = getAllCustomers.executeQuery();
            
            while (rs.next())
            {
                flights.add(rs.getString(2));
                dates.add(rs.getDate(3));
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
     }
     public static ArrayList<String> flightBookingList(){
            return flights;
     }
      public static ArrayList<Date> dateBookingList(){
            return dates;
     }
}
