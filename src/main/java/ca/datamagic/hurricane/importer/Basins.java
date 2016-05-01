/**
 * 
 */
package ca.datamagic.hurricane.importer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import ca.datamagic.hurricane.dto.BasinDTO;

/**
 * @author Greg
 *
 */
public class Basins {
	private static Hashtable<String, BasinDTO> _basins = null;
	
	private static Hashtable<String, BasinDTO> getBasins() {
		if (_basins == null) {
			Hashtable<String, BasinDTO> basins = new Hashtable<String, BasinDTO>();
			basins.put("AL", new BasinDTO("AL", "North Atlantic", -86.617337, 25.439439, 4));
			basins.put("EP", new BasinDTO("EP", "Eastern Pacific", -108.76577450000002, 23.841764893278484, 4));
			basins.put("CP", new BasinDTO("CP", "Central Pacific", -145.06460262500002, 25.201097984236153, 4));
			_basins = basins;
		}
		return _basins;
	}
	
	public synchronized static List<BasinDTO> listBasins() {
		Hashtable<String, BasinDTO> basins = getBasins();
		return new ArrayList<BasinDTO>(basins.values());
	}
	
	public synchronized static BasinDTO getBasin(String basin) {
		Hashtable<String, BasinDTO> basins = getBasins();
		String key = basin.toUpperCase();
		if (basins.containsKey(key)) {
			return basins.get(key);
		}
		return null;
	}
}
