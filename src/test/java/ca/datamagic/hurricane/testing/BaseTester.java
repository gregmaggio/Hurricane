/**
 * 
 */
package ca.datamagic.hurricane.testing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;

import ca.datamagic.hurricane.dao.BaseDAO;

/**
 * @author Greg
 *
 */
public abstract class BaseTester {
	private static final Logger logger = LogManager.getLogger(BaseTester.class);
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String realPath = (new File(".")).getAbsolutePath();
		String appKeyFile = MessageFormat.format("{0}/src/test/resources/bigquery.json", realPath);
		String appKey = null;
		InputStream appKeyStream = null;
		try {
			appKeyStream = new FileInputStream(appKeyFile);
			appKey = IOUtils.toString(appKeyStream, "UTF-8");
		} catch (IOException ex) {
			logger.error("IOException", ex);
		} finally {
			IOUtils.closeQuietly(appKeyStream);
		}
		BaseDAO.setAppKey(appKey);
	}	
}
