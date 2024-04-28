package ru.vladimirvorobev.ylabhomework.out;

import ru.vladimirvorobev.ylabhomework.annotations.TrainingGettingStatsLoggable;
import ru.vladimirvorobev.ylabhomework.models.Training;
import ru.vladimirvorobev.ylabhomework.services.TrainingService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Вывод данных в консоль.
 **/
public class ConsoleOutput {

    /**
     * Приветственное сообщение.
     **/
    public void greeiting() {
        System.out.println("______________");
        System.out.println("Enter 'reg' or 'login', or other to exit");
    }

    /**
     * Сообщение о регистрации.
     **/
    public void registrationMessage() {
        System.out.println("______________");
        System.out.println("Registration");
    }

    /**
     * Сообщение об авторизации.
     **/
    public void authorisationMessage() {
        System.out.println("______________");
        System.out.println("Authorization");
    }

    /**
     * Сообщение о необходимости ввода имени.
     **/
    public void usernameMessage() {
        System.out.println("Enter name");
    }

    /**
     * Сообщение о необходимости ввода пароля.
     **/
    public void passwordMessage() {
        System.out.println("Enter password");
    }

    /**
     * Главное меню. Если вошел администратор, добавляется пункт о просмотреть всех тренировок
     *
     * @param isAdmin вошел админ
     **/
    public void mainMenu(boolean isAdmin) {
        System.out.println("______________");
        System.out.println("Enter:");
        System.out.println("'type' to create type of training");
        System.out.println("'types' to list training types");
        System.out.println("'training' to create training");
        System.out.println("'trainings' to list your trainings");
        System.out.println("'byDate' to list your trainings sorted by date");
        System.out.println("'edit' to edit your trainings");
        System.out.println("'delete' to delete your trainings");
        System.out.println("'stats' to list static (sum amount of calories per date) ");
        if (isAdmin) System.out.println("'trainingsAll' to list all trainings (admin only)");
        System.out.println("or other key to exit");
    }

    /**
     * Сообщение при создании типа тренировок.
     **/
    public void trainingTypeMessage() {
        System.out.println("______________");
        System.out.println("Create training type");
        System.out.println("Enter name of type");
    }

    /**
     * Заголовок сообщения при создании тренировки.
     **/
    public void trainingStartMessage() {
        System.out.println("______________");
        System.out.println("Enter:");
    }

    /**
     * Сообщение при вводе длительности тренировки.
     **/
    public void trainingDateMessage() {
        System.out.print("Date in yyyy-MM-dd: ");
    }

    /**
     * Сообщение при вводе типа тренировки.
     **/
    public void trainingTrainingTypeMessage() {
        System.out.print("Training type: ");
    }

    /**
     * Сообщение при вводе длительности тренировки.
     **/
    public void trainingDurationMessage() {
        System.out.print("Duration: ");
    }

    /**
     * Сообщение при вводе сожженных калорий при тренировке.
     **/
    public void trainingAmountOfCaloriesMessage() {
        System.out.print("Amount of calories: ");
    }

    /**
     * Заглавное сообщение при вводе дополнительных сведений о тренировке.
     **/
    public void trainingAdditionalInformationStartMessage() {
        System.out.println("______________");
        System.out.println("Enter 'add' to enter additional information key, or 'value' to enter additional information value, or other to exit");
    }

    /**
     * Сообщение при вводе ключа дополнительного сведения.
     **/
    public void trainingAdditionalInformationKeyMessage() {
        System.out.println("______________");
        System.out.print("Key: ");
    }

    /**
     * Сообщение при вводе значение дополнительного сведения.
     **/
    public void trainingAdditionalInformationValueMessage() {
        System.out.print("Value: ");
    }

    /**
     * Заголовок при выводе тренировок.
     **/
    public void yourTrainingsMessage() {
        System.out.println("______________");
        System.out.println("Your trainings:");
    }

    /**
     * Сообщение при редактировании тренировки.
     **/
    public void trainingEditMessage() {
        System.out.println("______________");
        System.out.println("Enter id of training to edit:");
    }

    /**
     * Заглавное сообщение при удалении тренировки.
     **/
    public void deleteStartMessage() {
        System.out.println("______________");
        System.out.println("Your trainings:");
    }

    /**
     * Сообщение для ввода id удаляемой тренировки.
     **/
    public void deleteIdMessage() {
        System.out.println("______________");
        System.out.println("Enter id of training to delete:");
    }

    /**
     * Сообщение при отображении статистики.
     **/
    public void statsStartMessage() {
        System.out.println("______________");
        System.out.println("Your stats:");
    }
    /**
     * Вывод статистки (суммарных сожженных калорий по датам).
     *
     * @param mapOfTrainingsByPersonName результат группировки тренировок по дате.
     *
     **/
    @TrainingGettingStatsLoggable
    public void printStats(Map<Date, List<Training>> mapOfTrainingsByPersonName) {
        mapOfTrainingsByPersonName.forEach((key, value) ->
                System.out.println(key + " - " +  value.stream().mapToInt(Training::getAmountOfCalories).sum()+ " calories"));
    }

    /**
     * Вывод трениковок пользователя.
     *
     * @param trainingService экземпляр TrainingService
     * @param name имя пользователя
     *
     **/
    public void printTrainingsByPersonName(TrainingService trainingService, String name){

        AtomicInteger i = new AtomicInteger(1);

        trainingService.findTrainingsByPersonName(name).forEach( (trainings) -> {

            System.out.println(trainings.getId() + ": " + trainings);
            System.out.println("Additional information:");
                    trainingService.findTrainingAdditionalInformationByTraining(trainings).forEach( (additionalInformation) -> System.out.println(additionalInformation)       );

                    i.getAndIncrement();
                }
        );
    }

    /**
     * Вывод всех тренировок для администратора.
     *
     * @param trainingService экземпляр TrainingService
     *
     **/
    public void printAllTrainings(TrainingService trainingService){

        AtomicInteger i = new AtomicInteger(1);

        trainingService.findAllTrainings().forEach( (trainings) -> {

                    System.out.println(trainings.getId() + ": " + trainings + ", additional information: " + trainingService.findTrainingAdditionalInformationByTraining(trainings));

                    i.getAndIncrement();
                }
        );
    }

    /**
     * Вывод переданных тренировок для администратора.
     *
     * @param trainings список тренировок
     *
     **/
    public void printTrainings(List<Training> trainings){
        trainings.forEach(System.out::println);
    }


}
