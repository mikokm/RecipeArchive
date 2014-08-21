package fi.miko.EeppinenDrinkkiarkisto.Logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class NullAction implements Action {
	private final String url;
	private final boolean secure;

	public NullAction(String url, boolean secure) {
		this.url = url;
		this.secure = secure;
	}

	@Override
	public String execute(DataSource ds, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return url;
	}

	@Override
	public boolean secure() {
		return secure;
	}
}
