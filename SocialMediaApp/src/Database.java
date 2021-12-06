import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
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
	private static ArrayList<User> userList; // arraylist of users stored at the position of their id's
	private ArrayList<BST<User>> interests; // array list of interests and which users have them

	// Graph Team Data Structures for graph database, BFS and recommendation alg
	private static ArrayList<LinkedList<Integer>> allUsers; // graph database of all friend connections between users
	private static ArrayList<Integer> distance; // used in BFS to store distances from initial node
	private static ArrayList<Integer> interestScore; // array list of score calculated from # of interests in common between 2 users

	private static int numUsers; // holds number of users, used to create user id
	
	// Hash table team
	private HashTable<User> userHash; // a Hash table to store User Object, used for logging in
	private HashTable<Interest> interestHash; // a hash table to store Interest Object, used for searching interests
	
	// to load the data from a file
	public Database() {
		File file = new File("data.txt");

		// create an arraylist to temporarily store the friend list, index 
		//		ArrayList<LinkedList<Integer>> tempFriendList = new ArrayList<>();
		//		tempFriendList.add(new LinkedList<Integer>());

		// initialize data structures and create 0th place
		interests = new ArrayList<>();
		for (int i = 0; i < 135; i++) {
			interests.add(new BST<User>()); // 0th linked list, not used
		} // initialize first 135(same as in interestHash) so that won't get out of bound error
		
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
		userHash = new HashTable<>(45); //initialize a hash table with size 3*15 
		interestHash = new HashTable<>(135); //initialize a hash table with size 3*3 interests*15 users
		
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
				int numOfFriends = Integer.parseInt(br.readLine());
				
				allUsers.add(new LinkedList<Integer>());
				numUsers++;

				// Loop through user's friend list and add to graph as integer of userId
				for (int i = 0; i < numOfFriends; i++) {
					int friendID = Integer.parseInt(br.readLine());
					allUsers.get(numUsers).addLast(friendID); //
				}
				// Create arraylist of interests
				String city = br.readLine();
				User newUser = new User(userID, firstName, lastName, userName, password, city);
				int numOfInterests = Integer.parseInt(br.readLine());
				LinkedList<Interest> interestLinkedList = new LinkedList<>();

				// Add each interest to user's individual linked list of interests
				for (int j = 0; j < numOfInterests ; j++) {
					String interestName = br.readLine();				
					int interestID = interestHash.hash(interestName);
					Interest tempInterestObj = new Interest(interestName, interestID);
					interestLinkedList.addLast(tempInterestObj); // add interest object to linked list
					if (interestHash.search(tempInterestObj) == null) {
						interestHash.insert(tempInterestObj); // add interest object to hash table storing interest
					}
					interests.get(interestID).insert(newUser); // add userID to interest BST
				} // NEEDS WORK creating interest ob
				newUser.setInterests(interestLinkedList);
				// Create new user from input data -- with empty friendlist
				userList.add(newUser);
				distance.add(-1);
				interestScore.add(-1);
				userHash.insert(newUser);
				
				// Add the newly created user to the BST that store all user, sorted by name
				userBST.insert(newUser);
			}
			
			// Add User objects to each User's friend list
			for (int k = 1 ; k < userList.size() ; k++) { // here k is the index of userList, which is equal to user id
				try {
					allUsers.get(k).positionIterator(); //position iterator to front of the User k's friends linkedlist
				} catch(Exception e) {}
				for (int l = 0 ; l < allUsers.get(numUsers).getLength(); l++) { // this loop is used to get the friends' id for each user
					int friendToAddID = allUsers.get(k).getIterator(); //getting the ID of the friend
					User friendToAddObj = userList.get(friendToAddID); //getting the user object of that friend using ID#
					userList.get(k).addFriends(friendToAddObj); //adding that user object to k's friendlist
					allUsers.get(k).advanceIterator(); //moving iterator to second node (second friend id)
				}
			}
		} catch (IOException e) {
			System.out.println("File not found!");
		}
		
	}
	
	/*
	 * This method will also return the newly created user for the driver class.
	 * 
	 */
	public User createUser() {
		//generate a new user ID after the largest user ID in the database
		numUsers++;
		int userID = numUsers; 
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter your first name: ");
		String firstName = sc.nextLine();
		System.out.println("Please enter your last name: ");
		String lastName = sc.nextLine();
		System.out.println("Please enter your userName: ");
		String userName = sc.nextLine();
		System.out.println("Please enter your password: ");
		String password = sc.nextLine();
		System.out.println("Please enter your city: ");
		String city = sc.nextLine();
		User newUser = new User(userID, firstName, lastName, userName, password, city);
		boolean endOfInterest = false;
		LinkedList<Interest> interestLinkedList = new LinkedList<>();
		 while (!endOfInterest) {
			System.out.println("Please enter your interests (enter '0' if there is not more interest to add): ");
			String interest = sc.nextLine();		
			if (!interest.equals("0")) {
				int interestID = interestHash.hash(interest);
				Interest tempInterestObj = new Interest(interest, interestID);
				interestLinkedList.addLast(tempInterestObj); // add interest object to linked list
				if (interestHash.search(tempInterestObj) == null) {
					interestHash.insert(tempInterestObj); // add interest object to hash table storing interest
				}
				interests.get(interestID).insert(newUser);
            } else {
                endOfInterest = true;
            }
		}
		newUser.setInterests(interestLinkedList);
		
		//BST friendBST = new BST(); // should start with no friends, then the user can add friends
		allUsers.add(new LinkedList<Integer>());
		userList.add(newUser); //To graph team: Do you mean to insert UserId?
		userHash.insert(newUser);
		userBST.insert(newUser);
		distance.add(-1);
		interestScore.add(-1);
		return newUser;
	}
	
	//save file when we call the quit method
	public static void writeToFile() {
		try {
			PrintWriter out = new PrintWriter("data.txt");
			for (int i = 1; i < userList.size(); i++) {
				User currUser = userList.get(i);
				int numFriends = currUser.getSortedFriendArrayList().size();
				int numInterests = currUser.getInterests().getLength();
				out.print(currUser.getId() + "\n" + currUser.getFirstName() + " " + currUser.getLastName() + "\n" + currUser.getUserName() + "\n" + currUser.getPassword() + "\n" + numFriends);
				for (int j = 0; j < numFriends; j++) {
					out.print("\n" + currUser.getSortedFriendArrayList().get(j).getId());
				}
				out.print("\n" + currUser.getCity());
				out.print("\n" + numInterests);
				if (numInterests != 0) {
					currUser.getInterests().positionIterator();
				}
				for (int j = 0; j < numInterests; j++) {
					out.print("\n" + currUser.getInterests().getIterator().toString());
					currUser.getInterests().advanceIterator();
				}
				if (i != userList.size() - 1) {
					out.print("\n");
				}
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Create a string of the current data.txt file
	private static String getCurrentFileContentsAsString() {
		try {
			Scanner input = new Scanner(new File("data.txt"));
			StringBuilder sb = new StringBuilder();
			while (input.hasNext()) {
				sb.append(input.nextLine()).append("\n");
			}
			input.close();
			return sb.toString();
		} catch (Exception e) {
			return "";
		}
	}

	// BFS method
	public static void BFS(Integer source) {

        LinkedList<Integer> Q = new LinkedList<Integer>(); // temp linked list to store queue

		// set all initial values to -1 for both distance arraylist and interestScore arraylist
        for (int i = 1; i <= numUsers; i++) {
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
                   if (distance.get(allUsers.get(x).getIterator()) > 2) {
						userList.get(source).getInterests().positionIterator();
						userList.get(x).getInterests().positionIterator();
						int count = 0;

						while (userList.get(source).getInterests().offEnd() != true && userList.get(x).getInterests().offEnd() != true) {
							if (userList.get(source).getInterests().getIterator().compareTo(userList.get(x).getInterests().getIterator()) == 0) {
								count++;
							}
							userList.get(source).getInterests().advanceIterator();
							userList.get(x).getInterests().advanceIterator();
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

	public static int calcHighestIndex(ArrayList<Integer> input) {
		int highest = Collections.max(input);
		int index = input.indexOf(highest);
		return index;
	}

	// Recommendation Method
	public static ArrayList<User> getRecommendation(Integer source) {
		ArrayList<User> answer = new ArrayList<>(); // linked list of users in order of final recommendation
		BFS(source); // call BFS on user graph, update distance and interestScore arraylists
		// add eligible users to linked list in order of most interests shared
		int highestIndex = calcHighestIndex(interestScore);
		while (Collections.max(interestScore) != -1) {
			answer.add(userList.get(highestIndex)); // change here
			interestScore.set(highestIndex, -1);
			highestIndex = calcHighestIndex(interestScore);
		}
		return answer;
	}

	public static ArrayList<User> getNoFriendsRecommendation(Integer currId) {
		ArrayList<User> answer = new ArrayList<>();
		int count = 0;

		for (int i = 1; i <= numUsers; i++) {
            interestScore.set(i, -1);
        }

		for (int i = 1; i <= numUsers; i++) {
			if (i != currId) {
				userList.get(currId).getInterests().positionIterator();
				userList.get(i).getInterests().positionIterator();

				while (userList.get(currId).getInterests().offEnd() != true && userList.get(i).getInterests().offEnd() != true) {
					if (userList.get(source).getInterests().getIterator().compareTo(userList.get(x).getInterests().getIterator()) == 0) {
						count++;
					}

					userList.get(currId).getInterests().advanceIterator();
					userList.get(i).getInterests().advanceIterator();
				}
				interestScore.set(i, count);
				count = 0;
			}
		}

		int highestIndex = calcHighestIndex(interestScore);
		while (Collections.max(interestScore) != -1 && answer.size() < 10) {
			answer.add(userList.get(highestIndex)); 
			interestScore.set(highestIndex, -1);
			highestIndex = calcHighestIndex(interestScore);
		}
		return answer;

	}
	
	/* 
	 * ------------------------Complete-----------------------------
	 */
	public ArrayList<User> searchUserByName(String targetName, User currUser){
		System.out.println("Searching: " + targetName);
		ArrayList<User> userList = userBST.inOrderData();
		ArrayList<User> userFriendList = currUser.getSortedFriendArrayList();
		ArrayList<User> resultList = new ArrayList<>();
		for (int i = 0; i < userList.size(); i++) {
			User userInIndex = userList.get(i);
			String userNameInUpperCase = userInIndex.getFirstName().toUpperCase() + ' ' + userInIndex.getLastName().toUpperCase();
			String targetNameInUpperCase = targetName.toUpperCase();
			if (userNameInUpperCase.contains(targetNameInUpperCase) && !userFriendList.contains(userInIndex) && !Driver.currentUser.equals(userInIndex) ) {
				resultList.add(userInIndex);
			}
		}
		return resultList;
	}
	
	/* 
	 * ------------------------Needs testing-----------------------------
	 */
	public ArrayList<User> searchUserByInterest(String targetInterestName, User currUser){
		BST<User> temp = interests.get(interestHash.hash(targetInterestName));
		ArrayList<User> interestUserList = temp.inOrderData();
		System.out.println("interestUserList: " + interestUserList.toString());
		ArrayList<User> userFriendList = currUser.getSortedFriendArrayList();
		System.out.println("userFriendList: " + userFriendList.toString());
		ArrayList<User> resultList = new ArrayList<>();
		for (int i = 0; i < interestUserList.size(); i++) {
			if (!userFriendList.contains(interestUserList.get(i)) && !Driver.currentUser.equals(interestUserList.get(i))) {
				resultList.add(interestUserList.get(i));
			}
		}
		return resultList;
	}
	
	/* 
	 * ------------------------Finished-----------------------------
	 */
	public static void removeFriend(User originalUser, User toBeRemovedUser) {
		LinkedList<Integer> userLinkedList = allUsers.get(originalUser.getId()); 
		LinkedList<Integer> friendLinkedList = allUsers.get(toBeRemovedUser.getId()); 

		// removing friend from user in friend graph
		// move iterator to spot, then remove
		userLinkedList.iteratorToIndex(userLinkedList.linearSearch(toBeRemovedUser.getId()));
		userLinkedList.removeIterator();
		originalUser.getFriends().remove(toBeRemovedUser); // remove friend from user's personal friend BST

		// removing user from friend in friend graph
		// move iterator to spot, then remove
		friendLinkedList.iteratorToIndex(friendLinkedList.linearSearch(originalUser.getId()));
		friendLinkedList.removeIterator();
		toBeRemovedUser.getFriends().remove(originalUser); // remove user from friend's personal friend BST
	}
	
	/*
	 * ------------------------Finished-----------------------------
	 */
	public static void addFriend(User originalUser, User toBeAddedUser) {
		LinkedList<Integer> userLinkedList = allUsers.get(originalUser.getId()); 
		LinkedList<Integer> friendLinkedList = allUsers.get(toBeAddedUser.getId()); 

		// Graph Team updating all users graph (storing friend connections)
		userLinkedList.addLast(toBeAddedUser.getId()); // connection 1 to 2
		friendLinkedList.addLast(originalUser.getId()); // connection 2 to 1
		
		// update each user object's personal friend BST
		originalUser.getFriends().insert(toBeAddedUser);
		toBeAddedUser.getFriends().insert(originalUser);
	}

	public User login(String username, String password) {
		User tempU = new User(username, password);
		return (User) userHash.searchUser(tempU);
	}
}
