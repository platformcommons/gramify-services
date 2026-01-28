# Infrastructure Prerequisites for Gramify Services

This document provides detailed setup instructions for all infrastructure components required to run the Gramify microservices platform.

## Overview

The Gramify platform requires the following infrastructure components:
- **Spring Cloud Config Server**: Centralized configuration management
- **Apache Ignite**: Distributed caching with Spring Boot integration
- **Apache Kafka**: Message streaming platform
- **Apache Zookeeper**: Coordination service for Kafka
- **Netflix Eureka**: Service discovery and registration
- **Apache Solr**: Search platform for full-text search
- **Elasticsearch**: Distributed search and analytics engine

## Quick Start with Docker Compose

For development environments, use the provided Docker Compose setup:

```bash
# Start all infrastructure services
docker-compose -f infrastructure/docker-compose.yml up -d

# Stop all services
docker-compose -f infrastructure/docker-compose.yml down
```

## Detailed Setup Instructions

### 1. Spring Cloud Config Server

#### Docker Setup (Recommended)
```bash
docker run -d \
  --name config-server \
  -p 8888:8888 \
  -e SPRING_PROFILES_ACTIVE=native \
  -e SPRING_CLOUD_CONFIG_SERVER_NATIVE_SEARCH_LOCATIONS=file:///config \
  -v /path/to/config:/config \
  hyness/spring-cloud-config-server:3.1.3
```

#### Manual Installation
1. Download Spring Cloud Config Server:
```bash
wget https://repo1.maven.org/maven2/org/springframework/cloud/spring-cloud-config-server/3.1.3/spring-cloud-config-server-3.1.3.jar
```

2. Create application.yml: (details in config-repo directory)
```yaml
server:
  port: 8888
spring:
  cloud:
    config:
      server:
        native:
          search-locations: file:///config
  profiles:
    active: native
```

3. Run the server:
```bash
java -jar spring-cloud-config-server-3.1.3.jar
```

#### Configuration Structure
Create configuration files in `/config` directory:
```
config/
├── application.yml          # Default configuration
├── commons-iam-service.yml  # IAM service specific config
├── commons-post-service.yml # Post service specific config
└── ...
```

### 2. Apache Ignite with Spring Boot

#### Docker Setup
```bash
docker run -d \
  --name ignite-server \
  -p 47100:47100 \
  -p 47500:47500 \
  -p 49112:49112 \
  -e IGNITE_QUIET=false \
  apacheignite/ignite:2.14.0
```

#### Manual Installation
1. Download Apache Ignite:
```bash
wget https://downloads.apache.org/ignite/2.14.0/apache-ignite-2.14.0-bin.zip
unzip apache-ignite-2.14.0-bin.zip
cd apache-ignite-2.14.0-bin
```

2. Create ignite-config.xml:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean class="org.apache.ignite.configuration.IgniteConfiguration">
        <property name="discoverySpi">
            <bean class="org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi">
                <property name="ipFinder">
                    <bean class="org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder">
                        <property name="addresses">
                            <list>
                                <value>127.0.0.1:47500..47509</value>
                            </list>
                        </property>
                    </bean>
                </property>
            </bean>
        </property>
    </bean>
</beans>
```

3. Start Ignite:
```bash
./bin/ignite.sh config/ignite-config.xml
```

#### Spring Boot Integration
Add to your application.yml:
```yaml
spring:
  cache:
    type: ignite
ignite:
  config: classpath:ignite-config.xml
```

### 3. Apache Kafka

#### Docker Setup
```bash
# Start Kafka with Zookeeper
docker run -d \
  --name kafka \
  -p 9092:9092 \
  -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
  -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
  -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
  --link zookeeper:zookeeper \
  confluentinc/cp-kafka:7.4.0
