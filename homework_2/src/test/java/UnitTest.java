
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.vladimirvorobev.ylabhomework.daoClasses.PersonDAOImpl;
import ru.vladimirvorobev.ylabhomework.daoClasses.TrainingAdditionalInformationDAOImpl;
import ru.vladimirvorobev.ylabhomework.daoClasses.TrainingDAOImpl;
import ru.vladimirvorobev.ylabhomework.daoClasses.TrainingTypeDAOImpl;
import ru.vladimirvorobev.ylabhomework.dataBase.DatabaseService;
import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.models.Training;
import ru.vladimirvorobev.ylabhomework.models.TrainingAdditionalInformation;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.vladimirvorobev.ylabhomework.security.Role.ROLE_USER;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UnitTest {

  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
          "postgres:15-alpine"
  );

  private TrainingTypeDAOImpl trainingTypeDAOImpl;
  private TrainingDAOImpl trainingDAOImpl;
  private TrainingAdditionalInformationDAOImpl trainingAdditionalInformationDAOImpl;
  private  PersonDAOImpl personDAOImpl;

  @BeforeAll
  static void beforeAll() {
    postgres.start();
  }

  @AfterAll
  static void afterAll() {
    postgres.stop();
  }

  @BeforeEach
  void setUp() {
      DatabaseService databaseService = new DatabaseService(   postgres.getJdbcUrl(),
              postgres.getUsername(),
              postgres.getPassword());
    personDAOImpl = new PersonDAOImpl(databaseService);
    trainingTypeDAOImpl = new TrainingTypeDAOImpl(databaseService);
    trainingDAOImpl = new TrainingDAOImpl(databaseService);
    trainingAdditionalInformationDAOImpl = new TrainingAdditionalInformationDAOImpl(databaseService);
  }

  @InjectMocks
  private Person personTest = new Person(1,"personName", "1", ROLE_USER);
  private TrainingType trainingTypeTest = new TrainingType(1, "TrainingType1");

  private TrainingAdditionalInformation trainingAdditionalInformation = new TrainingAdditionalInformation(1, "Key", "Value");
  private Training trainingTest = new Training(1, personTest, java.sql.Date.valueOf("2021-01-01"),  trainingTypeTest, 10, 1000); // mocking this class// mocking this class


  @Test
  @Order(1)
  public void shouldCreateTrainingTypeAndFindByName() {
      trainingTypeDAOImpl.save(trainingTypeTest);

      Optional<TrainingType> foundOptionalTrainingType = trainingTypeDAOImpl.findByName(trainingTypeTest.getName());

      assertTrue(foundOptionalTrainingType.isPresent());

      TrainingType foundTrainingType = foundOptionalTrainingType.get();

      assertEquals("TrainingType1", foundTrainingType.getName());
  }

  @Test
  @Order(2)
  public void shouldCreatePersonAndFindByName() {
      Person personTest = new Person(1,"personName", "1", ROLE_USER);

      personDAOImpl.save(personTest);

      Optional<Person> foundOptionalPerson = personDAOImpl.findByName(personTest.getName());

      assertTrue(foundOptionalPerson.isPresent());

      Person foundPerson = foundOptionalPerson.get();

      assertEquals("personName", foundPerson.getName());
  }

  @Test
  @Order(3)
  public void shouldCreateTrainingAndFindById() {
      trainingTypeDAOImpl.save(trainingTypeTest);

      trainingDAOImpl.save(trainingTest);

      Optional<Training> foundOptionalTraining = trainingDAOImpl.findById(trainingTest.getId());

      assertTrue(foundOptionalTraining.isPresent());

      Training foundTraining = foundOptionalTraining.get();

      assertEquals(java.sql.Date.valueOf("2021-01-01"), foundTraining.getDate());
  }

  @Test
  @Order(4)
  public void shouldCreateTrainingAdditionalInformationAndFindByTraining() {
      trainingTypeDAOImpl.save(trainingTypeTest);

      trainingDAOImpl.save(trainingTest);

      trainingAdditionalInformationDAOImpl.save(trainingAdditionalInformation, trainingTest);

      List<TrainingAdditionalInformation> foundTrainingAdditionalInformations = trainingAdditionalInformationDAOImpl.findByTraining(trainingTest);

      assertTrue(foundTrainingAdditionalInformations.size() == 1);

      assertEquals("Value", foundTrainingAdditionalInformations.get(0).getValue());
  }

  @Test
  @Order(5)
  void shouldGetTrainingTypes() {
      List<TrainingType> trainingTypes = trainingTypeDAOImpl.findAll();

      assertEquals(3, trainingTypes.size());
  }

  @Test
  @Order(6)
  void shouldGetTrainings() {
      trainingTypeDAOImpl.save(trainingTypeTest);

      List<Training> trainings = trainingDAOImpl.findAll();

      assertEquals(2, trainings.size());
  }

  @Test
  @Order(7)
  void shouldGetTrainingsByPerson() {
      Person personTest = new Person(1,"personName5", "1", ROLE_USER);

      personDAOImpl.save(personTest);

      Person personTest2 = new Person(2, "personName6", "1", ROLE_USER);

      personDAOImpl.save(personTest2);

      trainingTypeDAOImpl.save(trainingTypeTest);

      trainingDAOImpl.save(new Training(1, personTest, java.sql.Date.valueOf("2021-01-01"), trainingTypeTest, 10, 1000));
      trainingDAOImpl.save(new Training(2, personTest, java.sql.Date.valueOf("2021-01-02"), trainingTypeTest, 20, 2000));
      trainingDAOImpl.save(new Training(3, personTest2, java.sql.Date.valueOf("2021-01-02"), trainingTypeTest, 20, 2000));


      List<Training> trainings = trainingDAOImpl.findByPerson(personTest2);
      assertEquals(1, trainings.size());
  }

  @Test
  @Order(8)
  void shouldGetTrainingPersonAndTrainingTypeAndDate() {
      Person personTest = new Person(1,"personName7", "1", ROLE_USER);

      personDAOImpl.save(personTest);

      trainingTypeDAOImpl.save(trainingTypeTest);

      trainingDAOImpl.save(new Training(1, personTest, java.sql.Date.valueOf("2021-01-01"), trainingTypeTest, 10, 1000));
      trainingDAOImpl.save(new Training(2, personTest, java.sql.Date.valueOf("2021-01-02"), trainingTypeTest, 20, 2000));

      Optional<Training> foundOptionalTraining = trainingDAOImpl.findByPersonAndTrainingTypeAndDate(personTest, trainingTypeTest, java.sql.Date.valueOf("2021-01-01"));

      assertTrue(foundOptionalTraining.isPresent());

      Training foundTraining = foundOptionalTraining.get();

      assertEquals(1000, foundTraining.getAmountOfCalories());
  }

  @Test
  @Order(9)
  public void shouldUpdateTrainingAndFindById() {
      Optional<Training> foundOptionalTraining = trainingDAOImpl.findByPersonAndTrainingTypeAndDate(personTest, trainingTypeTest, java.sql.Date.valueOf("2021-01-01"));

      assertTrue(foundOptionalTraining.isPresent());

      Training foundTraining = foundOptionalTraining.get();

      assertEquals(1000, foundTraining.getAmountOfCalories());

      foundTraining.setAmountOfCalories(2000);

      trainingDAOImpl.update(foundTraining.getId(), foundTraining);

      Optional<Training> foundOptionalTraining2 = trainingDAOImpl.findById(foundTraining.getId());

      assertTrue(foundOptionalTraining2.isPresent());

      Training foundTraining2 = foundOptionalTraining2.get();

      assertEquals(2000, foundTraining2.getAmountOfCalories());
  }

  @Test
  @Order(10)
  void shouldDeleteTrainingById() {
      List<Training> trainings = trainingDAOImpl.findAll();

      assertEquals(7, trainings.size());

      trainingDAOImpl.delete(trainings.get(0).getId());

      List<Training> trainings2 = trainingDAOImpl.findAll();

      assertEquals(6, trainings2.size());
  }

}
