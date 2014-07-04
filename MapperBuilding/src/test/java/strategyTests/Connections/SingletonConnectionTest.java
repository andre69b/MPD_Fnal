package strategyTests.Connections;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import Strategy.Connections.SingletonConnection;

public class SingletonConnectionTest {
	@Test
	public void SingletonConnection() throws SQLException{
		SingletonConnection sc = new SingletonConnection();
		Connection c =sc.getConnection();
		
		PreparedStatement cmd = c.prepareStatement(
                "SELECT ProductID, ProductName, UnitPrice, UnitsInStock "
                        + "FROM Products WHERE UnitPrice > ? AND UnitsInStock > ?");
        cmd.setDouble(1, 30.0);
        cmd.setInt(2, 20);
        
        ResultSet rs = cmd.executeQuery();
        int count = 0;
        
        while(rs.next()) count++;
        
              
        assertEquals(12,count);
	}
}
