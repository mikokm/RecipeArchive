package fi.miko.EeppinenDrinkkiarkisto.Logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public interface Action {
	public String execute(DataSource ds, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public boolean secure();
}
