package fi.miko.EeppinenDrinkkiarkisto.Logic;

public interface Action {
	public String execute(RequestData rd) throws Exception;

	public boolean secure();
}
