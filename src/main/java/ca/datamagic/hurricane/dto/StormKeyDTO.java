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
public class StormKeyDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String stormKey = null;
    private String basin = null;
    private Integer year = null;
    private Integer stormNo = null;
    private String stormName = null;

    public StormKeyDTO() {

    }

    public StormKeyDTO(String stormKey, String basin, Integer year, Integer stormNo, String stormName) {
        this.stormKey = stormKey;
        this.basin = basin;
        this.year = year;
        this.stormNo = stormNo;
        this.stormName = stormName;
    }

    public String getStormKey() {
        return this.stormKey;
    }

    public String getBasin() {
        return this.basin;
    }

    public Integer getYear() {
        return this.year;
    }

    public Integer getStormNo() {
        return this.stormNo;
    }

    public String getStormName() {
        return this.stormName;
    }

    public void setStormKey(String newVal) {
        this.stormKey = newVal;
    }

    public void setBasin(String newVal) {
        this.basin = newVal;
    }

    public void setYear(Integer newVal) {
        this.year = newVal;
    }

    public void setStormNo(Integer newVal) {
        this.stormNo = newVal;
    }

    public void setStormName(String newVal) {
        this.stormName = newVal;
    }
	
	@Override
	public String toString() {
		return (new Gson()).toJson(this);
	}
}
