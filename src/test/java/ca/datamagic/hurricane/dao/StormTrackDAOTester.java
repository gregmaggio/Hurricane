/**
 * 
 */
package ca.datamagic.hurricane.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import ca.datamagic.hurricane.dto.StormTrackDTO;
import ca.datamagic.hurricane.testing.BaseTester;

/**
 * @author Greg
 *
 */
public class StormTrackDAOTester extends BaseTester {
	private static final Logger logger = LogManager.getLogger(StormTrackDAOTester.class);
	
	@Test
	public void test1() throws Exception {
		StormTrackDAO dao = new StormTrackDAO();
		List<StormTrackDTO> tracks = dao.tracks("NA", 2021, 60);
		for (StormTrackDTO track : tracks) {
			logger.debug(track);
		}
	}	
}
