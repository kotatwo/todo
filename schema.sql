SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS tasks;




/* Create Tables */

CREATE TABLE tasks
(
	-- タスクID
	-- 主キー
	id int NOT NULL AUTO_INCREMENT COMMENT 'タスクID
主キー',
	-- 内容
	content varchar(256) NOT NULL COMMENT '内容',
	-- しめきり
	deadline date COMMENT 'しめきり',
	-- タスク情報登録日時
	created_at timestamp COMMENT 'タスク情報登録日時',
	-- タスク情報最終更新日時
	updated_at timestamp COMMENT 'タスク情報最終更新日時',
	PRIMARY KEY (id)
);



