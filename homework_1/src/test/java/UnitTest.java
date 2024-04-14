


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import ru.vladimirvorobev.ylabhomework.daoClasses.PersonDAOImpl;
import ru.vladimirvorobev.ylabhomework.daoClasses.TrainingDAOImpl;
import ru.vladimirvorobev.ylabhomework.daoClasses.TrainingTypeDAOImpl;
import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.models.Training;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import ru.vladimirvorobev.ylabhomework.services.TrainingService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.vladimirvorobev.ylabhomework.security.Role.ROLE_USER;


public class UnitTest {

  static HashMap <String, String> additionalInformationMap;
  static List<HashMap<String, String> > additionalInformation;

  @BeforeAll
  public static void setUp() {
    additionalInformationMap  = new HashMap<>();

    additionalInformationMap.put("key", "value");

    additionalInformation = new ArrayList<>();

    additionalInformation.add(additionalInformationMap);
  }

  @InjectMocks
  private Person personTest = new Person("personName", "1", ROLE_USER);
  private TrainingType trainingTypeTest = new TrainingType("TrainingType1"); // mocking this class// mocking this class
  private Training trainingTest = new Training(personTest, java.sql.Date.valueOf("2021-01-01"),  trainingTypeTest, 10, 1000, additionalInformation); // mocking this class// mocking this class

  @Test
  public void should_find_no_trainings_if_repository_is_empty() {
    TrainingService trainingService = new TrainingService();

    List<Training> tutorials = trainingService.findAllTrainings();

    assert(tutorials).isEmpty();
  }

  @Test
  public void should_create_a_training() {
    TrainingDAOImpl trainingDAOImpl = new TrainingDAOImpl();
    TrainingTypeDAOImpl trainingTypeDAOImpl = new TrainingTypeDAOImpl();
    PersonDAOImpl personDAOImpl = new PersonDAOImpl();
    TrainingService trainingService = new TrainingService();

    personDAOImpl.save(personTest);

    trainingTypeDAOImpl.save(trainingTypeTest);

    trainingService.createTraining("personName", java.sql.Date.valueOf("2021-01-01"), "TrainingType1", 10, 1000, additionalInformation);

    List<Training> foundedTrainings = trainingDAOImpl.findByPerson(personDAOImpl.findByName("personName"));

    Training foundedTraining = foundedTrainings.get(0);

    assertEquals(personTest, foundedTraining.getPerson());
    assertEquals(trainingTest.getDate(), java.sql.Date.valueOf("2021-01-01"));
    assertEquals(trainingTest.getTrainingType(), trainingTypeTest);
    assertEquals(trainingTest.getDuration(), 10);
    assertEquals(trainingTest.getAmountOfCalories(), 1000);
    assertEquals(trainingTest.getAdditionalInformation(), additionalInformation);
  }

  @Test
  public void should_find_all_trainings() {
    TrainingService trainingService = new TrainingService();
    TrainingDAOImpl trainingDAOImpl = new TrainingDAOImpl();

    trainingDAOImpl.save(trainingTest);

    List<Training> trainings = trainingService.findAllTrainings();

    assertEquals(trainings.size(),1);

    assert(trainings).contains(trainingTest);
  }

  @Test
  public void should_update_training_by_id() {
    TrainingService trainingService  = new TrainingService();
    PersonDAOImpl personDAOImpl = new PersonDAOImpl();
    TrainingDAOImpl trainingDAOImpl = new TrainingDAOImpl();
    TrainingTypeDAOImpl trainingTypeDAOImpl = new TrainingTypeDAOImpl();

    personDAOImpl.save(personTest);
    trainingDAOImpl.save(trainingTest);
    trainingTypeDAOImpl.save(trainingTypeTest);

    trainingService.updateTraining(1, "personName", java.sql.Date.valueOf("2021-02-02"), "TrainingType1", 20,  2000, additionalInformation);

    Training foundedTraining = trainingDAOImpl.findById(1);

    assertEquals(foundedTraining.getDate(), java.sql.Date.valueOf("2021-02-02"));
    assertEquals(foundedTraining.getDuration(), 20);
    assertEquals(foundedTraining.getAmountOfCalories(), 2000);
    assertEquals(foundedTraining.getAdditionalInformation(), additionalInformation);
  }

  @Test
  public void should_delete_training_by_id() {
    TrainingService trainingService = new TrainingService();
    TrainingDAOImpl trainingDAOImpl = new TrainingDAOImpl();

    trainingDAOImpl.save(trainingTest);

    trainingService.deleteTraining(1);

    List<Training> trainings = trainingService.findAllTrainings();

    assertEquals(trainings.size(),0);
  }

}
