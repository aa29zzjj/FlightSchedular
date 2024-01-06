
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author acv
 */
public class Customer
{

    private static Connection connection;
    private static PreparedStatement getAllCustomers;
    private static PreparedStatement insertNewCustomer;
    private static ResultSet rs;
    private static ArrayList<String> customers;

    public static ArrayList<String> getAllCustomers()
    {
        try
        {
            connection = DBConnection.getConnection();
            customers = new ArrayList();
            getAllCustomers = connection.prepareStatement("select name from customer");
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

        public static void addNewCustomer(String customer)
    {
        try
        {
            connection = DBConnection.getConnection();
            insertNewCustomer = connection.prepareStatement("insert into customer (name) values (?)");
            insertNewCustomer.setString(1, customer);
            insertNewCustomer.executeUpdate();
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }

    }

}
