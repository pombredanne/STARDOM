#!/bin/bash

php5=`which php`
max_records=5000
echo "Using $php5"


for l in {0..21};
do
  from=$(($l * $max_records))
  echo "Geting 5000 records from the database starting at $from"
  $php5 app/console iccs:scm $from $max_records;
  sleep 30;
done;