```

#### Manual Installation
1. Download Kafka:
```bash
wget https://downloads.apache.org/kafka/2.8.2/kafka_2.13-2.8.2.tgz
tar -xzf kafka_2.13-2.8.2.tgz
cd kafka_2.13-2.8.2
```

2. Configure server.properties:
```properties
broker.id=0
listeners=PLAINTEXT://localhost:9092
log.dirs=/tmp/kafka-logs
num.network.threads=3
num.io.threads=8
socket.send.buffer.bytes=102400
socket.receive.buffer.bytes=102400
socket.request.max.bytes=104857600
log.retention.hours=168
log.segment.bytes=1073741824
log.retention.check.interval.ms=300000
zookeeper.connect=localhost:2181
zookeeper.connection.timeout.ms=18000
group.initial.rebalance.delay.ms=0
```

3. Start Kafka:
```bash
./bin/kafka-server-start.sh config/server.properties
```

#### Create Required Topics
```bash
# Create topics for Gramify services
./bin/kafka-topics.sh --create --topic gramify-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
./bin/kafka-topics.sh --create --topic gramify-notifications --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
./bin/kafka-topics.sh --create --topic gramify-analytics --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
```

### 4. Apache Zookeeper

#### Docker Setup
```bash
docker run -d \
  --name zookeeper \
  -p 2181:2181 \
  -e ZOOKEEPER_CLIENT_PORT=2181 \
  -e ZOOKEEPER_TICK_TIME=2000 \
  confluentinc/cp-zookeeper:7.4.0
```

#### Manual Installation
1. Download Zookeeper:
```bash
wget https://downloads.apache.org/zookeeper/zookeeper-3.8.1/apache-zookeeper-3.8.1-bin.tar.gz
tar -xzf apache-zookeeper-3.8.1-bin.tar.gz
cd apache-zookeeper-3.8.1-bin
```

2. Create zoo.cfg:
```properties
tickTime=2000
dataDir=/tmp/zookeeper
clientPort=2181
initLimit=5
syncLimit=2
```

3. Start Zookeeper:
```bash
./bin/zkServer.sh start
```

### 5. Netflix Eureka Server

#### Docker Setup
```bash
docker run -d \
  --name eureka-server \
  -p 8761:8761 \
  springcloud/eureka
```

#### Manual Setup with Spring Boot
1. Create a new Spring Boot application with dependencies:
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

2. Main application class:
```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

3. application.yml:
```yaml
server:
  port: 8761
eureka:
  instance:
    hostname: localhost
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```

### 6. Apache Solr

#### Docker Setup
```bash
docker run -d \
  --name solr \
  -p 8983:8983 \
  -v solr_data:/var/solr \
  solr:9.3 solr-precreate gramify
```

#### Manual Installation
1. Download Solr:
```bash
wget https://downloads.apache.org/lucene/solr/9.3.0/solr-9.3.0.tgz
tar -xzf solr-9.3.0.tgz
cd solr-9.3.0
```

2. Start Solr:
```bash
./bin/solr start -p 8983
```

3. Create Gramify core:
```bash
./bin/solr create -c gramify
```

#### Spring Boot Integration
Add to your application.yml:
```yaml
spring:
  data:
    solr:
      host: http://localhost:8983/solr
```

### 7. Elasticsearch

#### Docker Setup
```bash
docker run -d \
  --name elasticsearch \
  -p 9200:9200 \
  -p 9300:9300 \
  -e "discovery.type=single-node" \
  -e "xpack.security.enabled=false" \
  -e "ES_JAVA_OPTS=-Xms512m -Xmx512m" \
  -v es_data:/usr/share/elasticsearch/data \
  docker.elastic.co/elasticsearch/elasticsearch:8.9.0
```

#### Manual Installation
1. Download Elasticsearch:
```bash
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-8.9.0-linux-x86_64.tar.gz
tar -xzf elasticsearch-8.9.0-linux-x86_64.tar.gz
cd elasticsearch-8.9.0
```

