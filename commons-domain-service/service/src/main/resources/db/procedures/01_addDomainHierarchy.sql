USE `commons_domain_db` #
CREATE  PROCEDURE `addDomainHierarchy`(IN parentDomainId bigint, IN domainId bigint,IN tenantId bigint , IN userId bigint)
BEGIN

 SET SESSION TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;

INSERT INTO `commons_domain_db`.`domain_hierarchy`(`created_by_tenant`,`created_by_user`,`created_timestamp`,`last_modified_by_tenant`,
`last_modified_by_user`,`last_modified_timestamp`,`unique_identifier`,`is_active`,`tenant_id`,`depth`,`parent_domain`,`domain`)
 (SELECT
    tenantId, userId, now(), tenantId , userId , now(), uuid(), 1, tenantId , ParentDepth + childDepth + 1 AS depth,  parent_domain,domain
FROM
    ((SELECT
        parent_domain, depth AS ParentDepth
    FROM
        commons_domain_db.domain_hierarchy
    WHERE
        domain = parentDomainId and is_active=1 UNION (SELECT parentDomainId, 0)) AS a)
        JOIN
    ((SELECT
        domain, depth AS ChildDepth
    FROM
        commons_domain_db.domain_hierarchy
    WHERE
        parent_domain = domainId and is_active=1 UNION (SELECT domainId, 0)) AS b))
	on duplicate key update
    last_modified_timestamp =now();

 Select "Success";
END ; #