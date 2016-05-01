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

import ca.datamagic.hurricane.dto.YearDTO;
import ca.datamagic.hurricane.inject.MemoryCache;

/**
 * @author Greg
 *
 */
public class YearDAO extends BaseDAO {
	private static Logger _logger = LogManager.getLogger(YearDAO.class);
	private String _database = null;
	private String _connnectionString = null;

	public YearDAO() throws Exception {
		_database = MessageFormat.format("{0}/years.db", getDataPath());
		_connnectionString = MessageFormat.format("jdbc:sqlite:{0}", _database);
	}

	@MemoryCache
	public List<Integer> getYears(String basin) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			List<Integer> years = new ArrayList<Integer>();
			connection = DriverManager.getConnection(_connnectionString);
			statement = connection.prepareStatement("SELECT * FROM year where basin = ?");
			statement.setString(1, basin);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				years.add(resultSet.getInt("year"));
			}
			return years;
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
			statement.executeUpdate("DELETE FROM year");
		} finally {
			if (statement != null) {
				close(statement);
			}
			if (connection != null) {
				close(connection);
			}
		}
	}
	
	public void save(YearDTO year) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DriverManager.getConnection(_connnectionString);
			statement = connection.prepareStatement("INSERT INTO year (basin, year) VALUES (?, ?)");
			statement.setString(1, year.getBasin());
			statement.setInt(2, year.getYear());
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
