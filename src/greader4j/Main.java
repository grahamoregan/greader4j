package greader4j;

import static java.lang.String.format;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {

	public static void main(String[] args) throws IOException {

		Properties props = new Properties();

		props.load(new FileReader(new File(System.getProperty("user.home"), ".greader4j")));

		String username = props.getProperty("username");
		String password = props.getProperty("password");

		Account ctx = Account.create(username, password);

		GoogleReaderNG reader = new GoogleReaderNG();

		System.out.println("Favourites");
		for (Link link : reader.getStarred(ctx))
			System.out.println(format("\t%s", link.getTitle()));

		System.out.println("Subscriptions");
		for (Link link : reader.getSubscriptions(ctx))
			System.out.println(format("\t%s", link.getTitle()));

	}

}
