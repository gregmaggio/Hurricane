
CREATE TABLE basin (
	name text primary key,
	description text,
	centerX number,
	centerY number,
	zoom integer
)

CREATE TABLE storm (
	storm_key text primary key,
	basin text,
	year integer,
	storm_no integer,
	storm_name text
)

CREATE INDEX storm_name_idx ON storm (storm_name)
