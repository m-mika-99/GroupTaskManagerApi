-- =========================
-- users
-- =========================
CREATE TABLE users (
	id						UUID			PRIMARY KEY
,	display_name			VARCHAR(256)	NOT NULL
,	created_at				TIMESTAMP 	    NOT NULL	DEFAULT CURRENT_TIMESTAMP
,	updated_at				TIMESTAMP    	NOT NULL	DEFAULT CURRENT_TIMESTAMP
);


-- =========================
-- auth_users
-- =========================
CREATE TABLE auth_users (
	id						UUID			PRIMARY KEY
,	email					VARCHAR(256)	NOT NULL	UNIQUE
,	password_hash			VARCHAR(256)	NOT NULL
,	user_id					UUID			NOT NULL
,	last_login_at			TIMESTAMP
,	last_pw_changed_at		TIMESTAMP
,	created_at				TIMESTAMP    	NOT NULL	DEFAULT CURRENT_TIMESTAMP
,	updated_at				TIMESTAMP    	NOT NULL	DEFAULT CURRENT_TIMESTAMP

,	CONSTRAINT fk_auth_user_user
		FOREIGN KEY (user_id)
		REFERENCES users(id)
);


-- =========================
-- groups
-- =========================
CREATE TABLE groups (
	id						UUID			PRIMARY KEY
,	name					VARCHAR(256)	NOT NULL
,	description				TEXT
,	created_at				TIMESTAMP    	NOT NULL	DEFAULT CURRENT_TIMESTAMP
,	updated_at				TIMESTAMP    	NOT NULL	DEFAULT CURRENT_TIMESTAMP
);


-- =========================
-- memebrs
-- =========================
CREATE TABLE members (
	id						UUID			PRIMARY KEY
,	user_id					UUID			NOT NULL
,	group_id				UUID			NOT NULL
,	joined_at				TIMESTAMP    	NOT NULL
,	role_code				smallint		NOT NULL
,	display_name_override	VARCHAR(256)
,	status_code				smallint		NOT NULL
,	created_at				TIMESTAMP    	NOT NULL	DEFAULT CURRENT_TIMESTAMP
,	updated_at				TIMESTAMP    	NOT NULL	DEFAULT CURRENT_TIMESTAMP

,	UNIQUE (group_id, user_id)

,	CONSTRAINT fk_member_user
		FOREIGN KEY (user_id)
		REFERENCES users(id)

,	CONSTRAINT fk_member_group
		FOREIGN KEY (group_id)
		REFERENCES groups(id)
);

CREATE INDEX idx_members_user_id ON members(user_id);


-- =========================
-- tasks
-- =========================
CREATE TABLE tasks (
	id						UUID			PRIMARY KEY
,	group_id				UUID			NOT NULL
,	title					VARCHAR(256)	NOT NULL
,	description				TEXT
,	issuer_member_id		UUID			NOT NULL
,	issued_at				TIMESTAMP		NOT NULL
,	deadline_at				TIMESTAMP		NOT NULL
,	status_code				smallint		NOT NULL
,	archived_at				TIMESTAMP
,	created_at				TIMESTAMP    	NOT NULL	DEFAULT CURRENT_TIMESTAMP
,	updated_at				TIMESTAMP    	NOT NULL	DEFAULT CURRENT_TIMESTAMP


,	CONSTRAINT fk_task_group
		FOREIGN KEY (group_id)
		REFERENCES groups(id)

,	CONSTRAINT fk_task_member
		FOREIGN KEY (issuer_member_id)
		REFERENCES members(id)
);

CREATE INDEX idx_tasks_group_deadline ON tasks(group_id, deadline_at);

CREATE INDEX idx_tasks_issuer_member_id ON tasks(issuer_member_id);


-- =========================
-- assignments
-- =========================
CREATE TABLE assignments (
	member_id				UUID			NOT NULL
,	task_id					UUID			NOT NULL
,	status_code				smallint		NOT NULL
,	done_at					TIMESTAMP
,	created_at				TIMESTAMP    	NOT NULL	DEFAULT CURRENT_TIMESTAMP
,	updated_at				TIMESTAMP    	NOT NULL	DEFAULT CURRENT_TIMESTAMP

,	PRIMARY KEY (member_id, task_id)

,	CONSTRAINT fk_assignment_member
		FOREIGN KEY (member_id)
		REFERENCES members(id)

,	CONSTRAINT fk_assignment_task
		FOREIGN KEY (task_id)
		REFERENCES tasks(id)
);

CREATE INDEX idx_assignments_task_id ON assignments(task_id);


------------------------------
-- rollback
------------------------------
/*

DROP TABLE assignments;
DROP TABLE tasks;
DROP TABLE members;
DROP TABLE groups;
DROP TABLE auth_users;
DROP TABLE users;

