INSERT  /* _SQL_ID_ */
INTO auth_users (
	id
,	email
,	password_hash
,	user_id
,	last_login_at
,	last_pw_changed_at
) VALUES (
	/*id*/'22222222-1111-1111-1111-000000000001'
,	/*email*/'jiro@example.com'
,	/*passwordHash*/'$2a$10$6cCND88Whs8QTCDqtc8P5uCmBaagZqTLkoCe4noQGpoWl49/FY4L.'
,	/*userId*/'11111111-1111-1111-1111-000000000001'
,	/*lastLoginAt*/NULL
,	/*lastPwChangedAt*/'2026-02-15T12:34:56+09:00'
)
ON CONFLICT (id)
DO UPDATE SET
	email				=	EXCLUDED.email
,	password_hash		=	EXCLUDED.password_hash
,	user_id				=	EXCLUDED.user_id
,	last_login_at		=	EXCLUDED.last_login_at
,	last_pw_changed_at	=	EXCLUDED.last_pw_changed_at
,	updated_at			=	now()
;