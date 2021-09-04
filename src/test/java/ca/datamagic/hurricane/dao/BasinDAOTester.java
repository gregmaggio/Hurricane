package ca.datamagic.hurricane.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import ca.datamagic.hurricane.dto.BasinDTO;
import ca.datamagic.hurricane.testing.BaseTester;

public class BasinDAOTester extends BaseTester {
	private static Logger logger = LogManager.getLogger(BasinDAOTester.class);

	@Test
	public void testBasins() throws Exception {
		BasinDAO dao = new BasinDAO();
		List<BasinDTO> basins = dao.getBasins();
		for (int ii = 0; ii < basins.size(); ii++) {
			logger.debug(basins.get(ii).getName());
		}
		BasinDTO basin = basins.get(0);
		List<Integer> years = dao.getYears(basin.getName());
		logger.debug("years for " + basin.getName());
		for (int ii = 0; ii < years.size(); ii++) {
			logger.debug(years.get(ii));
		}
	}

}
