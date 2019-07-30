package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.smartcardio.CommandAPDU;

import service.CommandProcess;

/**
 * Servlet implementation class Controller
 */
//@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Map<String, Object> commandMap = new HashMap<>();
    public void init(ServletConfig config)throws ServletException{
    	String props = config.getInitParameter("config");
    	//web.xml에서 properties에 해당하는 config 키 값(init-param)을 이용해 가져온다. 
    	Properties pr = new Properties();
    	FileInputStream f = null;
    	//명령어의 처리 클래스로 파일 정보를 저장할 Properties 객체 생성
    	try {
			String configFilePath = config.getServletContext().getRealPath(props);
			f= new FileInputStream(configFilePath);
			pr.load(f);
		} catch (IOException e) {
			// TODO: handle exception
			throw new ServletException(e);
		}finally {
			if(f!=null) {
				try {
					f.close();
				} catch (IOException e2) {
					// TODO: handle exception
				}
			}
		}
    	//Iterator 객체는 Enumeration 객체를 확장시킨 개념의 객체
    	Iterator keyIter = pr.keySet().iterator();
    	//객체를 하나씩 꺼내서 그 객체명으로 Properties에 저장된 객체에 접근
    	while(keyIter.hasNext()) {
    		String command = (String)keyIter.next();//list.do
    		String className = pr.getProperty(command);//service.ListAction
    		try {
				Class commandClass = Class.forName(className);//해당 문자열을 클래스로 만든다.
				Object commandInstance = commandClass.newInstance();//해당클래스의 객체를 생성
				commandMap.put(command, commandInstance);//Map 객체인 CommandMap에 객체 저장
			} catch (Exception e) {
				// TODO: handle exception
				throw new ServletException(e);
			}
    	}
    }
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		requestPro(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		requestPro(request, response);
	}
	private void requestPro(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
		String view = null;
		CommandProcess com = null;
		try {
			String command = req.getRequestURI();
			System.out.println(command);
			
			command= command.substring(req.getContextPath().length());
			com = (CommandProcess)commandMap.get(command);
			System.out.println("command--> "+command);
			System.out.println("com--> "+com);
			
			view = com.requestPro(req, resp);
			System.out.println("view--> "+ view);
		} catch (Throwable e) {
			// TODO: handle exception
			throw new ServletException(e);
		}
		RequestDispatcher rd = req.getRequestDispatcher(view);
		rd.forward(req, resp);
	}

}
