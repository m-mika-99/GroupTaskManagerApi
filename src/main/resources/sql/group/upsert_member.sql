INSERT  /* _SQL_ID_ */
INTO members (
	id
,   user_id
,   group_id
,   joined_at
,   role_code
,   display_name_override
,   status_code
) VALUES (
	/*id*/'44444444-1111-1111-1111-000000000001'
,	/*userId*/'11111111-1111-1111-1111-000000000001'
,   /*groupId*/'33333333-1111-1111-1111-000000000001'
,   /*joinedAt*/'2026-02-15 13:54:28.863678'
,   /*roleCode*/3
,   /*displayNameOverride*/'管理メンバ太郎'
,   /*statusCode*/1
)
ON CONFLICT (id)
DO UPDATE SET
    user_id                 =   EXCLUDED.user_id
,   group_id                =   EXCLUDED.group_id
,   joined_at               =   EXCLUDED.joined_at
,   role_code               =   EXCLUDED.role_code
,   display_name_override   =   EXCLUDED.display_name_override
,   status_code             =   EXCLUDED.status_code
,   updated_at              =   now()
;