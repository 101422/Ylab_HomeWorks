package ru.vladimirvorobev.ylabhomework.services;

import ru.vladimirvorobev.ylabhomework.daoClasses.PersonDAOImpl;
import ru.vladimirvorobev.ylabhomework.daoClasses.TrainingDAOImpl;
import ru.vladimirvorobev.ylabhomework.daoClasses.TrainingTypeDAOImpl;
import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.models.Training;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Сервис по взаимодействию с базами данных.
 **/
public class TrainingService {

    private TrainingDAOImpl trainingDAOImpl;
    private TrainingTypeDAOImpl trainingTypeDAOImpl;
    private PersonDAOImpl personDAOImpl;

    public TrainingService(){
        this.trainingDAOImpl = new TrainingDAOImpl();
        this.trainingTypeDAOImpl = new TrainingTypeDAOImpl();
        this.personDAOImpl = new PersonDAOImpl();
    }

    /**
     * Сохранение типа тренировки в базе.
     *
     * @param trainingTypeName имя типа тренировкок
     **/
    public void createTrainingType(String trainingTypeName) {
        trainingTypeDAOImpl.save(new TrainingType(trainingTypeName));
    }

    /**
     * Получение списка всех типов тренировок.
     *
     * @return список всех типов тренировок
     **/
    public List<TrainingType> findAllTrainingTypes(){
        return trainingTypeDAOImpl.findAll();
    }

    /**
     * Получение списка всех тренировок.
     *
     * @return список всех тренировок
     **/
    public List<Training> findAllTrainings(){
        return trainingDAOImpl.findAll();
    }

    /**
     * Получение списка всех тренировок пользователя.
     *
     * @param personName имя пользователя
     * @return список тренировок пользователя
     **/
    public List<Training> findTrainingsByPersonName(String personName){
        Person person = personDAOImpl.findByName(personName);

        return trainingDAOImpl.findByPerson(person);
    }

    /**
     * Получение списка тренировок с отбором по пользователю, типу тренировки и дате.
     *
     * @param person пользователь
     * @param trainingType тип тренировки
     * @param date дата
     * @return список тренировок с отбором по пользователю, типу тренировки и дате.
     **/
    public List<Training> findTrainingsByPersonTypeDate(Person person, TrainingType trainingType, Date date){
        return trainingDAOImpl.findByPersonAndTrainingTypeAndDate(person, trainingType, date);
    }


    /**
     * Создание и сохранение тренировки в базе.
     *
     * @param personName имя пользователь
     * @param date дата
     * @param trainingTypeName имя типа тренировок
     * @param duration продолжительность
     * @param amountOfCalories количество калорий
     * @param additionalInformation список из Map с дополнительными сведениями о тренировке
     **/
    public void createTraining(String personName, Date date, String trainingTypeName, int duration, int amountOfCalories, List<HashMap<String, String>> additionalInformation) {
        Person person = personDAOImpl.findByName(personName);

        if (person == null){
            System.out.println("Person with name " + personName + " wasn't found!");

            return;
        }

        TrainingType trainingType = trainingTypeDAOImpl.findByName(trainingTypeName);

        if (trainingType == null){
            System.out.println("Training type with name " + trainingTypeName + " wasn't found!");

            return;
        }

        if (findTrainingsByPersonTypeDate(person, trainingType, date).size() > 0) {
            System.out.println("You have already created training of type: " + trainingTypeName + " in this date: " + date + "!");

            return;
        }

        Training training = new Training(person, date, trainingType, duration, amountOfCalories, additionalInformation);

        trainingDAOImpl.save(training);

        System.out.println("Created training:");

        System.out.println(training);
    }

    /**
     * Изменение и сохранение тренировки в базе.
     *
     * @param id ИД изменяемой тренировки
     * @param personName имя пользователь
     * @param date дата
     * @param trainingTypeName имя типа тренировок
     * @param duration продолжительность
     * @param amountOfCalories количество калорий
     * @param additionalInformation список из Map с дополнительными сведениями о тренировке
     **/
    public void updateTraining(int id, String personName, Date date, String trainingTypeName, int duration, int amountOfCalories, List<HashMap<String, String>> additionalInformation) {
        Training training = trainingDAOImpl.findById(id);

        Person person = personDAOImpl.findByName(personName);

        if (person == null){
            System.out.println("Person with name " + personName + " wasn't found!");

            return;
        }

        TrainingType trainingType = trainingTypeDAOImpl.findByName(trainingTypeName);

        if (trainingType == null){
            System.out.println("Training type with name " + trainingTypeName + " wasn't found!");

            return;
        }

        training.setPerson(person);
        training.setDate(date);
        training.setTrainingType(trainingType);
        training.setDuration(duration);
        training.setAmountOfCalories(amountOfCalories);
        training.setAdditionalInformation(additionalInformation);

        trainingDAOImpl.update(id , training);

        System.out.println("Updated training:");

        System.out.println(training);
    }

    /**
     * Удаление тренировки из базеы.
     *
     * @param id
     **/
    public void deleteTraining(int id) { trainingDAOImpl.delete(id); }

}
