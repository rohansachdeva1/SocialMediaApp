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
	private static BST<User> allUsers;
	private static int numOfUser;
	
	
	// to load the data from a file
	public Database() {
		File file = new File("src/data.txt");
		// create an arraylist to temporarily store the friend list, index 
		ArrayList<LinkedList<Integer>> tempFriendList = new ArrayList<>();
		tempFriendList.add(new LinkedList<Integer>());
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String stringFromDataTxt;
			numOfUser = 0;
			while ((stringFromDataTxt = br.readLine()) != null) {
				int userID = Integer.parseInt(stringFromDataTxt);
				numOfUser++;
				String fullName = br.readLine();
				int spaceIndex = fullName.indexOf(" ");
				String firstName = fullName.substring(0, spaceIndex);
				String lastName = fullName.substring(spaceIndex+1);
				String userName = br.readLine();
				String password = br.readLine();
				String city = br.readLine();
				int numOfFriends = Integer.parseInt(br.readLine());
				

				// BST friendBST = new BST();
				tempFriendList.add(new LinkedList<Integer>());
				
				
				for (int i = 0; i < numOfFriends; i++) {
					int friendID = Integer.parseInt(br.readLine());
					tempFriendList.get(numOfUser).addLast(friendID);
				}

				
				int numOfInterests = Integer.parseInt(br.readLine());
				LinkedList<Interest> interestLinkedList = new LinkedList<>();
				
				for (int j = 0; j < numOfInterests ; j++){
					String interestName = br.readLine();
					// FIXME: Use hashtable to search for interest object, if not exist, create one
					Interest interestobj = new Interest();
					interestLinkedList.addLast(interestobj); 
				}
				User newUser = new User(userID, firstName, lastName, userName, password, city, interestLinkedList);
				allUsers = new BST<User>();
				allUsers.insert(newUser);
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
