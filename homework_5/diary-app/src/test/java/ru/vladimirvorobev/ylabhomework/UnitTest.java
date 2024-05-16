package ru.vladimirvorobev.ylabhomework;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.vladimirvorobev.ylabhomework.controllers.PersonController;
import ru.vladimirvorobev.ylabhomework.controllers.TrainingTypesController;
import ru.vladimirvorobev.ylabhomework.controllers.TrainingsController;
import ru.vladimirvorobev.ylabhomework.daoClasses.PersonDAOImpl;
import ru.vladimirvorobev.ylabhomework.daoClasses.TrainingAdditionalInformationDAOImpl;
import ru.vladimirvorobev.ylabhomework.daoClasses.TrainingDAOImpl;
import ru.vladimirvorobev.ylabhomework.daoClasses.TrainingTypeDAOImpl;
import ru.vladimirvorobev.ylabhomework.dto.PersonDTO;
import ru.vladimirvorobev.ylabhomework.dto.TrainingDTO;
import ru.vladimirvorobev.ylabhomework.dto.TrainingTypeDTO;
import ru.vladimirvorobev.ylabhomework.mappers.*;
import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.models.Training;
import ru.vladimirvorobev.ylabhomework.models.TrainingAdditionalInformation;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import ru.vladimirvorobev.ylabhomework.responseClasses.TrainingTypesResponse;
import ru.vladimirvorobev.ylabhomework.responseClasses.TrainingsResponse;
import ru.vladimirvorobev.ylabhomework.services.AuthorizationService;
import ru.vladimirvorobev.ylabhomework.services.SecurityService;
import ru.vladimirvorobev.ylabhomework.services.TrainingService;
import ru.vladimirvorobev.ylabhomework.validators.*;
import java.io.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.vladimirvorobev.ylabhomework.security.Role.ROLE_USER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UnitTest extends Mockito {

	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
			"postgres:15-alpine"
	);

	private static TrainingTypeDAOImpl trainingTypeDAOImpl;
	private static TrainingDAOImpl trainingDAOImpl;
	private static TrainingAdditionalInformationDAOImpl trainingAdditionalInformationDAOImpl;
	private static PersonDAOImpl personDAOImpl;

	@DisplayName("Запуск test container")
	@BeforeAll
	static void beforeAll() {
		postgres.start();

		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl(postgres.getJdbcUrl());
		dataSource.setUsername(postgres.getUsername());
		dataSource.setPassword(postgres.getPassword());

		try {
			Connection connection = dataSource.getConnection();

			Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

			Liquibase liquibase = new Liquibase("db/changelog/db.changelog-master.yaml", new ClassLoaderResourceAccessor(), database);

			liquibase.update();
		} catch (SQLException | DatabaseException e) {
			System.out.println("SQL Exception during migration " + e.getMessage());
		} catch (LiquibaseException e) {
			throw new RuntimeException(e);
		}

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		personDAOImpl = new PersonDAOImpl(jdbcTemplate);
		trainingTypeDAOImpl = new TrainingTypeDAOImpl(jdbcTemplate);
		trainingDAOImpl = new TrainingDAOImpl(jdbcTemplate);
		trainingAdditionalInformationDAOImpl = new TrainingAdditionalInformationDAOImpl(jdbcTemplate);
	}

	@DisplayName("Закрытие test container")
	@AfterAll
	static void afterAll() {
		postgres.stop();
	}

	@DisplayName("Инииализация DAO-объектов, очистка всех таблиц перед выполнением тестов")
	@BeforeEach
	void setUp() {
		trainingAdditionalInformationDAOImpl.deleteAll();
		trainingDAOImpl.deleteAll();
		personDAOImpl.deleteAll();
		trainingTypeDAOImpl.deleteAll();

		HashMap<String, Boolean> credentials = new HashMap<>();

		credentials.put("isAuthorized", true);
		credentials.put("isAdmin", false);

		SecurityService.credentials = credentials;

		SecurityService.name = "personName";
	}

	@InjectMocks
	private Person personTest = new Person(1,"personName", "1", ROLE_USER);
	private final TrainingType trainingTypeTest = new TrainingType(1, "TrainingType1");
	private final TrainingAdditionalInformation trainingAdditionalInformation = new TrainingAdditionalInformation(1, "Key", "Value");
	private final Training trainingTest = new Training(1, personTest, java.sql.Date.valueOf("2021-01-01"),  trainingTypeTest, 10, 1000);
	private TrainingTypeDTO trainingTypeDTOTest = new TrainingTypeDTO("TrainingType1");
	private PersonDTO personDTOTest = new PersonDTO("personName", "1");
	private final TrainingDTO trainingDTOTest = new TrainingDTO(1, personTest.getName(),  trainingTypeTest.getName(), java.sql.Date.valueOf("2021-01-01"), 10, 1000, new ArrayList<>());

	@InjectMocks
	private PersonController personController;
	@InjectMocks
	private TrainingsController trainingsController;
	@InjectMocks
	private TrainingTypesController trainingTypesController;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private PersonDAOImpl personDAOImplBean;

	@MockBean
	private TrainingService trainingServiceBean;

	@MockBean
	private TrainingDTOValidator trainingDTOValidatorBean;

	@DisplayName("Проверка получения тренировок по http-запросу")
	@Test
	public void shouldTestTrainingsControllerGetTrainingsHTTP() throws Exception {
		List <Training> trainings = new ArrayList<>();

		trainings.add(trainingTest);

		Mockito.when(trainingServiceBean.findTrainingsByPersonName(personTest.getName())).thenReturn(trainings);

		mockMvc.perform(
						get("/trainings"))
				.andExpect(status().isOk())
				.andExpect(content().json("{\"trainings\":[{\"id\":1,\"personName\":\"personName\",\"trainingTypeName\":\"TrainingType1\",\"date\":1609448400000,\"duration\":10,\"amountOfCalories\":1000,\"trainingAdditionalInformation\":[]}]}"));
	}


	@DisplayName("Проверка создания типа тренировок по http-запросу")
	@Test
	public void shouldTestTrainingsControllerCreateTrainingHTTP() throws Exception {
		BindingResult bindingResult = Mockito.mock(BindingResult.class);

		ArgumentCaptor<String> personNameCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Date> dateCaptor = ArgumentCaptor.forClass(Date.class);
		ArgumentCaptor<String> trainingTypeNameCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Integer> durationCaptor = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> amountOfCaloriesCaptor = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<ArrayList> additionalInformationCaptor = ArgumentCaptor.forClass(ArrayList.class);

		doNothing().when(trainingDTOValidatorBean).validate(trainingDTOTest, bindingResult);

		mockMvc.perform(
						post("/trainings/add")
								.content(objectMapper.writeValueAsString(trainingDTOTest))
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

		Mockito.verify(trainingServiceBean).createTraining(personNameCaptor.capture(), dateCaptor.capture(), trainingTypeNameCaptor.capture(), durationCaptor.capture(), amountOfCaloriesCaptor.capture(), additionalInformationCaptor.capture());

		String personNameActualValue = personNameCaptor.getValue();
		Date dateActualValue = dateCaptor.getValue();
		String trainingTypeActualValue = trainingTypeNameCaptor.getValue();

		assertThat(personNameActualValue).isEqualTo(trainingDTOTest.getPersonName());
		assertThat(dateActualValue.toLocalDate()).isEqualTo(trainingDTOTest.getDate().toLocalDate());
		assertThat(trainingTypeActualValue).isEqualTo(trainingDTOTest.getTrainingTypeName());
	}

	@DisplayName("Проверка регистрации пользователя по http-запросу")
	@Test
	public void shouldTestPersonControllerRegistrationHTTP() throws Exception {
		ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);

		mockMvc.perform(
						post("/auth/registration")
								.content(objectMapper.writeValueAsString(personDTOTest))
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());


		Mockito.verify(personDAOImplBean).save(captor.capture());
		Person actualValue = captor.getValue();
		assertThat(actualValue.getName()).isEqualTo(personDTOTest.getName());
	}


	@DisplayName("Проверка авторизации пользователя по http-запросу")
	@Test
	public void shouldTestPersonControllerLoginHTTP() throws Exception {
		Mockito.when(personDAOImplBean.findByName(personDTOTest.getName())).thenReturn(Optional.ofNullable(personTest));

		mockMvc.perform(
						post("/auth/login")
								.content(objectMapper.writeValueAsString(personDTOTest))
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}



	@DisplayName("Проверка получения типов тренировок по http-запросу")
	@Test
	public void shouldTestTrainingTypesControllerGetTrainingTypesHTTP() throws Exception {
		List <TrainingType> trainingTypes = new ArrayList<>();

		trainingTypes.add(trainingTypeTest);

		Mockito.when(trainingServiceBean.findAllTrainingTypes()).thenReturn(trainingTypes);

		mockMvc.perform(
						get("/types"))
				.andExpect(status().isOk())
				.andExpect(content().json("{'trainingTypes':[{'name':'TrainingType1'}]}"));
	}

	@DisplayName("Проверка создания типа тренировок по http-запросу")
	@Test
	public void shouldTestTrainingTypesControllerCreateTrainingTypeHTTP() throws Exception {
		ArgumentCaptor<TrainingType> captor = ArgumentCaptor.forClass(TrainingType.class);

		mockMvc.perform(
						post("/types/add")
								.content(objectMapper.writeValueAsString(trainingTypeDTOTest))
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

		Mockito.verify(trainingServiceBean).saveTrainingType(captor.capture());

		TrainingType actualValue = captor.getValue();
		assertThat(actualValue.getName()).isEqualTo("TrainingType1");
	}

	@DisplayName("Проверка контроллера пользователей, авторизации")
	@Test
	public void shouldTestPersonControllerLogin() throws Exception {
		AuthorizationService authorizationService = Mockito.mock(AuthorizationService.class);
		PersonRegistrationValidator personRegistrationValidator = Mockito.mock(PersonRegistrationValidator.class);
		PersonAuthorizationValidator personAuthorizationValidator = Mockito.mock(PersonAuthorizationValidator.class);
		BindingResult bindingResult = Mockito.mock(BindingResult.class);

		personController = new PersonController(authorizationService, new PersonMapperImpl(), personRegistrationValidator, personAuthorizationValidator);

		// Если авторизовн, статус 200.

		HttpServletResponse response = mock(HttpServletResponse.class);

		doNothing().when(personAuthorizationValidator).validate(personTest, bindingResult);

		HashMap<String, Boolean> credentialsTest = new HashMap<>();

		credentialsTest.put("isAuthorized", true);
		credentialsTest.put("isAdmin", false);

		Mockito.when(authorizationService.login(personTest.getName(), personTest.getPassword())).thenReturn(credentialsTest);

		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		when(response.getWriter()).thenReturn(writer);

		ResponseEntity<HttpStatus> responceTest =  personController.login(personDTOTest, bindingResult);

		assertThat(responceTest.getStatusCode().value()).isEqualTo(200);

		// Если не авторизовн, статус 403.

		doNothing().when(personAuthorizationValidator).validate(personTest, bindingResult);

		HashMap<String, Boolean> credentialsTest2 = new HashMap<>();

		credentialsTest2.put("isAuthorized", false);
		credentialsTest2.put("isAdmin", false);

		Mockito.when(authorizationService.login(personTest.getName(), personTest.getPassword())).thenReturn(credentialsTest2);

		StringWriter stringWriter2 = new StringWriter();
		PrintWriter writer2 = new PrintWriter(stringWriter2);
		when(response.getWriter()).thenReturn(writer2);

		ResponseEntity<HttpStatus> responceTest2 =  personController.login(personDTOTest, bindingResult);

		assertThat(responceTest2.getStatusCode().value()).isEqualTo(403);
	}

	@DisplayName("Проверка контроллера пользователей, регистрация")
	@Test
	public void shouldTestPersonControllerRegistration() throws Exception {
		AuthorizationService authorizationService = Mockito.mock(AuthorizationService.class);
		PersonRegistrationValidator personRegistrationValidator = Mockito.mock(PersonRegistrationValidator.class);
		PersonAuthorizationValidator personAuthorizationValidator = Mockito.mock(PersonAuthorizationValidator.class);
		BindingResult bindingResult = Mockito.mock(BindingResult.class);

		personController = new PersonController(authorizationService, new PersonMapperImpl(), personRegistrationValidator, personAuthorizationValidator);

		// Статус 201.

		HttpServletResponse response = mock(HttpServletResponse.class);

		doNothing().when(personAuthorizationValidator).validate(personTest, bindingResult);

		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		when(response.getWriter()).thenReturn(writer);

		ResponseEntity<HttpStatus> responceTest =  personController.registration(personDTOTest, bindingResult);

		assertThat(responceTest.getStatusCode().value()).isEqualTo(201);
	}


	@DisplayName("Проверка контроллера типов тренировок, получение типов тренировок")
	@Test
	public void shouldTestTrainingTypesControllerGetTrainingTypes() throws Exception {
		TrainingService trainingService = Mockito.mock(TrainingService.class);
		TrainingTypeMapper trainingTypeMapper  = Mockito.mock(TrainingTypeMapper.class );
		TrainingTypeValidator trainingTypeValidator  = Mockito.mock(TrainingTypeValidator.class );

		// Если авторизован.

		HttpServletResponse response = mock(HttpServletResponse.class);

		List <TrainingType> trainingTypes = new ArrayList<>();

		trainingTypes.add(trainingTypeTest);

		Mockito.when(trainingService.findAllTrainingTypes()).thenReturn(trainingTypes);

		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);

		when(response.getWriter()).thenReturn(writer);

		trainingTypesController = new TrainingTypesController(trainingService, trainingTypeMapper, trainingTypeValidator);

		TrainingTypesResponse responsetest = trainingTypesController.getTrainingTypes(response);

		assertThat(responsetest.getTrainingTypes().size()).isEqualTo(1);

		// Если не авторизован.

		HttpServletResponse response2 = mock(HttpServletResponse.class);

		HashMap<String, Boolean> credentials = new HashMap<>();

		credentials.put("isAuthorized", false);
		credentials.put("isAdmin", false);

		SecurityService.credentials = credentials;

		StringWriter stringWriter2 = new StringWriter();
		PrintWriter writer2 = new PrintWriter(stringWriter2);

		when(response.getWriter()).thenReturn(writer2);

		trainingTypesController = new TrainingTypesController(trainingService, trainingTypeMapper, trainingTypeValidator);

		TrainingTypesResponse responsetest2 = trainingTypesController.getTrainingTypes(response2);

		assertThat(responsetest2.getTrainingTypes().size()).isEqualTo(0);
	}

	@DisplayName("Проверка контроллера типов тренировок, создание типа тренировок")
	@Test
	public void shouldTestTrainingTypesControllerCreateTrainingType() throws Exception {
		TrainingService trainingService = Mockito.mock(TrainingService.class);
		TrainingTypeMapper trainingTypeMapper  = Mockito.mock(TrainingTypeMapper.class );
		TrainingTypeValidator trainingTypeValidator  = Mockito.mock(TrainingTypeValidator.class );
		BindingResult bindingResult = Mockito.mock(BindingResult.class);

		// Если авторизован, статус 201.

		trainingTypesController = new TrainingTypesController(trainingService, trainingTypeMapper, trainingTypeValidator);

		TrainingTypeDTO trainingTypeDTO = new TrainingTypeDTO();

		HttpServletResponse response = mock(HttpServletResponse.class);

		doNothing().when(trainingTypeValidator).validate(trainingTypeTest, bindingResult);


		when(trainingTypeMapper.trainingTypeDTOToTrainingType(trainingTypeDTOTest)).thenReturn(trainingTypeTest);

		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);

		when(response.getWriter()).thenReturn(writer);

		ResponseEntity<HttpStatus> responceTest =  trainingTypesController.add(trainingTypeDTOTest, bindingResult);

		assertThat(responceTest.getStatusCode().value()).isEqualTo(201);

		// Если не авторизовн, статус 403.

		doNothing().when(trainingTypeValidator).validate(trainingTypeDTO, bindingResult);

		SecurityService.credentials.put("isAuthorized", false);
		SecurityService.credentials.put("isAdmin", false);

		StringWriter stringWriter2 = new StringWriter();
		PrintWriter writer2 = new PrintWriter(stringWriter2);

		when(response.getWriter()).thenReturn(writer2);

		ResponseEntity<HttpStatus> responceTest2 =  trainingTypesController.add(trainingTypeDTOTest, bindingResult);

		assertThat(responceTest2.getStatusCode().value()).isEqualTo(403);
	}

	@DisplayName("Проверка контроллера тренировок, получение тренировок")
	@Test
	public void shouldTestTrainingsControllerGetTrainings() throws Exception {
		TrainingService trainingService = Mockito.mock(TrainingService.class);
		TrainingMapper trainingMapper  = Mockito.mock(TrainingMapper.class );
		TrainingAdditionalInformationMapper trainingAdditionalInformationMapper  = Mockito.mock(TrainingAdditionalInformationMapper.class );
		TrainingDTOValidator trainingDTOValidator  = Mockito.mock(TrainingDTOValidator.class );
		TrainingAdditionalInformationDTOValidator trainingAdditionalInformationDTOValidator  = Mockito.mock(TrainingAdditionalInformationDTOValidator.class );

		// Если авторизован.

		HttpServletResponse response = mock(HttpServletResponse.class);

		when(trainingMapper.trainingToTrainingDTO(trainingTest)).thenReturn(trainingDTOTest);

		List <Training> trainings = new ArrayList<>();

		trainings.add(trainingTest);

		Mockito.when(trainingService.findAllTrainings()).thenReturn(trainings);

		SecurityService.credentials.put("isAdmin", true);

		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);

		when(response.getWriter()).thenReturn(writer);

		trainingsController = new TrainingsController(trainingService, trainingMapper, trainingAdditionalInformationMapper, trainingDTOValidator, trainingAdditionalInformationDTOValidator);

		TrainingsResponse responseTest = trainingsController.getTrainings(response);

		assertThat(responseTest.getTrainings().size()).isEqualTo(1);

		// Если не авторизован.

		HttpServletResponse response2 = mock(HttpServletResponse.class);

		HashMap<String, Boolean> credentials = new HashMap<>();

		credentials.put("isAuthorized", false);
		credentials.put("isAdmin", false);

		SecurityService.credentials = credentials;

		StringWriter stringWriter2 = new StringWriter();
		PrintWriter writer2 = new PrintWriter(stringWriter2);

		when(response.getWriter()).thenReturn(writer2);

		trainingsController = new TrainingsController(trainingService, trainingMapper, trainingAdditionalInformationMapper, trainingDTOValidator, trainingAdditionalInformationDTOValidator);

		TrainingsResponse responseTest2 = trainingsController.getTrainings(response2);

		assertThat(responseTest2.getTrainings().size()).isEqualTo(0);
	}

	@DisplayName("Проверка контроллера тренировок, создание тренировок")
	@Test
	public void shouldTestTrainingsControllerCreateTraining() throws Exception {
		TrainingService trainingService = Mockito.mock(TrainingService.class);
		TrainingMapper trainingMapper  = Mockito.mock(TrainingMapper.class );
		TrainingAdditionalInformationMapper trainingAdditionalInformationMapper  = Mockito.mock(TrainingAdditionalInformationMapper.class );
		TrainingDTOValidator trainingDTOValidator  = Mockito.mock(TrainingDTOValidator.class );
		TrainingAdditionalInformationDTOValidator trainingAdditionalInformationDTOValidator  = Mockito.mock(TrainingAdditionalInformationDTOValidator.class );
		BindingResult bindingResult = Mockito.mock(BindingResult.class);

		// Если авторизован, статус 201.

		trainingsController = new TrainingsController(trainingService, trainingMapper, trainingAdditionalInformationMapper, trainingDTOValidator, trainingAdditionalInformationDTOValidator);

		TrainingTypeDTO trainingTypeDTO = new TrainingTypeDTO();

		HttpServletResponse response = mock(HttpServletResponse.class);

		doNothing().when(trainingDTOValidator).validate(trainingDTOTest, bindingResult);

		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);

		when(response.getWriter()).thenReturn(writer);

		ResponseEntity<HttpStatus> responseTest =  trainingsController.add(trainingDTOTest, bindingResult);

		assertThat(responseTest.getStatusCode().value()).isEqualTo(201);

		// Если не авторизовн, статус 403.

		doNothing().when(trainingDTOValidator).validate(trainingDTOTest, bindingResult);

		SecurityService.credentials.put("isAuthorized", false);
		SecurityService.credentials.put("isAdmin", false);

		StringWriter stringWriter2 = new StringWriter();
		PrintWriter writer2 = new PrintWriter(stringWriter2);

		when(response.getWriter()).thenReturn(writer2);

		ResponseEntity<HttpStatus> responseTest2 =  trainingsController.add(trainingDTOTest, bindingResult);

		assertThat(responseTest2.getStatusCode().value()).isEqualTo(403);
	}

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
		trainingDAOImpl.deleteAll();

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
