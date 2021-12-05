import java.util.ArrayList;
import java.util.Scanner;


/* 
 * Both addFriend() and removeFriend() requires everyone's effort to update the data structure.
 * Search result should return ArrayList<User>. Steven will use that to display the search result.
 */

public class Driver {
	static Database database;
	static User currentUser;
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {		
		boolean loginStatus = false;
		boolean createUserStatus = false; // Check if new User is created, only when is created, we will call writeToFile
		int choice;
//		Scanner sc = new Scanner(System.in);
		database = new Database();
		
		while (!loginStatus) {
			System.out.println("Choose the option number ('1', '2' or '3'): ");
			System.out.println("1: Login");
			System.out.println("2: Create Account");
			System.out.println("3: Exit");
			choice = Integer.parseInt(sc.nextLine());
			switch(choice) {
			case 1:
				/*
				 * ------------------------------------Hashtable group------------------------------------
				 * Please also return the logged in user
				 * might want to return to the main menu if login fail 
				 */
				System.out.println("Login: ");
				System.out.println("Please enter your username: ");
				String username = sc.nextLine();
				System.out.println("Please enter your password: ");
				String password = sc.nextLine();
				currentUser = database.login(username, password);
				if (currentUser == null) {
					System.out.println("username or password not found.");
					System.out.println("Please try login again or create an account\n");
				} else {
					loginStatus = true;
				}
				break;
			case 2:
				currentUser = database.createUser(); //should create a new user, then this becomes the current user.
				System.out.println();
				loginStatus = true;
				createUserStatus = true;
				break;
			case 3:
				System.exit(0); //exit the program
			default:
				System.out.println("Invalid input. Please enter the option number only.");
				break;
			}
			while (loginStatus) {
				System.out.println("Enter the  option number ('1', '2' or '3'): ");
				System.out.println("1: View my friends");
				System.out.println("2: Make new friend");
				System.out.println("3: Exit");
				choice = Integer.parseInt(sc.nextLine());
				switch(choice) {
				case 1:
					viewFriend();
					break;
				case 2:
					makeFriendsOutsideOfTheCircle();
					break;
				case 3:
					Database.writeToFile();
					loginStatus = false;
					break;
				default:
					System.out.println("Invalid input. Please enter the option number only.");
					break;
				}
				
			}
		}
		sc.close();

	}
	
	/* Helper method to view friends of the current user.
	 * Current user can remove / sort / search their friend here.
	 */
	public static void viewFriend() {
		int choice;
		int index;
		User selectedUser;
		Boolean exitStatus = false;
		while (!exitStatus) {
			System.out.println("Enter the following options ('1', '2' or '3'): ");
			System.out.println("1. View sorted friend list");
			System.out.println("2. Search friends by their names");
			System.out.println("3. Go back to the previous page");
			choice = Integer.parseInt(sc.nextLine());
			switch (choice) {
			case 1: //view sorted friend
				ArrayList<User> sortedFriendList = currentUser.getSortedFriendArrayList();//sort by name
				if (sortedFriendList.size() == 0) {
					System.out.println("No user is found. Going back to the previous page.");
					break;
				}	
				displayArrayListUser(sortedFriendList);
				
				System.out.println("Enter the friend number ('1', '2', '3' etc.): ");
				index = Integer.parseInt(sc.nextLine());
				
				selectedUser = getSelectedUser(sortedFriendList, index - 1);
				selectedUser.displayUserProfile();
				System.out.println("Enter the following options ('1' or '2'): ");
				System.out.println("1. Remove this friend");
				System.out.println("2. Go back to the previous page");
				choice = Integer.parseInt(sc.nextLine());
				switch (choice) {
				case 1:
					//remove friend
					currentUser.removeFriend(selectedUser);
					break;
				case 2:
					//go back
					break;
				default:
					System.out.println("Invalid input. Please enter the option number only.");
					break;
				}
				break;
			case 2: //search friend
				System.out.println("Enter the name that you would like to search: ");
				String targetName = sc.nextLine();
				ArrayList<User> searchResult = currentUser.searchFromFriendList(targetName);
				if (searchResult.size() == 0) {
					System.out.println("No user is found. Going back to the previous page.");
					break;
				}	
				displayArrayListUser(searchResult);
				
				System.out.println("Enter the friend number ('1', '2', '3' etc.): ");
				index = Integer.parseInt(sc.nextLine());
				
				selectedUser = getSelectedUser(searchResult, index - 1);
				selectedUser.displayUserProfile();
				System.out.println("Enter the following options ('1' or '2'): ");
				System.out.println("1. Remove this friend");
				System.out.println("2. Go back to the previous page");
				choice = Integer.parseInt(sc.nextLine());
				switch (choice) {
				case 1:
					//remove friend
					currentUser.removeFriend(selectedUser);
					break;
				case 2:
					//go back
					break;
				default:
					System.out.println("Invalid input. Please enter the option number only.");
					break;
				}
				break;
			case 3:
				exitStatus = true;
				break;
			default:
				System.out.println("Invalid input. Please enter the option number only.");
				break;
			}
		}
	}
	
