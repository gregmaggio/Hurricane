/**
 * 
 */
package ca.datamagic.hurricane.dao;

import java.io.File;
import java.util.List;

import org.apache.log4j.xml.DOMConfigurator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.datamagic.hurricane.dto.BasinDTO;
import ca.datamagic.hurricane.dto.StormDTO;
import ca.datamagic.hurricane.dto.StormTrackDTO;
import ca.datamagic.hurricane.dto.YearDTO;
import ca.datamagic.hurricane.importer.Importer;

/**
 * @author Greg
 *
 */
public class StormTrackDAOTester {
	private StormTrackDAO _dao = null;
	
	private Integer getStormNo(String stormName) throws Exception {
		List<StormDTO> storms = _dao.getStorms();
		for (StormDTO storm : storms) {
			if (storm.getStormName().compareToIgnoreCase(stormName) == 0) {
				return storm.getStormNo();
			}
		}
		return null;
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
		_dao = new StormTrackDAO();
		_dao.setBasin(testBasin.getName());
		_dao.setYear(testYear.getYear());
		_dao.clear();
		for (StormTrackDTO stormTrack : testYear.getTracks()) {
			_dao.save(stormTrack);
		}
	}

	@After
	public void tearDown() throws Exception {
		_dao = null;
	}

	@Test
	public final void testGetStorms() throws Exception {
		Integer stormNo = getStormNo("KATRINA");
		Assert.assertNotNull(stormNo);
	}

	@Test
	public final void testGetStormTracksInteger() throws Exception {
		Integer stormNo = getStormNo("KATRINA");
		Assert.assertNotNull(stormNo);
		List<StormTrackDTO> stormTracks = _dao.getStormTracks(stormNo);
		Assert.assertNotNull(stormTracks);
		Assert.assertEquals(34, stormTracks.size());
	}

	@Test
	public final void testGetStormTracksIntegerIntegerIntegerIntegerIntegerInteger() throws Exception {
		List<StormTrackDTO> stormTracks = _dao.getStormTracks(2005, 8, 24, 2005, 8, 25);
		Assert.assertNotNull(stormTracks);
		Assert.assertEquals(9, stormTracks.size());
	}

	@Test
	public final void testGetStormsIntegerIntegerIntegerIntegerIntegerInteger() throws Exception {
		List<StormDTO> storms = _dao.getStorms(2005, 8, 24, 2005, 8, 25);
		Assert.assertNotNull(storms);
		Assert.assertEquals(1, storms.size());
		Assert.assertEquals(9, storms.get(0).getTracks().intValue());
	}
}
