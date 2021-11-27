import java.util.Scanner;

public class Driver {
	

	public static void main(String[] args) {
		boolean loginStatus = false;
		int choice;
		Scanner sc = new Scanner(System.in);
		Database dataBase = new Database();
		
		while (!loginStatus) {
			System.out.println("Choose the following options ('1', '2' or '3'): ");
			System.out.println("1: Login");
			System.out.println("2: Create Account");
			System.out.println("3: Exit");
			choice = sc.nextInt();
			switch(choice) {
			case 1:
//				login(); //might want to return to the main menu if login fail
			case 2:
				dataBase.createUser(); //should create a new user
				loginStatus = true;
			}
		}
		while (loginStatus) {
			System.out.println("Enter the following options ('1', '2' or '3'): ");
			System.out.println("1: View my friends");
			System.out.println("2: Make new friend");
			System.out.println("3: Exit");
			choice = sc.nextInt(); //could give some exception handling
			switch(choice) {
			case 1:
//				viewFriend();
//					searchfriend(); //this method should take the input, then display the search result
//					selectFriend(); //user will enter the userID as their selection, this should return <user> object
//						displayUser(); // this will just display all the info of the selected user
//						removefriend(); 
//						goback();
			case 2:
//				makeFriend();
//					searchbyname();
//						displayProfile();
//						selectFriend();
//							displayUser();
//							addfriend() or goback();
//					searchbyInterest();
//						displayProfile();
//							selectFriend();
//							displayUser();
//							addfriend() or goback();
//					getRecommendation();
//						displayProfile();
//							selectFriend();
//							displayUser();
//							addfriend() or goback();
//					goback();
			case 3:
				dataBase.writeToFile();
				loginStatus = false;
			}
			
		}

	}

}
