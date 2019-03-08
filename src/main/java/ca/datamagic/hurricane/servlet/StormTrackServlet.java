/**
 * 
 */
package ca.datamagic.hurricane.servlet;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

import ca.datamagic.hurricane.dao.StormTrackDAO;
import ca.datamagic.hurricane.dto.StormTrackDTO;

/**
 * @author Greg
 *
 */
public class StormTrackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(StormServlet.class);
	private static final Pattern stormTracksPattern = Pattern.compile("/(?<basin>\\w+)/(?<year>\\d+)/(?<stormNo>\\d+)", Pattern.CASE_INSENSITIVE);
	private static final Pattern stormTracksRangePattern = Pattern.compile("/(?<basin>\\w+)/(?<startYear>\\d+)/(?<startMonth>\\d+)/(?<startDay>\\d+)/(?<endYear>\\d+)/(?<endMonth>\\d+)/(?<endDay>\\d+)", Pattern.CASE_INSENSITIVE);
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String pathInfo = request.getPathInfo();
			logger.debug("pathInfo: " + pathInfo);
			Matcher stormTracksRangeMatcher = stormTracksRangePattern.matcher(pathInfo);
			if (stormTracksRangeMatcher.find()) {
				logger.debug("stormTracksRangeMatcher");
				String basin = stormTracksRangeMatcher.group("basin");				
				String startYear = stormTracksRangeMatcher.group("startYear");
				String startMonth = stormTracksRangeMatcher.group("startMonth");
				String startDay = stormTracksRangeMatcher.group("startDay");
				String endYear = stormTracksRangeMatcher.group("endYear");
				String endMonth = stormTracksRangeMatcher.group("endMonth");
				String endDay = stormTracksRangeMatcher.group("endDay");
				logger.debug("basin: " + basin);
				logger.debug("startYear: " + startYear);
				logger.debug("startMonth: " + startMonth);
				logger.debug("startDay: " + startDay);
				logger.debug("endYear: " + endYear);
				logger.debug("endMonth: " + endMonth);
				logger.debug("endDay: " + endDay);
				Integer intStartYear = Integer.parseInt(startYear);
				Integer intStartMonth = Integer.parseInt(startMonth);
				Integer intStartDay = Integer.parseInt(startDay);
				Integer intEndYear = Integer.parseInt(endYear);
				Integer intEndMonth = Integer.parseInt(endMonth);
				Integer intEndDay = Integer.parseInt(endDay);
				List<StormTrackDTO> stormTracks = new ArrayList<StormTrackDTO>();
				for (int jj = intStartYear; jj <= intEndYear; jj++) {
					StormTrackDAO dao = HurricaneContextListener.getStormTrackDAO(basin, jj);
					List<StormTrackDTO> currStormTracks = null;
					if ((jj == intStartYear) && (jj == intEndYear)) {
						currStormTracks = dao.getStormTracks(jj, intStartMonth, intStartDay, jj, intEndMonth, intEndDay);
					} else if (jj == intStartYear) {
						currStormTracks = dao.getStormTracks(jj, intStartMonth, intStartDay, jj, 12, 31);
					} else if (jj == intEndYear) {
						currStormTracks = dao.getStormTracks(jj, 1, 1, jj, intEndMonth, intEndDay);
					} else {
						currStormTracks = dao.getStormTracks(jj, 1, 1, jj, 12, 13);
					}
					if ((currStormTracks != null) && (currStormTracks.size() > 0)) {
						stormTracks.addAll(currStormTracks);
					}
				}
				String json = (new Gson()).toJson(stormTracks);
				response.setContentType("application/json");
				response.getWriter().println(json);
				return;
			}
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
				List<StormTrackDTO> stormTracks = HurricaneContextListener.getStormTrackDAO(basin, intYear).getStormTracks(intStormNo);
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
