package greader4j;

import java.util.Date;

public class Link {

	private String title;

	private String description;

	private String url;

	private Date firstSeen;

	public static Link create(String title, String url) {

		Link link = new Link();

		link.title = title;
		link.url = url;

		if (link.firstSeen == null)
			link.firstSeen = new Date();

		return link;

	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getUrl() {
		return url;
	}

	public Date getFirstSeen() {
		return firstSeen;
	}

}
