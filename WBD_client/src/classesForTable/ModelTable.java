package classesForTable;

public class ModelTable {
	String id,name,email,prog;

	public ModelTable(String[] strArray) {
		this.id = strArray[0];
		this.name = strArray[1];
		this.email = strArray[2];
		this.prog = strArray[3];
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProg() {
		return prog;
	}

	public void setProg(String prog) {
		this.prog = prog;
	}
	 
	
}
