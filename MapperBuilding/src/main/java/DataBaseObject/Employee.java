package DataBaseObject;

import java.sql.Timestamp;


@EDTable(TableName = "Employees")
public class Employee {
	
	@PrimaryKey
	public int EmployeeID;
	public String LastName;
	public String FirstName;
	public String Title;
	public String TitleOfCourtesy;
	public Timestamp BirthDate;
	public Timestamp HireDate;
	public String Address;
	public String City;
	public String Region;
	public String PostalCode;
	public String Country;
	public String HomePhone;
	public String Extension;
	public byte[] Photo;
	public String Notes;
	public int ReportsTo;
	public String PhotoPath;
	@ForeignKey(Type = Employee.class,KeyName="EmployeeID", Association = Association.Single, AttributeName="ReportsTo")
	public Employee Report;
	
	
	
	
	@ForeignKey(Type = Employee.class,KeyName="EmployeeID", Association = Association.Single, AttributeName="ReportsTo")
	public Employee getReport() {
		return Report;
	}

	public void setReport(Employee report) {
		Report = report;
	}

	@PrimaryKey
	public int getEmployeeID() {
		return EmployeeID;
	}

	public void setEmployeeID(int employeeID) {
		EmployeeID = employeeID;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getTitleOfCourtesy() {
		return TitleOfCourtesy;
	}

	public void setTitleOfCourtesy(String titleOfCourtesy) {
		TitleOfCourtesy = titleOfCourtesy;
	}

	public Timestamp getBirthDate() {
		return BirthDate;
	}

	public void setBirthDate(Timestamp birthDate) {
		BirthDate = birthDate;
	}

	public Timestamp getHireDate() {
		return HireDate;
	}

	public void setHireDate(Timestamp hireDate) {
		HireDate = hireDate;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getRegion() {
		return Region;
	}

	public void setRegion(String region) {
		Region = region;
	}

	public String getPostalCode() {
		return PostalCode;
	}

	public void setPostalCode(String postalCode) {
		PostalCode = postalCode;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getHomePhone() {
		return HomePhone;
	}

	public void setHomePhone(String homePhone) {
		HomePhone = homePhone;
	}

	public String getExtension() {
		return Extension;
	}

	public void setExtension(String extension) {
		Extension = extension;
	}

	public byte[]  getPhoto() {
		return Photo;
	}

	public void setPhoto(byte[]  photo) {
		Photo = photo;
	}

	public String getNotes() {
		return Notes;
	}

	public void setNotes(String notes) {
		Notes = notes;
	}

	public int getReportsTo() {
		return ReportsTo;
	}

	public void setReportsTo(int reportsTo) {
		ReportsTo = reportsTo;
	}

	public String getPhotoPath() {
		return PhotoPath;
	}

	public void setPhotoPath(String photoPath) {
		PhotoPath = photoPath;
	}
}
