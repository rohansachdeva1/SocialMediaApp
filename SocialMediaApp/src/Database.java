import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Collections.BST;

/**
 * This class load all the data from the data file and put them in appropriate data structures.
 * The data will be called in other class do do things like search, add friends, etc.
 */
public class Database {
	private BST<User> allUsers;
	
	
	// to load the data from a file
	public Database() {
		File file = new File("src/data.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String stringFromDataTxt;
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
				
				BST friendBST = new BST();
				for (int i = 0; i < numOfFriends; i++) {
					String friendsName = br.readLine();
					friendBST.insert(friendsName); // or change it to <User>? It should be string when constructing?
				}
				
				int numOfInterests = Integer.parseInt(br.readLine());
				ArrayList interestLinkedList = new ArrayList();
//				interestLinkedList.positionIterator();
				for (int i = 0; i < numOfInterests; i++) {
					String interestName = br.readLine();
					interestLinkedList.add(interestName); //or change it to <Interest>? We can build interest object on the fly.
				}
				User newUser = new User(userID, firstName, lastName, userName, password, city, friendBST, interestLinkedList);
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
		while (!endOfInterest) {
			System.out.println("Please enter your interests (enter '0' if there is not more interest to add): ");
			String interest = sc.next();
			if (interest != "0") {
				//buildInterest();
				continue;
			}
			endOfInterest = true;
		}
		//generate a new user ID after the largest user ID in the database
		int userID = lastUserID + 1;
		BST friendBST = new BST();
		User newUser = new User(userID, firstName, lastName, userName, password, city, friendBST, interestLinkedList);
		allUsers.insert(newUser);
	}
	
	//save file when we call the quit method
	public void writeToFile() {
		
	}
	
	
}
