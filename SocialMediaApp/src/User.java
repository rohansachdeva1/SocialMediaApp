import Collections.*;

public class User implements Comparable<User>{
	private int id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String city;
    private BST<User> friends;
    private LinkedList<Interest> interests;
    
    //default constructor
	public User() {
		this.id = -1;
		this.firstName = "";
		this.lastName = "";
		this.userName = "";
		this.password = "";
		this.city = "";
		this.friends = new BST<>();
		this.interests = new LinkedList<>();
	}
    
	public User(int id, String firstName, String lastName, String userName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;

	}
	
	public User(int id, String firstName, String lastName, String userName, String password, String city,
			LinkedList interests) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.city = city;
		this.friends = new BST<>();
		this.interests = interests;
	}

	public User(int id, String firstName, String lastName, String userName, String password, String city,
			BST friends, LinkedList interests) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.city = city;
		this.friends = friends;
		this.interests = interests;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public BST<User> getFriends() {
		return friends;
	}
	public void setFriends(BST<User> friends) {
		this.friends = friends;
	}
	
	public LinkedList<Interest> getInterests() {
		return interests;
	}
	public void setInterests(LinkedList<Interest> interests) {
		this.interests = interests;
	}

	@Override
	public int compareTo(User o) {
		// TODO Auto-generated method stub
		return 0;
	}
    
    
    
    
    
    
    
}

