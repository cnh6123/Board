package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Board;
import dao.BoardDao;

public class WriteProAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			request.setCharacterEncoding("utf-8");
			int num = Integer.parseInt(request.getParameter("num"));
			String pageNum = request.getParameter("pageNum");
			String writer = request.getParameter("writer");
			String email = request.getParameter("email");
			String subject = request.getParameter("subject");
			String password = request.getParameter("passwd");
			int ref = Integer.parseInt(request.getParameter("ref"));
			int re_step = Integer.parseInt(request.getParameter("re_step"));
			int re_level = Integer.parseInt(request.getParameter("re_level"));
			String content = request.getParameter("content");
			String ip = request.getRemoteAddr();
			
			Board board = new Board();
			board.setNum(num);
			board.setWriter(writer);
			board.setEmail(email);
			board.setSubject(subject);
			board.setPassword(password);
			board.setRef(ref);
			board.setRe_step(re_step);
			board.setRe_level(re_level);
			board.setContent(content);
			board.setIp(ip);
			
			BoardDao bd = BoardDao.getInstance();
			
			int result = bd.insert(board);
			request.setAttribute("num", board.getNum());
			request.setAttribute("result", result);
			request.setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		return "writePro.jsp";
	}

}
