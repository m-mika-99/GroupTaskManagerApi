SELECT	/* _SQL_ID_ */
	id
,	user_id
,	group_id
,	joined_at
,	role_code
,	display_name_override
,	status_code
FROM
	members
WHERE
	group_id = /*groupId*/'33333333-1111-1111-1111-000000000001'
;