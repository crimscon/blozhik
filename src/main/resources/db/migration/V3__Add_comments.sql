create table comments
(
	id int8 not null
		constraint comments_pk
			primary key,
	text text,
	date timestamp,
	message_id int8 not null
		constraint message_comments_message_fk
			references message,
	user_id int8 not null
		constraint message_comments_user_fk
			references usr
);