package DataMapper;

import org.junit.Assert;
import org.junit.Test;

import DataBaseObject.Customer;
import DataBaseObject.SQLIterableImpl;
import MapperBuilder.Builder;
import MapperBuilder.DataMapper;
import Strategy.Connections.SingletonConnection;
import Strategy.Mappers.FieldsMappingStrategy;

public class DataMapperTest {
	
	@Test
	public void Example() {
		Builder b = new Builder(new FieldsMappingStrategy(),new SingletonConnection());
		DataMapper<Customer> prodMapper = b.build(Customer.class);
		SQLIterableImpl<Customer> curts = prodMapper.getAll();
		
		for (Customer customer : curts) {
			System.out.println(customer.country);
		}
		
		int count = 10;//curts.where("country = France").count();
		Assert.assertEquals(10, count);
	}
}
