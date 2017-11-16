package com;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import twitter4j.FilterQuery;
import twitter4j.HashtagEntity;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class LiveStream {
	static int i = 1;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		String keyword = sc.next();
		ArrayList<Status> arrayList=new ArrayList<Status>();
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey("dasz5CfZJTlP6xTg4SYS5PbZU");
		cb.setOAuthConsumerSecret("GxRetxDJiCibMKj4yMV5WFgbn5mD7exsSelaAJ3zdakLcvVOgZ");
		cb.setOAuthAccessToken("1330557589-GT7Yv7OTcff4q1yiMzeMPO6pycvTRXrcUz7hSw1");
		cb.setOAuthAccessTokenSecret("UJynhSNNLuqxMIQKzwwEv5wg805xwvJ3zFPYHASI75jz7");
		TwitterStreamFactory tf = new TwitterStreamFactory(cb.build());
		StatusListener listener = new StatusListener() {

			@Override
			public void onException(Exception arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTrackLimitationNotice(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStatus(Status status) {
				arrayList.add(status);
			}

			@Override
			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice arg0) {
				// TODO Auto-generated method stub

			}
		};

		TwitterStream twitterStream = tf.getInstance();
		twitterStream.addListener(listener);
		FilterQuery filtre = new FilterQuery();
		String[] keywordsArray = { keyword }; // filter based on your
												// choice of keywords
		filtre.track(keywordsArray);
		twitterStream.filter(filtre);
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				for (Status status : arrayList) {
					Date currentDate = new Date(System.currentTimeMillis());
					Date previousDate = new Date(System.currentTimeMillis() - i * 60000);
					Calendar calendar1 = Calendar.getInstance();
					calendar1.setTime(currentDate);
					Calendar calendar2 = Calendar.getInstance();
					calendar2.setTime(previousDate);
					calendar2.add(Calendar.DATE, 1);
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

			}
		};

		Timer timer = new Timer();
		long delay = 60000;
		long intevalPeriod = 60000;
		timer.scheduleAtFixedRate(task, delay, intevalPeriod);

	}

}
