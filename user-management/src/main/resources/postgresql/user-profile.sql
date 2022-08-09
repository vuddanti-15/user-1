
CREATE TYPE public.gender_type AS ENUM
    ('M', 'F', 'T');
    
CREATE TABLE IF NOT EXISTS public.user_profile
(
    user_id integer NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255),
    mobile_number character varying(255) NOT NULL,
    CONSTRAINT user_profile_user_id_pkey PRIMARY KEY (user_id),
    CONSTRAINT user_profile_mapping_key FOREIGN KEY (user_id)
        REFERENCES public.user_credential (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
)