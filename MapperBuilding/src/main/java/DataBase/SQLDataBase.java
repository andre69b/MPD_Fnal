package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import DataBaseObject.Customer;
import MapperBuilder.Builder;
import MapperBuilder.DataMapper;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class SQLDataBase {
	
	public static void main(String[] args) throws SQLException{
	         
	        SQLServerDataSource ds = new SQLServerDataSource();
	        ds.setUser("MPDProject");
	        ds.setPassword("123456");
	        ds.setServerName("localhost");
	        ds.setPortNumber(1433);
	        ds.setDatabaseName("Northwind");
	        Connection c = ds.getConnection();
	        
	        PreparedStatement cmd = c.prepareStatement(
	                "SELECT ProductID, ProductName, UnitPrice, UnitsInStock "
	                        + "FROM Products WHERE UnitPrice > ? AND UnitsInStock > ?");
	        cmd.setDouble(1, 30.0);
	        cmd.setInt(2, 20);
	        
	        ResultSet rs = cmd.executeQuery();
	        int count = 0;
	        while(rs.next())
	        {
	            System.out.println("("+rs.getInt(1)+"-"+ rs.getString(2)+"-"+ rs.getDouble(3)+"-"+ rs.getInt(4)+")");
	            count++;
	        }
	        System.out.println("Fetched " + count + " rows");
	        
	        
	        
	        List<String> list= new LinkedList<String>();
	        int[] array = new int[]{2,3,4,5,6,7,8,9};
	        list.add("CENA");
	        list.add("COISA");
	        list.add("ADEUS");
	        
	        list.stream().forEach(System.out::println);
	        
	        Arrays.stream(array).forEach(s->System.out.println(s));
	        
	        
	        Builder b = new Builder(null, null); 
	        DataMapper<Customer> prodMapper = b.build(Customer.class); 
	        Iterable<Customer> prods = prodMapper.getAll();
	}
}
