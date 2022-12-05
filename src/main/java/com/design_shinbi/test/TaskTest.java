package com.design_shinbi.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.design_shinbi.model.dao.TaskDAO;
import com.design_shinbi.model.entity.Task;
import com.design_shinbi.util.DbUtil;

class TaskTest {

	@Test
	void test() throws ClassNotFoundException, SQLException {
		Connection connection = DbUtil.connect();
		
		TaskDAO dao = new TaskDAO(connection);
		
		int oldCount = dao.count();
		System.out.println("現在のレコード数: " + oldCount);
		
		Task task = new Task();
		task.setContent("追加のテストをする。");
		dao.insert(task);
		
		int newCount = dao.count();
		System.out.println("現在のレコード数: " + newCount);
		if(newCount != oldCount + 1) {
			connection.close();
			fail("レコードの追加に失敗しました。");
		}
		
		System.out.println("現在の一覧");
		List<Task> list = dao.findAll();
		for(Task element : list) {
			display(element);
		}
		
		task = list.get(list.size() - 1);
		
		task.setContent("更新のテスト");
		task.setDeadline(Date.valueOf("2022-10-26"));
		dao.update(task);
		
		System.out.println("現在の一覧");
		list = dao.findAll();
		for(Task element : list) {
			display(element);
		}
		
		Task dbTask = dao.find(task.getId());
		
		dao.delete(task);
		newCount = dao.count();
		
		connection.close();
		
		if(!dbTask.equals(dbTask)) {
			fail("更新に失敗しました。");
		}
		
		if(newCount != oldCount) {
			fail("削除に失敗しました。");
		}
	}
	
	private void display(Task task) {
		System.out.println(
			"id: " + task.getId()
			+ "   content: " + task.getContent()
			+ "   deadline: " + task.getDeadline()
			+ "   created_at: " + task.getCreatedAt()
			+ "   updated_at: " + task.getUpdatedAt()
		);		
	}
}
