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
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class Day {
    
    private static Connection connection;
    private static PreparedStatement getAllDays;
    private static PreparedStatement insertNewDays;
    private static ResultSet rs;
    private static ArrayList<Date> days;
public static ArrayList<Date> getAllDay(){
     try
        {
            connection = DBConnection.getConnection();
            days = new ArrayList();
            getAllDays = connection.prepareStatement("select day from days");
            rs = getAllDays.executeQuery();
             while (rs.next())
        {
                days.add(rs.getDate(1));
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
            
        return days;

}
     public static void addNewDays(Date days)
    {
        
        try
        {
            connection = DBConnection.getConnection();
            insertNewDays = connection.prepareStatement("insert into days (day) values (?)");
            insertNewDays.setDate(1, days);
            insertNewDays.executeUpdate();
           

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        
    }
}

 