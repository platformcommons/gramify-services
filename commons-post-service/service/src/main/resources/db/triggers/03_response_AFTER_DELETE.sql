USE `commons_post_db` #
CREATE  TRIGGER `commons_post_db`.`response_AFTER_DELETE` AFTER DELETE ON `response` FOR EACH ROW
BEGIN
	    If old.response_on_entity_type='ENTITY_TYPE.POST' AND old.is_active=1 then
    	    update post set comment_count= IFNULL(comment_count,0) -1  where id= old.response_on_entity_id ;
    	end If;
END; #