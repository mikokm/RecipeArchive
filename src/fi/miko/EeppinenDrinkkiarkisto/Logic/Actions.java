package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class Actions {
	private final Map<String, Action> actions = new HashMap<String, Action>();

	public Actions() {
	}

	public void add(String name, Action action) {
		actions.put(name, action);
	}

	public Action get(HttpServletRequest request) {
		return actions.get(request.getMethod() + request.getPathInfo());
	}
}
