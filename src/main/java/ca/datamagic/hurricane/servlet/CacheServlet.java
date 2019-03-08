/**
 * 
 */
package ca.datamagic.hurricane.servlet;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

import ca.datamagic.hurricane.dto.CachedItemDTO;
import ca.datamagic.hurricane.inject.MemoryCacheInterceptor;

/**
 * @author Greg
 *
 */
public class CacheServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(CacheServlet.class);
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			List<CachedItemDTO> items = new ArrayList<CachedItemDTO>();
			Enumeration<String> keys = MemoryCacheInterceptor.getKeys();
			Gson gson = new Gson();
			if (keys != null) {
				while (keys.hasMoreElements()) {
					String key = keys.nextElement();
					Object value = MemoryCacheInterceptor.getValue(key);
					String json = null;
					try {
						json = gson.toJson(value);
					} catch (Throwable t) {
						logger.warn("Exception", t);
					}
					CachedItemDTO item = new CachedItemDTO();
					item.setKey(key);
					item.setJson(json);
					items.add(item);
				}
			}
			String json = gson.toJson(items);
			response.setContentType("application/json");
			response.getWriter().println(json);
		} catch (Throwable t) {
			logger.error("Exception", t);
			throw new IOError(t);
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			MemoryCacheInterceptor.clearCache();
		} catch (Throwable t) {
			logger.error("Exception", t);
			throw new IOError(t);
		}
	}
}
