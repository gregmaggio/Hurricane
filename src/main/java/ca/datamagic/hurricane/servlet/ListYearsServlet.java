/**
 * 
 */
package ca.datamagic.hurricane.servlet;

import java.io.IOError;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

/**
 * @author Greg
 *
 */
public class ListYearsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(ListYearsServlet.class);
	private static final Pattern listYearsPattern = Pattern.compile("/(?<basin>\\w+)/years", Pattern.CASE_INSENSITIVE);
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String pathInfo = request.getPathInfo();
			logger.debug("pathInfo: " + pathInfo);
			Matcher listYearsMatcher = listYearsPattern.matcher(pathInfo);
			if (listYearsMatcher.find()) {
				String basin = listYearsMatcher.group("basin");
				List<Integer> years = HurricaneContextListener.getYearDAO().getYears(basin);
				String json = (new Gson()).toJson(years);
				response.setContentType("application/json");
				response.getWriter().println(json);
				return;
			}
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		} catch (Throwable t) {
			logger.error("Exception", t);
			throw new IOError(t);
		}
	}
}
