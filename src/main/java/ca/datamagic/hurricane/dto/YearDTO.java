/**
 * 
 */
package ca.datamagic.hurricane.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg
 *
 */
public class YearDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String _basin = null;
	private Integer _year = null;
	private List<StormTrackDTO> _tracks = new ArrayList<StormTrackDTO>();
	
	public YearDTO() {
		
	}
	
	public String getBasin() {
		return _basin;
	}
	
	public void setBasin(String newVal) {
		_basin = newVal;
	}
	
	public Integer getYear() {
		return _year;
	}	
	
	public void setYear(Integer newVal) {
		_year = newVal;
	}
	
	public List<StormTrackDTO> getTracks() {
		return _tracks;
	}
	
	public void setTracks(List<StormTrackDTO> newVal) {
		_tracks = newVal;
	}
}
