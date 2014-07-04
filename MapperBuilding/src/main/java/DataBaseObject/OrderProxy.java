package DataBaseObject;

import java.sql.Date;

public class OrderProxy extends Order{

	public OrderProxy(int orderID, String customerID, int employeeID,
			Date orderDate, Date requiredDate, Date shippedDate, int shipVia,
			double freight, String shipName, String shipAddress,
			String shipCity, String shipRegion, String shipPostalCode,
			String shipCountry, DataBaseObject.Customer customer,
			DataBaseObject.Employee employee, DataBaseObject.Shipper shipper) {
		super(orderID, customerID, employeeID, orderDate, requiredDate, shippedDate,
				shipVia, freight, shipName, shipAddress, shipCity, shipRegion,
				shipPostalCode, shipCountry);
	}
	
	@Override
	public Customer getCustomer() {
		/*
		Customer = new Customer(CustomerID,);*/
		return super.getCustomer();
	}

}
