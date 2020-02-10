CREATE TABLE `users` (
    `id` char(36) NOT NULL,
    `username` varchar(256) NOT NULL,
    `password` varchar(256),
    `first_name` varchar(128),
    `last_name` varchar(128),
    `title` varchar(128),
    `profile_pic` varchar(128),
    PRIMARY KEY (`id`),
    UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `organisations` (
    `id` char(36) NOT NULL,
    `organisation_id` varchar(256) NOT NULL,
    `organisation_name` varchar(256) NOT NULL,
    `status` bit(1) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `organisation_id_UNIQUE` (`organisation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `rooms` (
    `id` char(36) NOT NULL,
    `devices` varchar(256) DEFAULT NULL,
    `organisation_id` char(36) NOT NULL,
    `password` varchar(256) NOT NULL,
    `room_number` varchar(256) NOT NULL,
    `status` bit(1) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `password_UNIQUE` (`password`),
    UNIQUE KEY `room_number_UNIQUE` (`room_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `roles_name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `users_roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `user_id` char(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `users_roles_role_id_idx` (`role_id`),
  KEY `users_roles_user_id_idx` (`user_id`),
  UNIQUE KEY `users_roles_user_id_role_id_UNIQUE` (`user_id`,`role_id`),
  CONSTRAINT `users_roles_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON UPDATE RESTRICT ON DELETE CASCADE,
  CONSTRAINT `users_roles_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON UPDATE RESTRICT ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `rooms_roles` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT,
   `role_id` bigint(20) NOT NULL,
   `room_id` char(36) NOT NULL,
   PRIMARY KEY (`id`),
   KEY `rooms_roles_role_id_idx` (`role_id`),
   KEY `rooms_roles_room_id_idx` (`room_id`),
   UNIQUE KEY `rooms_roles_room_id_role_id_UNIQUE` (`room_id`,`role_id`),
   CONSTRAINT `rooms_roles_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON UPDATE RESTRICT ON DELETE CASCADE,
   CONSTRAINT `rooms_roles_room_id` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`) ON UPDATE RESTRICT ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `privileges` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `privileges_name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `roles_privileges` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `privilege_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `roles_privileges_role_id_idx` (`role_id`),
  KEY `roles_privileges_privilege_id_idx` (`privilege_id`),
  UNIQUE KEY `roles_privileges_privilege_id_role_id_UNIQUE` (`privilege_id`,`role_id`),
  CONSTRAINT `roles_privileges_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON UPDATE RESTRICT ON DELETE CASCADE,
  CONSTRAINT `roles_privileges_privilege_id` FOREIGN KEY (`privilege_id`) REFERENCES `privileges` (`id`) ON UPDATE RESTRICT ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `token` blob DEFAULT NULL,
  `authentication_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_name` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `client_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `authentication` blob DEFAULT NULL,
  `refresh_token` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `oauth_client_details` (
  `client_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL,
  `resource_ids` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `client_secret` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `scope` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `authorized_grant_types` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `authorities` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT -1,
  `refresh_token_validity` int(11) DEFAULT -1,
  `additional_information` varchar(4096) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `autoapprove` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `token` blob DEFAULT NULL,
  `authentication` blob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
