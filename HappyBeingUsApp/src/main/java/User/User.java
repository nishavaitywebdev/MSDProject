package User;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User{
	@Id
	private Integer user_id;
	private String username;
	private String password;


	public Integer getuId() {
		return user_id;
	}

	public void setuId(Integer uId) {
		this.user_id = uId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public User(Integer uId, String username, String password) {
		super();
		this.user_id = uId;
		this.username = username;
		this.password = password;
		
		
	
	 
	}

	public User() {
		super();
	}

}