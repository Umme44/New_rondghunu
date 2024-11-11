ALTER TABLE PF_NOMINEE
    ADD COLUMN IS_APPROVED BOOLEAN default false;

ALTER TABLE JHI_REFERENCES
    ADD COLUMN EMPLOYEE_ID BIGINT not null,
    ADD constraint FK_JHI_REFERENCES_EMPLOYEE_ID
        foreign key (EMPLOYEE_ID) references EMPLOYEE (ID);