USE `commons_post_db`#
CREATE TRIGGER `commons_post_db`.`reaction_AFTER_DELETE` AFTER DELETE ON `reaction` FOR EACH ROW
BEGIN
    IF OLD.reacted_on_entity_type='ENTITY_TYPE.POST' AND OLD.is_active=1 THEN
 	     IF OLD.reaction_status='ACTIVE' THEN
 			      update post set react_count= IFNULL(react_count,0) -1  where id= OLD.reacted_on_entity_id ;
 	     END IF;
    END IF;
END; #

