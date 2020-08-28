/**
 * 
 */
package ca.datamagic.hurricane.dto;

import com.google.gson.Gson;

/**
 * @author Greg
 *
 */
public class CachedItemDTO {
	private String key = null;
	private String json = null;
	
	public CachedItemDTO() {
	}

	public String getKey() {
		return this.key;
	}
	
	public String getJson() {
		return this.json;
	}
	
	public void setKey(String newVal) {
		this.key = newVal;
	}
	
	public void setJson(String newVal) {
		this.json = newVal;
	}
	
	@Override
	public String toString() {
		return (new Gson()).toJson(this);
	}
}
