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
	private long destroyDate;
	private final long delayBeforeDestruction;

	public Session(long delayBeforeDestruction) {
		int firstPartId = rand.nextInt();
		this.id = Integer.toString(firstPartId) + nextId++;
		this.delayBeforeDestruction = delayBeforeDestruction;
		actualiseDestroyDate();
	}

	public void actualiseDestroyDate() {
		this.destroyDate = System.currentTimeMillis() + delayBeforeDestruction;
	}

	@Override
	public String getId() {
		actualiseDestroyDate();
		return id;
	}

	@Override
	public Object getValue(String key) {
		actualiseDestroyDate();
		return attributes.get(key);
	}

	@Override
	public void setValue(String key, Object value) {
		actualiseDestroyDate();
		attributes.put(key, value);
	}

	public boolean shouldBeDestroyed() {
		return System.currentTimeMillis() >= destroyDate;
	}
}
