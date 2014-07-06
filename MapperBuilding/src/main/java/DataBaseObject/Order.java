package DataBaseObject;

import java.math.BigDecimal;
import java.sql.Timestamp;

@EDTable(TableName = "Orders")
public class Order {

	@PrimaryKey
	public int OrderID;
	public String CustomerID;
	public int EmployeeID;
	public Timestamp OrderDate;
	public Timestamp RequiredDate;
	public Timestamp ShippedDate;
	public int ShipVia;
	public BigDecimal Freight;
	public String ShipName;
	public String ShipAddress;
	public String ShipCity;
	public String ShipRegion;
	public String ShipPostalCode;
	public String ShipCountry;
	@ForeignKey(Type = Customer.class,KeyName="CustomerID", Association = Association.Single,AttributeName="CustomerID")
	public Customer Customer;
	@ForeignKey(Type = Employee.class,KeyName="EmployeeID",Association = Association.Single,AttributeName="EmployeeID")
	public Employee Employee;
	@ForeignKey(Type = Shipper.class, KeyName="ShipperID",Association = Association.Single,AttributeName="ShipVia")
	public Shipper Shipper;

	
	

	@ForeignKey(Type = Customer.class,KeyName="CustomerID", Association = Association.Single,AttributeName="CustomerID")
	public Customer getCustomer() {
		return Customer;
	}

	public void setCustomer(Customer customer) {
		Customer = customer;
	}

	@ForeignKey(Type = Employee.class,KeyName="EmployeeID",Association = Association.Single,AttributeName="EmployeeID")
	public Employee getEmployee() {
		return Employee;
	}

	public void setEmployee(Employee employee) {
		Employee = employee;
	}
	
	@ForeignKey(Type = Shipper.class, KeyName="ShipperID",Association = Association.Single,AttributeName="ShipVia")
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

	public Timestamp getOrderDate() {
		return OrderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		OrderDate = orderDate;
	}

	public Timestamp getRequiredDate() {
		return RequiredDate;
	}

	public void setRequiredDate(Timestamp requiredDate) {
		RequiredDate = requiredDate;
	}

	public Timestamp getShippedDate() {
		return ShippedDate;
	}

	public void setShippedDate(Timestamp shippedDate) {
		ShippedDate = shippedDate;
	}

	public int getShipVia() {
		return ShipVia;
	}

	public void setShipVia(int shipVia) {
		ShipVia = shipVia;
	}

	public BigDecimal getFreight() {
		return Freight;
	}

	public void setFreight(BigDecimal freight) {
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
