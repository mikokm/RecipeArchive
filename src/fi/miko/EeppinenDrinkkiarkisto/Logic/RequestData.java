package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

public class RequestData {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private DataSource dataSource;

	private String defaultPage;
	private String errorPage;
	private String indexPage;
	private String jspPath;

	public RequestData(HttpServletRequest request, HttpServletResponse response, DataSource dataSource, String jspPath) {
		super();
		this.request = request;
		this.response = response;
		this.dataSource = dataSource;
		this.jspPath = jspPath;
	}

	public void dispatch(String url) throws ServletException, IOException {
		request.getRequestDispatcher(getJspPath() + url).forward(request, response);
	}

	public Object getAttribute(String attribute) {
		return request.getAttribute(attribute);
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public String getDefaultPage() {
		return defaultPage;
	}

	public String getErrorPage() {
		return errorPage;
	}

	public String getIndexPage() {
		return indexPage;
	}

	public String getJspPath() {
		return jspPath;
	}

	public String getParameter(String parameter) {
		return request.getParameter(parameter);
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public HttpSession getSession() {
		return request.getSession();
	}

	public void redirect(String url) throws IOException {
		response.sendRedirect(response.encodeRedirectURL(url));
	}

	public void setAttribute(String attribute, Object object) {
		request.setAttribute(attribute, object);
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setDefaultPage(String defaultPage) {
		this.defaultPage = defaultPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	public void setIndexPage(String indexPage) {
		this.indexPage = indexPage;
	}

	public void setJspPath(String jspPath) {
		this.jspPath = jspPath;
	}

	public void setPageError(String error) {
		request.setAttribute("pageError", error);
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
}
