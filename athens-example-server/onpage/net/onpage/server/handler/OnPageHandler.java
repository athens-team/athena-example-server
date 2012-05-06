package net.onpage.server.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.onpage.data.page.Page;
import net.onpage.db.DBManager;
import net.onpage.db.mapper.OnPageMapper;
import net.onpage.json.JSONConvertor;
import net.onpage.json.JSONConvertorFactory;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnPageHandler extends HttpServlet {

	static final Logger logger = LoggerFactory.getLogger(OnPageHandler.class);
	
	private static final long serialVersionUID = -8241635562329750348L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html");
		
		PrintWriter writer = response.getWriter();
		writer.write("Hello World!");
    }

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		
		SqlSession session = null;
		
		response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("EUC-KR");
        
        try {
        	logger.info("contextPath = {}", request.getContextPath());
        	int id = Integer.parseInt(request.getParameter("id"));
        	
        	Charset cset = Charset.defaultCharset();
        	String csetName = cset.name();
        	logger.info("default charset:" + csetName);

        	
			session = DBManager.getInstance().openSession();
			OnPageMapper mapper = session.getMapper(OnPageMapper.class);
			JSONConvertor convertor = JSONConvertorFactory.getInstance().getInstance(Page.class);
			
			Page page = mapper.getPage(id);
			JSONObject result = convertor.getJSONObject(page);
			
			
			String resString = result.toString();
			logger.info("result = {}", convert(resString, "UTF8", "UTF8"));
			response.getWriter().write(convert(resString, "UTF8", "UTF8"));
			
	        
		} catch (SQLException e) {
			logger.error("SQLException occurred when doGet.", e);
		} catch(IOException e) {
			throw e;
		}  catch (Exception e) {
			logger.error("Exception occurred when doGet.", e);
		} finally {
       
			try { session.close(); } catch(Exception e) {}
		} 
		
    }
	
	public static String convert(String src, String from, String to) 
	throws UnsupportedEncodingException {

		return new String(src.getBytes(from),to);
}
}
