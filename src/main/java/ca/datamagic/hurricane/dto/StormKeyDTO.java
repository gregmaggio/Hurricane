/**
 * 
 */
package ca.datamagic.hurricane.dto;

import java.io.Serializable;
import java.text.MessageFormat;

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
	
	public String getStormKey() {
		return this.stormKey;
	}
	
	public void setStormKey(String newVal) {
		this.stormKey = newVal;
	}
	
	public void setStormKey(String basin, Integer year, Integer stormNo) {
		setStormKey(MessageFormat.format("{0}-{1}-{2}", basin, Integer.toString(year.intValue()), Integer.toString(stormNo.intValue())));
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
	
	public Integer getStormNo() {
		return this.stormNo;
	}
	
	public void setStormNo(Integer newVal) {
		this.stormNo = newVal;
	}
	
	public String getStormName() {
		return this.stormName;
	}
	
	public void setStormName(String newVal) {
		this.stormName = newVal;
	}
}
