/**
 * 
 */
package ca.datamagic.hurricane.dto;

import java.io.Serializable;

/**
 * @author Greg
 *
 */
public class StormDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer _stormNo = null;
	private String _stormName = null;
	private Integer _tracks = null;
	
	public Integer getStormNo() {
		return _stormNo;
	}
	
	public void setStormNo(Integer newVal) {
		_stormNo = newVal;
	}
	
	public String getStormName() {
		return _stormName;
	}
	
	public void setStormName(String newVal) {
		_stormName = newVal;
	}

	public Integer getTracks() {
		return _tracks;
	}
	
	public void setTracks(Integer newVal) {
		_tracks = newVal;
	}
}
