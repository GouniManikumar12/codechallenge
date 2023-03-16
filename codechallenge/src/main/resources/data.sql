INSERT INTO USERS (id, first_name, last_name, age, gender, date_of_birth)
VALUES (1, 'Kumar', 'Saketh', 30, 'M', '1990-01-01');

INSERT INTO ADDRESSES (id, type, line1, line2, postcode, city, state, user_id)
VALUES (1, 'RESIDENTIAL', '4C Main St', '4 Ridge', '12345', 'New York', 'NY', 1);

INSERT INTO ADDRESSES (id, type, line1, line2, postcode, city, state, user_id)
VALUES (2, 'WORK', '456 Business Sandy', 'Suite 100', '67890', 'San Francisco', 'CA', 1);
