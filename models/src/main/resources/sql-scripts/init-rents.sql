INSERT INTO rents (title, startDate, endDate, client_id, status, price, bike_id) VALUES ('smart watch', TIMESTAMP '2017-03-01 11:23:38', TIMESTAMP '2017-03-03 11:23:38', 1, 'queued', 50.12, 1);
INSERT INTO rents (title, startDate, endDate, client_id, status, price, bike_id) VALUES ('laptop', TIMESTAMP '2017-04-12 01:12:38', TIMESTAMP '2017-03-02 11:23:38', 1, 'queued', 12.65, 2);
INSERT INTO rents (title, startDate, endDate, client_id, status, price, bike_id) VALUES ('tablet', TIMESTAMP '2017-06-17 12:00:08', TIMESTAMP '2017-03-06 11:23:38', 3, 'queued', 54.89, 3);


INSERT INTO clients (name, surname) VALUES ('kupec1', 'priimek1');
INSERT INTO clients (name, surname) VALUES ('kupec2', 'priimek2');
INSERT INTO clients (name, surname) VALUES ('kupec3', 'priimek3');

INSERT INTO bikes (model, size, enabled) VALUES ('sport', 'L', true);
INSERT INTO bikes (model, size, enabled) VALUES ('tourer', 'M', true);
INSERT INTO bikes (model, size, enabled) VALUES ('mountain', 'XL', true);