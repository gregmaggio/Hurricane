/**
 * 
 */
package ca.datamagic.hurricane.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Greg
 *
 */
public class BasinDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String _name = null;
	private String _description = null;
	private Double _centerX = null;
	private Double _centerY = null;
	private Integer _zoom = null;
	private transient List<YearDTO> _years = new ArrayList<YearDTO>();
	
	public BasinDTO() {
		
	}
	
	public BasinDTO(String name, String description, double centerX, double centerY, int zoom) {
		_name = name;
		_description = description;
		_centerX = new Double(centerX);
		_centerY = new Double(centerY);
		_zoom = new Integer(zoom);
	}

	public String getName() {
		return _name;
	}
	
	public void setName(String newVal) {
		_name = newVal;
	}
	
	public String getDescription() {
		return _description;
	}
	
	public void setDescription(String newVal) {
		_description = newVal;
	}
	
	public Double getCenterX() {
		return _centerX;
	}
	
	public void setCenterX(Double newVal) {
		_centerX = newVal;
	}
	
	public Double getCenterY() {
		return _centerY;
	}
	
	public void setCenterY(Double newVal) {
		_centerY = newVal;
	}
	
	public Integer getZoom() {
		return _zoom;
	}
	
	public void setZoom(Integer newVal) {
		_zoom = newVal;
	}
	
	@JsonIgnore
	public List<YearDTO> getYears() {
		return _years;
	}
	
	@JsonIgnore
	public void setYears(List<YearDTO> newVal) {
		_years = newVal;
	}
}
