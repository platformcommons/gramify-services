USE `commons_post_db` #
CREATE TRIGGER `commons_post_db`.`user_read_event_log_AFTER_INSERT` AFTER INSERT ON `user_read_event_log` FOR EACH ROW
BEGIN
    IF NEW.entity_type = 'ENTITY_TYPE.POST' THEN
        UPDATE commons_post_db.post
        SET read_count = IFNULL(read_count, 0) + 1
        WHERE id = NEW.entity_id;
    END IF;
END; #
