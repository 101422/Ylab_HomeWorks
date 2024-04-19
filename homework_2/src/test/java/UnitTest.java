
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.models.Training;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import ru.vladimirvorobev.ylabhomework.security.Role;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UnitTest {

  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
          "postgres:15-alpine"
  );

  private PersonsTestService personsTestService;
  private TrainingTypeTestService trainingTypeTestService;
  private TrainingTestService trainingTestService;

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
    DBConnectionProvider connectionProvider = new DBConnectionProvider(
            postgres.getJdbcUrl(),
            postgres.getUsername(),
            postgres.getPassword()
    );
    personsTestService = new PersonsTestService(connectionProvider);
    trainingTypeTestService = new TrainingTypeTestService(connectionProvider);
    trainingTestService = new TrainingTestService(connectionProvider);
  }

  @Test
  @Order(1)
  void shouldGetPersons() {
    personsTestService.createPerson(new Person(1, "personTest", "passwordTest", Role.valueOf ("ROLE_USER")));
    personsTestService.createPerson(new Person(2, "personTest2", "passwordTest2", Role.valueOf ("ROLE_USER")));

    List<Person> persons = personsTestService.getAllPersons();

    assertEquals(2, persons.size());
  }

  @Test
  @Order(2)
  void shouldGetTrainingTypes() {
    trainingTypeTestService.createTrainingType(new TrainingType(1, "trainingTypeTest"));
    trainingTypeTestService.createTrainingType(new TrainingType(2, "trainingTypeTest2"));

    List<TrainingType> trainingTypes = trainingTypeTestService.getAllTrainingTypes();

    assertEquals(2, trainingTypes.size());
  }

  @Test
  @Order(3)
  void shouldGetTrainings() {
    personsTestService.createPerson(new Person(3, "personTest3", "passwordTest", Role.valueOf ("ROLE_USER")));

    Person person = personsTestService.getAllPersons().get(0);

    trainingTypeTestService.createTrainingType(new TrainingType(3, "trainingTypeTest3"));

    TrainingType trainingType = trainingTypeTestService.getAllTrainingTypes().get(0);

    trainingTestService.createTraining(new Training(1, person, java.sql.Date.valueOf("2021-01-01"), trainingType, 10, 1000));
    trainingTestService.createTraining(new Training(2, person, java.sql.Date.valueOf("2021-01-01"), trainingType, 20, 2000));

    List<Training> trainings = trainingTestService.getAllTraining();
    assertEquals(2, trainings.size());
  }

}
