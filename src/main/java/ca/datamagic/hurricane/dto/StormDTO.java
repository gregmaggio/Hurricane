/**
 * 
 */
package ca.datamagic.hurricane.dto;

import java.io.Serializable;

import com.google.gson.Gson;

/**
 * @author Greg
 *
 */
public class StormDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer stormNo = null;
    private String stormName = null;
    private Integer tracks = null;

    public StormDTO() {

    }

    public StormDTO(Integer stormNo, String stormName, Integer tracks) {
        this.stormNo = stormNo;
        this.stormName = stormName;
        this.tracks = tracks;
    }

    public Integer getStormNo() {
        return this.stormNo;
    }

    public String getStormName() {
        return this.stormName;
    }

    public Integer getTracks() {
        return this.tracks;
    }

    public void setStormNo(Integer newVal) {
        this.stormNo = newVal;
    }

    public void setStormName(String newVal) {
        this.stormName = newVal;
    }

    public void setTracks(Integer newVal) {
        this.tracks = newVal;
    }
	
	@Override
	public String toString() {
		return (new Gson()).toJson(this);
	}
}
