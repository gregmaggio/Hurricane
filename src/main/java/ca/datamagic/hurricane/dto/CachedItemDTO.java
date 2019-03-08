/**
 * 
 */
package ca.datamagic.hurricane.dto;

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
}
