/**
 * 
 */
package ca.datamagic.hurricane.dto;

import java.io.Serializable;

/**
 * @author Greg
 *
 */
public class StormTrackDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer _stormNo = null;
	private String _stormName = null;
	private Integer _trackNo = null;
	private Integer _year = null;
	private Integer _month = null;
	private Integer _day = null;
	private Integer _hours = null;
	private Integer _minutes = null;
	private String _status = null;
	private String _recordIdentifier = null;
	private Double _maxWindSpeed = null;
	private Double _minPressure = null;
	private Double _latitude = null;
	private String _latitudeHemisphere = null;
	private Double _longitude = null;
	private String _longitudeHemisphere = null;
	private Double _x = null;
	private Double _y = null;
	
	public StormTrackDTO() {
		
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
	
	public Integer getTrackNo() {
		return _trackNo;
	}
	
	public void setTrackNo(Integer newVal) {
		_trackNo = newVal;
	}
	
	public Integer getYear() {
		return _year;
	}
	
	public void setYear(Integer newVal) {
		_year = newVal;
	}
	
	public Integer getMonth() {
		return _month;
	}
	
	public void setMonth(Integer newVal) {
		_month = newVal;
	}
	
	public Integer getDay() {
		return _day;
	}
	
	public void setDay(Integer newVal) {
		_day = newVal;
	}
	
	public Integer getHours() {
		return _hours;
	}
	
	public void setHours(Integer newVal) {
		_hours = newVal;
	}
	
	public Integer getMinutes() {
		return _minutes;
	}
	
	public void setMinutes(Integer newVal) {
		_minutes = newVal;
	}
	
	public String getStatus() {
		return _status;
	}
	
	public void setStatus(String newVal) {
		_status = newVal;
	}
	
	public String getRecordIdentifier() {
		return _recordIdentifier;
	}
	
	public void setRecordIdentifier(String newVal) {
		_recordIdentifier = newVal;
	}
	
	public Double getMaxWindSpeed() {
		return _maxWindSpeed;
	}
	
	public void setMaxWindSpeed(Double newVal) {
		_maxWindSpeed = newVal;
	}
	
	public Double getMinPressure() {
		return _minPressure;
	}
	
	public void setMinPressure(Double newVal) {
		_minPressure = newVal;
	}
	
	public Double getLatitude() {
		return _latitude;
	}
	
	public void setLatitude(Double newVal) {
		_latitude = newVal;
	}
	
	public String getLatitudeHemisphere() {
		return _latitudeHemisphere;
	}
	
	public void setLatitudeHemisphere(String newVal) {
		_latitudeHemisphere = newVal;
	}
	
	public Double getLongitude() {
		return _longitude;
	}
	
	public void setLongitude(Double newVal) {
		_longitude = newVal;
	}
	
	public String getLongitudeHemisphere() {
		return _longitudeHemisphere;
	}
	
	public void setLongitudeHemisphere(String newVal) {
		_longitudeHemisphere = newVal;
	}
	
	public Double getX() {
		return _x;
	}
	
	public void setX(Double newVal) {
		_x = newVal;
	}
	
	public Double getY() {
		return _y;
	}
	
	public void setY(Double newVal) {
		_y = newVal;
	}
}
