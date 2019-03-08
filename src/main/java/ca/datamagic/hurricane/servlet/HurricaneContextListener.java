/**
 * 
 */
package ca.datamagic.hurricane.servlet;

import java.text.MessageFormat;
import java.util.Hashtable;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.google.inject.Guice;
import com.google.inject.Injector;

import ca.datamagic.hurricane.dao.BaseDAO;
import ca.datamagic.hurricane.dao.BasinDAO;
import ca.datamagic.hurricane.dao.StormTrackDAO;
import ca.datamagic.hurricane.dao.YearDAO;
import ca.datamagic.hurricane.inject.DAOModule;

/**
 * @author Greg
 *
 */
public class HurricaneContextListener implements ServletContextListener {
	private static Logger logger = LogManager.getLogger(HurricaneContextListener.class);
	private static Injector injector = null;
	private static BasinDAO basinDAO = null;
	private static YearDAO yearDAO = null;
	private static Hashtable<String, StormTrackDAO> stormTrackDAOs = new Hashtable<String, StormTrackDAO>();
	
	public static BasinDAO getBasinDAO() {
		return basinDAO;
	}
	
	public static YearDAO getYearDAO() {
		return yearDAO;
	}
	
	public static synchronized StormTrackDAO getStormTrackDAO(String basin, Integer year) {
		String key = MessageFormat.format("{0}-{1}", basin.toUpperCase(), Integer.toString(year.intValue()));		
		if (!stormTrackDAOs.containsKey(key)) {
			StormTrackDAO dao  = injector.getInstance(StormTrackDAO.class);
			dao.setBasin(basin);
			dao.setYear(year);
			stormTrackDAOs.put(key, dao);
		}
		return stormTrackDAOs.get(key);
	}
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String realPath = sce.getServletContext().getRealPath("/");
		String fileName = MessageFormat.format("{0}/WEB-INF/classes/log4j.cfg.xml", realPath);
		String dataPath = MessageFormat.format("{0}/WEB-INF/classes/data", realPath);
		DOMConfigurator.configure(fileName);
		BaseDAO.setDataPath(dataPath);
		injector = Guice.createInjector(new DAOModule());
		basinDAO = injector.getInstance(BasinDAO.class);
		yearDAO = injector.getInstance(YearDAO.class);
		logger.debug("contextInitialized");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.debug("contextDestroyed");
	}
}
