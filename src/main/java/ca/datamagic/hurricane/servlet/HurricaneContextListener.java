/**
 * 
 */
package ca.datamagic.hurricane.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Guice;
import com.google.inject.Injector;

import ca.datamagic.hurricane.dao.BaseDAO;
import ca.datamagic.hurricane.dao.BasinDAO;
import ca.datamagic.hurricane.dao.SearchDAO;
import ca.datamagic.hurricane.dao.StormDAO;
import ca.datamagic.hurricane.dao.StormTrackDAO;
import ca.datamagic.hurricane.inject.DAOModule;

/**
 * @author Greg
 *
 */
public class HurricaneContextListener implements ServletContextListener {
	private static Logger logger = LogManager.getLogger(HurricaneContextListener.class);
	private static Injector injector = null;
	private static BasinDAO basinDAO = null;
	private static StormDAO stormDAO = null;
	private static StormTrackDAO stormTrackDAO = null;
	private static SearchDAO searchDAO = null;
	
	public static BasinDAO getBasinDAO() {
		return basinDAO;
	}
	
	public static StormDAO getStormDAO() {
		return stormDAO;
	}
	
	public static StormTrackDAO getStormTrackDAO() {
		return stormTrackDAO;
	}
	
	public static SearchDAO getSearchDAO() {
		return searchDAO;
	}
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String realPath = sce.getServletContext().getRealPath("/");
		String appKeyFile = MessageFormat.format("{0}/WEB-INF/classes/bigquery.json", realPath);
		String appKey = null;
		InputStream appKeyStream = null;
		try {
			appKeyStream = new FileInputStream(appKeyFile);
			appKey = IOUtils.toString(appKeyStream, "UTF-8");
		} catch (IOException ex) {
			logger.error("IOException", ex);
		} finally {
			IOUtils.closeQuietly(appKeyStream);
		}
		BaseDAO.setAppKey(appKey);
		injector = Guice.createInjector(new DAOModule());
		basinDAO = injector.getInstance(BasinDAO.class);
		stormDAO = injector.getInstance(StormDAO.class);
		stormTrackDAO = injector.getInstance(StormTrackDAO.class);
		searchDAO = injector.getInstance(SearchDAO.class);
		logger.debug("contextInitialized");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.debug("contextDestroyed");
	}
}
