/**
 * 
 */
package ca.datamagic.hurricane.importer;

import java.text.MessageFormat;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Greg
 *
 */
public class Track {
	private static Logger _logger = LogManager.getLogger(Track.class);
	private Integer _year = null;
	private Integer _month = null;
	private Integer _day = null;
	private Integer _hours = null;
	private Integer _minutes = null;
	private String _recordIdentifier = null;
	private String _status = null;
	private Double _latitude = null;
	private String _latitudeHemisphere = null;
	private Double _longitude = null;
	private String _longitudeHemisphere = null;
	private Double _maxWindSpeed = null;
	private Double _minPressure = null;
	
	public static Track getTrack(String text) {
		try {
			Track track = new Track();
			track._year = new Integer(text.substring(0, 4).trim());
			track._month = new Integer(text.substring(4, 6).trim());
			track._day = new Integer(text.substring(6, 8).trim());
			track._hours = new Integer(text.substring(10, 12).trim());
			track._minutes = new Integer(text.substring(12, 14).trim());
			track._recordIdentifier = text.substring(16, 17).trim();
			track._status = text.substring(19, 21).trim();
			track._latitude = new Double(text.substring(23, 27).trim());
			track._latitudeHemisphere = text.substring(27, 28).trim();
			track._longitude = new Double(text.substring(30, 35).trim());
			track._longitudeHemisphere = text.substring(35, 36).trim();
			track._maxWindSpeed = new Double(text.substring(38, 41).trim());
			track._minPressure = new Double(text.substring(43, 47).trim());
			return track;
		} catch (Throwable t) {
			_logger.warn("Exception", t);
		}
		return null;
	}
	
	public Integer getYear() {
		return _year;
	}
	
	public Integer getMonth() {
		return _month;
	}
	
	public Integer getDay() {
		return _day;
	}
	
	public Integer getHours() {
		return _hours;
	}
	
	public Integer getMinutes() {
		return _minutes;
	}
	
	public String getRecordIdentifier() {
		return _recordIdentifier;
	}
	
	public String getStatus() {
		return _status;
	}
	
	public Double getLatitude() {
		return _latitude;
	}
	
	public String getLatitudeHemisphere() {
		return _latitudeHemisphere;
	}
	
	public Double getLongitude() {
		return _longitude;
	}
	
	public String getLongitudeHemisphere() {
		return _longitudeHemisphere;
	}
	
	public Double getMaxWindSpeed() {
		return _maxWindSpeed;
	}
	
	public Double getMinPressure() {
		return _minPressure;
	}
	
	@Override
	public String toString() {
		return MessageFormat.format("{0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},{11},{12}", new Object[] {
				Integer.toString(_year.intValue()),
				Integer.toString(_month.intValue()),
				Integer.toString(_day.intValue()),
				Integer.toString(_hours.intValue()),
				Integer.toString(_minutes.intValue()),
				_recordIdentifier,
				_status,
				Double.toString(_latitude.doubleValue()),
				_latitudeHemisphere,
				Double.toString(_longitude.doubleValue()),
				_longitudeHemisphere,
				Double.toString(_maxWindSpeed.doubleValue()),
				Double.toString(_minPressure.doubleValue())
			});
	}
}