	/* Helper method to add friends outside of current user's network
	 * Current user can searchbyname, searchbyinterest, and get recommendation, then add friends from the list.
	 * Should not be able to add a user that is already current user's friend
	 */
	public static void makeFriendsOutsideOfTheCircle() {
		int choice;
		int index;
		User selectedUser;
		ArrayList<User> searchResult;
		Boolean exitStatus = false;
		while (!exitStatus) {
			System.out.println("Enter the following options ('1', '2' or '3'): ");
			System.out.println("1. Search users by name ");
			System.out.println("2. Search users by interest");
			System.out.println("3. Get recommendation");
			System.out.println("4. Go back to the previous page");
			choice = Integer.parseInt(sc.nextLine());
			switch (choice) {
			case 1:
				System.out.println("Enter the name that you would like to search: ");
				String targetName = sc.nextLine();
				searchResult = database.searchUserByName(targetName, currentUser);
				if (searchResult.size() == 0) {
					System.out.println("No user is found. Going back to the previous page.");
					break;
				}		
				System.out.println("search result" + searchResult.toString());
				displayArrayListUser(searchResult);
				
				System.out.println("Enter the friend number ('1', '2', '3' etc.): ");
				index = Integer.parseInt(sc.nextLine());
				
				selectedUser = getSelectedUser(searchResult, index - 1);
				selectedUser.displayUserProfile();
				System.out.println("Enter the following options ('1' or '2'): ");
				System.out.println("1. add this user");
				System.out.println("2. Go back to the previous page");
				choice = Integer.parseInt(sc.nextLine());
				switch(choice) {
				case 1:
					System.out.println("adding friend");
					currentUser.addFriend(selectedUser);
					break;
				case 2:
					//go back
					break;
				default:
					System.out.println("Invalid input. Please enter the option number only.");
					break;
				}
				break;
			case 2:
				System.out.println("Enter the interest that you would like to search: ");
				String targetInterest = sc.nextLine();
				searchResult = database.searchUserByInterest(targetInterest, currentUser);
				System.out.println("interest search result: " + searchResult.toString());
				if (searchResult.size() == 0) {
					System.out.println("No user is found. Going back to the previous page.");
					break;
				}	
				displayArrayListUser(searchResult);
				
				System.out.println("Enter the friend number ('1', '2', '3' etc.): ");
				index = Integer.parseInt(sc.nextLine());
				
				selectedUser = getSelectedUser(searchResult, index - 1);
				selectedUser.displayUserProfile();
				System.out.println("Enter the following options ('1' or '2'): ");
				System.out.println("1. add this user");
				System.out.println("2. Go back to the previous page");
				choice = Integer.parseInt(sc.nextLine());
				switch (choice) {
				case 1:
					currentUser.addFriend(selectedUser);
					break;
				case 2:
					break;
				default:
					System.out.println("Invalid input. Please enter the option number only.");
					break;
				}
				break;
			case 3:
				ArrayList<User> recommendationResult = currentUser.getRecommendation(); //get recommendation from the user object
				displayArrayListUser(recommendationResult);
				
				System.out.println("Enter the friend number ('1', '2', '3' etc.): ");
				index = Integer.parseInt(sc.nextLine());
				
				selectedUser = getSelectedUser(recommendationResult, index - 1);
				selectedUser.displayUserProfile();
				System.out.println("Enter the following options ('1' or '2'): ");
				System.out.println("1. add this user");
				System.out.println("2. Go back to the previous page");
				choice = Integer.parseInt(sc.nextLine());
				switch (choice) {
				case 1:
					currentUser.addFriend(selectedUser);
					break;
				case 2:
					break;
				default:
					System.out.println("Invalid input. Please enter the option number only.");
					break;
				}
				break;
			case 4:
				exitStatus = true;
				break;
			default:
				System.out.println("Invalid input. Please enter the option number only.");
				break;
			}
		}
	}
	
	
	
	
	public static void displayArrayListUser(ArrayList<User> inputArrayList) {
		System.out.println("inputArrayList: " + inputArrayList.toString());
		for (int i = 0; i < inputArrayList.size(); i++) {
			User userFromCurrentIndex = inputArrayList.get(i);
			System.out.println(i+1 + ": " + userFromCurrentIndex.getFirstName() + " " + userFromCurrentIndex.getLastName());
		}
	}
	
	public static User getSelectedUser(ArrayList<User> searchResultArrayListwithUsers, int index) {
		return searchResultArrayListwithUsers.get(index);
	}

}
