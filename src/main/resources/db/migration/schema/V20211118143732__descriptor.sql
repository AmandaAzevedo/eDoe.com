CREATE SEQUENCE descriptor_sequence
    INCREMENT BY 1
    START WITH 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    NO CYCLE;

CREATE TABLE descriptor_table (
    id int8 NOT NULL DEFAULT nextval('descriptor_sequence'),
    name varchar(60) NOT NULL,
    unique(name)
)
