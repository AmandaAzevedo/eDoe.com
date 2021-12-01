CREATE SEQUENCE user_sequence
    INCREMENT BY 1
    START WITH 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    NO CYCLE;

CREATE TABLE user_system (
    id int8 NOT NULL DEFAULT nextval('user_sequence'),
    name varchar(60) NOT NULL,
    email varchar(40) NOT NULL,
    password varchar(30) NOT NULL,
    telephone varchar(16),
    user_category varchar(255),
    user_role varchar(15) NOT NULL,
    unique(email),
    CONSTRAINT user_pkey PRIMARY KEY (id)

)
