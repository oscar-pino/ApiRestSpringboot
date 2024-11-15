package api.security.dto;

import java.util.Date;

public class PublisherDTO {		

	private Long id;

	private String name;

	private String address;

	private String phone;

	private String webSite;

	private String email;

	private Date foundingDate;

	public PublisherDTO() {
	}

	public PublisherDTO(String name, String address, String phone, String webSite, String email, Date foundingDate) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.webSite = webSite;
		this.email = email;
		this.foundingDate = foundingDate;
	}

	public PublisherDTO(Long id, String name, String address, String phone, String webSite, String email,
			Date foundingDate) {
		this(name, address, phone, webSite, email, foundingDate);
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFoundingDate() {
		return foundingDate;
	}

	public void setFoundingDate(Date foundingDate) {
		this.foundingDate = foundingDate;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Publisher [publisherId=" + id + ", name=" + name + ", address=" + address + ", phone=" + phone
				+ ", webSite=" + webSite + ", email=" + email + ", foundingDate=" + foundingDate + "]";
	}
}
