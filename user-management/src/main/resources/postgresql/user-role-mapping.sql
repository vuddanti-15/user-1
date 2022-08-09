 CREATE TABLE IF NOT EXISTS public.user_role_mapping
(
    user_id integer NOT NULL,
    role_id integer NOT NULL,
    CONSTRAINT user_role_user_fkey FOREIGN KEY (user_id)
        REFERENCES user_credential (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT user_role_role_fkey FOREIGN KEY (role_id)
        REFERENCES public.role (role_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)

INSERT INTO user_role_mapping(user_id, role_id) VALUES (1, 1);