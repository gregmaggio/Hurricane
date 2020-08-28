/**
 * 
 */
package ca.datamagic.hurricane.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

/**
 * @author Greg
 *
 */
public class YearDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String basin = null;
	private Integer year = null;
	private List<StormTrackDTO> tracks = new ArrayList<StormTrackDTO>();
	
	public YearDTO() {
		
	}
	
	public String getBasin() {
		return this.basin;
	}
	
	public void setBasin(String newVal) {
		this.basin = newVal;
	}
	
	public Integer getYear() {
		return this.year;
	}	
	
	public void setYear(Integer newVal) {
		this.year = newVal;
	}
	
	public List<StormTrackDTO> getTracks() {
		return this.tracks;
	}
	
	public void setTracks(List<StormTrackDTO> newVal) {
		this.tracks = newVal;
	}
	
	@Override
	public String toString() {
		return (new Gson()).toJson(this);
	}
}
