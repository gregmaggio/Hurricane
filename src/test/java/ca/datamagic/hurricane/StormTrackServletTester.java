/**
 * 
 */
package ca.datamagic.hurricane;

import static org.mockito.Mockito.*;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.datamagic.hurricane.servlet.HurricaneContextListener;
import ca.datamagic.hurricane.servlet.StormTrackServlet;

/**
 * @author Greg
 *
 */
public class StormTrackServletTester {

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test1() throws Exception {
		ServletContext context = mock(ServletContext.class);
		when(context.getRealPath("/")).thenReturn("C:/Program Files (x86)/apache-tomcat-8.5.29/webapps/Hurricane");
		
		ServletContextEvent sce = mock(ServletContextEvent.class);
		when(sce.getServletContext()).thenReturn(context);
		
		HurricaneContextListener listener = new HurricaneContextListener();
		listener.contextInitialized(sce);
		
		HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);    

        when(request.getPathInfo()).thenReturn("/AL/2000/1/1/2010/12/31");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        
        StormTrackServlet servlet = new StormTrackServlet();
        servlet.doGet(request, response);
	}

}
