ALTER TABLE RECRUITMENT_REQUISITION_FORM
    ADD COLUMN IF NOT EXISTS  IS_DELETED       BOOLEAN   DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS  DELETED_BY_ID    BIGINT,

    ADD constraint FK_RECRUITMENT_REQUISITION_FORM_DELETED_BY_ID
        foreign key (DELETED_BY_ID) references JHI_USER;
