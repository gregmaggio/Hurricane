/**
 * 
 */
package ca.datamagic.hurricane.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import ca.datamagic.hurricane.dto.StormKeyDTO;
import ca.datamagic.hurricane.testing.BaseTester;

/**
 * @author Greg
 *
 */
public class SearchDAOTester extends BaseTester {
	private static final Logger logger = LogManager.getLogger(SearchDAOTester.class);
	
	@Test
	public void testSearch() throws Exception {
		SearchDAO dao = new SearchDAO();
		List<StormKeyDTO> storms = dao.search("KA");
		for (int ii = 0; ii < storms.size(); ii++) {
			logger.debug(storms.get(ii));
		}
	}
}
