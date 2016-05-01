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
public class Header {
	private static Logger _logger = LogManager.getLogger(Header.class);
	private String _basin = null;
	private Integer _cycloneNumber = null;
	private Integer _year = null;
	private String _name = null;
	private Integer _tracks = null;
	
	public static Header getHeader(String text) {
		try {
			Header header = new Header();
			header._basin = text.substring(0, 2).trim();
			header._cycloneNumber = new Integer(text.substring(2, 4).trim());
			header._year = new Integer(text.substring(4, 8).trim());
			header._name = text.substring(18, 28).trim();
			header._tracks = new Integer(text.substring(33, 36).trim());
			return header;
		} catch (Throwable t) {
			_logger.warn("Exception", t);
		}
		return null;
	}
	
	public String getBasin() {
		return _basin;
	}
	
	public Integer getCycloneNumber() {
		return _cycloneNumber;
	}
	
	public Integer getYear() {
		return _year;
	}
	
	public String getName() {
		return _name;
	}
	
	public Integer getTracks() {
		return _tracks;
	}
	
	@Override
	public String toString() {
		return MessageFormat.format("{0},{1},{2},{3},{4}", new Object[] {
				_basin,
				Integer.toString(_cycloneNumber.intValue()),
				Integer.toString(_year.intValue()),
				_name,
				Integer.toString(_tracks.intValue())
			});
	}
}
