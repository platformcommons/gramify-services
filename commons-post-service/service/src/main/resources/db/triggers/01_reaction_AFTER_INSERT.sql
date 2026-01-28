USE `commons_post_db` #
CREATE  TRIGGER `commons_post_db`.`reaction_AFTER_INSERT` AFTER INSERT ON `reaction` FOR EACH ROW
BEGIN
	case
    	when NEW.reacted_on_entity_type='ENTITY_TYPE.POST' then
            update post set react_count= IFNULL(react_count, 0) + 1 where id= NEW.reacted_on_entity_id ;
    	when NEW.reacted_on_entity_type='ENTITY_TYPE.RESPONSE' then
            update response set react_count= IFNULL(react_count, 0) + 1 where id= NEW.reacted_on_entity_id ;
    end case;
END; #
