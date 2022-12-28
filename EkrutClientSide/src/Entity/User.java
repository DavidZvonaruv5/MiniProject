package Entity;

public class User
{
	@Override
	public String toString() {
		return "User [ID=" + ID + ", UserName=" + UserName + ", FirstName=" + FirstName + ", LastName=" + LastName
				+ ", Password=" + Password + ", Phone=" + Phone + ", Email=" + Email + ", OnlineStatus=" + OnlineStatus
				+ "]";
	}
	private String ID,UserName,FirstName,LastName,Password,Phone,Email,OnlineStatus;

	public User(String userName, String password)
	{
		UserName = userName;
		Password = password;
	}
	public User(String firstName, String lastName, String phone, String email, String ID, String UserName,
			String Password) 
	{
		super();
		this.ID = ID;
		this.UserName = UserName;
		FirstName = firstName;
		LastName = lastName;
		this.Password = Password;
		Phone = phone;
		Email = email;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getOnlineStatus() {
		return OnlineStatus;
	}
	public void setOnlineStatus(String onlineStatus) {
		OnlineStatus = onlineStatus;
	}
	
}
