import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;

import Collections.*;

/**
 * This class load all the data from the data file and put them in appropriate data structures.
 * The data will be called in other class do do things like search, add friends, etc.
 */
public class Database {
	private ArrayList<LinkedList<Integer>> allUsers; // graph database of all friend connections between users
	private BST<User> userBST = new BST<>(); // stores all users in a BST of user objects
	
	private ArrayList<User> userList; // arraylist of users stored at the position of their id's
	private ArrayList<BST> interests; // array list of interests and which users have them

	// BFS ArrayLists for recommendation alg
	private ArrayList<Integer> distance; // used in BFS to store distances from initial node
	private ArrayList<Integer> interestScore; // array list of score calculated from # of interests in common between 2 users

	private static int numUsers; // holds number of users, used to create user id
	
	
	// to load the data from a file
	public Database() {
		File file = new File("src/data.txt");

		// create an arraylist to temporarily store the friend list, index 
		//		ArrayList<LinkedList<Integer>> tempFriendList = new ArrayList<>();
		//		tempFriendList.add(new LinkedList<Integer>());

		// initialize data structures and create 0th place
		allUsers = new ArrayList<>();
		allUsers.add(new LinkedList<>()); // 0th linked list, not used
		interests = new ArrayList<>();
		interests.add(new BST<>()); // 0th linked list, not used
		userList = new ArrayList<>();
		userList.add(new User<>()); // 0th linked list, not used // am i doing this right

		distance = new ArrayList<>();
		distance.add(-1); // 0th linked list, not used
		interestScore = new ArrayList<>();
		interestScore.add(-1); // 0th linked list, not used
		
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
				distance.add(-1);
				interestScore.add(-1);
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
					int currentID = hash(interestName); // HASH TEAM
					/*
					HASH TEAM: hash(interestName) above needs to return back the interest id
					 */

					Interest interestobj = new Interest();
					interestLinkedList.addLast(interestobj); 
				} // NEEDS WORK creating interest obj

				// Create new user from input data
				User newUser = new User(userID, firstName, lastName, userName, password, city, interestLinkedList);
				userList.add(newUser);

				// Loop through interests and add to arraylist
				for (int j = 0; j < numOfInterests; j++){
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

					interests.get(position).insert(userID);
				}

				// Loop through interests linked list and add user to each interest in interest arraylist BST
				interestLinkedList.positionIterator();
				while (interestLinkedList.offEnd() == false) {
					int position = interestLinkedList.getIterator().getID();
					interests.get(position).insert(newUser);
					interestLinkedList.advanceIterator();
				}

				userBST.insert(newUser);
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
		allUsers.insert(newUser);
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
        /*
        for all verticies in adj                
			color[x] = white     
			distance[x] = -1       
			parent[x] = Nil              
		color[s] = grey                 
		distance[s] = 0                  
		Enqueue(Q,s)
			
		while(Q is not empty)
			x = front of Q
			Dequeue(Q,x)
			for all y in adj[x]
				if color[y] == white
					color[y] = grey
					distance[y] = distance[x] + 1
					parent[y] = x
					Enqueue(Q, y)
			color[x] = black
         */

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

	public int calcHighestScore(ArrayList input) {
		int highest = Collections.max(input);
		int index = input.indexOf(highest);
		return index;
	}

	// Recommendation Method
	public LinkedList recommendFriends(int source) {
		LinkedList<User> answer = new LinkedList<>(); // linked list of users in order of final recommendation

		allUsers.BFS(source);

		int highestIndex = calcHighestScore(interestScore);
		while (highestIndex != -1) {
			answer.addLast(userList.get(highestIndex));
			interestScore.set(highestIndex, -1);
			highestIndex = calcHighestScore(interestScore);
		}

		return answer;
	}
}
