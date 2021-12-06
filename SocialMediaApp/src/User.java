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
	
	public User(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	
	public User(int id, String firstName, String lastName, String userName, String password, String city) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.city = city;
		this.friends = new BST<>();
		this.interests = new LinkedList<Interest>();
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
	
	// for user's friend list, has nothing to do with database class
	public void addFriends(User friend) {
		this.friends.insert(friend);
	}
	// for user's friend list, has nothing to do with database class
	public void removeFriends(User friend) {
		this.friends.remove(friend);
	}
	
	public boolean hasNoFriends() {
		return (this.getFriends().isEmpty() == true);
	}
	
	
	/*
	 * ---------------------------------------NOT COMPLETE---------------------------------------
	 * Need to print friends and interests
	 */
	public void displayUserProfile() {
		System.out.println("Name: " + firstName + " " + lastName);
		System.out.println("City: " + city);
		System.out.println("Friends: " + friends.inOrderData());
		System.out.println("Interests: " + interests.toString());
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
        ArrayList<User> returnResult = new ArrayList<User>();
        for (int i = 0; i < result.size(); i++) {
            User userInIndex = result.get(i);
            String userNameInUpperCase = userInIndex.getFirstName().toUpperCase() + ' ' + userInIndex.getLastName().toUpperCase();
            String targetNameInUpperCase = targetName.toUpperCase();
            if (userNameInUpperCase.contains(targetNameInUpperCase)) {
                returnResult.add(userInIndex);
            }
        }
        return returnResult;
    }
	
	// possibly pass in database as a parameter from the driver and then call the database method on it
	public ArrayList<User> getRecommendation() {
		if (this.hasNoFriends() == false) {
			return Database.getRecommendation(this.getId());
		}
		else {
			return Database.getNoFriendsRecommendation(this.getId());
		}
	}

	public void addFriend(User toBeAddedUser) {
		Database.addFriend(this, toBeAddedUser);
	}

	public void removeFriend(User toBeRemovedUser) {
		Database.removeFriend(this, toBeRemovedUser);
	}
	
	@Override
	public String toString() {
		return getFirstName() + " " + getLastName();
	}
	
	@Override
    public int hashCode() {
        return (userName + password).hashCode();
    }
	
	/* Compares two User objects
	 * Returns 0 if the two user objects has the same name
	 * else, returns the compareTo value of this.firstName compared to o.firstName.
	 * else, returns the compareTo value of this.lastName compared to o.lastName.
	 */
	@Override
	public int compareTo(User o) {
        if (firstName.equals(o.firstName)&&lastName.equals(o.lastName)) 
            return 0;
        else if (!firstName.equals(o.firstName))
            return firstName.compareTo(o.firstName);
        else
            return lastName.compareTo(o.lastName);
	}
}

