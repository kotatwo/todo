package com.design_shinbi.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.design_shinbi.model.dao.TaskDAO;
import com.design_shinbi.model.entity.Task;
import com.design_shinbi.util.DbUtil;

@WebServlet("/task")
public class TaskServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Connection connection = DbUtil.connect();
			TaskDAO dao = new TaskDAO(connection);
			
			String method = req.getParameter("method");
			String jsp = null;
			if(method != null && (method.equals("new") || method.equals("edit"))) {
				jsp = "/WEB-INF/jsp/edit.jsp";
				if(method.equals("edit")) {
					int id = Integer.parseInt(req.getParameter("id"));
					Task task = dao.find(id);
					req.setAttribute("task", task);
				}
			}
			else {
				jsp = "/WEB-INF/jsp/list.jsp";
				List<Task> list = dao.findAll();
				req.setAttribute("list", list);
			}
			
			connection.close();
			
			RequestDispatcher dispatcher = req.getRequestDispatcher(jsp);
			dispatcher.forward(req, resp);
		}
		catch(Exception e) {
			throw new ServletException();
		}
	
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		try {
			Connection connection = DbUtil.connect();
			TaskDAO dao = new TaskDAO(connection);
			
			String method = req.getParameter("method");
			boolean success = true;
			
			if(method.equals("add")) {
				success = add(req, dao);
			}
			else {
				int id = Integer.parseInt(req.getParameter("id"));
				Task task = dao.find(id);
				
				if(method.equals("update")) {
					success = update(req, dao, task);
				}
				else if(method.equals("delete")){
					dao.delete(task);
				}
			}
			
			String jsp = null;
			if(success) {
				jsp = "/WEB-INF/jsp/list.jsp";
				
				List<Task> list = dao.findAll();
				req.setAttribute("list", list);
			}
			else {
				jsp="/WEB-INF/jsp/edit.jsp";
			}
			
			connection.close();
			
			RequestDispatcher dispatcher = req.getRequestDispatcher(jsp);
			dispatcher.forward(req, resp);
		}
		catch(Exception e) {
			throw new ServletException(e);
		}
		
	}

	private boolean add(HttpServletRequest request, TaskDAO dao) throws SQLException {
		boolean success = true;
		if(this.checkTask(dao,request)) {
			Task task = new Task();
			this.setTask(task, request);
			dao.insert(task);
			
			List<Task> list = dao.findAll();
			request.setAttribute("list", list);
		}
		else {
			success = false;
		}
		
		return success;
	}

	private boolean update(HttpServletRequest request, TaskDAO dao, Task task) throws SQLException {
		boolean success = true;
		if(this.checkTask(dao, request)) {
			this.setTask(task, request);
			dao.update(task);
		}
		else {
			request.setAttribute("task", task);
			success = false;
		}
		return success;
	}
	
	private boolean checkTask(TaskDAO dao, HttpServletRequest request) {
		boolean result = true;
		String content = request.getParameter("content");
		if(content == null || content.isEmpty()) {
			result = false;
			request.setAttribute("message", "[内容]を記述しろよ...。");
		}
		return result;
	}
	
	private void setTask(Task task, HttpServletRequest request) {
		task.setContent(request.getParameter("content"));
		
		String date = request.getParameter("deadline");
		if(date != null && !date.isEmpty()) {
			task.setDeadline(Date.valueOf(date));
		}
		
	}
	
}
