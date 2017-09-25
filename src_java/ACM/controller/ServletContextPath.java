package ACM.controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 
 * @author root
 * @version
 */

@SuppressWarnings("serial")
public class ServletContextPath extends HttpServlet {

	private ServletContext sc;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		sc = config.getServletContext();
		
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(sc);

		AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext
				.getAutowireCapableBeanFactory();

	;

	}

	@SuppressWarnings("unchecked")
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String relatorioXml = request.getParameter("relatorioXml");
		//response.set
		System.out.println("Servlet: " + relatorioXml);
		byte[] bytes = relatorioXml.getBytes();			
		if (bytes != null && bytes.length > 0) {
			//response.setContentType("application/pdf");
			//response.setContentLength(bytes.length);
			//ServletOutputStream ouputStream = response.getOutputStream();
			//ouputStream.write(bytes, 0, bytes.length);
			//ouputStream.flush();
			//ouputStream.close();
		}		
	}
}