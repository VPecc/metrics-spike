# Read Me
Spike to test microMeter - stackdriver metrics gathering with spring-boot 2.0.3 version. 
(newer versions have built-in autoconfiguration for the stackdriver implementation)

#How to use
- set management.metrics.export.stackdriver.project-id in application.properties
- add GOOGLE_APPLICATION_CREDENTIALS env var. The account should have metrics write permissions.
- run the application
- In GCP, look for metrics called custom/springmetrics/*

