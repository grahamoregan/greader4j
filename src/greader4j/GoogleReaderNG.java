package greader4j;

import static java.lang.String.format;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This borrows heavily from <a href=
 * "http://www.chrisdadswell.co.uk/android-coding-example-authenticating-clientlogin-google-reader-api/"
 * >Chris Dadswell's blog post</a>
 */
public class GoogleReaderNG {

	private static final String BASE_URL = "https://www.google.com/reader/%s";

	private static final String API_URL = format(BASE_URL, "api/0/%s");

	private final String userAgent;

	public GoogleReaderNG() {
		this("GReader4j Google Reader Client");
	}

	public GoogleReaderNG(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getToken(Account account) {

		Document doc = get(format(API_URL, "token"), account);

		String token = doc.body().text();

		return token;

	}

	public String getAuthKey(Account account) {

		Document doc = post("https://www.google.com/accounts/ClientLogin", account);

		String auth = doc.body().text().substring(doc.body().text().indexOf("Auth=") + 5, doc.body().text().length());

		return auth;

	}

	public String getUserInfo(Account account) {

		Document doc = get(format(API_URL, "user-info"), account);

		String userInfo = doc.body().text();

		return userInfo;
	}

	public String getUserID(Account account) {
		String userInfo = getUserInfo(account);
		String userId = (String) userInfo.subSequence(11, 31);
		return userId;
	}

	public List<String> getTagList(Account account) {

		List<String> list = new ArrayList<String>();

		String label = format("user/%s/label/", getUserID(account));

		Document doc = get(format(API_URL, "tag/list"), account);

		Elements links = doc.select("string");

		for (Element link : links) {

			String tagText = link.text();

			if (tagText.indexOf(label) > -1)
				list.add(tagText.substring(32));

		}

		return list;
	}

	public List<Link> getSubscriptions(Account account) {

		List<Link> list = new ArrayList<Link>();

		Document doc = get(format(API_URL, "subscription/list"), account);

		Elements links = doc.select("object list object");

		for (Element link : links) {

			String title = null;

			try {
				title = link.select("string[name=title]").first().text();
			} catch (NullPointerException e) {
				// swallow NPE, occurs from JSoup matching
			}

			// if we didn't get a title then we won't have a URL
			if (title != null) {

				String id = null;

				try {

					id = link.select("string[name=id]").first().text();

					id = id.substring("feed/".length(), id.length());

				} catch (NullPointerException e) {
					e.printStackTrace();
				}

				list.add(Link.create(title, id));

			}

		}

		return list;

	}

	public List<Link> getStarred(Account account) throws UnsupportedEncodingException, IOException {

		List<Link> list = new ArrayList<Link>();

		Document doc = get(format(BASE_URL, "atom/user/-/state/com.google/starred"), account);

		Elements entries = doc.select("entry");

		for (Element entry : entries) {

			String title = entry.getElementsByTag("title").first().text();
			String url = entry.getElementsByTag("link").first().text();

			Link link = Link.create(title, url);

			list.add(link);

		}

		return list;

	}

	private Document get(String url, Account account) {

		try {

			Connection conn = Jsoup.connect(url);
			conn.userAgent(userAgent);
			conn.timeout(5000);

			conn.header("Authorization", "GoogleLogin auth=" + getAuthKey(account));

			return conn.get();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Document post(String url, Account account) {

		try {

			Connection conn = Jsoup.connect(url);

			conn.data("accountType", "GOOGLE");
			conn.data("service", "reader");
			conn.data("source", userAgent);
			conn.userAgent(userAgent);
			conn.timeout(4000);

			conn.data("Email", account.getUsername());
			conn.data("Passwd", account.getPassword());

			return conn.post();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
