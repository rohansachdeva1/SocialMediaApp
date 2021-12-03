
public class Interest implements Comparable<Interest>{
	private int interestID;
	private String interestName;

	public Interest(String name, int id) {
		this.interestName = name;
		// HASH TEAM: need a hash function that takes interest name and generates an id
		this.interestID = id;
	}

	public int getId() {
		return interestID;
	}

	public void setId(int id) {
		this.interestID = id;
	}

	public String getName() {
		return interestName;
	}

	public void setName(String name) {
		this.interestName = name;
	}

	@Override
	public int hashCode() {
		String key = getName() + getId();
		int sum = 0;
		for (int i = 0; i < key.length(); i++) {
			sum += (int) key.charAt(i);
		}
		return sum;
	}

	@Override
	public int compareTo(Interest o) {
		// TODO Auto-generated method stub
		return 0;
	}
}

