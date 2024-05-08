package ru.vladimirvorobev.ylabhomework.in;

import ru.vladimirvorobev.ylabhomework.models.Training;
import ru.vladimirvorobev.ylabhomework.out.ConsoleOutput;
import ru.vladimirvorobev.ylabhomework.security.AuthorizationService;
import ru.vladimirvorobev.ylabhomework.services.TrainingService;

import java.io.IOException;
import java.util.*;
import static java.util.stream.Collectors.groupingBy;

/**
 * Ввод данных из консоли.
 **/
//@Loggable
public class ConsoleInput {
   // final static Logger logger = LogManager.getLogger(Main.class);

    /**
    * Ввод данных из консоли.
    **/
    //@Loggable
    public void input() throws InstantiationException, IllegalAccessException, IOException {
        AuthorizationService authorizationService = new AuthorizationService();
        TrainingService trainingService = new TrainingService();
        ConsoleOutput consoleOutput = new ConsoleOutput();

        //logger.info("App started");

        while (true) {
            consoleOutput.greeiting();

            Scanner console = new Scanner(System.in);

            String enteredString = console.nextLine();

            if (enteredString.equals("reg")) {
                // Регистрация пользователя.

                consoleOutput.registrationMessage();
                consoleOutput.usernameMessage();

                String name = console.nextLine();

                consoleOutput.passwordMessage();

                String password = console.nextLine();

                authorizationService.registration(name, password);
            } else if (enteredString.equals("login")) {
                // Авторизация пользователя.

                consoleOutput.authorisationMessage();
                consoleOutput.usernameMessage();

                String name = console.nextLine();

                consoleOutput.passwordMessage();

                String password = console.nextLine();

                boolean isAuthorized = authorizationService.login(name, password).get("isAuthorized");
                boolean isAdmin      = authorizationService.login(name, password).get("isAdmin");

//                logger.info("User " + name + " authorized");

                if (isAuthorized)
                    while (true) {
                        consoleOutput.mainMenu(isAdmin);

                        Scanner console2 = new Scanner(System.in);

                        String enteredString2 = console2.nextLine();

                        if (enteredString2.equals("type")) {
                            // Создание типа тренировки.

                            consoleOutput.trainingTypeMessage();

                            String nameOfTrainingType = console2.nextLine();

                            trainingService.createTrainingType(nameOfTrainingType);
                        }
                        else if (enteredString2.equals("types")) {
                            // Вывод типов тренировок.

                            trainingService.findAllTrainingTypes().forEach(System.out::println);
                        }
                        else if (enteredString2.equals("training")) {
                            // Создание тренировки.

                            consoleOutput.trainingStartMessage();
                            consoleOutput.trainingDateMessage();

                            String dateString = console2.nextLine();

                            consoleOutput.trainingTrainingTypeMessage();

                            String trainingTypeName = console2.nextLine();

                            consoleOutput.trainingDurationMessage();

                            int duration = console2.nextInt();

                            consoleOutput.trainingAmountOfCaloriesMessage();

                            int amountOfCalories = console2.nextInt();

                            // Дополнительные сведения о тренировке хранятся в виде ArrayList of HashMap.

                            List<HashMap<String, String>> additionalInformation = new ArrayList<>();

                            while (true) {
                                consoleOutput.trainingAdditionalInformationStartMessage();

                                Scanner console3 = new Scanner(System.in);

                                String enteredString3 = console3.nextLine();

                                if (enteredString3.equals("add")) {
                                    // Создание дополнительных сведений о тренировке.

                                    HashMap<String, String> additionalInformationMap = new HashMap<>();

                                    consoleOutput.trainingAdditionalInformationKeyMessage();

                                    String key = console3.nextLine();

                                    consoleOutput.trainingAdditionalInformationValueMessage();

                                    String value = console3.nextLine();

                                    additionalInformationMap.put(key, value);

                                    additionalInformation.add(additionalInformationMap);
                                }
                                else
                                    break;
                            }

                            trainingService.createTraining(name, java.sql.Date.valueOf(dateString), trainingTypeName, duration, amountOfCalories, additionalInformation);

//                            logger.info("Created new training by user " + name);

                        }
                        else if (enteredString2.equals("trainings")) {
                            // Вывод тренировок пользователя.

                            consoleOutput.printTrainingsByPersonName(trainingService, name);

                        }
                        else if (isAdmin && enteredString2.equals("trainingsAll")) {
                            // Вывод всех тренировок.

                            consoleOutput.printAllTrainings(trainingService);

                        }
                        else if (enteredString2.equals("byDate")) {
                            // Вывод тренировок пользователя с сортировкой по дате.

                            List<Training> trainings = trainingService.findTrainingsByPersonName(name);

                            trainings.sort(Comparator.comparing(Training::getDate));

                            consoleOutput.printTrainings(trainings);

                        }
                        else if (enteredString2.equals("edit")) {
                            // Редактирование тренировок пользователя.

                            consoleOutput.yourTrainingsMessage();

                            consoleOutput.printTrainingsByPersonName(trainingService, name);

                            consoleOutput.trainingEditMessage();

                            Scanner console4 = new Scanner(System.in);

                            int id = console4.nextInt();

                            consoleOutput.trainingStartMessage();
                            consoleOutput.trainingDateMessage();

                            Scanner console5 = new Scanner(System.in);

                            String dateString = console5.nextLine();

                            consoleOutput.trainingTrainingTypeMessage();

                            String trainingTypeName = console5.nextLine();

                            consoleOutput.trainingDurationMessage();

                            int duration = console5.nextInt();

                            consoleOutput.trainingAmountOfCaloriesMessage();

                            int amountOfCalories = console5.nextInt();

                            // Дополнительные сведения о тренировке хранятся в виде ArrayList of HashMap.

                            List<HashMap<String, String>> additionalInformation = new ArrayList<>();

                            while (true) {
                                consoleOutput.trainingAdditionalInformationStartMessage();

                                Scanner console6 = new Scanner(System.in);

                                String enteredString3 = console6.nextLine();

                                if (enteredString3.equals("add")) {
                                    // Создание дополнительных сведений о тренировке.

                                    HashMap<String, String> additionalInformationMap = new HashMap<>();

                                    consoleOutput.trainingAdditionalInformationKeyMessage();

                                    String key = console6.nextLine();

                                    consoleOutput.trainingAdditionalInformationValueMessage();

                                    String value = console6.nextLine();

                                    additionalInformationMap.put(key, value);

                                    additionalInformation.add(additionalInformationMap);
                                }
                                else
                                    break;
                            }
                            trainingService.updateTraining(id, name, java.sql.Date.valueOf(dateString), trainingTypeName, duration, amountOfCalories, additionalInformation);

//                            logger.info("Edited training by user " + name);
                        }
                        else if (enteredString2.equals("delete")) {
                            // Удаление тренировки. Отображение всех тренировок пользователя и последующее удаление по id.

                            consoleOutput.deleteStartMessage();

                            consoleOutput.printTrainingsByPersonName(trainingService, name);

                            consoleOutput.deleteIdMessage();

                            Scanner console7 = new Scanner(System.in);

                            int id = console7.nextInt();

                            trainingService.deleteTraining(id);

//                            logger.info("Deleted training by user " + name);
                        }
                        else if (enteredString2.equals("stats")) {
                            // Отображение статистики.

                            consoleOutput.statsStartMessage();

                            List<Training> trainingsByPersonName = trainingService.findTrainingsByPersonName(name);

                            // Группировка по дате и вывод суммы калорий по каждой дате.

                            Map<Date, List<Training>> mapOfTrainingsByPersonName= trainingsByPersonName.stream()
                                    .collect(groupingBy(Training::getDate));

                            consoleOutput.printStats(mapOfTrainingsByPersonName);

//                            logger.info("Showed stats of user " + name);
                        }
                        else
                            break;
                    }
            }
            else
                break;
        }
//        logger.info("App finished");
    }

}


