USE `commons_domain_db` #
CREATE  PROCEDURE `addSkillHierarchy`(IN parentSkillId bigint, IN skillId bigint,IN tenantId bigint , IN userId bigint)
BEGIN

SET SESSION TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;

INSERT INTO `commons_domain_db`.`skill_hierarchy`(`created_by_tenant`,`created_by_user`,`created_timestamp`,`last_modified_by_tenant`,
                                                   `last_modified_by_user`,`last_modified_timestamp`,`unique_identifier`,`is_active`,`tenant_id`,`depth`,`parent_Skill`,`Skill`)
    (SELECT tenantId, userId, now(), tenantId , userId , now(), uuid(), 1, tenantId , ParentDepth + childDepth + 1 AS depth,  parent_Skill,skill
     FROM
         (
             (SELECT
                parent_Skill, depth AS ParentDepth
             FROM
                commons_domain_db.skill_hierarchy
             WHERE
                skill = parentSkillId and is_active=1 UNION (SELECT parentSkillId, 0)
            ) AS a
        )
         JOIN
        (
            (SELECT
                skill, depth AS ChildDepth
             FROM
                commons_domain_db.skill_hierarchy
             WHERE
                parent_Skill = SkillId and is_active=1 UNION (SELECT SkillId, 0)
            ) AS b
        )
     )
    on duplicate key update
                         last_modified_timestamp =now();

Select "Success";
END ; #