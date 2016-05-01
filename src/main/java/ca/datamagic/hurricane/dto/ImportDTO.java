/**
 * 
 */
package ca.datamagic.hurricane.dto;

import java.util.List;

/**
 * @author Greg
 *
 */
public class ImportDTO {
	private String _fileName = null;
	private List<BasinDTO> _basins = null;
	
	public String getFileName() {
		return _fileName;
	}
	
	public void setFileName(String newVal) {
		_fileName = newVal;
	}

	public List<BasinDTO> getBasins() {
		return _basins;
	}
	
	public void setBasins(List<BasinDTO> newVal) {
		_basins = newVal;
	}
}
