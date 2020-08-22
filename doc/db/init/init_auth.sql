-- --------------
-- 初始化client数据
-- --------------


INSERT INTO `cs_auth`.`oauth_client_details`(`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`, `description`, `is_deleted`, `create_time`, `create_user_id`, `create_user_name`, `update_time`, `update_user_id`, `update_user_name`) VALUES ('app', '', '$2a$10$EV3oHC67efFrb9gEidXSzesTKIkHEEpoJsNJwnWo79d88PyuEvHMW', 'read', 'password,refresh_token', '', '', 7200, 3600, '{}', '', NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
