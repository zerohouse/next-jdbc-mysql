# Next-JDBC-MYSQL Library 0.0.5
JDBC를 한줄로!

    DAO dao = new DAO();
    User user = dao.find(new User("parksungho86@gmail.com));
    List<User> user = dao.findList(new User("parksungho86@gmail.com));
    dao.insert(user);
    dao.update(user);
    dao.delete(user);    
복잡한 쿼리도 외울 필요없이 자동완성으로 해결!

    List<Map<String, Object>> mapList = dao.getSelectQuery(User.class).select("email", "id").orderBy("id").limit(3,3).whereField("email").like("3").and().field("email").equal(3).asMapList();

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
		<version>0.0.5</version>
	</dependency>



## DAO.class
    
### Methods
    
    find(T)
    find(T, String...)
    findList(T)
    findList(T, String)
    insert(Object)
    insertIfExistUpdate(Object)
    update(Object)
    update(Object, String...)
    delete(Object)
    get(Class<T>, String, Object...)
    getList(Class<T>, String, Object...)
    getSelectQuery(Class<T>)
    getInsertQuery(Class<?>)
    getUpdateQuery(Class<?>)
    getDeleteQuery(Class<?>)
    getRecord(String, Object...)
    getRecords(String, Object...)
    getRecordAsList(String, Object...)
    getRecordsAsList(String, Object...)
    
    
### Example Usage

#### 1. Model
    @Table
    public class User {
        @Key(AUTO_INCREMENT = true)
    	private Integer id;
    	@Column(function = { "index", "unique" })
    	private String email;
    	private String name;
    	private String password;
    	@Column(DATA_TYPE = "CHAR(1)")
    	private String gender;
    }
    
#### 2. Use

	TableMaker maker = new TableMaker(User.class);
	maker.createTable();
	DAO dao = new DAO();
	dao.get(User.class, 1);
	dao.insert(new User("parksungho86@gmail.com", "newPassword"));
	dao.update(new User("parksungho86@gmail.com", "newPassword"), "email");
	dao.find(new User("parksungho86@gmail.com", "newPassword")); 
	dao.findList(new User("parksungho86@gmail.com", "newPassword")); 
	dao.delete(new User("parksungho86@gmail.com", "newPassword")); 
	dao.getSelectQuery(User.class).field("email").like("pa").or().field("password").equal("3").limit(3, 4).orderBy("id").find(); 
    
    
    
### Transaction : 사용후 반드시 DAO를 close해야함.
 
    DAO dao = new DAO(new Transaction());
    dao.close();
    
    
## TableMaker.class, PackageCreator.class
모델만 만들면 테이블 만들어줍니다.

### Example Usage
    PackageCreator.create(); // 모든 테이블 생성
    PackageCreator.drop(); // 모든 테이블 드롭
    PackageCreator.reset(); // 모든 테이블 리셋
    TableMaker tm = new TableMaker(User.class, new DAO()); // User 테이블 생성
    tm.dropTable();
	tm.createTable();
    tm.reset();
    
    
### @Table, @Key, @Column, @Exclude, @RegexFormat

### Example Model
    @Table
    public class User {
        @Key(AUTO_INCREMENT = true) //프라이머리 키
        private Integer id;
    	@Column(DATA_TYPE="TEXT", function="INDEX") //칼럼 설정, 이름, 데이터 타입등
        private String introduce;
    	private Integer age;
        
        @Exclude //라이브러리에서 무시할 필드
        private static final String EMAIL_PATTERN =
    	"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+
    		"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        
        @RegexFormat(EMAIL_PATTERN)
        // 포맷에 맞지 않으면 insert, update하지않고 RegexNotMatchedException을 발생시킴                  
        private String email;
        
    }


#Join

### Join Model Example
    public class UserMessage extends Join<User, Message> {
    
        public UserMessage(User left, Message right) {
    		super(left, right);
    		this.joinType = JoinType.LEFT; // default INNER
    	}
    
    	@Override
    	public String getLeftOnFieldName() {
    		return "id"; // User field id;
    	}
    
    	@Override
    	public String getRightOnFieldName() {
    		return "from"; // Message field from;
    	}
    
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
    

    
