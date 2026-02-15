INSERT  /* _SQL_ID_ */
INTO users (
	id
,	display_name
) VALUES (
	/*id*/'11111111-1111-1111-1111-000000000001'
,	/*displayName*/'サンプル太郎'
)
ON CONFLICT (id)
DO UPDATE SET
	display_name		=	EXCLUDED.display_name
,	updated_at			=	now()
;