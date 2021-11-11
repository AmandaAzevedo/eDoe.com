--private Long id;
--     private User requestingUser;
--     private UserRoleEnum requestedRole;
--     private boolean evaluationResult;

CREATE SEQUENCE request_sequence
    INCREMENT BY 1
    START WITH 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    NO CYCLE;

CREATE TABLE request (
    id int8 NOT NULL DEFAULT nextval('request_sequence'),
    user_role_request varchar(15) NOT NULL,
    evaluation_result boolean NOT NULL,
    user_id int8 NOT NULL,
    CONSTRAINT user_id_FK FOREIGN KEY (user_id) REFERENCES user_system(id) ON DELETE CASCADE

)
