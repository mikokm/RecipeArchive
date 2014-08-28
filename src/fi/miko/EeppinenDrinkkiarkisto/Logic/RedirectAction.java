package fi.miko.EeppinenDrinkkiarkisto.Logic;

public class RedirectAction implements Action {
	private final String url;
	private final boolean secure;

	public RedirectAction(String url, boolean secure) {
		this.url = url;
		this.secure = secure;
	}

	@Override
	public String execute(RequestData rd) throws Exception {
		return url;
	}

	@Override
	public boolean secure() {
		return secure;
	}
}
