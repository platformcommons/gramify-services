USE `commons_post_db` #
CREATE TRIGGER `commons_post_db`.`reaction_AFTER_UPDATE` AFTER UPDATE ON `reaction` FOR EACH ROW
BEGIN
 IF NEW.is_active=1 AND OLD.reacted_on_entity_type='ENTITY_TYPE.POST' THEN
 	IF OLD.is_active=0 AND NEW.reaction_status='ACTIVE' then
 		 update post set react_count= IFNULL(react_count, 0) + 1 where id= old.reacted_on_entity_id ;
 	END IF;
 	IF OLD.is_active=1 THEN
 		CASE
 			when OLD.reaction_status!=NEW.reaction_status THEN
 			     IF NEW.reaction_status='ACTIVE' THEN
 			           update post set react_count= IFNULL(react_count, 0) + 1
 			           where id= OLD.reacted_on_entity_id ;
 			     END IF;
 			     IF NEW.reaction_status='INACTIVE' THEN
                        update post set react_count= IFNULL(react_count, 1) - 1
                        where id= OLD.reacted_on_entity_id ;
                 END IF;
        END CASE;
    END IF;
 END IF;

 IF NEW.is_active=0 AND OLD.reacted_on_entity_type='ENTITY_TYPE.POST' THEN
 	IF OLD.is_active=1 AND NEW.reaction_status='ACTIVE' then
 		 update post set react_count= IFNULL(react_count, 1) - 1 where id= old.reacted_on_entity_id ;
 	END IF;
 END IF;
END; #



