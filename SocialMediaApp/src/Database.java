import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Collections.*;

/**
 * This class load all the data from the data file and put them in appropriate data structures.
 * The data will be called in other class do do things like search, add friends, etc.
 */
public class Database {
	private ArrayList<LinkedList<Integer>> allUsers; // graph database of all users
	private static int numUsers; // holds number of users, used to create user id
	private ArrayList<Integer> distance; // used in BFS to store distances from initial node
	private ArrayList<Integer> interestScore; // array list of score calculated from # of interests in common between 2 users
	private ArrayList<Integer> interests; // array list of interests and which users have them
	private BST<User> userList = new BST<>();
	
	
	// to load the data from a file
	public Database() {
		File file = new File("src/data.txt");

		// create an arraylist to temporarily store the friend list, index 
		//		ArrayList<LinkedList<Integer>> tempFriendList = new ArrayList<>();
		//		tempFriendList.add(new LinkedList<Integer>());

		allUsers = new ArrayList<>();
		allUsers.add(new LinkedList<>()); // 0th linked list, not used
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String stringFromDataTxt;
			numUsers = 0;

			/* INPUT FILE FORMAT FOR EACH USER
			id
			name
			username
			password
			total number of friends
			list of ids of all friends on separate lines
			city
			total number of interests
			list of interests, each on a separate line
			*/

			// Loop through input file and take in all user data
			while ((stringFromDataTxt = br.readLine()) != null) {
				int userID = Integer.parseInt(stringFromDataTxt);
				String fullName = br.readLine();
				int spaceIndex = fullName.indexOf(" ");
				String firstName = fullName.substring(0, spaceIndex);
				String lastName = fullName.substring(spaceIndex+1);
				String userName = br.readLine();
				String password = br.readLine();
				String city = br.readLine();
				int numOfFriends = Integer.parseInt(br.readLine());
				
				// Add linked list for each new user
				// 		tempFriendList.add(new LinkedList<Integer>());
				allUsers.add(new LinkedList<>());
				numUsers++;
				
				// Loop through user's friend list and add to database
				for (int i = 0; i < numOfFriends; i++) {
					int friendID = Integer.parseInt(br.readLine());
					allUsers.get(numUsers).addLast(friendID);
				}

				// Create arraylist of interests
				int numOfInterests = Integer.parseInt(br.readLine());
				LinkedList<Interest> interestLinkedList = new LinkedList<>();
				
				// Loop through interests and add to arraylist
				for (int j = 0; j < numOfInterests ; j++){
					String interestName = br.readLine();
					/*
					HASH TEAM: Use a hashtable to search for interest object
					need method that takes interestName as parameter and returns 
					its postion in interests arraylist
					*/ 
					// need to figure out how to size interest arraylist when we do 
					// not know the amount of interests from the start
					int position = hash(interestName);

					//Interest interestobj = new Interest();
					//interestLinkedList.addLast(interestobj); 

					interests.get(position).addLast(userID);
				}

				// Create new user from input data
				User newUser = new User(userID, firstName, lastName, userName, password, city, interestLinkedList);
				userList.insert(newUser);
			}
		
		} catch (IOException e) {
			System.out.println("File not found!");
		}
		
	}
	
	
	public void createUser() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter your first name: ");
		String firstName = sc.next();
		System.out.println("Please enter your last name: ");
		String lastName = sc.next();
		System.out.println("Please enter your userName: ");
		String userName = sc.next();
		System.out.println("Please enter your password: ");
		String password = sc.next();
		System.out.println("Please enter your city: ");
		String city = sc.next();
		boolean endOfInterest = false;
		LinkedList<Interest> interestLinkedList = new LinkedList<>();
		while (!endOfInterest) {
			System.out.println("Please enter your interests (enter '0' if there is not more interest to add): ");
			String interest = sc.next();
			
			if (interest != "0") {
				//buildInterest();
				continue;
			}
			endOfInterest = true;
		}
		numOfUser++;
		//generate a new user ID after the largest user ID in the database
		int userID = numOfUser;
		
		//BST friendBST = new BST(); // should start with no friends, then the user can add friends
		User newUser = new User(userID, firstName, lastName, userName, password, city, interestLinkedList);
		allUsers.insert(newUser);
	}
	
	public ArrayList searchByNameReturnListOfResult(String nameKeyword) {
		ArrayList searchResult = new ArrayList();
		return searchResult;
	}
	
	//save file when we call the quit method
	public void writeToFile() {
		
	}
	
	
}