2. Configure elasticsearch.yml:
```yaml
cluster.name: gramify-cluster
node.name: gramify-node-1
network.host: localhost
http.port: 9200
discovery.type: single-node
xpack.security.enabled: false
```

3. Start Elasticsearch:
```bash
./bin/elasticsearch
```

#### Spring Boot Integration
Add to your application.yml:
```yaml
spring:
  elasticsearch:
    uris: http://localhost:9200
```

## Complete Docker Compose Setup

Create `infrastructure/docker-compose.yml`:

```yaml
version: '3.8'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.4.0
    ports:
      - "2181:2181"
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000

  kafka:
    image: confluentinc/cp-kafka:7.4.0
    ports:
      - "9092:9092"
    depends_on:
      - zookeeper
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1

  ignite:
    image: apacheignite/ignite:2.14.0
    ports:
      - "47100:47100"
      - "47500:47500"
      - "49112:49112"
    environment:
      IGNITE_QUIET: false

  config-server:
    image: hyness/spring-cloud-config-server:3.1.3
    ports:
      - "8888:8888"
    environment:
      SPRING_PROFILES_ACTIVE: native
      SPRING_CLOUD_CONFIG_SERVER_NATIVE_SEARCH_LOCATIONS: file:///config
    volumes:
      - ./config:/config

  eureka-server:
    image: springcloud/eureka
    ports:
      - "8761:8761"

  solr:
    image: solr:9.3
    ports:
      - "8983:8983"
    volumes:
      - solr_data:/var/solr
    command:
      - solr-precreate
      - gramify

  elasticsearch:
    image: docker.elastic.co/elasticsearch/elasticsearch:8.9.0
    ports:
      - "9200:9200"
      - "9300:9300"
    environment:
      - discovery.type=single-node
      - xpack.security.enabled=false
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
    volumes:
      - es_data:/usr/share/elasticsearch/data

volumes:
  solr_data:
  es_data:
```

## Service Configuration

### Spring Boot Application Properties
Add to each service's application.yml:

```yaml
spring:
  application:
    name: ${service.name}
  cloud:
    config:
      uri: http://localhost:8888
  cache:
    type: ignite
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: ${spring.application.name}
      auto-offset-reset: earliest
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
  instance:
    prefer-ip-address: true

ignite:
  config: classpath:ignite-config.xml
```

## Health Checks

Verify all services are running:

```bash
# Config Server
curl http://localhost:8888/actuator/health

# Eureka Server
curl http://localhost:8761/actuator/health

# Kafka Topics
kafka-topics.sh --list --bootstrap-server localhost:9092

# Ignite REST API
curl http://localhost:8080/ignite?cmd=version

# Solr Admin
curl http://localhost:8983/solr/admin/cores?action=STATUS

# Elasticsearch
curl http://localhost:9200/_cluster/health
```

## Troubleshooting

### Common Issues

1. **Port Conflicts**: Ensure ports 2181, 8761, 8888, 9092, 47100, 47500, 49112, 8983, 9200, 9300 are available
2. **Memory Issues**: Increase JVM heap size for Kafka and Ignite:
   ```bash
   export KAFKA_HEAP_OPTS="-Xmx1G -Xms1G"
   export IGNITE_JVM_OPTS="-Xmx2G -Xms2G"
   ```
3. **Network Issues**: Check firewall settings and ensure services can communicate

### Logs Location
- Kafka: `logs/server.log`
- Zookeeper: `logs/zookeeper.out`
- Ignite: `work/log/ignite-*.log`
- Solr: `server/logs/solr.log`
- Elasticsearch: `logs/elasticsearch.log`

## Production Considerations

1. **Security**: Enable SSL/TLS for all services
2. **Clustering**: Configure multi-node setups for high availability
3. **Monitoring**: Integrate with monitoring tools (Prometheus, Grafana)
4. **Backup**: Implement backup strategies for Kafka topics and Ignite data
5. **Resource Limits**: Set appropriate memory and CPU limits