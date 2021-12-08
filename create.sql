create table appointment (appointment_id  bigserial not null, appointment_status varchar(255), created_at timestamp, description varchar(255), last_modified_at timestamp, total_price int4, customer_customer_id int8, primary key (appointment_id))
create table appointment_services (appointment_id int8 not null, service_id int8 not null)
create table customer (customer_id  bigserial not null, email varchar(255), first_name varchar(255), last_name varchar(255), vehicle_model varchar(255), vin varchar(255), primary key (customer_id))
create table service (service_id  bigserial not null, duration int4, price int4, service_type varchar(255), primary key (service_id))
create table slot (slot_id  bigserial not null, start_date date, start_hours int4, station_id int8, appointment_appointment_id int8, primary key (slot_id))
create table slot_view (slot_id int8 not null, start_date date, station_id int8, primary key (slot_id))
create table status (status_id  bigserial not null, status_name varchar(255), description varchar(255), primary key (status_id))
alter table appointment add constraint FKkhkxjn0gtqyxcm33ovpaht875 foreign key (customer_customer_id) references customer
alter table appointment_services add constraint FKlftn8g6pb51cx9r5dwacexm2b foreign key (service_id) references service
alter table appointment_services add constraint FKkv6gwfscv4td54g96ra0p0gn0 foreign key (appointment_id) references appointment
alter table slot add constraint FK23ykmvkya3vxp54syhf3g8b4d foreign key (appointment_appointment_id) references appointment
