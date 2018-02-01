* **Build to docker**

./gradlew clean buildDocker

* **Deploy to local instance**

docker-compose up --scale tree-service=2

* **CRUD**

**Create node/Find node**

curl -XPOST http://localhost:9100/tree/api/crud -H 'Content-Type: application/json' -d '{"id":1,"parentId":null,"name":"test1"}'
curl -XPOST http://localhost:9100/tree/api/crud -H 'Content-Type: application/json' -d '{"id":2,"parentId":1,"name":"test2"}'

**Change current node**

curl -XPATCH http://localhost:9100/tree/api/crud/2 -H 'Content-Type: application/json' -d '{"id":9992}'

curl -XPOST http://localhost:9100/tree/api/crud -H 'Content-Type: application/json' -d '{"id":2,"parentId":1}'
curl -XPOST http://localhost:9100/tree/api/crud -H 'Content-Type: application/json' -d '{"id":9992,"parentId":1}'


**Delete current node**
curl -XPOST http://localhost:9100/tree/api/crud -H 'Content-Type: application/json' -d '{"id":3,"parentId":2,"name":"test3"}'

curl -XDELETE http://localhost:9100/tree/api/crud/3