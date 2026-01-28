USE `commons_domain_db` #

CREATE PROCEDURE `commons_domain_db`.`addTagHierarchy`(
    IN parentTagId BIGINT,
    IN tagId BIGINT,
    IN tenantId BIGINT,
    IN userId BIGINT
)
BEGIN
    SET SESSION TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;

INSERT INTO `commons_domain_db`.`tag_hierarchy`(`created_by_tenant`,`created_by_user`,`created_timestamp`,`last_modified_by_tenant`,
`last_modified_by_user`,`last_modified_timestamp`,`unique_identifier`,`is_active`,`tenant_id`,`depth`,`parent_tag`,`tag`)
 (
  SELECT tenantId,
         userId,
         NOW(),
         tenantId,
         userId,
         NOW(),
         UUID(),
         1,
         tenantId,
         ParentDepth + childDepth + 1 AS depth,
         parent_tag,
         tag
  FROM ( SELECT parent_tag, depth AS ParentDepth FROM commons_domain_db.tag_hierarchy WHERE tag = parentTagId AND is_active = 1
         UNION
        (SELECT parentTagId, 0)
       ) AS a
  JOIN (SELECT tag,
                depth AS ChildDepth
         FROM commons_domain_db.tag_hierarchy
         WHERE parent_tag = tagId
           AND is_active = 1
         UNION (SELECT tagId, 0)
       ) AS b
) on duplicate key update last_modified_timestamp =now();

 Select "Success";
END; #