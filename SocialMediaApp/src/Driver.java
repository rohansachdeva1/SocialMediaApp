import java.util.ArrayList;
import java.util.Scanner;


/* 
 * Both addFriend() and removeFriend() requires everyone's effort to update the data structure.
 * Search result should return ArrayList<User>. Steven will use that to display the search result.
 */

public class Driver {
	static Database database;
	static User currentUser;

	public static void main(String[] args) {
		boolean loginStatus = false;
		int choice;
		Scanner sc = new Scanner(System.in);
		Database database = new Database();
		User currentUser; //This is assigned at the login
		
		while (!loginStatus) {
			System.out.println("Choose the option number ('1', '2' or '3'): ");
			System.out.println("1: Login");
			System.out.println("2: Create Account");
			System.out.println("3: Exit");
			choice = sc.nextInt();
			switch(choice) {
			case 1:
				/*
				 * Hashtable group
				 * Please also return the logged in user
				 * might want to return to the main menu if login fail 
				 */
				currentUser = database.login();
			case 2:
				currentUser = database.createUser(); //should create a new user, then this becomes the current user.
				loginStatus = true;
			case 3:
				System.exit(0); //exit the program
			}
			while (loginStatus) {
				System.out.println("Enter the  option number ('1', '2' or '3'): ");
				System.out.println("1: View my friends");
				System.out.println("2: Make new friend");
				System.out.println("3: Exit");
				choice = sc.nextInt(); //could give some exception handling
				switch(choice) {
				case 1:
					viewFriend(); //the code is written below, implement your method in viewFriend();
				case 2:
					makeFriendsOutsideOfTheCircle(); //the code is written below, implement your method in viewFriend();
				case 3:
					database.writeToFile();
					loginStatus = false;
				}
				
			}
		}

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
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter the following options ('1', '2' or '3'): ");
			System.out.println("1. View sorted friend list");
			System.out.println("2. Search friends their their names");
			System.out.println("3. Go back to the previous page"); //not implmented
			choice = sc.nextInt();
			switch (choice) {
			case 1: //view sorted friend
				ArrayList<User> sortedFriendList = currentUser.getSortedFriendArrayList();//sort by name
				displayArrayListUser(sortedFriendList);
				
				System.out.println("Enter the friend number ('1', '2', '3' etc.): ");
				index = sc.nextInt();
				
				selectedUser = getSelectedUser(sortedFriendList, index);
				selectedUser.displayUserProfile();
				System.out.println("Enter the following options ('1' or '2'): ");
				System.out.println("1. Remove this friend");
				System.out.println("Go back to the previous page"); //hasn't implemented yet
				choice = sc.nextInt();
				switch (choice) {
				case 1:
					//remove friend
					database.removeFriend(selectedUser);
				case 2:
					//go back
				}
				
			case 2: //search friend
				//search by name
				System.out.println("Enter the name that you would like to search: ");
				String targetName = sc.next();
				ArrayList<User> searchResult = currentUser.searchFromFriendList(targetName);
				displayArrayListUser(searchResult);
				
				System.out.println("Enter the friend number ('1', '2', '3' etc.): ");
				index = sc.nextInt();
				
				selectedUser = getSelectedUser(searchResult, index);
				selectedUser.displayUserProfile();
				System.out.println("Enter the following options ('1' or '2'): ");
				System.out.println("1. Remove this friend");
				System.out.println("2. Go back to the previous page"); //hasn't implemented yet
				choice = sc.nextInt();
				switch (choice) {
				case 1:
					//remove friend
					database.removeFriend(selectedUser);
				case 2:
					//go back
				}
				
			case 3:
				exitStatus = true;
				
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
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter the following options ('1', '2' or '3'): ");
			System.out.println("1. Search users by name ");
			System.out.println("2. Search users by interest");
			System.out.println("3. Go back to the previous page"); //not implmented
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				System.out.println("Enter the name that you would like to search: ");
				String targetName = sc.next();
				searchResult = database.searchUserByName(targetName);
				displayArrayListUser(searchResult);
				
				System.out.println("Enter the friend number ('1', '2', '3' etc.): ");
				index = sc.nextInt();
				
				selectedUser = getSelectedUser(searchResult, index);
				selectedUser.displayUserProfile();
				System.out.println("Enter the following options ('1' or '2'): ");
				System.out.println("1. add this user");
				System.out.println("2. Go back to the previous page"); //hasn't implemented yet
				choice = sc.nextInt();
				switch (choice) {
				case 1:
					//remove friend
					database.addFriend(selectedUser);
				case 2:
					//go back
				
				}
			case 2:
				System.out.println("Enter the interest that you would like to search: ");
				String targetInterest = sc.next();
				searchResult = database.searchUserByInterest(targetInterest);
				displayArrayListUser(searchResult);
				
				System.out.println("Enter the friend number ('1', '2', '3' etc.): ");
				index = sc.nextInt();
				
				selectedUser = getSelectedUser(searchResult, index);
				selectedUser.displayUserProfile();
				System.out.println("Enter the following options ('1' or '2'): ");
				System.out.println("1. add this user");
				System.out.println("2. Go back to the previous page"); //hasn't implemented yet
				choice = sc.nextInt();
				switch (choice) {
				case 1:
					//remove friend
					database.addFriend(selectedUser);
				case 2:
					//go back
				
				}
			case 3:
				ArrayList<User> recommendationResult = currentUser.getRecommendation(); //get recommendation from the user object
				displayArrayListUser(recommendationResult);
				
				System.out.println("Enter the friend number ('1', '2', '3' etc.): ");
				index = sc.nextInt();
				
				selectedUser = getSelectedUser(recommendationResult, index);
				selectedUser.displayUserProfile();
				System.out.println("Enter the following options ('1' or '2'): ");
				System.out.println("1. add this user");
				System.out.println("2. Go back to the previous page"); //hasn't implemented yet
				choice = sc.nextInt();
				switch (choice) {
				case 1:
					//remove friend
					database.addFriend(selectedUser);
				case 2:
					//go back
				
				}
				
			}
		}
	}
	
	
	
	
	public static void displayArrayListUser(ArrayList<User> inputArrayList) {
		for (int i = 0; i < inputArrayList.size(); i++) {
			User userFromCurrentIndex = inputArrayList.get(i);
			System.out.println(i + ": " + userFromCurrentIndex.getFirstName() + " " + userFromCurrentIndex.getLastName());
		}
	}
	
	public static User getSelectedUser(ArrayList<User> searchResultArrayListwithUsers, int index) {
		return searchResultArrayListwithUsers.get(index);
	}

}
