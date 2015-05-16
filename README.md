# Next DAO Library 0.0.1
편합니다!


##GET
pom.xml에 아래의 레파지토리와 Dependency설정을 추가합니다.

###Repository
    <repository>
        <id>next-mvn-repo</id>
        <url>https://raw.github.com/zerohouse/next-jdbc-mysql/mvn-repo/</url>
	</repository>

###Dependency
	<dependency>
		<groupId>at.begin</groupId>
		<artifactId>next-jdbc-mysql</artifactId>
		<version>0.0.1</version>
	</dependency>


# DAO
어노테이션 기반 모델 설정 -> JDBC 한줄로 해결
테이블 생성 SQL 파일도 필요없습니다.
    

## DAO.class, GDAO<T>.class
    
### Example Usage
    DAO dao = new DAO();
    User user = dao.find(User.class, userId);
    
    GDAO<User> userDao = new GDAO<User>(User.class);
    User user2 = userDAO.find(userId);
    
    user.equals(user2); // true
    
### Transaction : 사용후 반드시 DAO를 close해야함.
메서드 파라미터에서 사용시, 메서드 레벨 실행 후 close()호출함
 
    DAO dao = new DAO(new Transaction());
    dao.close();
    GDAO<User> gdao = new GDAO<User>(new Transaction());
    gdao.close();
    
    
## TableMaker.class
아래의 어노테이션 설정하고 모델만 만들면 테이블 만들어줍니다.

### Example Usage
    boolean ifExistDrop = false;
    TableMaker.create(ifExsitDrop); // 모든 테이블 생성
    TableMaker tm = new TableMaker(User.class, dao); // User 테이블 생성
    tm.dropTable();
	tm.createTable();
    tm.reset(); // 드롭 후 크리에이트
    
    
### @Table, @Key, @Column, @Exclude, @RequiredRegex

### Example Model
    @Table
    public class User {
        @Key(AUTO_INCREMENT = true)
    	private Integer id;
    	@Column(DATA_TYPE="TEXT", function="INDEX")
    	private String introduce;
    	private Integer age;
        
        @Exclude
    	private static final String EMAIL_PATTERN =
    	"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+
    		"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        
        @RequiredRegex(EMAIL_PATTERN)
        private String email;
        
        @OtherTable
        private List<Post> posts;
        
    }
  

# Setting
resource폴더 내에 next-jdbc-mysql.json 위치 (기본 세팅을 담당)

## next-jdbc-mysql.json (resources/next-jdbc-mysql.json)
### Setting Required Options : 아래 옵션은 필수입니다.

    {
      "basePackage" : "",
      "connectionSetting": {
        "jdbcUrl": "jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=utf8",
        "username": "root",
        "password": ""
      }
    }



### Setting Example : 필요한 옵션을 더 세팅하여 세팅 파일을 만듭니다.

    {
      "basePackage": "",
      "connectionSetting": {
        "minConnectionsPerPartition": 3,
        "maxConnectionsPerPartition": 10,
        "acquireIncrement": 2,
        "partitionCount": 3,
        "jdbcUrl": "jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=utf8",
        "username": "root",
        "password": ""
      }
    }



### Default Setting : 기본 세팅은 아래와 같습니다.
    {
      "basePackage" : "",
      "connectionSetting": {
        "minConnectionsPerPartition": 0,
        "maxConnectionsPerPartition": 10,
        "acquireIncrement": 2,
        "partitionCount": 1,
        "jdbcUrl": "",
        "username": "",
        "password": "",
        "idleConnectionTestPeriodInSeconds": 14400,
        "idleMaxAgeInSeconds": 3600,
        "statementsCacheSize": 0,
        "statementsCachedPerConnection": 0,
        "releaseHelperThreads": 0,
        "statementReleaseHelperThreads": 0,
        "closeConnectionWatch": false,
        "logStatementsEnabled": false,
        "acquireRetryDelayInMs": 7000,
        "acquireRetryAttempts": 5,
        "lazyInit": false,
        "transactionRecoveryEnabled": false,
        "disableJMX": false,
        "queryExecuteTimeLimitInMs": 0,
        "poolAvailabilityThreshold": 0,
        "disableConnectionTracking": false,
        "connectionTimeoutInMs": 0,
        "closeConnectionWatchTimeoutInMs": 0,
        "maxConnectionAgeInSeconds": 0,
        "serviceOrder": "FIFO",
        "statisticsEnabled": false,
        "defaultAutoCommit": true,
        "defaultReadOnly": false,
        "defaultTransactionIsolationValue": -1,
        "externalAuth": false,
        "deregisterDriverOnClose": false,
        "nullOnConnectionTimeout": false,
        "resetConnectionOnClose": false,
        "detectUnresolvedTransactions": false,
        "poolStrategy": "DEFAULT",
        "closeOpenStatements": false,
        "detectUnclosedStatements": false
      },
      "createOption": {
        "table_suffix": "ENGINE \u003d InnoDB DEFAULT CHARACTER SET utf8",
        "stringOptions": {
          "dataType": "VARCHAR(255)",
          "notNull": true,
          "hasDefaultValue": true,
          "defaultValue": ""
        },
        "integerOptions": {
          "dataType": "INTEGER",
          "notNull": true,
          "hasDefaultValue": true,
          "defaultValue": 0
        },
        "booleanOptions": {
          "dataType": "TINYINT(1)",
          "notNull": true,
          "hasDefaultValue": true,
          "defaultValue": 0
        },
        "dateOptions": {
          "dataType": "DATETIME",
          "notNull": true,
          "hasDefaultValue": true,
          "defaultValue": "CURRENT_TIMESTAMP"
        },
        "floatOptions": {
          "dataType": "FLOAT",
          "notNull": true,
          "hasDefaultValue": true,
          "defaultValue": 0
        },
        "longOptions": {
          "dataType": "BIGINT",
          "notNull": true,
          "hasDefaultValue": true,
          "defaultValue": 0
        }
      }
    }
    

    
