/**
 * 
 */
package ca.datamagic.hurricane.dao;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;

import ca.datamagic.hurricane.dto.BasinDTO;
import ca.datamagic.hurricane.inject.MemoryCache;
import ca.datamagic.hurricane.inject.Performance;

/**
 * @author Greg
 *
 */
public class BasinDAO extends BaseDAO {
	public static List<BasinDTO> basins = null;
	
	static {
		basins = new ArrayList<BasinDTO>();
		basins.add(new BasinDTO("NA", "North Atlantic",27.042943892507832,-64.06562896264852));
		basins.add(new BasinDTO("EP", "Eastern North Pacific",17.34682960352381,-101.40464744010902));
		basins.add(new BasinDTO("WP", "Western North Pacific",20.393590910059267,133.57668140439355));
		basins.add(new BasinDTO("NI", "North Indian",17.770978228587104,81.49652796447191));
		basins.add(new BasinDTO("SI", "South Indian",-17.595758564542496,76.92105309952329));
		basins.add(new BasinDTO("SP", "Southern Pacific",-19.537684973260493,123.76518808351108));
		basins.add(new BasinDTO("SA", "South Atlantic",-25.933099999999992,-41.72111596638654));
	}
	
	@Performance
	public List<BasinDTO> getBasins() throws Exception {
		return basins;
	}
	
	@Performance
	@MemoryCache
	public List<Integer> getYears(String basin) throws IOException, InterruptedException {
        List<Integer> years = new ArrayList<Integer>();
        if ((basin != null) && (basin.length() > 0)) {
            String query = MessageFormat.format("SELECT DISTINCT season FROM `bigquery-public-data.noaa_hurricanes.hurricanes` WHERE basin = {0} ORDER BY season", "'" + basin + "'");
            TableResult result = runQuery(query);
            for (FieldValueList row : result.iterateAll()) {
                years.add(new Integer(row.get("season").getStringValue()));
            }
        }
        return years;
    }
}
