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

import ca.datamagic.hurricane.dto.StormTrackDTO;

/**
 * @author Greg
 *
 */
public class StormTrackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(StormServlet.class);
	private static final Pattern stormTracksPattern = Pattern.compile("/(?<basin>\\w+)/(?<year>\\d+)/(?<stormNo>\\d+)", Pattern.CASE_INSENSITIVE);
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String pathInfo = request.getPathInfo();
			logger.debug("pathInfo: " + pathInfo);			
			Matcher stormTracksMatcher = stormTracksPattern.matcher(pathInfo);
			if (stormTracksMatcher.find()) {
				logger.debug("stormTracksMatcher");
				String basin = stormTracksMatcher.group("basin");
				String year = stormTracksMatcher.group("year");
				String stormNo = stormTracksMatcher.group("stormNo");
				logger.debug("basin: " + basin);
				logger.debug("year: " + year);
				logger.debug("stormNo: " + stormNo);
				Integer intYear = Integer.parseInt(year);
				Integer intStormNo = Integer.parseInt(stormNo);
				List<StormTrackDTO> stormTracks = HurricaneContextListener.getStormTrackDAO().tracks(basin, intYear, intStormNo);
				String json = (new Gson()).toJson(stormTracks);
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
