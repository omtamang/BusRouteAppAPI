
insert into Passenger(passenger_id, passenger_name, email, password, verified)
values(22, 'CEO of Orange','om@gmail.com', '$2a$10$GkfoWS4hQeCphUNwU0LDDOP.uwGomDoL3AEDJDQkqWNe7Aam0sRQ.', 'false');

insert into Passenger(passenger_id, passenger_name,email, password, verified)
values(23, 'CEO of banana','omyonzon@gmail.com', '$2a$10$Jd6we3DjxzWTJrYFp9er7.w7O8UkbtHMM7rnfOnzMaw/nvjJQh8c2', 'false');

insert into verify(id, passenger_id, code)
values(12, 22, 1234);

insert into verify(id, passenger_id, code)
values(13, 22, 2345);