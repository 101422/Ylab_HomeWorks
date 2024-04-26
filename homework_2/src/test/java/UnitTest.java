
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
import static org.assertj.core.api.Assertions.*;
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

    @DisplayName("Запуск test container")
    @BeforeAll
    static void beforeAll() {
    postgres.start();
    }

    @DisplayName("Закрытие test container")
    @AfterAll
    static void afterAll() {
    postgres.stop();
    }

    @DisplayName("Инииализация DAO-объектов, очистка всех таблиц перед выполнением тестов")
    @BeforeEach
    void setUp() {
        DatabaseService databaseService = new DatabaseService(   postgres.getJdbcUrl(),
        postgres.getUsername(),
        postgres.getPassword());

        personDAOImpl = new PersonDAOImpl(databaseService);
        trainingTypeDAOImpl = new TrainingTypeDAOImpl(databaseService);
        trainingDAOImpl = new TrainingDAOImpl(databaseService);
        trainingAdditionalInformationDAOImpl = new TrainingAdditionalInformationDAOImpl(databaseService);

        trainingAdditionalInformationDAOImpl.deleteAll();
        trainingDAOImpl.deleteAll();
        personDAOImpl.deleteAll();
        trainingTypeDAOImpl.deleteAll();
    }

    @InjectMocks
    private Person personTest = new Person(1,"personName", "1", ROLE_USER);
    private final TrainingType trainingTypeTest = new TrainingType(1, "TrainingType1");
    private final TrainingAdditionalInformation trainingAdditionalInformation = new TrainingAdditionalInformation(1, "Key", "Value");
    private final Training trainingTest = new Training(1, personTest, java.sql.Date.valueOf("2021-01-01"),  trainingTypeTest, 10, 1000); // mocking this class// mocking this class

    @DisplayName("Создание типа тренировки и поиск его по имени")
    @Test
    public void shouldCreateTrainingTypeAndFindByName() {
        trainingTypeDAOImpl.save(trainingTypeTest);

        Optional<TrainingType> foundOptionalTrainingType = trainingTypeDAOImpl.findByName(trainingTypeTest.getName());

        assertThat(foundOptionalTrainingType.isPresent()).isTrue();

        TrainingType foundTrainingType = foundOptionalTrainingType.get();

        assertThat(foundTrainingType.getName()).isEqualTo("TrainingType1");
    }

    @DisplayName("Создание пользователя и поиск его по имени")
    @Test
    public void shouldCreatePersonAndFindByName() {
        personDAOImpl.save(personTest);

        Optional<Person> foundOptionalPerson = personDAOImpl.findByName(personTest.getName());

        assertThat(foundOptionalPerson.isPresent()).isTrue();

        Person foundPerson = foundOptionalPerson.get();

        assertThat(foundPerson.getName()).isEqualTo("personName");
    }

    @DisplayName("Создание тренировки и поиск ее по id")
    @Test
    public void shouldCreateTrainingAndFindById() {
        personDAOImpl.save(personTest);

        Optional<Person> foundOptionalPerson = personDAOImpl.findByName(personTest.getName());

        assertThat(foundOptionalPerson.isPresent()).isTrue();

        Person foundPerson = foundOptionalPerson.get();

        trainingTypeDAOImpl.save(trainingTypeTest);

        Optional<TrainingType> foundOptionalTrainingType = trainingTypeDAOImpl.findByName(trainingTypeTest.getName());

        assertThat(foundOptionalTrainingType.isPresent()).isTrue();

        TrainingType foundTrainingType = foundOptionalTrainingType.get();

        Training trainingTest = new Training(1, foundPerson, java.sql.Date.valueOf("2021-01-01"),  foundTrainingType, 10, 1000);

        trainingDAOImpl.save(trainingTest);

        List<Training> trainings = trainingDAOImpl.findAll();

        assertThat(trainings.size()).isEqualTo(1);

        Optional<Training> foundOptionalTraining = trainingDAOImpl.findById(trainings.get(0).getId());

        assertThat(foundOptionalTraining.isPresent()).isTrue();

        Training foundTraining = foundOptionalTraining.get();

        assertThat(foundTraining.getDate()).isEqualTo(java.sql.Date.valueOf("2021-01-01"));
        }

    @DisplayName("Создание дополнительной информации о тренировке и поиск ее по тренировке")
    @Test
    public void shouldCreateTrainingAdditionalInformationAndFindByTraining() {
        personDAOImpl.save(personTest);

        Optional<Person> foundOptionalPerson = personDAOImpl.findByName(personTest.getName());

        assertThat(foundOptionalPerson.isPresent()).isTrue();

        Person foundPerson = foundOptionalPerson.get();

        trainingTypeDAOImpl.save(trainingTypeTest);

        Optional<TrainingType> foundOptionalTrainingType = trainingTypeDAOImpl.findByName(trainingTypeTest.getName());

        assertThat(foundOptionalTrainingType.isPresent()).isTrue();

        TrainingType foundTrainingType = foundOptionalTrainingType.get();

        Training trainingTest = new Training(1, foundPerson, java.sql.Date.valueOf("2021-01-01"),  foundTrainingType, 10, 1000);

        trainingDAOImpl.save(trainingTest);

        Optional<Training> foundOptionalTraining = trainingDAOImpl.findByPersonAndTrainingTypeAndDate(foundPerson, foundTrainingType, java.sql.Date.valueOf("2021-01-01"));

        assertThat(foundOptionalTraining.isPresent()).isTrue();

        Training foundTraining = foundOptionalTraining.get();

        trainingAdditionalInformationDAOImpl.save(trainingAdditionalInformation, foundTraining);

        List<TrainingAdditionalInformation> foundTrainingAdditionalInformations = trainingAdditionalInformationDAOImpl.findByTraining(foundTraining);

        assertThat(foundTrainingAdditionalInformations.size()).isEqualTo(1);

        assertThat(foundTrainingAdditionalInformations.get(0).getValue()).isEqualTo("Value");
    }

    @DisplayName("Получение типов тренировок")
    @Test
    void shouldGetTrainingTypes() {
        trainingTypeDAOImpl.save(new TrainingType(1, "trainingTypeTest"));
        trainingTypeDAOImpl.save(new TrainingType(2, "trainingTypeTest2"));

        List<TrainingType> trainingTypes = trainingTypeDAOImpl.findAll();

        assertThat(trainingTypes.size()).isEqualTo(2);
    }

    @DisplayName("Получение тренировок")
    @Test
    void shouldGetTrainings() {
        personDAOImpl.save(personTest);

        Optional<Person> foundOptionalPerson = personDAOImpl.findByName(personTest.getName());

        assertThat(foundOptionalPerson.isPresent()).isTrue();

        Person foundPerson = foundOptionalPerson.get();

        trainingTypeDAOImpl.save(trainingTypeTest);

        Optional<TrainingType> foundOptionalTrainingType = trainingTypeDAOImpl.findByName(trainingTypeTest.getName());

        assertThat(foundOptionalTrainingType.isPresent()).isTrue();

        TrainingType foundTrainingType = foundOptionalTrainingType.get();

        trainingDAOImpl.save(new Training(1, foundPerson, java.sql.Date.valueOf("2021-01-01"), foundTrainingType, 10, 1000));
        trainingDAOImpl.save(new Training(2, foundPerson, java.sql.Date.valueOf("2021-01-02"), foundTrainingType, 20, 2000));

        List<Training> trainings = trainingDAOImpl.findAll();

        assertThat(trainings.size()).isEqualTo(2);
    }

    @DisplayName("Получение тренировок")
    @Test
    void shouldGetTrainingsByPerson() {
        Person personTest = new Person(1,"personName5", "1", ROLE_USER);

        personDAOImpl.save(personTest);

        Optional<Person> foundOptionalPerson = personDAOImpl.findByName(personTest.getName());

        assertThat(foundOptionalPerson.isPresent()).isTrue();

        Person foundPerson = foundOptionalPerson.get();

        Person personTest2 = new Person(2, "personName6", "1", ROLE_USER);

        personDAOImpl.save(personTest2);

        Optional<Person> foundOptionalPerson2 = personDAOImpl.findByName(personTest2.getName());

        assertThat(foundOptionalPerson2.isPresent()).isTrue();

        Person foundPerson2 = foundOptionalPerson2.get();

        trainingTypeDAOImpl.save(trainingTypeTest);

        Optional<TrainingType> foundOptionalTrainingType = trainingTypeDAOImpl.findByName(trainingTypeTest.getName());

        assertThat(foundOptionalTrainingType.isPresent()).isTrue();

        TrainingType foundTrainingType = foundOptionalTrainingType.get();

        trainingDAOImpl.save(new Training(1, foundPerson, java.sql.Date.valueOf("2021-01-01"), foundTrainingType, 10, 1000));
        trainingDAOImpl.save(new Training(2, foundPerson, java.sql.Date.valueOf("2021-01-02"), foundTrainingType, 20, 2000));
        trainingDAOImpl.save(new Training(3, foundPerson2, java.sql.Date.valueOf("2021-01-02"), foundTrainingType, 20, 2000));

        List<Training> trainings = trainingDAOImpl.findByPerson(foundPerson2);

        assertThat(trainings.size()).isEqualTo(1);
    }

    @DisplayName("Получение тренировок по пользователю, типу тренировки и дате")
    @Test
    void shouldGetTrainingPersonAndTrainingTypeAndDate() {
        personDAOImpl.save(personTest);

        Optional<Person> foundOptionalPerson = personDAOImpl.findByName(personTest.getName());

        assertThat(foundOptionalPerson.isPresent()).isTrue();

        Person foundPerson = foundOptionalPerson.get();

        trainingTypeDAOImpl.save(trainingTypeTest);

        Optional<TrainingType> foundOptionalTrainingType = trainingTypeDAOImpl.findByName(trainingTypeTest.getName());

        assertThat(foundOptionalTrainingType.isPresent()).isTrue();

        TrainingType foundTrainingType = foundOptionalTrainingType.get();

        trainingDAOImpl.save(new Training(1, foundPerson, java.sql.Date.valueOf("2021-01-01"), foundTrainingType, 10, 1000));
        trainingDAOImpl.save(new Training(2, foundPerson, java.sql.Date.valueOf("2021-01-02"), foundTrainingType, 20, 2000));

        Optional<Training> foundOptionalTraining = trainingDAOImpl.findByPersonAndTrainingTypeAndDate(foundPerson, foundTrainingType, java.sql.Date.valueOf("2021-01-01"));

        assertThat(foundOptionalTraining.isPresent()).isTrue();

        Training foundTraining = foundOptionalTraining.get();

        assertThat(foundTraining.getAmountOfCalories()).isEqualTo(1000);
    }

    @DisplayName("Обновление тренировки и поиск ее по id")
    @Test
    public void shouldUpdateTrainingAndFindById() {
        personDAOImpl.save(personTest);

        Optional<Person> foundOptionalPerson = personDAOImpl.findByName(personTest.getName());

        assertThat(foundOptionalPerson.isPresent()).isTrue();

        Person foundPerson = foundOptionalPerson.get();

        trainingTypeDAOImpl.save(trainingTypeTest);

        Optional<TrainingType> foundOptionalTrainingType = trainingTypeDAOImpl.findByName(trainingTypeTest.getName());

        assertThat(foundOptionalTrainingType.isPresent()).isTrue();

        TrainingType foundTrainingType = foundOptionalTrainingType.get();

        Training trainingTest = new Training(1, foundPerson, java.sql.Date.valueOf("2021-01-01"),  foundTrainingType, 10, 1000);

        trainingDAOImpl.save(trainingTest);

        List<Training> trainings = trainingDAOImpl.findAll();

        assertThat(trainings.size()).isEqualTo(1);

        Optional<Training> foundOptionalTraining = trainingDAOImpl.findById(trainings.get(0).getId());

        assertThat(foundOptionalTraining.isPresent()).isTrue();

        Training foundTraining = foundOptionalTraining.get();

        assertThat(foundTraining.getAmountOfCalories()).isEqualTo(1000);

        foundTraining.setAmountOfCalories(2000);

        trainingDAOImpl.update(foundTraining.getId(), foundTraining);

        Optional<Training> foundOptionalTraining2 = trainingDAOImpl.findById(foundTraining.getId());

        assertThat(foundOptionalTraining2.isPresent()).isTrue();

        Training foundTraining2 = foundOptionalTraining2.get();

        assertThat(foundTraining2.getAmountOfCalories()).isEqualTo(2000);
    }

    @DisplayName("Удаление тренировки по id")
    @Test
    void shouldDeleteTrainingById() {
        personDAOImpl.save(personTest);

        Optional<Person> foundOptionalPerson = personDAOImpl.findByName(personTest.getName());

        assertThat(foundOptionalPerson.isPresent()).isTrue();

        Person foundPerson = foundOptionalPerson.get();

        trainingTypeDAOImpl.save(trainingTypeTest);

        Optional<TrainingType> foundOptionalTrainingType = trainingTypeDAOImpl.findByName(trainingTypeTest.getName());

        assertThat(foundOptionalTrainingType.isPresent()).isTrue();

        TrainingType foundTrainingType = foundOptionalTrainingType.get();

        trainingDAOImpl.save(new Training(1, foundPerson, java.sql.Date.valueOf("2021-01-01"), foundTrainingType, 10, 1000));
        trainingDAOImpl.save(new Training(2, foundPerson, java.sql.Date.valueOf("2021-01-02"), foundTrainingType, 20, 2000));

        List<Training> trainings = trainingDAOImpl.findAll();

        assertThat(trainings.size()).isEqualTo(2);

        trainingDAOImpl.delete(trainings.get(0).getId());

        List<Training> trainings2 = trainingDAOImpl.findAll();

        assertThat(trainings2.size()).isEqualTo(1);
    }

}
