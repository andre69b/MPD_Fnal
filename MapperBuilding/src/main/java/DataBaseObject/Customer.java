package DataBaseObject;

@EDTable(TableName = "Customers")
public class Customer {

	@PrimaryKey
	public String CustomerID;
	public String CompanyName;
	public String ContactName;
	public String ContactTitle;
	public String Address;
	public String City;
	public String Region;
	public String PostalCode;
	public String Country;
	public String Phone;
	public String Fax;

	@ForeignKey(Table = "Orders", Association = Association.Multiple,AttributeName="CustomerID")
	public Iterable<Order> Orders;

	public Customer(String customerID, String companyName, String contactName,
			String contactTitle, String address, String city, String region,
			String postalCode, String country, String phone, String fax) {

		this.CustomerID = customerID;
		this.CompanyName = companyName;
		this.ContactName = contactName;
		this.ContactTitle = contactTitle;
		this.Address = address;
		this.City = city;
		this.Region = region;
		this.PostalCode = postalCode;
		this.Country = country;
		this.Phone = phone;
		this.Fax = fax;
	}
	
	@ForeignKey(Table = "Orders", Association = Association.Multiple,AttributeName="CustomerID")
	public Iterable<Order> getOrders() {
		return Orders;
	}

	public void setOrders(Iterable<Order> orders) {
		this.Orders = orders;
	}
	@PrimaryKey
	public String getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(String customerID) {
		this.CustomerID = customerID;
	}

	public String getCompanyName() {
		return CompanyName;
	}

	public void setCompanyName(String companyName) {
		this.CompanyName = companyName;
	}

	public String getContactName() {
		return ContactName;
	}

	public void setContactName(String contactName) {
		this.ContactName = contactName;
	}

	public String getContactTitle() {
		return ContactTitle;
	}

	public void setContactTitle(String contactTitle) {
		this.ContactTitle = contactTitle;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		this.Address = address;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		this.City = city;
	}

	public String getRegion() {
		return Region;
	}

	public void setRegion(String region) {
		this.Region = region;
	}

	public String getPostalCode() {
		return PostalCode;
	}

	public void setPostalCode(String postalCode) {
		this.PostalCode = postalCode;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		this.Country = country;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		this.Phone = phone;
	}

	public String getFax() {
		return Fax;
	}

	public void setFax(String fax) {
		this.Fax = fax;
	}
}
