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
public class BasinDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name = null;
	private String description = null;
	private Double centerX = null;
	private Double centerY = null;
	
	public BasinDTO() {
		
	}
	
	public BasinDTO(String name, String description, double centerX, double centerY) {
		this.name = name;
		this.description = description;
		this.centerX = new Double(centerX);
		this.centerY = new Double(centerY);
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String newVal) {
		this.name = newVal;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String newVal) {
		this.description = newVal;
	}
	
	public Double getCenterX() {
		return this.centerX;
	}
	
	public void setCenterX(Double newVal) {
		this.centerX = newVal;
	}
	
	public Double getCenterY() {
		return this.centerY;
	}
	
	public void setCenterY(Double newVal) {
		this.centerY = newVal;
	}
	
	@Override
	public String toString() {
		return (new Gson()).toJson(this);
	}
}
