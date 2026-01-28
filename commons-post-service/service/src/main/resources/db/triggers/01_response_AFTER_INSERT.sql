USE `commons_post_db` #
CREATE TRIGGER `commons_post_db`.`response_AFTER_INSERT` AFTER INSERT ON `response` FOR EACH ROW
BEGIN
	If NEW.response_on_entity_type='ENTITY_TYPE.POST' then
        update post set comment_count= IFNULL(comment_count, 0) + 1 where id= NEW.response_on_entity_id ;
    end if;
END; #