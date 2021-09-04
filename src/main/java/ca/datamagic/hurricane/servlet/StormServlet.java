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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import ca.datamagic.hurricane.dto.StormDTO;

/**
 * @author Greg
 *
 */
public class StormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(StormServlet.class);
	private static final Pattern stormPattern = Pattern.compile("/(?<basin>\\w+)/(?<year>\\d+)", Pattern.CASE_INSENSITIVE);
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String pathInfo = request.getPathInfo();
			logger.debug("pathInfo: " + pathInfo);
			Matcher stormMatcher = stormPattern.matcher(pathInfo);
			if (stormMatcher.find()) {
				String basin = stormMatcher.group("basin");
				String year = stormMatcher.group("year");
				Integer intYear = Integer.parseInt(year);
				List<StormDTO> storms = HurricaneContextListener.getStormDAO().storms(basin, intYear);
				String json = (new Gson()).toJson(storms);
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
