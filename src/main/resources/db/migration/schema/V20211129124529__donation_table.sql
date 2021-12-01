CREATE SEQUENCE donation_sequence
    INCREMENT BY 1
    START WITH 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    NO CYCLE;

CREATE TABLE donation (
    id int8 NOT NULL DEFAULT nextval('donation_sequence'),
    donation_date timestamp NOT NULL,
    donor_user_id int8 NOT NULL,
    receiving_user_id int8 NOT NULL,
    description_donated_item varchar(250) NOT NULL,
    quantity_of_donated_item int8 NOT NULL,
    CONSTRAINT donor_user_id_FK FOREIGN KEY (donor_user_id) REFERENCES user_system(id),
    CONSTRAINT receiving_user_id_FK FOREIGN KEY (receiving_user_id) REFERENCES user_system(id),
    CONSTRAINT donation_pkey PRIMARY KEY (id)

)
