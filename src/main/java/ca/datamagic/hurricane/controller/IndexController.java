/**
 * 
 */
package ca.datamagic.hurricane.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ca.datamagic.hurricane.dao.BaseDAO;
import ca.datamagic.hurricane.dao.BasinDAO;
import ca.datamagic.hurricane.dao.StormTrackDAO;
import ca.datamagic.hurricane.dao.YearDAO;
import ca.datamagic.hurricane.dto.BasinDTO;
import ca.datamagic.hurricane.dto.CachedItemDTO;
import ca.datamagic.hurricane.dto.StormDTO;
import ca.datamagic.hurricane.dto.StormKeyDTO;
import ca.datamagic.hurricane.dto.StormTrackDTO;
import ca.datamagic.hurricane.dto.SwaggerConfigurationDTO;
import ca.datamagic.hurricane.dto.SwaggerResourceDTO;
import ca.datamagic.hurricane.inject.DAOModule;
import ca.datamagic.hurricane.inject.MemoryCacheInterceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author Greg
 *
 */
@Controller
@RequestMapping("")
public class IndexController {
	private static Logger _logger = LogManager.getLogger(IndexController.class);
	private static Injector _injector = null;
	private static BasinDAO _basinDAO = null;
	private static YearDAO _yearDAO = null;
	private static HashMap<String, StormTrackDAO> _StormTrackDAOs = new HashMap<String, StormTrackDAO>();
	private static SwaggerConfigurationDTO _swaggerConfiguration = null;
	private static SwaggerResourceDTO[] _swaggerResources = null;
	private static String _swagger = null;
	
	static {
		FileInputStream swaggerStream = null;
		try {
			DefaultResourceLoader loader = new DefaultResourceLoader();       
		    Resource dataResource = loader.getResource("classpath:data");
		    Resource metaInfResource = loader.getResource("META-INF");
		    String dataPath = dataResource.getFile().getAbsolutePath();
		    String metaInfPath = metaInfResource.getFile().getAbsolutePath();
		    _logger.debug("dataPath: " + dataPath);
		    _logger.debug("metaInfPath: " + metaInfPath);
		    
		    String swaggerFileName = MessageFormat.format("{0}/swagger.json", metaInfPath);
		    swaggerStream = new FileInputStream(swaggerFileName);
		    _swagger = IOUtils.toString(swaggerStream, "UTF-8");
		    
		    BaseDAO.setDataPath(dataPath);
			_injector = Guice.createInjector(new DAOModule());
			_basinDAO = _injector.getInstance(BasinDAO.class);
			_yearDAO = _injector.getInstance(YearDAO.class);
			_swaggerConfiguration = new SwaggerConfigurationDTO();
			_swaggerResources = new SwaggerResourceDTO[] { new SwaggerResourceDTO() };
		} catch (Throwable t) {
			_logger.error("Exception", t);
		}
		if (swaggerStream != null) {
			IOUtils.closeQuietly(swaggerStream);
		}
	}
	
	public IndexController() {
	}

	private static synchronized StormTrackDAO getDAO(String basin, Integer year) {
		String key = MessageFormat.format("{0}-{1}", basin.toUpperCase(), Integer.toString(year.intValue()));		
		if (!_StormTrackDAOs.containsKey(key)) {
			StormTrackDAO dao  = _injector.getInstance(StormTrackDAO.class);
			dao.setBasin(basin);
			dao.setYear(year);
			_StormTrackDAOs.put(key, dao);
		}
		return _StormTrackDAOs.get(key);
	}
	
	@RequestMapping(value="/api/basins", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
    public List<BasinDTO> basins(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		try {
			_logger.debug("basins");
			return _basinDAO.getBasins();
		} catch (Throwable t) {
			_logger.error("Exception", t);
			throw new Exception(t);
		}
    }

	@RequestMapping(value="/api/basin/{basin}/years", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Integer> years(Model model, HttpServletRequest request, HttpServletResponse response, @PathVariable String basin) throws Exception {		
		try {
			return _yearDAO.getYears(basin);
		} catch (Throwable t) {
			_logger.error("Exception", t);
			throw new Exception(t);
		}
	}
	
	@RequestMapping(value="/api/search/{searchText}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
    public List<StormKeyDTO> search(Model model, HttpServletRequest request, HttpServletResponse response, @PathVariable String searchText) throws Exception {		
		try {
			_logger.debug("search");
			return _basinDAO.search(searchText);
		} catch (Throwable t) {
			_logger.error("Exception", t);
			throw new Exception(t);
		}
    }
	
	@RequestMapping(value="/api/storm/{basin}/{year}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<StormDTO> storms(Model model, HttpServletRequest request, HttpServletResponse response, @PathVariable String basin, @PathVariable Integer year) throws Exception {
		try {
			StormTrackDAO dao = getDAO(basin, year);
			return dao.getStorms();
		} catch (Throwable t) {
			_logger.error("Exception", t);
			throw new Exception(t);
		}
	}
	
	@RequestMapping(value="/api/storm/{basin}/{year}/{stormNo}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<StormTrackDTO> stormTracksByStormNo(Model model, HttpServletRequest request, HttpServletResponse response, @PathVariable String basin, @PathVariable Integer year, @PathVariable Integer stormNo) throws Exception {
		try {
			StormTrackDAO dao = getDAO(basin, year);
			return dao.getStormTracks(stormNo);
		} catch (Throwable t) {
			_logger.error("Exception", t);
			throw new Exception(t);
		}
	}
	
	@RequestMapping(value="/api/storm/{basin}/{startYear}/{startMonth}/{startDay}/{endYear}/{endMonth}/{endDay}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<StormTrackDTO> stormTracks(Model model, HttpServletRequest request, HttpServletResponse response, @PathVariable String basin, @PathVariable Integer startYear, @PathVariable Integer startMonth, @PathVariable Integer startDay, @PathVariable Integer endYear, @PathVariable Integer endMonth, @PathVariable Integer endDay) throws Exception {
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
	
	@RequestMapping(method=RequestMethod.GET, value="/api/cache", produces="application/json")
	@ResponseBody
	public List<CachedItemDTO> getCachedItems() throws JsonProcessingException {
		List<CachedItemDTO> items = new ArrayList<CachedItemDTO>();
		ObjectMapper mapper = new ObjectMapper();
		Enumeration<String> keys = MemoryCacheInterceptor.getKeys();
		if (keys != null) {
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				Object value = MemoryCacheInterceptor.getValue(key);
				String json = mapper.writeValueAsString(value);
				CachedItemDTO dto = new CachedItemDTO();
				dto.setKey(key);
				dto.setJson(json);
				items.add(dto);
			}
		}
		return items;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/cache")
	public void clearCachedItems() {
		MemoryCacheInterceptor.clearCache();
	}
	
	@RequestMapping(value="/swagger-resources/configuration/ui", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public SwaggerConfigurationDTO getSwaggerConfigurationUI() {
		return _swaggerConfiguration;
	}
	
	@RequestMapping(value="/swagger-resources", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public SwaggerResourceDTO[] getSwaggerResources() {
		return _swaggerResources;
	}
	
	@RequestMapping(value="/v2/api-docs", method=RequestMethod.GET, produces="application/json")
	public void getSwagger(Writer responseWriter) throws IOException {
		responseWriter.write(_swagger);
	}
}
