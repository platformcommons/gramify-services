USE `commons_post_db` #
CREATE  TRIGGER `commons_post_db`.`response_AFTER_UPDATE` AFTER UPDATE ON `response` FOR EACH ROW
BEGIN
 If NEW.is_active!=old.is_active then
	If NEW.is_active=1 and old.response_on_entity_type='ENTITY_TYPE.POST' then
        update post set comment_count= IFNULL(comment_count, 0) + 1 where id= old.response_on_entity_id ;
    end if;
	If NEW.is_active=0 and old.response_on_entity_type='ENTITY_TYPE.POST' then
        update post set comment_count= IFNULL(comment_count, 1) - 1 where id= old.response_on_entity_id ;
    end if;
 end if;
END; #