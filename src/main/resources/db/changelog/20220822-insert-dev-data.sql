--liquibase formatted sql
--changeset hungdh:20220822_2

insert into CUSTOMER(customer_email, customer_status, created_at)
values ('customer1@email.com', '1', current_timestamp);
insert into CUSTOMER(customer_email, customer_status, created_at)
values ('customer2@email.com', '1', current_timestamp);
insert into CUSTOMER(customer_email, customer_status, created_at)
values ('customer3@email.com', '1', current_timestamp);
insert into CUSTOMER(customer_email, customer_status, created_at)
values ('customer4@email.com', '1', current_timestamp);
insert into CUSTOMER(customer_email, customer_status, created_at)
values ('customer5@email.com', '1', current_timestamp);

-- customer1@email.com
insert into CART(CUSTOMER_ID, CREATED_AT)
values ((select c.CUSTOMER_ID from CUSTOMER c where c.CUSTOMER_EMAIL = 'customer1@email.com'),
        current_timestamp);

insert into CART(CUSTOMER_ID, CREATED_AT)
values ((select c.CUSTOMER_ID from CUSTOMER c where c.CUSTOMER_EMAIL = 'customer1@email.com'),
        current_timestamp);
insert into CART(CUSTOMER_ID, CREATED_AT)
values ((select c.CUSTOMER_ID from CUSTOMER c where c.CUSTOMER_EMAIL = 'customer1@email.com'),
        current_timestamp);

-- customer2@email.com
insert into CART(CUSTOMER_ID, CREATED_AT)
values ((select c.CUSTOMER_ID from CUSTOMER c where c.CUSTOMER_EMAIL = 'customer2@email.com'),
        current_timestamp);
insert into CART(CUSTOMER_ID, CREATED_AT)
values ((select c.CUSTOMER_ID from CUSTOMER c where c.CUSTOMER_EMAIL = 'customer2@email.com'),
        current_timestamp);
