# 테스트 환경 구축

## 1. 프로젝트 가져오기
```sh
git clone https://github.com/Capston-Calcite/calcite.git
```

## 2. IntelliJ에서 프로젝트 불러오기
## 3. model.json 파일 수정하기
위치 : /redis/src/test/resources/redis-mix-model.json

수정 예시 :
```json
{
  "version": "1.0",
  "defaultSchema": "redis",
  "schemas": [
    {
      "type": "custom",
      "name": "redis",
      "factory": "org.apache.calcite.adapter.redis.RedisSchemaFactory",
      "operand": {
        "host": "localhost",
        "port": 6379,
        "database": 0,
        "password": ""
      },
      "tables": [
        {
          "name": "programmers",
          "factory": "org.apache.calcite.adapter.redis.RedisTableFactory",
          "operand": {
            "dataFormat": "raw",
            "fields": [
              {
                "name": "MEMBERS",
                "type": "varchar",
                "mapping": "MEMBERS"
              }
            ]
          }
        }
      ]
    }
  ]
}
```
<br>

## 4. RedisDataProcess의 read() 메서드 찾아서 수정하고 저장
위치 : /redis/src/main/java/org/apache/calcite/adapter/redis/RedisDataProcess.java

수정 예시 :
```java
public List<Object[]> read() {
    List<Object[]> objs = new ArrayList<>();
    switch (dataType) {
    case STRING:
      return parse(jedis.keys(tableName));
    case LIST:
      return parse(jedis.lrange(tableName, 0, -1));
    case SET:
      return parse(jedis.smembers(tableName));
    case SORTED_SET:
      System.out.println("jedis.zrangeWithScores(tableName, 0, 10).toString() = "
                        + (jedis.zrangeWithScores(tableName, 0, 10).toString()));
      return parse(jedis.zrange(tableName, 0, -1));
    case HASH:
      return parse(jedis.hvals(tableName));
    default:
      return objs;
    }
  }
```
<br>

## 5. 프로젝트의 루트 디렉토리에서 터미널 열고
### sqlline 실행
```sh
./sqlline
```
### calcite 모델 등록 및 연결
```
sqlline> !connect jdbc:calcite:model=redis/src/test/resources/redis-mix-model.json admin admin
```
### table 확인
```
0: jdbc:calcite:model=redis/src/test/resource> !table
```
### 질의
```
0: jdbc:calcite:model=redis/src/test/resource> select * from "programmers" limit 10;
```


