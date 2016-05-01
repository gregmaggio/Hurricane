/**
 * 
 */
package ca.datamagic.hurricane.dao;

import java.io.File;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import ca.datamagic.hurricane.dto.BasinDTO;
import ca.datamagic.hurricane.dto.StormTrackDTO;
import ca.datamagic.hurricane.dto.YearDTO;
import ca.datamagic.hurricane.importer.Importer;
import ca.datamagic.hurricane.inject.DAOModule;

/**
 * @author Greg
 *
 */
public class CacheTester {
	private static Logger _logger = LogManager.getLogger(CacheTester.class);
	private StormTrackDAO _dao = null;
	
	public CacheTester() {
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DOMConfigurator.configure("src/test/resources/META-INF/log4j.cfg.xml");
	}

	@Before
	public void setUp() throws Exception {
		String dataPath = (new File("src/test/resources/META-INF/data")).getCanonicalPath();
		BaseDAO.setDataPath(dataPath);
		
		List<BasinDTO> testBasins = Importer.parse("src/test/resources/KatrinaTest.txt");
		BasinDTO testBasin = testBasins.get(0);
		YearDTO testYear = testBasin.getYears().get(0);
		
		Injector injector = Guice.createInjector(new DAOModule());
		_dao = injector.getInstance(StormTrackDAO.class);
		_dao.setBasin(testBasin.getName());
		_dao.setYear(testYear.getYear());
		_dao.clear();
		for (StormTrackDTO stormTrack : testYear.getTracks()) {
			_dao.save(stormTrack);
		}
	}
	
	@Test
	public void test1() throws Exception {
		long startTimeMillis = System.currentTimeMillis();
		_dao.getStormTracks(1);
		long endTimeMillis = System.currentTimeMillis();
		double runningTime = (endTimeMillis - startTimeMillis) / 1000;
		_logger.debug("firstRun: " + runningTime);
		
		startTimeMillis = System.currentTimeMillis();
		_dao.getStormTracks(1);
		endTimeMillis = System.currentTimeMillis();
		runningTime = (endTimeMillis - startTimeMillis) / 1000;
		_logger.debug("secondRun: " + runningTime);
	}
}
