package ca.datamagic.hurricane.dao;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.datamagic.hurricane.dto.StormKeyDTO;

public class BasinDAOTester {
	private static Logger _logger = LogManager.getLogger(BasinDAOTester.class);
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DOMConfigurator.configure("src/test/resources/META-INF/log4j.cfg.xml");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSearch() throws Exception {
		BasinDAO dao = new BasinDAO();
		List<StormKeyDTO> storms = dao.search("KA");
		for (int ii = 0; ii < storms.size(); ii++) {
			_logger.debug(storms.get(ii).getStormName());
		}
	}

}
