package com.design_shinbi.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.design_shinbi.model.entity.Task;

public class TaskDAO {
	private Connection connection;
	
	public TaskDAO(Connection connection) {
		this.connection = connection;
	}
	
	public int count() throws SQLException {
		String sql = "SELECT count(*) AS count From tasks;";

		Statement statement = this.connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		int count = 0;
		if(resultSet.next()) {
			count = resultSet.getInt("count");
		}
		
		resultSet.close();
		statement.close();
		
		return count;
	}
	
	private Task getTask(ResultSet resultSet) throws SQLException {
		Task task = new Task();

		task.setId(resultSet.getInt("id"));
		task.setContent(resultSet.getString("content"));
		task.setDeadline(resultSet.getDate("deadline"));
		task.setCreatedAt(resultSet.getTimestamp("created_at"));
		task.setUpdatedAt(resultSet.getTimestamp("updated_at"));
		
		return task;
	}
	
	public List<Task> findAll() throws SQLException {
		String sql = "SELECT * FROM tasks;";
		
		Statement statement = this.connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		List<Task> list = new ArrayList<Task>();
		while(resultSet.next()) {
			Task task = this.getTask(resultSet);
			list.add(task);
		}

		resultSet.close();
		statement.close();
		
		return list;
	}
	
	public Task find(int id) throws SQLException {	
		String sql = "SELECT * FROM tasks WHERE id = ?;";
		
		PreparedStatement statement = this.connection.prepareStatement(sql);
		statement.setInt(1,id);
		ResultSet resultSet = statement.executeQuery();
		

		Task task = null;
		if(resultSet.next()) {
			task = this.getTask(resultSet);
		}
		
		resultSet.close();
		statement.close();
		
		return task;
	}
	
	public void insert(Task task) throws SQLException {

		String sql = "INSERT INTO tasks(content, deadline, created_at, updated_at)"
				+ "VALUES(?, ?, ?, ?)";
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		PreparedStatement statement = this.connection.prepareStatement(sql);
		statement.setString(1, task.getContent());
		if(task.getDeadline() == null) {
			statement.setNull(2, Types.DATE);
		}
		else {
			statement.setDate(2, task.getDeadline());
		}
		statement.setTimestamp(3, now);
		statement.setTimestamp(4, now);
		
		statement.executeUpdate();
		statement.close();
	}
	
	public void update(Task task) throws SQLException {

		String sql = "UPDATE tasks SET content = ?, deadline = ?, updated_at = ? WHERE id = ?;";
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		PreparedStatement statement = this.connection.prepareStatement(sql);
		statement.setString(1, task.getContent());
		if(task.getDeadline() == null) {
			statement.setNull(2, Types.DATE);
		}
		else {
			statement.setDate(2, task.getDeadline());
		}
		statement.setTimestamp(3, now);
		statement.setInt(4, task.getId());
		
		statement.executeUpdate();
		statement.close();
	}
	
	public void delete(Task task) throws SQLException {

		String sql = "DELETE FROM tasks WHERE id = ?";
		
		PreparedStatement statement = this.connection.prepareStatement(sql);
		statement.setInt(1, task.getId());
		
		statement.executeUpdate();
		statement.close();
	}
}
