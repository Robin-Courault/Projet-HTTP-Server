package httpserver.itf.impl;

import httpserver.itf.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Session implements HttpSession {
	private static Integer nextId = 0;
	private static Random rand = new Random();
	private final String id;
	private final Map<String, Object> attributes = new HashMap<String, Object>();

	public Session() {
		int firstPartId = rand.nextInt();
		this.id = Integer.toString(firstPartId) + nextId++;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Object getValue(String key) {
		return attributes.get(key);
	}

	@Override
	public void setValue(String key, Object value) {
		attributes.put(key, value);
	}
}
