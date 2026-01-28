USE commons_post_db #
CREATE PROCEDURE `get_latest_n_response`(IN postIds TEXT,IN size int)
BEGIN
     DECLARE strLen int default 0;
     DECLARE SubStrLen int default 0;
     DECLARE finalResponseIds LONGTEXT default '';
     DECLARE concatenatedIds LONGTEXT default '';

     IF postIds IS NOT NULL then

		response_loop: LOOP
           SELECT group_concat(r1.id) FROM
	           (SELECT id FROM response
                WHERE response_on_entity_id = substring_index(postIds,',',1)
	            AND response_on_entity_type='ENTITY_TYPE.POST' AND is_active=1
                ORDER BY id DESC LIMIT size) r1
			LIMIT 1 INTO concatenatedIds;


          IF concatenatedIds IS NOT NULL then
            SET finalResponseIds=CONCAT(finalResponseIds,',',concatenatedIds);
          END IF;
          SET concatenatedIds='';

          IF char_length(postIds) = substring_index(postIds,',',1)  THEN
             LEAVE response_loop;
          ELSE
			 SET SubStrLen=char_length(SUBSTRING_INDEX(postIds, ',', 1))+2;
	         SET strLen=char_length(postIds)-2;
             SET postIds=MID(postIds,SubStrLen,strLen);
          END IF;

	    END LOOP response_loop;
     END IF;

     SELECT SUBSTRING(finalResponseIds,2,LENGTH(finalResponseIds)) as reactionIds;
END; #