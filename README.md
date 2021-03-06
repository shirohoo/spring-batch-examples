# π Batch ?

---

- ν° λ¨μμ μμμ μΌκ΄ μ²λ¦¬νλ κ². νμμμλ μ΄λ₯Ό λ°°μΉλΌκ³  λΆλ₯Έλ€
    - λμ©λ λ°μ΄ν° κ³μ°μ μ£Όλ‘ μ¬μ©(ex> λ§€μΆ μ§κ³ λ±, λμ¬, μ μ° λ±)
- λΉ μ€μκ°μ± μμμ΄λ©° κ·μΉμ μΈ μ£ΌκΈ°(μ€μΌμ₯΄λ¬, ν¬λ‘  λ±μ νμ©)λ‘ μ€ν λ¨
- μ¬μ©μ νΈλν½μ΄ λΈν μλ²½ μκ°λμ μ£Όλ‘ μ€νλλ―λ‘ μλ²μμμ μ΅λλ‘ νμ©ν  μ μμ

<br />

# π Spring-Batch ?

---

![image](https://user-images.githubusercontent.com/71188307/137231237-587ab9d4-b5d8-4d91-8e8b-6fd1bde51749.png)

<br />

- Java μνκ³μ νμ€ λ°°μΉ κΈ°μ  λΆμ¬λ‘, μ΄λ¬ν νμ€μ νμμ±μ΄ λλ λμλ€
- `μ‘μΌμΈμ΄(Accenture)`κ° μμ νκ³ μλ λ°°μΉ νλ μμν¬λ₯Ό Spring νμ κΈ°μ¦νμ¬ νμν¨
- Batch μ²λ¦¬λ₯Ό μ½κ² νκΈ° μν Spring μνκ³μ Framework
- Spring Triangle (DI, AOP, PSA) νμ© κ°λ₯
- λ€μν μ¬μ©μλ₯Ό κ³ λ €ν΄ μ€κ³λμ΄ νμ₯μ±κ³Ό μ¬μ©μ±μ΄ λ§€μ° μ’λ€   
- `Job`κ³Ό `Step`μΌλ‘ λλλ©° `Step`μ `Tasklet`κ³Ό `Chunk`λ‘ λλ¨  
- κ°λ¨ν μμ(Tasklet), λκ·λͺ¨ μμ(Chunk)

<br />

# π Spring-Batch μ€μ 

---

### π build.gradle μ€μ 
---

```groovy
implementation 'org.springframework.boot:spring-boot-starter-batch'
```

<br />

### π SpringBootApplication μ€μ 

---

```java
@EnableBatchProcessing // νμ: Spring-Batchμ κΈ°λ₯μ νμ±ν
@SpringBootApplication
public class SpringBatchApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(SpringBatchApplication.class, args);
    }
    
}

```

<br />

### π application.yaml μ€μ 

---

```yaml
spring:
  batch:
    job:
      ## μ€νμ΅μμ job nameμ΄ μμ κ²½μ° μλ¬΄λ° jobλ μ€ννμ§ μμ (μμ μ₯μΉ)
      ## ex) --job.name=itemReaderJob
      names: ${job.name:NONE}

    ## always - Spring-Batch DDLμ΄ DBμ ν­μ λ°μ // κ°λ°νκ²½ μΆμ²
    ## embedded - Embedded DBμΈ κ²½μ°(λνμ μΌλ‘ H2)μλ§ Spring-Batch DDLμ΄ DBμ λ°μ // κ°λ°νκ²½ μΆμ²
    ## never - Spring-Batch DDLμ΄ DBμ μ λ λ°μλμ§ μμ (μ§μ  SQLμ κ΄λ¦¬) // μ΄μνκ²½ μΆμ²
    initialize-schema: embedded (cf.default)
```

<br />

### π Database μ€μ 

---

![image](https://user-images.githubusercontent.com/71188307/137231262-c2b4aef6-1230-44d0-88f8-95bc8bedae83.png)

<br />

`Spring-Batch`λ DBμ `Metadata table`μ μμ±νμ¬, μ΄ λ°μ΄ν°λ€μ κΈ°λ°μΌλ‘ λμνλ―λ‘ `BatchApplication`κ³Ό μ°κ²°λ `DB`μ `Spring-Batch DDL`μ μ μ©ν΄μ€μΌ νλ€

μ΄ `DDL`μ `Spring-Batch Core`μ ν¬ν¨λΌμμΌλ―λ‘, μμ μ΄ μ¬μ©νλ `DB`μ λ§λ `sql`νμΌμ μ μ©νλ©΄ λλ€

<br />

```text
path: spring-batch-core/org.springframework/batch/core/*
```

<br />

![image](https://user-images.githubusercontent.com/71188307/137231278-132a8b60-83d3-400b-89a0-c19ebecba634.png)

<br />

![image](https://user-images.githubusercontent.com/71188307/137231272-e3e6d374-39c2-4ac3-850d-f4639acf05b5.png)

<br />

# π Spring-Batch κ΅¬μ‘°

---

![image](https://user-images.githubusercontent.com/71188307/137231254-c9e20062-4884-4201-b262-d0dd89a303a2.png)

<br />

### π Metadata

---

- `BATCH_JOB_INSTANCE`
  - `Job`μ΄ μ€νλλ©° μμ±λλ μ΅μμ κ³μΈ΅μ νμ΄λΈλ‘ λ§ κ·Έλλ‘ Jobμ μΈμ€ν΄μ€λ₯Ό μλ―Ένλ€
  - `job_name`κ³Ό `job_key`λ₯Ό κΈ°μ€μΌλ‘ νλμ `row`κ° μμ±λλ©°, κ°μ `job_name`κ³Ό `job_key`κ° μ μ₯λ  μ μλ€ (Unique key)
  - `job_key`λ `BATCH_JOB_EXECUTION_PARAMS`μ μ μ₯λλ `Parameter`λ₯Ό λμ΄ν΄ μνΈνν΄ μ μ₯νλ€
- `BATCH_JOB_EXECUTION`
  - Job μΈμ€ν΄μ€μ `νλ²μ μ€ν`μ μλ―Ένλ νμ΄λΈ
  - Jobμ΄ μ€νλλ λμ μμ/μ’λ£ μκ°, job μν λ±μ κ΄λ¦¬νλ€
- `BATCH_JOB_EXECUTION_PARAMS` 
  - Jobμ μ€ννκΈ° μν΄ μ£Όμλ parameter μ λ³΄ μ μ₯νλ€
- `BATCH_JOB_EXECUTION_CONTEXT`
  - Jobμ΄ μ€νλλ©° κ³΅μ ν΄μΌν  λ°μ΄ν°λ₯Ό μ§λ ¬νν΄ μ μ₯νλ€
- `BATCH_STEP_EXECUTION`
  - Stepμ `νλ²μ μ€ν`μ μλ―Ένλ νμ΄λΈ
  - `Step`μ΄ μ€νλλ λμ νμν λ°μ΄ν° λλ μ€νλ κ²°κ³Ό μ μ₯νλ€
- `BATCH_STEP_EXECUTION_CONTEXT`
  - `Step`μ΄ μ€νλλ©° κ³΅μ ν΄μΌν  λ°μ΄ν°λ₯Ό μ§λ ¬νν΄ μ μ₯νλ€

<br />

### π Job

---

- `Job`μ λ°°μΉμ μ€ν λ¨μ(Application)λ₯Ό μλ―Έν¨
- jar μ€ν μ `Job Name`μ μ€νμ΅μμΌλ‘ μ£Όλ©΄ ν΄λΉ `Job`λ§ μ€ν ν  μ μλ€  
- `Job`μ `JobLauncher`μ μν΄ μ€ν λ¨
- `Job`μ Nκ°μ `Step`μ μμ°¨μ μΌλ‘ μ€νν  μ μκ³ , μ μ²΄μ μΈ νλ¦μ μ΄λ₯Ό νλ€

<br />

![image](https://user-images.githubusercontent.com/71188307/137231279-77bb4ecc-2b70-4899-a8f7-4ee973fcf122.png)

<br />

- `JobInstance`: `BATCH_JOB_INSTANCE` νμ΄λΈκ³Ό λ§€ν
  - μλ‘μ΄ JobInstanceμ μμ± κΈ°μ€μ JobParametersμ μ€λ³΅ μ¬λΆμ΄λ€
  - λ€λ₯Έ JobParametersλ‘ Jobμ΄ μ€νλλ©΄ μλ‘μ΄ JobInstanceκ° μμ±λλ€
  - κ°μ JobParameters Jobμ΄ μ€νλλ©΄, μ΄λ―Έ μμ±λ JobInstance μ€νλλ€
- `JobExecution`: `BATCH_JOB_EXECUTION` νμ΄λΈκ³Ό λ§€ν
  - JobExecutionμ ν­μ μλ‘ μμ±λλ€
- `JobParameters`: `BATCH_JOB_EXECUTION_PARAMS` νμ΄λΈκ³Ό λ§€ν
- `ExecutionContext`: `BATCH_JOB_EXECUTION_CONTEXT` νμ΄λΈκ³Ό λ§€ν
- `StepExecution`: `BATCH_STEP_EXECUTION` νμ΄λΈκ³Ό λ§€ν
- `ExecutionContext`: `BATCH_STEP_EXECUTION_CONTEXT` νμ΄λΈκ³Ό λ§€ν

<br />

### π Step

---

- `Step`μ `Job`μ μΈλΆ μ€ν λ¨μμ΄λ©°, νλμ `Job`μ μ¬λ¬κ°κ° λ±λ‘ λ  μ μλ€
- `Step`μ 2κ°μ§ μ’λ₯λ‘ λλ μ μλ€
  - `Tasklet`: νλμ μμμ κ·Έλλ‘ μ§ν(μ£Όλ‘ μκ·λͺ¨ μμ)
    - μ£Όλ‘ μκ·λͺ¨ μμμ μ¬μ©νλ©°, μ΄ κ²½μ°`Chunk`λ³΄λ€ μ½κ² μ¬μ©ν  μ μλ€
    - λ°λλ‘ λλμ²λ¦¬μ `Chunk` λμ  `Tasklet`μ μ¬μ©νλ€λ©΄ λ§€μ° λ³΅μ‘ν΄μ§λ€
  - `Chunk`: νλμ μμμ μ¬λ¬λ² μͺΌκ°μ μ§ν(μ£Όλ‘ λκ·λͺ¨ μμ), 3κ°μ§ μμμ κ±°μΉλ€
    - λκ·λͺ¨ μμμ μ¬μ© ν  κ²½μ° `Tasklet`λ³΄λ€ μ½κ² κ΅¬ν ν  μ μλ€
    - μλ₯Ό λ€λ©΄ DB λ°μ΄ν° 10,000κ°μ rowλ₯Ό μ²λ¦¬ν΄μΌ ν  κ²½μ° 1,000κ°μ© 10λ² λλμ΄ μ²λ¦¬νλ€ 
    - `ItemReader`: λ°°μΉ μμ΄νμ μ½λλ€. ***λμ΄μ μ½μ μμ΄νμ΄ μλ€λ©΄ `Jobμ μ’λ£`***νλ€
    - `ItemProcessor`: μ½μ μμ΄νμ νΉμ ν κ°κ³΅μ κ±°μΉλ€. `optional`μ΄λ―λ‘ μλ΅κ°λ₯νλ€
    - `ItemWriter`: μμ΄νμ μ΅μ’ μ²λ¦¬νλ€. μλ₯Όλ€λ©΄ `DB`μ `DML`μ `commit`νκ±°λ, νμΌμ μμ±νλ€

<br />

# π JobParameters

---

- `Spring-Batch`κ° λ°°μΉμμμ νμν λ°μ΄ν°λ₯Ό μΈλΆμμ μ£Όμλ°μ μ μλ`λ³μ`
- μ¦, λ°°μΉ μ νλ¦¬μΌμ΄μ μΈλΆμμ ν΅λ‘μ΄λ€ (μΈλΆ > λ΄λΆ λ¨λ°©ν₯)
- `Spring-Expression-Language`μ `JobExecution` κ°μ²΄μ `getJobParameters` λ©μλλ₯Ό ν΅ν΄ μ κ·Όν  μ μλ€

<br />

```java
// Spring-Expression-Language
@Value("#{jobParameters[key]}") 

// JobExecution.getJobParameters();
String param = jobParameters.getString(key, value);
```

<br />

# π DataSharing

---

- `Spring-Batch`μλ `Metadata` μ λ§€νλλ `ExecutionContext` κ°μ²΄κ° μ‘΄μ¬
- `ExecutionContext` κ°μ²΄λ₯Ό μμλ°μ `JobExecution`, `StepExecution` κ°μ²΄κ° μ‘΄μ¬
- `JobExecution`μ ν΄λΉ Jobμ λͺ¨λ  Stepμ΄ μ κ·Όνμ¬ λ°μ΄ν°λ₯Ό μ»μ μ μλ€
- `StepExecution`μ Step νλμ μ’μμ μ΄λ€

<br />

```java
@Slf4j
@Configuration
@RequiredArgsConstructor
public class SharedJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    
    /**
     * <pre>
     * κ° StepλΌλ¦¬λ λ°μ΄ν° κ³΅μ κ° λμ§ μμΌλ―λ‘
     * emptyStepKeyμ΄ μΆλ ₯λλκ² μ μ
     * StepExecutionμ Stepνλμ μ’μμ μ΄λ©°,
     * JobExecutionμ Jobμ μ²΄μμ κ³΅μ ν  μ μλ€
     *
     * JobExecution = μ μ­
     * StepExecution = μ§μ­
     * </pre>
     */
    @Bean
    public Job shareJob() {
        return jobBuilderFactory.get("shareJob")
                                .incrementer(new RunIdIncrementer())
                                .start(this.shareStep1())
                                .next(this.shareStep2())
                                .build();
    }
    
    @Bean
    public Step shareStep1() {
        return stepBuilderFactory.get("shareStep1")
                                 .tasklet((contribution, chunkContext)->{
                                     StepExecution stepExecution = contribution.getStepExecution();
            
                                     ExecutionContext stepExecutionContext = stepExecution.getExecutionContext();
                                     stepExecutionContext.putString("stepKey", "step execution context");
            
                                     JobExecution jobExecution = stepExecution.getJobExecution();
                                     ExecutionContext jobExecutionContext = jobExecution.getExecutionContext();
                                     jobExecutionContext.putString("jobKey", "job execution context");
            
                                     JobParameters jobParameters = jobExecution.getJobParameters();
                                     JobInstance jobInstance = jobExecution.getJobInstance();
            
                                     log.info(">>>>>>>>>> shareStep1\njobName: {}\nstepName: {}\nparameter:{}",
                                              jobInstance.getJobName(),
                                              stepExecution.getStepName(),
                                              jobParameters.getLong("run.id")
                                             );
            
                                     return RepeatStatus.FINISHED;
                                 }).build();
    }
    
    @Bean
    public Step shareStep2() {
        return stepBuilderFactory.get("shareStep2")
                                 .tasklet((contribution, chunkContext)->{
                                     StepExecution stepExecution = contribution.getStepExecution();
            
                                     ExecutionContext stepExecutionContext = stepExecution.getExecutionContext();
            
                                     JobExecution jobExecution = stepExecution.getJobExecution();
                                     ExecutionContext jobExecutionContext = jobExecution.getExecutionContext();
            
                                     log.info(">>>>>>>>>> shareStep2\njobKey: {}\nstepKey: {}",
                                              jobExecutionContext.getString("jobKey", "emptyJobKey"),
                                              stepExecutionContext.getString("stepKey", "emptyStepKey")
                                             );
            
                                     return RepeatStatus.FINISHED;
                                 }).build();
    }
}
```

<br />

# π Bean Scope & Lifecycle

---

- `@Scope`λ μ΄λ€ μμ μ beanμ μμ±/μλ©Έμν¬μ§λ₯Ό μ€μ ν  μ μλ `Spring`μ μ λΈνμ΄μμ΄λ€
- `Spring-Batch`λ νΉμ΄ν κ΅¬μ‘°λ‘ μΈν΄ λ μΈλΆμ μΌλ‘ μ€μ ν μ λΈνμ΄μμ μ¬μ©νλ©° λ μμ λ κ°μ λμμ νλ€
  - `@JobScope` == `@Scope("job")`
    - Job μ€ν μμ μ beanμ΄ μμ±λλ©°, Stepμ μ μΈνλ€
  - `@StepScope` == `@Scope("step")`
    - `Step` μ€ν μμ μ beanμ΄ μμ±λλ©° `Tasklet`, `Chunk`μ μ μΈνλ€
- `Job`κ³Ό `Step`μ μ€ν μμ μ μν΄ μ μ΄λκΈ° λλ¬Έμ `Thread Safe`νκ² λμνλ€
- νΉν `Spring-Expression-Language`λ₯Ό μ΄μ©ν΄ `JobParameters`λ₯Ό μ μ°νκ² μ¬μ©νκΈ° μν΄μλ νμλ‘ μ¬μ©νλ μ λΈνμ΄μλ€μ΄λ€ 

<br />

# π Database Cursor & Paging

---

## π Cursor

---

- λ°°μΉκ° μμλ  λ DB Connectionμ΄ μ°κ²°λ ν λ°°μΉ μ²λ¦¬κ° μλ£λ  λ κΉμ§ Connectionμ΄ μ μ§λ¨
- DB Connection λΉλκ° λ§€μ° λ?μ μ±λ₯μ΄ μ’μ§λ§, Connection μ μ§μκ°μ΄ κΈΈλ€λ λ¨μ μ΄ μλ€
- νλ²μ Connectionμμ μμμ΄ μ²λ¦¬λκΈ° λλ¬Έμ `Thread Safe`νμ§ μλ€
- λͺ¨λ  κ²°κ³Όλ₯Ό λ©λͺ¨λ¦¬μ ν λΉνκΈ° λλ¬Έμ λ¦¬μμ€ μ¬μ©λμ΄ λλ€

<br />

## π Paging

---

- νμ΄μ§(SQL offset, limit) λ¨μλ‘ DB Connectionμ μ μ§νλ€
- CursorκΈ°λ° μ‘°νμ λΉν΄ μλμ μΌλ‘ DB Connection λΉλκ° λμ μ±λ₯μ΄ λ?λ€
- νμ΄μ§ λ¨μλ‘ λ©λͺ¨λ¦¬λ₯Ό μ¬μ©νκΈ° λλ¬Έμ CursorκΈ°λ° μ‘°νμ λΉν΄ λ¦¬μμ€ μ¬μ©λμ΄ μ λ€
- νμ΄μ§ λ¨μλ‘ λ§€λ² Connectionμ νκΈ° λλ¬Έμ `Thread Safe`νλ©°, λ³λ ¬μ²λ¦¬λ₯Ό μλν  μ μκ²λλ€
- λ³λ ¬μ²λ¦¬μ CursorκΈ°λ° μ‘°νλ³΄λ€ μ±λ₯μ΄ λ μ’μμλ μμ§λ§, λ°λλ‘ λ¦¬μμ€ μ¬μ©λμ΄ λ μ»€μ§μλ μλ€

<br />

# Test Code

---

μ€νλ§ λ°°μΉμμλ λ°°μΉ μ¬λΌμ΄μ± νμ€νΈλ₯Ό μν `@SpringBatchTest`λ₯Ό μ§μνλ€.

κ³΅μλ¬Έμμμλ `JUnit4`λ₯Ό κΈ°μ€μΌλ‘ μ€λͺνμ§λ§, λ³ΈμΈμ `JUnit5`λ₯Ό μ νΈνλ―λ‘ JUnit5 

λ€μκ³Ό κ°μ μ λ¬Έ νμΌμ μ½μ΄ DBμ μ μ₯νλ λ°°μΉμ‘μ μμ±νλ€.

μ΄ λ°°μΉμ‘μ νμ€νΈ μ½λ μμ λ λ€μκ³Ό κ°λ€.

<br >

```java
@Configuration
public class FileReadJobConfiguration {

    public static final String TEST_FILE_PATH = "src/test/resources/TestSpecFile";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final TestSpecRepository testSpecRepository;

    public FileReadJobConfiguration(final JobBuilderFactory jobBuilderFactory, final StepBuilderFactory stepBuilderFactory, final TestSpecRepository testSpecRepository) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.testSpecRepository = testSpecRepository;
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("testSpecFileJob")
            .start(step())
            .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("testSpecFileStep")
            .tasklet((contribution, chunkContext) -> {
                TestSpec testSpec = TestSpecFileParser.parse(new File(TEST_FILE_PATH));
                testSpecRepository.save(testSpec);
                return RepeatStatus.FINISHED;
            })
            .build();
    }

}
```

<br />

```java
// file: 'TestBatchConfiguration.java'
@Configuration // νμ€νΈμμ μ¬μ©ν  μ€μ  νμΌ
@EnableBatchProcessing // μ€νλ§ λ°°μΉλ₯Ό μ¬μ©
@EnableAutoConfiguration // μλ μ€μ μ μ¬μ©
@EnableTransactionManagement // μ¬λΌμ΄μ± νμ€νΈλ νκ°λ§ μ¬μ© κ°λ₯νλ―λ‘ @DataJpaTestλ₯Ό μΈ μ μμΌλ―λ‘ μΆκ° 
@EntityScan("io.spring.batch") // μ¬λΌμ΄μ± νμ€νΈλ νκ°λ§ μ¬μ© κ°λ₯νλ―λ‘ @DataJpaTestλ₯Ό μΈ μ μμΌλ―λ‘ μΆκ°
@EnableJpaRepositories("io.spring.batch") // μ¬λΌμ΄μ± νμ€νΈλ νκ°λ§ μ¬μ© κ°λ₯νλ―λ‘ @DataJpaTestλ₯Ό μΈ μ μμΌλ―λ‘ μΆκ°
public class TestBatchConfiguration {

}
```

<br />

```java
@SpringBatchTest // λ°°μΉ μ¬λΌμ΄μ± νμ€νΈ μ΄λΈνμ΄μ. λ°°μΉκ΄λ ¨ λΉμ μ£Όμν΄μ€λ€.
@SpringBootTest(classes = {
    TestBatchConfiguration.class, // μΆκ°μμ±ν μ€μ νμΌμ λ‘λ
    FileReadJobConfiguration.class, // νμ€νΈν  λ°°μΉμ‘μ λΉμ λ‘λ
})
class FileReadJobConfigurationTest {

    private final JobLauncherTestUtils jobLauncherTestUtils; // λ°°μΉμ‘ μ€νκ΄λ ¨ νμ€νΈ μ νΈ ν΄λμ€
    private final JobRepositoryTestUtils jobRepositoryTestUtils; // λ°°μΉ λ©νλ°μ΄ν° κ΄λ ¨ νμ€νΈ μ νΈ ν΄λμ€

    @Autowired
    public FileReadJobConfigurationTest(final JobLauncherTestUtils jobLauncherTestUtils, final JobRepositoryTestUtils jobRepositoryTestUtils) {
        this.jobLauncherTestUtils = jobLauncherTestUtils;
        this.jobRepositoryTestUtils = jobRepositoryTestUtils;
    }

    @BeforeEach
    public void clearJobExecutions() {
        jobRepositoryTestUtils.removeJobExecutions(); // λ°°μΉ νμ€νΈ μ€ν μ  λͺ¨λ  λ©νλ°μ΄ν°λ₯Ό μ΄κΈ°ννλ€.
    }

    @Test
    void job() throws Exception {
        // given
        // λ°°μΉμ‘μ λ¬΄λν μ€νμ μν΄ μ λν¬ νλΌλ―Έν°λ₯Ό μμ±
        JobParameters jobParameters = jobLauncherTestUtils.getUniqueJobParameters();

        // when
        // JobLauncherTestUtilsλ‘ λ‘λν λ°°μΉμ‘μ μ€ν
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // then
        // λ°°μΉμ‘μ κ²°κ³Όλ₯Ό κ²μ¦
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }

}
```

<br />
