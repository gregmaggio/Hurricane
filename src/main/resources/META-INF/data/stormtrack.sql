
CREATE TABLE stormtrack (
	storm_no integer,
	storm_name text,
	track_no integer,
	year integer,
	month integer,
	day integer,
	hours integer,
	minutes integer,
	record_identifier text,
	status text,
	latitude number,
	latitude_hemisphere text,
	longitude number,
	longitude_hemisphere text,
	max_wind_speed number,
	min_pressure number,
	x number,
	y number,
	PRIMARY KEY (storm_no, track_no)
)
