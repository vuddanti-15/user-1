CREATE SEQUENCE public.user_credentails_sequence;



CREATE TABLE IF NOT EXISTS user_credential
(
    user_id integer NOT NULL DEFAULT nextval('user_credentails_sequence'::regclass),
    user_name character varying(255) NOT NULL,
	email_id character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (user_id),
    CONSTRAINT user_name_unique_index UNIQUE (user_name),
	CONSTRAINT email_id_unique_index UNIQUE (email_id)
)

INSERT INTO user_credential(user_id, user_name,email_id,password)
  VALUES (1, 'superadmin@demo.com','superadmin@demo.com', /*admin*/'$2a$08$WhflkbQx9CwspjvWr2gGu.Mc9zGUshO5u70R.b2rCvjc91DS2ABbK')
  