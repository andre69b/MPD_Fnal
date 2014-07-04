package DataBaseObject;

import java.sql.Date;

@EDTable(TableName = "Employees")
public class Employee {
	
	@PrimaryKey
	public int EmployeeID;
	public String LastName;
	public String FirstName;
	public String Title;
	public String TitleOfCourtesy;
	public Date BirthDate;
	public Date HireDate;
	public String Address;
	public String City;
	public String Region;
	public String PostalCode;
	public String Country;
	public String HomePhone;
	public String Extension;
	public String Photo;
	public String Notes;
	public int ReportsTo;
	public String PhotoPath;
	
	@ForeignKey(Type = Employee.class, Association = Association.Single, AttributeName="ReportsTo")
	public Employee Report;
	
	public Employee(int employeeID, String lastName, String firstName,
			String title, String titleOfCourtesy, Date birthDate,
			Date hireDate, String address, String city, String region,
			String postalCode, String country, String homePhone,
			String extension, String photo, String notes, int reportsTo,
			String photoPath) {
		
		EmployeeID = employeeID;
		LastName = lastName;
		FirstName = firstName;
		Title = title;
		TitleOfCourtesy = titleOfCourtesy;
		BirthDate = birthDate;
		HireDate = hireDate;
		Address = address;
		City = city;
		Region = region;
		PostalCode = postalCode;
		Country = country;
		HomePhone = homePhone;
		Extension = extension;
		Photo = photo;
		Notes = notes;
		ReportsTo = reportsTo;
		PhotoPath = photoPath;
	}
	
	@ForeignKey(Type = Employee.class, Association = Association.Single, AttributeName="ReportsTo")
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

	public Date getBirthDate() {
		return BirthDate;
	}

	public void setBirthDate(Date birthDate) {
		BirthDate = birthDate;
	}

	public Date getHireDate() {
		return HireDate;
	}

	public void setHireDate(Date hireDate) {
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

	public String getPhoto() {
		return Photo;
	}

	public void setPhoto(String photo) {
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
