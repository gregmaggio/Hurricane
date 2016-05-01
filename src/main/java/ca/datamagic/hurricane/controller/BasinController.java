/**
 * 
 */
package ca.datamagic.hurricane.controller;

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

import ca.datamagic.hurricane.dao.BasinDAO;
import ca.datamagic.hurricane.dao.YearDAO;
import ca.datamagic.hurricane.dto.BasinDTO;

/**
 * @author Greg
 *
 */
@Api(value="basin", description="Basin operations")
@Controller
@RequestMapping("/basin")
public class BasinController extends BaseController {
	private static Logger _logger = LogManager.getLogger(BasinController.class);
	private BasinDAO _basinDAO = null;
	private YearDAO _yearDAO = null;
	
	public BasinController() {
		_basinDAO = getInjector().getInstance(BasinDAO.class);
		_yearDAO = getInjector().getInstance(YearDAO.class);
	}

	@ApiOperation(value = "List the basins")
	@RequestMapping(value="", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
    public List<BasinDTO> basins(@ApiIgnore Model model, @ApiIgnore HttpServletRequest request, @ApiIgnore HttpServletResponse response) throws Exception {		
		try {
			_logger.debug("basins");
			return _basinDAO.getBasins();
		} catch (Throwable t) {
			_logger.error("Exception", t);
			throw new Exception(t);
		}
    }

	@ApiOperation(value = "List the years for a basin")
	@RequestMapping(value="{basin}/years", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Integer> years(@ApiIgnore Model model, @ApiIgnore HttpServletRequest request, @ApiIgnore HttpServletResponse response, @ApiParam(name="basin", value="The basin", required=true) @PathVariable String basin) throws Exception {		
		try {
			return _yearDAO.getYears(basin);
		} catch (Throwable t) {
			_logger.error("Exception", t);
			throw new Exception(t);
		}
	}
}
