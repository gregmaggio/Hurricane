/**
 * 
 */
package ca.datamagic.hurricane.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ca.datamagic.hurricane.dto.BasinDTO;
import ca.datamagic.hurricane.dto.StormKeyDTO;
import ca.datamagic.hurricane.inject.MemoryCache;

/**
 * @author Greg
 *
 */
public class BasinDAO extends BaseDAO {
	private static Logger _logger = LogManager.getLogger(BaseDAO.class);
	private String _database = null;
	private String _connnectionString = null;
	
	public BasinDAO() {
		_database = MessageFormat.format("{0}/basins.db", getDataPath());
		_connnectionString = MessageFormat.format("jdbc:sqlite:{0}", _database);
	}
	
	@MemoryCache
	public List<BasinDTO> getBasins() throws Exception {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			List<BasinDTO> basins = new ArrayList<BasinDTO>();
			connection = DriverManager.getConnection(_connnectionString);
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM basin");
			while (resultSet.next()) {
				BasinDTO dto = new BasinDTO();
				dto.setName(resultSet.getString("name"));
				dto.setDescription(resultSet.getString("description"));
				dto.setCenterX(resultSet.getDouble("centerX"));
				dto.setCenterY(resultSet.getDouble("centerY"));
				dto.setZoom(resultSet.getInt("zoom"));
				basins.add(dto);
			}
			return basins;
		} finally {
			if (resultSet != null) {
				close(resultSet);
			}
			if (statement != null) {
				close(statement);
			}
			if (connection != null) {
				close(connection);
			}
		}
	}
	
	public List<StormKeyDTO> search(String searchText) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			List<StormKeyDTO> storms = new ArrayList<StormKeyDTO>();
			if (!searchText.endsWith("%")) {
				searchText += "%";
			}
			connection = DriverManager.getConnection(_connnectionString);
			statement = connection.prepareStatement("SELECT * FROM storm WHERE storm_name LIKE ? ORDER BY storm_name");
			statement.setString(1, searchText);
			resultSet = statement.executeQuery();			
			while (resultSet.next()) {
				StormKeyDTO dto = new StormKeyDTO();
				dto.setStormKey(resultSet.getString("storm_key"));
				dto.setBasin(resultSet.getString("basin"));
				dto.setYear(resultSet.getInt("year"));
				dto.setStormName(resultSet.getString("storm_name"));
				storms.add(dto);
			}
			return storms;
		} finally {
			if (resultSet != null) {
				close(resultSet);
			}
			if (statement != null) {
				close(statement);
			}
			if (connection != null) {
				close(connection);
			}
		}
	}
	
	public void clear() throws Exception {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = DriverManager.getConnection(_connnectionString);
			statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM basin");
			
			close(statement);
			statement = null;
			
			statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM storm");
		} finally {
			if (statement != null) {
				close(statement);
			}
			if (connection != null) {
				close(connection);
			}
		}
	}
	
	public void save(BasinDTO basin) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DriverManager.getConnection(_connnectionString);
			statement = connection.prepareStatement("INSERT INTO basin (name, description, centerX, centerY, zoom) VALUES (?, ?, ?, ?, ?)");
			statement.setString(1, basin.getName());
			statement.setString(2, basin.getDescription());
			statement.setDouble(3, basin.getCenterX());
			statement.setDouble(4, basin.getCenterY());
			statement.setDouble(5, basin.getZoom());
			int affectedRecords = statement.executeUpdate();
			_logger.debug("affectedRecords: " + affectedRecords);
		} finally {
			if (statement != null) {
				close(statement);
			}
			if (connection != null) {
				close(connection);
			}
		}
	}
	
	public void save(StormKeyDTO stormKey) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DriverManager.getConnection(_connnectionString);
			statement = connection.prepareStatement("INSERT INTO storm (storm_key, basin, year, storm_name) VALUES (?, ?, ?, ?)");
			statement.setString(1, stormKey.getStormKey());
			statement.setString(2, stormKey.getBasin());
			statement.setInt(3, stormKey.getYear());
			statement.setString(4, stormKey.getStormName());
			int affectedRecords = statement.executeUpdate();
			_logger.debug("affectedRecords: " + affectedRecords);
		} finally {
			if (statement != null) {
				close(statement);
			}
			if (connection != null) {
				close(connection);
			}
		}
	}
}
