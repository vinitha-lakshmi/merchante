package com.merchante.dto;

public class UserDTO {

	private String name;
	private String userName;
	private String email;
	private String addressStreet;
	private String addressSuite;
	private String addressCity;
	private String addressZipCode;
	private String companyName;
	private String companyPhone;
	private String companyWebsite;
	private String id;


	public UserDTO(String name, String userName, String email, String addressStreet, String addressSuite, String addressCity, String addressZipCode, String companyName, String companyPhone, String companyWebsite) {
		this.name = name;
		this.userName = userName;
		this.email = email;
		this.addressStreet = addressStreet;
		this.addressSuite = addressSuite;
		this.addressCity = addressCity;
		this.addressZipCode = addressZipCode;
		this.companyName = companyName;
		this.companyPhone = companyPhone;
		this.companyWebsite = companyWebsite;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddressStreet() {
		return addressStreet;
	}

	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}

	public String getAddressSuite() {
		return addressSuite;
	}

	public void setAddressSuite(String addressSuite) {
		this.addressSuite = addressSuite;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getAddressZipCode() {
		return addressZipCode;
	}

	public void setAddressZipCode(String addressZipCode) {
		this.addressZipCode = addressZipCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getCompanyWebsite() {
		return companyWebsite;
	}

	public void setCompanyWebsite(String companyWebsite) {
		this.companyWebsite = companyWebsite;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
