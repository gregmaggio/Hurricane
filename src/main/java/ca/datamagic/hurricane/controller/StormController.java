/**
 * 
 */
package ca.datamagic.hurricane.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import ca.datamagic.hurricane.dao.StormTrackDAO;
import ca.datamagic.hurricane.dto.StormDTO;
import ca.datamagic.hurricane.dto.StormTrackDTO;

/**
 * @author Greg
 *
 */
@Api(value="storm", description="Storm operations")
@Controller
@RequestMapping("/storm")
public class StormController extends BaseController {
	private static Logger _logger = LogManager.getLogger(StormController.class);
	private static HashMap<String, StormTrackDAO> _daos = new HashMap<String, StormTrackDAO>();
	
	public StormController() {
	}
	
	private static synchronized StormTrackDAO getDAO(String basin, Integer year) {
		String key = MessageFormat.format("{0}-{1}", basin.toUpperCase(), Integer.toString(year.intValue()));		
		if (!_daos.containsKey(key)) {
			StormTrackDAO dao  = getInjector().getInstance(StormTrackDAO.class);
			dao.setBasin(basin);
			dao.setYear(year);
			_daos.put(key, dao);
		}
		return _daos.get(key);
	}
	
	@ApiOperation(value = "List the storms by basin and year")
	@RequestMapping(value="/{basin}/{year}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<StormDTO> storms(@ApiIgnore Model model, @ApiIgnore HttpServletRequest request, @ApiIgnore HttpServletResponse response, @ApiParam(name="basin", value="The basin", required=true) @PathVariable String basin, @ApiParam(name="year", value="The year", required=true) @PathVariable Integer year) throws Exception {
		try {
			StormTrackDAO dao = getDAO(basin, year);
			return dao.getStorms();
		} catch (Throwable t) {
			_logger.error("Exception", t);
			throw new Exception(t);
		}
	}
	
	@ApiOperation(value = "List the storm tracks for a storm")
	@RequestMapping(value="/{basin}/{year}/{stormNo}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<StormTrackDTO> stormTracksByStormNo(@ApiIgnore Model model, @ApiIgnore HttpServletRequest request, @ApiIgnore HttpServletResponse response, @ApiParam(name="basin", value="The basin", required=true) @PathVariable String basin, @ApiParam(name="year", value="The year", required=true) @PathVariable Integer year, @ApiParam(name="stormNo", value="The storm number", required=true) @PathVariable Integer stormNo) throws Exception {
		try {
			StormTrackDAO dao = getDAO(basin, year);
			return dao.getStormTracks(stormNo);
		} catch (Throwable t) {
			_logger.error("Exception", t);
			throw new Exception(t);
		}
	}
	
	@ApiOperation(value = "List the storm tracks for a basin within a date range")
	@RequestMapping(value="/{basin}/{startYear}/{startMonth}/{startDay}/{endYear}/{endMonth}/{endDay}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<StormTrackDTO> stormTracks(@ApiIgnore Model model, @ApiIgnore HttpServletRequest request, @ApiIgnore HttpServletResponse response, @ApiParam(name="basin", value="The basin", required=true) @PathVariable String basin, @ApiParam(name="startYear", value="The start year", required=true) @PathVariable Integer startYear, @ApiParam(name="startMonth", value="The start month", required=true) @PathVariable Integer startMonth, @ApiParam(name="startDay", value="The start day", required=true) @PathVariable Integer startDay, @ApiParam(name="endYear", value="The end year", required=true) @PathVariable Integer endYear, @ApiParam(name="endMonth", value="The end month", required=true) @PathVariable Integer endMonth, @ApiParam(name="endDay", value="The end day", required=true) @PathVariable Integer endDay) throws Exception {
		try {
			List<StormTrackDTO> stormTracks = new ArrayList<StormTrackDTO>();
			for (int jj = startYear; jj <= endYear; jj++) {
				StormTrackDAO dao = getDAO(basin, jj);
				List<StormTrackDTO> currStormTracks = null;
				if ((jj == startYear) && (jj == endYear)) {
					currStormTracks = dao.getStormTracks(jj, startMonth, startDay, jj, endMonth, endDay);
				} else if (jj == startYear) {
					currStormTracks = dao.getStormTracks(jj, startMonth, startDay, jj, 12, 31);
				} else if (jj == endYear) {
					currStormTracks = dao.getStormTracks(jj, 1, 1, jj, endMonth, endDay);
				} else {
					currStormTracks = dao.getStormTracks(jj, 1, 1, jj, 12, 13);
				}
				if ((currStormTracks != null) && (currStormTracks.size() > 0)) {
					stormTracks.addAll(currStormTracks);
				}
			}
			return stormTracks;
		} catch (Throwable t) {
			_logger.error("Exception", t);
			throw new Exception(t);
		}
	}
}
