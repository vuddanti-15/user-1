CREATE SEQUENCE public.role_sequence
  
  create table if not exists role
  
  (
	role_id integer not null default nextval('role_sequence'::regclass),
	name character varying(50) NOT NULL,
	CONSTRAINT role_pkey PRIMARY KEY (role_id),
    CONSTRAINT role_name_unique_index UNIQUE (name)
	)

INSERT INTO role(role_id, name)VALUES (1, 'ROLE_SUPER_ADMIN'),(2, 'ROLE_ADMIN'),(3, 'ROLE_USER');