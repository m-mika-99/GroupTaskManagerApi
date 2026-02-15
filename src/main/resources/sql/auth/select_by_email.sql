SELECT	/* _SQL_ID_ */
	id
,	email
,	password_hash
,   user_id
,	last_login_at
,	last_pw_changed_at
FROM
	auth_users
WHERE
	auth_users.email = /*email*/'taro@example.com'
;