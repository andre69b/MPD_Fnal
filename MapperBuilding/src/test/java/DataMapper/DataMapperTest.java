package DataMapper;

import org.junit.Assert;
import org.junit.Test;

import DataBaseObject.Customer;
import DataBaseObject.Employee;
import DataBaseObject.SQLIterableImpl;
import MapperBuilder.Builder;
import MapperBuilder.DataMapper;
import Strategy.Connections.SingletonConnection;
import Strategy.Mappers.FieldsMappingStrategy;

public class DataMapperTest {
	
	@Test
	public void TestForBindStr(){
		Builder b = new Builder(new FieldsMappingStrategy(),new SingletonConnection());
		DataMapper<Customer> prodMapper = b.build(Customer.class);
		SQLIterableImpl<Customer> curts = prodMapper.getAll();
		
		int count = curts.where("Country=?").bind("France").count();
		Assert.assertEquals(11, count);
	}
	
	@Test
	public void TestForWhereStr(){
		Builder b = new Builder(new FieldsMappingStrategy(),new SingletonConnection());
		DataMapper<Customer> prodMapper = b.build(Customer.class);
		SQLIterableImpl<Customer> curts = prodMapper.getAll();
		
		int count = curts.where("Country='France'").count();
		Assert.assertEquals(11, count);
	}
	
	@Test
	public void TestForCountStr() {
		Builder b = new Builder(new FieldsMappingStrategy(),new SingletonConnection());
		DataMapper<Customer> prodMapper = b.build(Customer.class);
		SQLIterableImpl<Customer> curts = prodMapper.getAll();
		
		int count = curts.count();
		Assert.assertEquals(91, count);
	}
	
	@Test
	public void TestForBindInt(){
		Builder b = new Builder(new FieldsMappingStrategy(),new SingletonConnection());
		DataMapper<Employee> prodMapper = b.build(Employee.class);
		SQLIterableImpl<Employee> curts = prodMapper.getAll();
		
		int count = curts.where("Extension>?").bind(2500).count();
		Assert.assertEquals(5, count);
	}
	
	@Test
	public void TestForWhereInt(){
		Builder b = new Builder(new FieldsMappingStrategy(),new SingletonConnection());
		DataMapper<Employee> prodMapper = b.build(Employee.class);
		SQLIterableImpl<Employee> curts = prodMapper.getAll();
		
		int count = curts.where("Extension>2500").count();
		Assert.assertEquals(5, count);
	}
	
	@Test
	public void TestForCountInt() {
		Builder b = new Builder(new FieldsMappingStrategy(),new SingletonConnection());
		DataMapper<Employee> prodMapper = b.build(Employee.class);
		SQLIterableImpl<Employee> curts = prodMapper.getAll();
		
		int count = curts.count();
		Assert.assertEquals(9, count);
	}
}
