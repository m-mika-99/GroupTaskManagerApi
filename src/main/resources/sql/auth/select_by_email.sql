SELECT	/* _SQL_ID_ */
	id
,	email
,	password_hash
,   user_id
FROM
	auth_users
WHERE
	auth_users.email = /*email*/'taro@example.com'
;