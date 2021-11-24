CREATE SEQUENCE item_sequence
    INCREMENT BY 1
    START WITH 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    NO CYCLE;

CREATE TABLE item_table (
    id int8 NOT NULL DEFAULT nextval('item_sequence'),
    owner_id int8 NOT NULL,
    quantity int8 NOT NULL,
    detailed_description varchar(250) NOT NULL,
    CONSTRAINT owner_id_FK FOREIGN KEY (owner_id) REFERENCES user_system(id) ON DELETE CASCADE
);

CREATE TABLE item_descriptor(
    item_id int8 NOT NULL,
    descriptor_id int8 NOT NULL,
    CONSTRAINT item_id_FK FOREIGN KEY (item_id) REFERENCES item_table(id),
    CONSTRAINT descriptor_id_FK FOREIGN KEY (descriptor_id) REFERENCES descriptor_table(id)
)