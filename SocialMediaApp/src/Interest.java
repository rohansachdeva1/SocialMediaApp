
public class Interest {
	private int interestID;
	private String interestName;

	public Interest(String Name) {
		this.interestName = name;
		// HASH TEAM: need a hash function that takes interest name and generates an id
		this.interestID = hash(name);
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

	public void setName(int name) {
		this.interestName = name;
	}
}

