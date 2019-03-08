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

import ca.datamagic.hurricane.dto.StormKeyDTO;

/**
 * @author Greg
 *
 */
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(SearchServlet.class);
	private static final Pattern searchPattern = Pattern.compile("/(?<searchText>\\w+)", Pattern.CASE_INSENSITIVE);
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String pathInfo = request.getPathInfo();
			logger.debug("pathInfo: " + pathInfo);
			Matcher searchMatcher = searchPattern.matcher(pathInfo);
			if (searchMatcher.find()) {
				String searchText = searchMatcher.group("searchText");
				List<StormKeyDTO> stormKeys = HurricaneContextListener.getBasinDAO().search(searchText);
				String json = (new Gson()).toJson(stormKeys);
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
