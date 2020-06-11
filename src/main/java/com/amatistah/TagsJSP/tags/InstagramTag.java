package com.amatistah.TagsJSP.tags;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.amatistah.TagsJSP.tags.supes.TagManager;

public class InstagramTag extends TagManager implements Job {

	/**
	 * 
	 */
	private static final long serialVersionUID = 221783522671782231L;

	private static final String url = "https://www.instagram.com/ama.tistah/?__a=1";
	private static final Pattern pu = Pattern.compile("\"shortcode\":\"([^\"]+)");
	private static final Pattern pc = Pattern.compile("\\\\u003cblockquote(.*)blockquote\\\\u003e");

	private static final long secondsToAdd = 60;
	private static Instant cacheTime = Instant.MIN;
	private static String cache;
	private static HashMap<String, String> cachePost = new HashMap<>();

	private static final double threadSleepSeconds = (secondsToAdd * 1000) / 0.55;
	{
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Instant now = Instant.now();
						if (now.isAfter(cacheTime)) {
							String[] instagramPosts = getInstagramPosts();
							String code = "<div class=\"instagramParent\">";
							for (String post : instagramPosts) {
								code += "<div class=\"instagram\">" + post + "</div>";
							}
							code += "</div>";

							cacheTime = now.plusSeconds(secondsToAdd);
							cache = code;

							Thread.sleep((long) (threadSleepSeconds));
							System.out.println("running thread");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		t.start();
	}

	public String appendOut(){
		return getCodeOfPosts();
	}

	protected static String getCodeOfPosts() {
		Instant now = Instant.now();
		if (now.isBefore(cacheTime)) {
			System.out.println("Getting instragram from cache");
			return cache;
		}

		String[] instagramPosts = getInstagramPosts();
		String code = "<div class=\"instagramParent\">";
		for (String post : instagramPosts) {
			if(cachePost.containsKey(post)) {
				code += cachePost.get(post);
			}else {
				String postCode = "<div class=\"instagram\">" + post + "</div>";
				cachePost.put(post, postCode);
				code += postCode;
			}
		}
		code += "</div>";

		cacheTime = now.plusSeconds(secondsToAdd);
		return cache = code;
	}

	private static String[] getInstagramPosts() {
		ArrayList<String> arrayList = new ArrayList<>();
		try {
			String code = getStringFromURL(url);
			Matcher m = pu.matcher(code);
			boolean omitscript = false;
			while (m.find()) {
				String idPost = m.group(1);
				String urlPost = "https://api.instagram.com/oembed/?url=https://www.instagram.com/p/" + idPost
						+ "&maxwidth=321&omitscript=" + omitscript;
				String postCode = getStringFromURL(urlPost);
				Matcher m2 = pc.matcher(postCode);
				if (m2.find())
					postCode = m2.group(0);
				postCode = unicodeToText(postCode);
				arrayList.add(postCode);

				if (!omitscript)
					omitscript = true;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return arrayList.toArray(new String[arrayList.size()]);
	}

	private static String getStringFromURL(String url2)
			throws IOException, MalformedURLException, UnsupportedEncodingException {
		HttpURLConnection connection;
		connection = (HttpURLConnection) new URL(url2).openConnection();
		InputStream inputStream = connection.getInputStream();
		InputStreamReader inputReader = new InputStreamReader(inputStream, "UTF-8");
		BufferedReader lector = new BufferedReader(inputReader);
		String temp;
		String code = "";
		while ((temp = lector.readLine()) != null)
			code += temp;

		return code;
	}

	private static String unicodeToText(String code) {
		code = code.replace("\\u003c", "<").replace("\\u003e", ">").replace("\\\"", "\"").replace("\\u0026", "&")
				.replace("\\u00a0", " ").replace("\\u00e1", "�").replace("\\u00e9", "�").replace("\\u00ed", "�")
				.replace("\\u00f3", "�").replace("\\ud83e", "&#55358;");
		return code;
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		InstagramTag.getCodeOfPosts();
	}

}