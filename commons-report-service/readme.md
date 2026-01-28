# Commons-Report-Service


## Features


- [x] 1.  **Scheduling Cron Tasks**


### Scheduling Cron Tasks
Create a new Dataset for which the cron task will be scheduled. <br>
Note: The cron task will be scheduled only for datasets with cron enabled.<br>
The cron task will be scheduled for the dataset at the time specified in the cron expression.<br>


```ccURL
Sample Curl for dataset creation 
curl -X POST "https://dev.platformcommons.org/gateway/commons-report-service/api/v1/datasets?datasetGroupCodes=VMS_TEST&dataSource=2" -H "accept: */*" -H "X-SESSIONID: 2ea80e8a-3968-42c2-8807-de46d5cf88dd" -H "Content-Type: application/json" -d "{ \"id\": 0, \"isCronEnabled\": true, \"name\": \"CRON_TEST\", \"queryText\": \"select * from commons_assessment_db.assessment where tenant_id=<::CURRENT_TENANT_ID::>\"}"
```
After creating the dataset, create a cron meta entity for the dataset. <br>
The cron meta entity contains the cron expression, dataset, and the method to be executed.<br>
It Contains Data required for the job to be executed.
The cron task will be scheduled for the dataset at the time specified in the cron expression.<br>
The Job data will be validated based on the method for which the job is scheduled.<br>

```ccURL
Sample Curl for scheduling cron task for cron job emaihandler
curl -X POST "https://dev.platformcommons.org/gateway/commons-report-service/api/v1/dataset-cron-meta" -H "accept: */*" -H "X-SESSIONID: 2ea80e8a-3968-42c2-8807-de46d5cf88dd" -H "Content-Type: application/json" -d "{ \"cronExpression\": \"*/10 * * * * *\", \"dataSetCronData\": { \"username\": \"admin\", \"password\": \"password1\", \"tenant\": \"igx\", \"emails\": \"[\\\"jitendra@platformcommons.com\\\"]\", \"notificationCode\": \"CRON_TEST\", \"fromUser\": \"hi@commons.team\", \"datasetParams\": \"\", \"uploadPublic\": \"true\", \"notificationParams\": \"{\ \\\"MSG_SUBJ\\\": \\\"Dev Test\\\"\}\" }, \"dataset\": { \"uuid\": \"26c3fa80-c19b-4673-97f1-a95bb3e4c527\", \"id\": 698, \"name\": \"CRON_TEST\", \"queryText\": \"select * from commons_assessment_db.assessment where tenant_id=<::CURRENT_TENANT_ID::>\", \"defaultParamMap\": \"\", \"isCronEnabled\": true }, \"endTime\": \"2024-04-29T09:29:29.822Z\", \"id\": 0, \"method\": \"EmailJobHandler\", \"name\": \"EmailJob\", \"startTime\": \"2024-04-29T09:29:29.822Z\", \"timeZone\": \"IST\"}"
```
To Unschedule the jobs for a dataset update cron meta enable in dataset entity.