#!/bin/sh

echo "This is a pre-script for the 1.0.0 release."
COMMONS_MYSQL_HOST=127.0.0.1
COMMONS_MYSQL_USER=root
COMMONS_MYSQL_PASSWORD=root
COMMONS_MYSQL_PORT=3306

mysql -h $COMMONS_MYSQL_HOST -P$COMMONS_MYSQL_PORT -u $COMMONS_MYSQL_USER -p$COMMONS_MYSQL_PASSWORD -e 'RENAME TABLE commons_assessment_db.assessmentconfig TO commons_assessment_db.assessment_config'