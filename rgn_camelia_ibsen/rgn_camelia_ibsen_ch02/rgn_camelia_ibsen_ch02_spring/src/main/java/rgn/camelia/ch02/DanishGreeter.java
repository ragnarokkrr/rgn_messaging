package rgn.camelia.ch02;

public class DanishGreeter implements Greeter{

	public String sayHello() {
		return "Davs " + System.getProperty("user.name");
	}

}
