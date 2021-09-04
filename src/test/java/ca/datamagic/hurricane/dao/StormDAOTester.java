/**
 * 
 */
package ca.datamagic.hurricane.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import ca.datamagic.hurricane.dto.StormDTO;
import ca.datamagic.hurricane.testing.BaseTester;

/**
 * @author Greg
 *
 */
public class StormDAOTester extends BaseTester {
	private static final Logger logger = LogManager.getLogger(StormDAOTester.class);
	
	@Test
	public void test1() throws Exception {
		StormDAO dao = new StormDAO();
		List<StormDTO> storms = dao.storms("NA", 2021);
		Assert.assertNotNull(storms);
		for (StormDTO storm : storms) {
			logger.debug(storm);
		}
	}
}
