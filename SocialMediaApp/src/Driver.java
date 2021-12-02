import java.util.ArrayList;
import java.util.Scanner;


/* 
 * Both addFriend() and removeFriend() requires everyone's effort to update the data structure.
 * Search result should return ArrayList<User>. Steven will use that to display the search result.
 */

public class Driver {
	

	public static void main(String[] args) {
		boolean loginStatus = false;
		int choice;
		Scanner sc = new Scanner(System.in);
		Database dataBase = new Database();
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
				 * Please also assign the logged in user as currentUser
				 * might want to return to the main menu if login fail 
				 */
//				login(); 
			case 2:
				dataBase.createUser(); //should create a new user, general method
				loginStatus = true;
			}
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
//					searchfriend //this method should take the input, then display the search result, BST group
//					selectFriend //user will enter the userID as their selection, this should return <user> object
//						displayUser // this will just display all the info of the selected user (general method)
//						removefriend //This requires all the teams' effort (update data structures)
//						goback // we don't need a method but will write it out in the driver instead.
			case 2:
				makeFriendsOutsideOfTheCircle(); //the code is written below, implement your method in viewFriend();
//					searchbyname(); BST group
//						displaySearchResult(); general method
//						selectFriend(); general method
//							displayUser(); general method
//							addfriend() or goback(); Everyone's effort
//					searchbyInterest(); <--------------Fill this--------------->
//						displaySearchResult();
//							selectFriend();
//							displayUser();
//							addfriend() or goback();
//					getRecommendation(); <--------------Fill this--------------->
//						displaySearchResult(); general method
//							selectFriend(); general method
//							displayUser(); general method
//							addfriend() or goback(); Everyone's effort
//					goback();
			case 3:
				dataBase.writeToFile();
				loginStatus = false;
			}
			
		}

	}
	
	/* Helper method to view friends of the current user.
	 * Current user can remove / sort / search their friend here.
	 */
	public void viewFriend() {
		int choice;
		
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
			int index = sc.nextInt();
			
			User selectedUser = getSelectedUser(sortedFriendList, index);
			selectedUser.displayUserProfile();
			System.out.println("Enter the following options ('1' or '2'): ");
			System.out.println("1. Remove this friend");
			System.out.println("Go back to the previous page"); //hasn't implemented yet
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				//remove friend
				database.removeFriend();
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
			int index = sc.nextInt();
			
			User selectedUser = getSelectedUser(searchResult, index);
			selectedUser.displayUserProfile();
			System.out.println("Enter the following options ('1' or '2'): ");
			System.out.println("1. Remove this friend");
			System.out.println("2. Go back to the previous page"); //hasn't implemented yet
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				//remove friend
				database.removeFriend();
			case 2:
				//go back
			}
			
		}
	}
	
	/* Helper method to add friends outside of current user's network
	 * Current user can searchbyname, searchbyinterest, and get recommendation, then add friends from the list.
	 * Should not be able to add a user that is already current user's friend
	 */
	public void makeFriendsOutsideOfTheCircle() {
		int choice;
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
			ArrayList<User> searchResult = database.searchUserByName(targetName);
			displayArrayListUser(searchResult);
			
			System.out.println("Enter the friend number ('1', '2', '3' etc.): ");
			int index = sc.nextInt();
			
			User selectedUser = getSelectedUser(searchResult, index);
			selectedUser.displayUserProfile();
			System.out.println("Enter the following options ('1' or '2'): ");
			System.out.println("1. add this user");
			System.out.println("2. Go back to the previous page"); //hasn't implemented yet
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				//remove friend
				database.addFriend();
			case 2:
				//go back
			
			}
		case 2:
			System.out.println("Enter the interest that you would like to search: ");
			String targetInterest = sc.next();
			ArrayList<User> searchResult = database.searchUserByInterest(targetInterest);
			displayArrayListUser(searchResult);
			
			System.out.println("Enter the friend number ('1', '2', '3' etc.): ");
			int index = sc.nextInt();
			
			User selectedUser = getSelectedUser(searchResult, index);
			selectedUser.displayUserProfile();
			System.out.println("Enter the following options ('1' or '2'): ");
			System.out.println("1. add this user");
			System.out.println("2. Go back to the previous page"); //hasn't implemented yet
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				//remove friend
				database.addFriend();
			case 2:
				//go back
			
			}
		case 3:
			ArrayList<User> recommendationResult = currentUser.getRecommendation(); //get recommendation from the user object
			displayArrayListUser(recommendationResult);
			
			System.out.println("Enter the friend number ('1', '2', '3' etc.): ");
			int index = sc.nextInt();
			
			User selectedUser = getSelectedUser(searchResult, index);
			selectedUser.displayUserProfile();
			System.out.println("Enter the following options ('1' or '2'): ");
			System.out.println("1. add this user");
			System.out.println("2. Go back to the previous page"); //hasn't implemented yet
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				//remove friend
				database.addFriend();
			case 2:
				//go back
			
			}
			
		}
	}
	
	
	
	
	public void displayArrayListUser(ArrayList<User> searchResultArrayListwithUsers) {
		
	}
	
	public User getSelectedUser(ArrayList<User> searchResultArrayListwithUsers, int index) {
		return searchResultArrayListwithUsers.get(index);
	}

}
