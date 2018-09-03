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
	private String _stormKey = null;
	private String _basin = null;
	private Integer _year = null;
	private Integer _stormNo = null;
	private String _stormName = null;
	
	public String getStormKey() {
		return _stormKey;
	}
	
	public void setStormKey(String newVal) {
		_stormKey = newVal;
	}
	
	public void setStormKey(String basin, Integer year, Integer stormNo) {
		setStormKey(MessageFormat.format("{0}-{1}-{2}", basin, Integer.toString(year.intValue()), Integer.toString(stormNo.intValue())));
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
}