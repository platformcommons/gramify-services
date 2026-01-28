START TRANSACTION;

INSERT INTO `tenant` (
	`created_by_tenant`, `created_by_user`, `created_timestamp`, `last_modified_by_tenant`, `last_modified_by_user`, `last_modified_timestamp`, `unique_identifier`,
	`realm_representation`, `tenant_email`, `tenant_login`, `tenant_name`, `tenant_uuid`)
	VALUES (1, 1, now() , 1, 1,  now(), 'platform', 'master' , 'admin@platformcommons.com', 'platform',
			'platform', 'platform');

INSERT INTO `user` (
	`created_by_tenant`, `created_by_user`, `created_timestamp`, `last_modified_by_tenant`, `last_modified_by_user`, `last_modified_timestamp`, `unique_identifier`,
	`app_created_timestamp`, `app_last_modified_timestamp`, `created_by_app_version`, `inactive_reason`, `inactive_timestamp`, `is_active`, `last_modified_by_app_version`,
	`tenant_id`, `first_name`, `last_name`, `realm_representation`, `user_login`)
	VALUES (1, 1,now(), 1, 1, now() ,'platform-admin',
			null,null,null,null,null,true ,null,1,'admin',null,'platform-admin','admin');

INSERT INTO `role` (
	`created_by_tenant`, `created_by_user`, `created_timestamp`, `last_modified_by_tenant`, `last_modified_by_user`, `last_modified_timestamp`, `unique_identifier`,
	`app_created_timestamp`, `app_last_modified_timestamp`, `created_by_app_version`, `inactive_reason`, `inactive_timestamp`, `is_active`, `last_modified_by_app_version`,
	`tenant_id`, `realm_representation`, `role_code`,`role_name`)
	VALUES (1, 1,  now() , 1, 1,  now(),'platform-admin',
			null,null,null,null,null,true ,null,1,'role-platform-admin','role.platform.admin','Platform Admin');
COMMIT;