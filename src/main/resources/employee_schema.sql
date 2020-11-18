-- Create employee database
create database employee;

-- Checks if extension is available
select * from pg_extension;

-- Loads uuid-ossp extension
CREATE EXTENSION "uuid-ossp";

-- Create employee_info table
create table employee_info (
	employee_id uuid primary key DEFAULT uuid_generate_v4(),
	first_name varchar(50) not null,
	last_name varchar(50) not null,
	email varchar(255) unique not null,
	created_on timestamp not null DEFAULT now()
);
