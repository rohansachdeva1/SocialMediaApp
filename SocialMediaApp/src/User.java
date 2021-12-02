import java.util.ArrayList;

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
			LinkedList<Interest> interests) {
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
	
	public void addFriends(User friend) {
		this.friends.insert(friend);
	}
	
	
	/*
	 * ---------------------------------------NOT COMPLETE---------------------------------------
	 * Need to print friends and interests
	 */
	public void displayUserProfile() {
		System.out.println("Name: " + firstName + " " + lastName);
		System.out.println("City: " + city);
		System.out.println("Friends: ");
		System.out.println("Interests: ");
	}
	
	/*
	 * ---------------------------------------Complete---------------------------------------
	 * inOrderData() method is added to BST class
	 */
	public ArrayList<User> getSortedFriendArrayList() {
		ArrayList<User> result = friends.inOrderData();
		return result;
		
	}
	
	/*
	 * ---------------------------------------COMPLETE---------------------------------------
	 */
	public ArrayList<User> searchFromFriendList(String targetName) { //would be more efficient if we use hashtable, but I will do it with arraylist since efficiency isn't required.
		ArrayList<User> result = getSortedFriendArrayList();
		for (int i = 0; i < result.size(); i++) {
			User userInIndex = result.get(i);
			String userNameInUpperCase = userInIndex.getFirstName().toUpperCase() + ' ' + userInIndex.getFirstName().toUpperCase();
			String targetNameInUpperCase = targetName.toUpperCase();
			if (userNameInUpperCase.contains(targetNameInUpperCase)) {
				result.add(userInIndex);
			}
		}
		return result;
	}
	
	/*
	 * ---------------------------------------NOT COMPLETE---------------------------------------
	 */
	public ArrayList<User> getRecommendation() {
		ArrayList<User> result;
		return result;
	}
	

	@Override
	public int compareTo(User o) {
		// TODO Auto-generated method stub
		return 0;
	}
    
    
    
    
    
    
    
}

