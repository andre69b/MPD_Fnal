package SQL;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import DataBaseObject.Shipper;
import MapperBuilder.Builder;
import MapperBuilder.DataMapper;
import Strategy.Connections.SingletonConnection;
import Strategy.Mappers.FieldsMappingStrategy;

public class OperationsSQLTest {

	Builder b;
	DataMapper<Shipper> custShipper;
	Shipper shipper,newShipper;
	
	@Before
	public void beforeTests(){
		b = new Builder(new FieldsMappingStrategy(),new SingletonConnection());
		custShipper = b.build(Shipper.class);
		shipper = new Shipper();
		shipper.CompanyName = "'companyTest'";
		shipper.Phone = "'phoneTest'";
		shipper.ShipperID = custShipper.insert(shipper);
		shipper.CompanyName = "companyTest";
		shipper.Phone = "phoneTest";
	}
	
	@Test
	public void TestForInsert(){
		
		Shipper newShipper = custShipper.getAll().where("ShipperID = ?").bind(shipper.ShipperID).iterator().next();
		Assert.assertEquals(shipper.ShipperID,newShipper.ShipperID);
		Assert.assertEquals(shipper.CompanyName,newShipper.CompanyName);
		Assert.assertEquals(shipper.Phone,newShipper.Phone);
		custShipper.delete(shipper);
	}
	
	@Test
	public void TestForDelete(){
		custShipper.delete(shipper);
		Iterator<Shipper> it = custShipper.getAll().where("ShipperID = ?").bind(shipper.ShipperID).iterator();
		int count=0;
		while (it.hasNext()) {    
			it.next();    
			count++;    
        }
		Assert.assertEquals(0,count);
	}
	
	@Test
	public void TestForUpdate(){
		shipper.CompanyName = "'companyTest2'";
		shipper.Phone = "'phoneTest2'";
		custShipper.update(shipper);
		Shipper newShipper = custShipper.getAll().where("ShipperID = ?").bind(shipper.ShipperID).iterator().next();
		Assert.assertEquals(shipper.ShipperID,newShipper.ShipperID);
		Assert.assertEquals("companyTest2",newShipper.CompanyName);
		Assert.assertEquals("phoneTest2",newShipper.Phone);
		custShipper.delete(shipper);
	}
}
