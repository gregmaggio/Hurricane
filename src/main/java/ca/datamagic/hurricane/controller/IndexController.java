/**
 * 
 */
package ca.datamagic.hurricane.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ca.datamagic.hurricane.dto.CachedItemDTO;
import ca.datamagic.hurricane.inject.MemoryCacheInterceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author Greg
 *
 */
@Api(value="index", description="Index operations")
@Controller
@RequestMapping("")
public class IndexController {
	
	public IndexController() {
	}

	@ApiOperation(value = "Returned the cached items")
	@RequestMapping(method=RequestMethod.GET, value="/cache", produces="application/json")
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
	
	@ApiOperation(value = "Clear the cache")
	@RequestMapping(method=RequestMethod.DELETE, value="/cache")
	public void clearCachedItems() {
		MemoryCacheInterceptor.clearCache();
	}
	
	@ApiIgnore
	@RequestMapping(method=RequestMethod.GET, value="")
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "index";
	}
}
