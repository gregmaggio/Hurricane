/**
 * 
 */
package ca.datamagic.hurricane.dto;

/**
 * @author Greg
 *
 */
public class CachedItemDTO {
	private String _key = null;
	private String _json = null;
	
	public CachedItemDTO() {
	}

	public String getKey() {
		return _key;
	}
	
	public String getJson() {
		return _json;
	}
	
	public void setKey(String newVal) {
		_key = newVal;
	}
	
	public void setJson(String newVal) {
		_json = newVal;
	}
}
