/**
 * 
 */
package ca.datamagic.hurricane.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import com.google.inject.Guice;
import com.google.inject.Injector;

import ca.datamagic.hurricane.dao.BaseDAO;
import ca.datamagic.hurricane.inject.DAOModule;

/**
 * @author Greg
 *
 */
public abstract class BaseController {
	private static Logger _logger = LogManager.getLogger(BaseController.class);
	private static Injector _injector = null;
	
	static {
		try {
			DefaultResourceLoader loader = new DefaultResourceLoader();       
		    Resource resource = loader.getResource("classpath:META-INF/data");
		    String dataPath = resource.getFile().getAbsolutePath();
		    _logger.debug("dataPath: " + dataPath);
		    BaseDAO.setDataPath(dataPath);
		    _injector = Guice.createInjector(new DAOModule());
		} catch (Throwable t) {
			_logger.error("Exception", t);
		}
	}

	protected static Injector getInjector() {
		return _injector;
	}
}
