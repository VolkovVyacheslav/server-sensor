create schema if not exists server_sensor;

create table server_sensor.t_sensors (
    id uuid primary key,
    name varchar not null unique constraint name_check check ( length(trim(name)) >= 3 and length(trim(name)) <= 30),
    active boolean not null
);

create table server_sensor.t_measurements (
    id uuid primary key,
    sensor_id uuid references server_sensor.t_sensors (id),
    temp numeric not null constraint temp_check check ( temp <=100 and temp >= -100),
    rain boolean not null,
    time_measurements timestamp not null
);