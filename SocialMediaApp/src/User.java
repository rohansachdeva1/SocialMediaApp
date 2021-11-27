import java.util.List;

public class User {
	private int id;

    private String firstName;

    private String lastName;

    private String userName;

    private String password;
    private String city;

    private BST<User> friends;

    private List<Interest> interests; //recommended to create an Interest class
}
