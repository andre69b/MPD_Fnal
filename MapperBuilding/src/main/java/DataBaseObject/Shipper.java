package DataBaseObject;

@EDTable(TableName = "Shippers")
public class Shipper {
	
	@PrimaryKey
	public int ShipperID;
	public String CompanyName;
	public String Phone;
	
	public Shipper(int shipperID, String companyName, String phone) {
		ShipperID = shipperID;
		CompanyName = companyName;
		Phone = phone;
	}
	
	@PrimaryKey
	public int getShipperID() {
		return ShipperID;
	}

	public void setShipperID(int shipperID) {
		ShipperID = shipperID;
	}

	public String getCompanyName() {
		return CompanyName;
	}

	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}
}
