package rgn.camelia.ch02;

public class EnglishGreeter implements Greeter{

	public String sayHello() {
		return "Hello " + System.getProperty("user.name");
	}

}
