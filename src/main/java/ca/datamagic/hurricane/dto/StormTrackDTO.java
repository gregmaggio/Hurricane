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
public class StormTrackDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer stormNo = null;
	private String stormName = null;
	private Integer trackNo = null;
	private Integer year = null;
	private Integer month = null;
	private Integer day = null;
	private Integer hours = null;
	private Integer minutes = null;
	private String status = null;
	private String recordIdentifier = null;
	private Double maxWindSpeed = null;
	private Double minPressure = null;
	private Double latitude = null;
	private String latitudeHemisphere = null;
	private Double longitude = null;
	private String longitudeHemisphere = null;
	private Double x = null;
	private Double y = null;
	
	public StormTrackDTO() {
		
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
	
	public Integer getTrackNo() {
		return this.trackNo;
	}
	
	public void setTrackNo(Integer newVal) {
		this.trackNo = newVal;
	}
	
	public Integer getYear() {
		return this.year;
	}
	
	public void setYear(Integer newVal) {
		this.year = newVal;
	}
	
	public Integer getMonth() {
		return this.month;
	}
	
	public void setMonth(Integer newVal) {
		this.month = newVal;
	}
	
	public Integer getDay() {
		return this.day;
	}
	
	public void setDay(Integer newVal) {
		this.day = newVal;
	}
	
	public Integer getHours() {
		return this.hours;
	}
	
	public void setHours(Integer newVal) {
		this.hours = newVal;
	}
	
	public Integer getMinutes() {
		return this.minutes;
	}
	
	public void setMinutes(Integer newVal) {
		this.minutes = newVal;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String newVal) {
		this.status = newVal;
	}
	
	public String getRecordIdentifier() {
		return this.recordIdentifier;
	}
	
	public void setRecordIdentifier(String newVal) {
		this.recordIdentifier = newVal;
	}
	
	public Double getMaxWindSpeed() {
		return this.maxWindSpeed;
	}
	
	public void setMaxWindSpeed(Double newVal) {
		this.maxWindSpeed = newVal;
	}
	
	public Double getMinPressure() {
		return this.minPressure;
	}
	
	public void setMinPressure(Double newVal) {
		this.minPressure = newVal;
	}
	
	public Double getLatitude() {
		return this.latitude;
	}
	
	public void setLatitude(Double newVal) {
		this.latitude = newVal;
	}
	
	public String getLatitudeHemisphere() {
		return this.latitudeHemisphere;
	}
	
	public void setLatitudeHemisphere(String newVal) {
		this.latitudeHemisphere = newVal;
	}
	
	public Double getLongitude() {
		return this.longitude;
	}
	
	public void setLongitude(Double newVal) {
		this.longitude = newVal;
	}
	
	public String getLongitudeHemisphere() {
		return this.longitudeHemisphere;
	}
	
	public void setLongitudeHemisphere(String newVal) {
		this.longitudeHemisphere = newVal;
	}
	
	public Double getX() {
		return this.x;
	}
	
	public void setX(Double newVal) {
		this.x = newVal;
	}
	
	public Double getY() {
		return this.y;
	}
	
	public void setY(Double newVal) {
		this.y = newVal;
	}
	
	@Override
	public String toString() {
		return (new Gson()).toJson(this);
	}
}
