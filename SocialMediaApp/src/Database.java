import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
//import java.util.LinkedList;

/**
 * This class load all the data from the data file and put them in appropriate data structures.
 * The data will be called in other class do do things like search, add friends, etc.
 */
public class Database {

	/*
	All teams, organize your data structures here by comments,
	that way we can keep track of whos is whos
	ex. graph team below
	 */

	private BST<User> userBST = new BST<>(); // stores all users in a BST of user objects
	private ArrayList<User> userList; // arraylist of users stored at the position of their id's
	private ArrayList<BST> interests; // array list of interests and which users have them

	// Graph Team Data Structures for graph database, BFS and recommendation alg
	private ArrayList<LinkedList<Integer>> allUsers; // graph database of all friend connections between users
	private ArrayList<Integer> distance; // used in BFS to store distances from initial node
	private ArrayList<Integer> interestScore; // array list of score calculated from # of interests in common between 2 users

	private static int numUsers; // holds number of users, used to create user id
	
	// Hash table team
	private HashTable userHash; // a Hash table to store User Object, used for logging in
	private HashTable interestsHash; // a hash table to store Interest Object, used for searching interests
	
	// to load the data from a file
	public Database() {
		File file = new File("src/data.txt");

		// create an arraylist to temporarily store the friend list, index 
		//		ArrayList<LinkedList<Integer>> tempFriendList = new ArrayList<>();
		//		tempFriendList.add(new LinkedList<Integer>());

		// initialize data structures and create 0th place
		interests = new ArrayList<>();
		interests.add(new BST<>()); // 0th linked list, not used

		// Graph Team initialize data structures and create 0th place
		allUsers = new ArrayList<>();
		allUsers.add(new LinkedList<Integer>()); // 0th linked list, not used
		userList = new ArrayList<>();
		userList.add(new User()); // 0th linked list, not used // am i doing this right
		distance = new ArrayList<>();
		distance.add(-1); // 0th linked list, not used
		interestScore = new ArrayList<>();
		interestScore.add(-1); // 0th linked list, not used
		
		
		// Hash team initialize data structure
		userHash = new HashTable(45); //initialize a hashtable with size 3*15 
		interestsHash = new HashTable(135); //initialize a hash table with size 3*3 interests*15 users
		
		try {
			/* 
			INPUT FILE FORMAT FOR EACH USER
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

			BufferedReader br = new BufferedReader(new FileReader(file));
			String stringFromDataTxt;
			numUsers = 0;

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
				
				allUsers.add(new LinkedList<Integer>());
				numUsers++;

				// Loop through user's friend list and add to graph as integer of userId
				for (int i = 0; i < numOfFriends; i++) {
					int friendID = Integer.parseInt(br.readLine());
					allUsers.get(numUsers).addLast(friendID); //
				}

				// Create arraylist of interests
				int numOfInterests = Integer.parseInt(br.readLine());
				LinkedList<Interest> interestLinkedList = new LinkedList<>();

				// Add each interest to user's individual linked list of interests
				for (int j = 0; j < numOfInterests ; j++){
					String interestName = br.readLine();
//					int interestID = hash(interestName); // HASH TEAM
					int interestID = interestsHash.hash(interestName);

					Interest tempInterestObj = new Interest(interestName, interestID);
					interestLinkedList.addLast(tempInterestObj); // add interest object to linked list
					interestsHash.insert(tempInterestObj); // add interest object to hash table storing interest
					
					interests.get(interestID).insert(userID); // add userID to interest BST
				} // NEEDS WORK creating interest ob

				// Create new user from input data -- with empty friendlist
				User newUser = new User(userID, firstName, lastName, userName, password, city, interestLinkedList);
				userList.add(newUser);
				distance.add(-1);
				interestScore.add(-1);
				userHash.insert(newUser);

				// Loop through interests linked list and add user to each interest in interest arraylist BST
				interestLinkedList.positionIterator();
				while (interestLinkedList.offEnd() == false) {
					int position = interestLinkedList.getIterator().getId();
					interests.get(position).insert(newUser);
					interestLinkedList.advanceIterator();
				}
				
				// Add the newly created user to the BST that store all user, sorted by name
				userBST.insert(newUser);
				
				// Add User objects to each User's friend list
				for (int k = 1 ; k < userList.size() ; k++) { // here k is the index of userList, which is equal to user id
					allUsers.get(k).positionIterator(); //position iterator to front of the User k's friends linkedlist
					for (int l = 0 ; l < allUsers.get(numUsers).getLength(); l++) { // this loop is used to get the friends' id for each user
						int friendToAddID = allUsers.get(k).getIterator(); //getting the ID of the friend
						User friendToAddObj = userList.get(friendToAddID); //getting the user object of that friend using ID#
						userList.get(k).addFriends(friendToAddObj); //adding that user object to k's friendlist
						
						//FIXME need to update the compare to method in User class, so BST can be sorted by name
						
						allUsers.get(k).advanceIterator(); //moving iterator to second node (second friend id)
					}
				}
				
				
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
		//generate a new user ID after the largest user ID in the database
		int userID = ++numUsers;
		
		//BST friendBST = new BST(); // should start with no friends, then the user can add friends
		User newUser = new User(userID, firstName, lastName, userName, password, city, interestLinkedList);
		allUsers.insert(newUser); //To graph team: Do you mean to insert UserId?
		
		
	}
	
	public ArrayList searchByNameReturnListOfResult(String nameKeyword) {
		ArrayList searchResult = new ArrayList();
		return searchResult;
	}
	
	//save file when we call the quit method
	public void writeToFile() {
		
	}

	// BFS method
	public void BFS(Integer source) {

        LinkedList<Integer> Q = new LinkedList<Integer>(); // temp linked list to store queue

		// set all initial values to -1 for both distance arraylist and interestScore arraylist
        for (int i = 1; i <= this.numUsers; i++) {
            distance.set(i, -1);
            interestScore.set(i, -1);
        }

        distance.set(source, 0);
        Q.addLast(source);

        while (Q.isEmpty() == false) {
            int x = (int)Q.getFirst();
            Q.removeFirst();
            allUsers.get(x).positionIterator();

            while (allUsers.get(x).offEnd() == false) {
				// if user has not been visited, set distance in distance arraylist
                if (distance.get(allUsers.get(x).getIterator()) == -1) {
                    distance.set(allUsers.get(x).getIterator(), distance.get(x) + 1);

					// calculate interest score between source and current user, store in interestScore arraylist
					if (distance.get(allUsers.get(x).getIterator()) > 1) {
						userList.get(source).interests.positionIterator();
						userList.get(x).interests.positionIterator();
						int count = 0;

						while (userList.get(source).interests.offEnd() != true && userList.get(x).interests.offEnd() != true) {
							if (userList.get(source).interests.getIterator().compareTo(userList.get(x).interests.getIterator()) == 0) {
								count++;
							}
							userList.get(source).interests.advanceIterator();
							userList.get(x).interests.advanceIterator();
						}
						interestScore.set(x, count);
						count = 0;
					}
                    Q.addLast(allUsers.get(x).getIterator());
                }
                allUsers.get(x).advanceIterator();
            }
        }
    }

	public int calcHighestIndex(ArrayList input) {
		int highest = Collections.max(input);
		int index = input.indexOf(highest);
		return index;
	}

	// Recommendation Method
	public LinkedList recommendFriends(int source) {
		LinkedList<User> answer = new LinkedList<>(); // linked list of users in order of final recommendation
		allUsers.BFS(source); // call BFS on user graph, update distance and interestScore arraylists

		// add eligible users to linked list in order of most interests shared
		int highestIndex = calcHighestIndex(interestScore);
		while (highestIndex != -1) {
			answer.addLast(userList.get(highestIndex));
			interestScore.set(highestIndex, -1);
			highestIndex = calcHighestIndex(interestScore);
		}

		return answer;
	}
	
	//Hash method
	private int hash(T t) {
		return Math.abs(t.hashCode()) % userHash.size();
	}
}
