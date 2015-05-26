package test;

import next.jdbc.mysql.annotation.Column;
import next.jdbc.mysql.annotation.Key;
import next.jdbc.mysql.annotation.Table;

@Table
public class User {

	public User(String email, String name, String password, String gender) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.gender = gender;
	}

	public User(Integer id, String email, String name, String password, String gender, String message, String photo, String cover) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.password = password;
		this.gender = gender;
		this.message = message;
		this.photo = photo;
		this.cover = cover;
	}

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public User(String email) {
		this.email = email;
	}

	@Key(AUTO_INCREMENT = true)
	private Integer id;

	@Column(function = { "index", "unique" })
	private String email;

	private String auth;
	private String name;
	private String password;
	@Column(DATA_TYPE = "CHAR(1)")
	private String gender;
	private String message;
	private String photo;
	private String cover;

	public String getMessage() {
		return message;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getGender() {
		return gender;
	}

	public void update(User updateUser) {
		String email = updateUser.getEmail();
		String name = updateUser.getName();
		String gender = updateUser.getGender();
		String message = updateUser.getMessage();
		String photo = updateUser.getPhoto();
		String cover = updateUser.getCover();
		if (email != null)
			this.email = email;
		if (name != null)
			this.name = name;
		if (gender != null)
			this.gender = gender;
		if (message != null)
			this.message = message;
		if (photo != null)
			this.photo = photo;
		if (cover != null)
			this.cover = cover;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", auth=" + auth + ", name=" + name + ", password=" + password + ", gender=" + gender
				+ ", message=" + message + ", photo=" + photo + ", cover=" + cover + "]";
	}

}
