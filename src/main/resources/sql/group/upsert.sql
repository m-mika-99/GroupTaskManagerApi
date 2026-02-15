INSERT  /* _SQL_ID_ */
INTO groups (
	id
,   name
,	description
) VALUES (
	/*id*/'33333333-1111-1111-1111-000000000001'
,	/*name*/'サンプルグループ'
,   /*description*/'今は一個しかないよ'
)
ON CONFLICT (id)
DO UPDATE SET
    name                =   EXCLUDED.name
,	description		    =	EXCLUDED.description
,	updated_at			=	now()
;