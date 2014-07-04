package DataBaseObject;

import java.sql.Date;

@EDTable(TableName = "Orders")
public class Order {

	@PrimaryKey
	public int OrderID;
	public String CustomerID;
	public int EmployeeID;
	public Date OrderDate;
	public Date RequiredDate;
	public Date ShippedDate;
	public int ShipVia;
	public double Freight;
	public String ShipName;
	public String ShipAddress;
	public String ShipCity;
	public String ShipRegion;
	public String ShipPostalCode;
	public String ShipCountry;

	@ForeignKey(Table = "Customers", Association = Association.Single,AttributeName="CustomerID")
	public Customer Customer;

	@ForeignKey(Table = "Employees", Association = Association.Single,AttributeName="EmployeeID")
	public Employee Employee;

	@ForeignKey(Table = "Shippers", Association = Association.Single,AttributeName="ShipVia")
	public Shipper Shipper;

	public Order(int orderID, String customerID, int employeeID,
			Date orderDate, Date requiredDate, Date shippedDate, int shipVia,
			double freight, String shipName, String shipAddress,
			String shipCity, String shipRegion, String shipPostalCode,
			String shipCountry) {

		OrderID = orderID;
		CustomerID = customerID;
		EmployeeID = employeeID;
		OrderDate = orderDate;
		RequiredDate = requiredDate;
		ShippedDate = shippedDate;
		ShipVia = shipVia;
		Freight = freight;
		ShipName = shipName;
		ShipAddress = shipAddress;
		ShipCity = shipCity;
		ShipRegion = shipRegion;
		ShipPostalCode = shipPostalCode;
		ShipCountry = shipCountry;
	}

	@ForeignKey(Table = "Customers", Association = Association.Single,AttributeName="CustomerID")
	public Customer getCustomer() {
		return Customer;
	}

	public void setCustomer(Customer customer) {
		Customer = customer;
	}

	@ForeignKey(Table = "Employees", Association = Association.Single,AttributeName="EmployeeID")
	public Employee getEmployee() {
		return Employee;
	}

	public void setEmployee(Employee employee) {
		Employee = employee;
	}
	
	@ForeignKey(Table = "Shippers", Association = Association.Single,AttributeName="ShipVia")
	public Shipper getShipper() {
		return Shipper;
	}

	public void setShipper(Shipper shipper) {
		Shipper = shipper;
	}
	
	@PrimaryKey
	public int getOrderID() {
		return OrderID;
	}

	public void setOrderID(int orderID) {
		OrderID = orderID;
	}

	public String getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}

	public int getEmployeeID() {
		return EmployeeID;
	}

	public void setEmployeeID(int employeeID) {
		EmployeeID = employeeID;
	}

	public Date getOrderDate() {
		return OrderDate;
	}

	public void setOrderDate(Date orderDate) {
		OrderDate = orderDate;
	}

	public Date getRequiredDate() {
		return RequiredDate;
	}

	public void setRequiredDate(Date requiredDate) {
		RequiredDate = requiredDate;
	}

	public Date getShippedDate() {
		return ShippedDate;
	}

	public void setShippedDate(Date shippedDate) {
		ShippedDate = shippedDate;
	}

	public int getShipVia() {
		return ShipVia;
	}

	public void setShipVia(int shipVia) {
		ShipVia = shipVia;
	}

	public double getFreight() {
		return Freight;
	}

	public void setFreight(double freight) {
		Freight = freight;
	}

	public String getShipName() {
		return ShipName;
	}

	public void setShipName(String shipName) {
		ShipName = shipName;
	}

	public String getShipAddress() {
		return ShipAddress;
	}

	public void setShipAddress(String shipAddress) {
		ShipAddress = shipAddress;
	}

	public String getShipCity() {
		return ShipCity;
	}

	public void setShipCity(String shipCity) {
		ShipCity = shipCity;
	}

	public String getShipRegion() {
		return ShipRegion;
	}

	public void setShipRegion(String shipRegion) {
		ShipRegion = shipRegion;
	}

	public String getShipPostalCode() {
		return ShipPostalCode;
	}

	public void setShipPostalCode(String shipPostalCode) {
		ShipPostalCode = shipPostalCode;
	}

	public String getShipCountry() {
		return ShipCountry;
	}

	public void setShipCountry(String shipCountry) {
		ShipCountry = shipCountry;
	}
}
