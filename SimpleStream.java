

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import twitter4j.HashtagEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class SimpleStream {
	static int i = 1;

	/*
	 * static Date previousDate; static Date currentDate; static Calendar
	 * calendar1; static Calendar calendar2;
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String keyword = sc.next();
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey("dasz5CfZJTlP6xTg4SYS5PbZU");
		cb.setOAuthConsumerSecret("GxRetxDJiCibMKj4yMV5WFgbn5mD7exsSelaAJ3zdakLcvVOgZ");
		cb.setOAuthAccessToken("1330557589-GT7Yv7OTcff4q1yiMzeMPO6pycvTRXrcUz7hSw1");
		cb.setOAuthAccessTokenSecret("UJynhSNNLuqxMIQKzwwEv5wg805xwvJ3zFPYHASI75jz7");
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		Query query = new Query(keyword);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				try {
					Date currentDate = new Date(System.currentTimeMillis());
					Date previousDate = new Date(System.currentTimeMillis() - i * 60000);
					Calendar calendar1 = Calendar.getInstance();
					calendar1.setTime(currentDate);
					Calendar calendar2 = Calendar.getInstance();
					calendar2.setTime(previousDate);
					calendar2.add(Calendar.DATE, 1);
					QueryResult result;
					result = twitter.search(query);
					for (Status status : result.getTweets()) {
						Calendar calendar3 = Calendar.getInstance();
						calendar3.setTime(status.getCreatedAt());
						calendar3.add(Calendar.DATE, 1);
						Date x = calendar3.getTime();
						if (x.before(calendar1.getTime()) || x.after(calendar2.getTime())) {
							System.out.println("Username:" + status.getUser().getScreenName() + " Counts:"
									+ status.getUser().getListedCount() + " ");

							for (HashtagEntity urle : status.getHashtagEntities()) {
								System.out.println(urle.getText());
							}
						}
					}
					i++;
					if (i == 5) {
						System.exit(0);
					}
				} catch (TwitterException e) {
					e.printStackTrace();
				}

			}
		};

		Timer timer = new Timer();
		long delay = 0;
		long intevalPeriod = 60000;
		timer.scheduleAtFixedRate(task, delay, intevalPeriod);
	}
}
