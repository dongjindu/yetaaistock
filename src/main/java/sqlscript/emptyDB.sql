set foreign_key_checks = 0;
SET @tables = '';
SELECT GROUP_CONCAT(table_schema, '.', table_name) INTO @tables
  FROM information_schema.tables 
  WHERE table_schema = 'invest001';
--    and not(table_name = 'zzdummy'); -- specify DB name here.
SET @tables = CONCAT('DROP TABLE if exists ', @tables);
select @tables;
PREPARE stmt FROM @tables;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
set foreign_key_checks = 1;

