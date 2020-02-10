INSERT INTO `privileges` (`id`, `name`) VALUES ('1', 'ADMIN_PRIVILEGE');
INSERT INTO `privileges` (`id`, `name`) VALUES ('2', 'ORGANISATION_PRIVILEGE');
INSERT INTO `privileges` (`id`, `name`) VALUES ('3', 'ROOM_PRIVILEGE');
INSERT INTO `roles` (`id`, `name`) VALUES ('1', 'ROLE_ADMIN');
INSERT INTO `roles` (`id`, `name`) VALUES ('2', 'ROLE_ORGANISATION');
INSERT INTO `roles` (`id`, `name`) VALUES ('3', 'ROLE_ROOM');
INSERT INTO `users` (`id`, `username`, `password`) VALUES ('66da546f-9c1a-4b7d-a9b8-e938a6f228c5', 'user', '$2y$12$PPovTkvok9F4PdNT83MPvOustgvFIznJPibFlkBWg7rfC1qL2T3ui');
INSERT INTO `roles_privileges` (`id`, `role_id`, `privilege_id`) VALUES ('1', '1', '1');
INSERT INTO `roles_privileges` (`id`, `role_id`, `privilege_id`) VALUES ('2', '2', '2');
INSERT INTO `roles_privileges` (`id`, `role_id`, `privilege_id`) VALUES ('3', '3', '3');
INSERT INTO `users_roles` (`id`, `role_id`, `user_id`) VALUES ('1', '1', '66da546f-9c1a-4b7d-a9b8-e938a6f228c5');
INSERT INTO `organisations` (`id`, `organisation_id`, `organisation_name`, `status`) VALUES ('b646822e-d464-4047-8462-100e24a64a8f', '2020', 'TST', b'1');
INSERT INTO `rooms` (`id`, `organisation_id`, `password`, `room_number`, `status`) VALUES ('26415820-c499-4b4c-bf64-52b169b2978b', 'b646822e-d464-4047-8462-100e24a64a8f', '$2y$12$NnFFBpwudpRxZEkTugYvr.4r9p3u7JzbR5XuI87HsxxL4WC7lZe5e', '100', b'1');
INSERT INTO `rooms_roles` (`id`, `role_id`, `room_id`) VALUES ('1', '3', '26415820-c499-4b4c-bf64-52b169b2978b');
