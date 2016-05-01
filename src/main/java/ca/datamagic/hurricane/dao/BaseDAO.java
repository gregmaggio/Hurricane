/**
 * 
 */
package ca.datamagic.hurricane.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Greg
 *
 */
public abstract class BaseDAO {
	private static Logger _logger = LogManager.getLogger(BaseDAO.class);
	private static String _dataPath = "C:/Dev/Applications/Hurricane/src/main/resources/META-INF/data";
	
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (Throwable t) {
			_logger.error("Exception", t);
		}
	}
	
	public BaseDAO() {
	}
	
	public static String getDataPath() {
		return _dataPath;
	}
	
	public static void setDataPath(String newVal) {
		_dataPath = newVal;
	}
	
	protected static void close(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Throwable t) {
			_logger.warn("Exception", t);
		}
	}
	
	protected static void close(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (Throwable t) {
			_logger.warn("Exception", t);
		}
	}
	
	protected static void close(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (Throwable t) {
			_logger.warn("Exception", t);
		}
	}
}
