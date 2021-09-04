/**
 * 
 */
package ca.datamagic.hurricane.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import ca.datamagic.hurricane.dto.BasinDTO;
import ca.datamagic.hurricane.dto.StormDTO;
import ca.datamagic.hurricane.dto.StormTrackDTO;
import ca.datamagic.hurricane.inject.DAOModule;
import ca.datamagic.hurricane.testing.BaseTester;

/**
 * @author Greg
 *
 */
public class CacheTester extends BaseTester {
	@Test
	public void testBasins() throws Exception {
		Injector injector = Guice.createInjector(new DAOModule());
		BasinDAO dao = injector.getInstance(BasinDAO.class);
		List<BasinDTO> basins = dao.getBasins();
		BasinDTO basin = basins.get(0);
		List<Integer> years1 = dao.getYears(basin.getName());
		List<Integer> years2 = dao.getYears(basin.getName());
		Assert.assertEquals(years1.size(), years2.size());
	}
	
	@Test
	public void testStorms() throws Exception {
		Injector injector = Guice.createInjector(new DAOModule());
		StormDAO dao = injector.getInstance(StormDAO.class);
		List<StormDTO> storms1 = dao.storms("NA", 2021);
		List<StormDTO> storms2 = dao.storms("NA", 2021);
		Assert.assertEquals(storms1.size(), storms2.size());
	}
	
	@Test
	public void testStormTracks() throws Exception {
		Injector injector = Guice.createInjector(new DAOModule());
		StormTrackDAO dao = injector.getInstance(StormTrackDAO.class);
		List<StormTrackDTO> tracks1 = dao.tracks("NA", 2021, 60);
		List<StormTrackDTO> tracks2 = dao.tracks("NA", 2021, 60);
		Assert.assertEquals(tracks1.size(), tracks2.size());
	}
}
