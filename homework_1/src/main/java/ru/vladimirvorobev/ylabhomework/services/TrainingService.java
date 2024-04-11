package ru.vladimirvorobev.ylabhomework.services;

import ru.vladimirvorobev.ylabhomework.dao.PersonDAO;
import ru.vladimirvorobev.ylabhomework.dao.TrainingDAO;
import ru.vladimirvorobev.ylabhomework.dao.TrainingTypeDAO;
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

    private TrainingDAO trainingDAO;
    private TrainingTypeDAO trainingTypeDAO;
    private PersonDAO personDAO;

    public TrainingService(){
        this.trainingDAO = new TrainingDAO();
        this.trainingTypeDAO = new TrainingTypeDAO();
        this.personDAO = new PersonDAO();
    }

    /**
     * Сохранение типа тренировки в базе.
     *
     * @param trainingTypeName имя типа тренировкок
     **/
    public void createTrainingType(String trainingTypeName) {
        trainingTypeDAO.save(new TrainingType(++TrainingTypeDAO.TRAINING_TYPES_COUNT, trainingTypeName));
    }

    /**
     * Получение списка всех типов тренировок.
     *
     * @return список всех типов тренировок
     **/
    public List<TrainingType> showAllTrainingTypes(){
        return trainingTypeDAO.showAll();
    }

    /**
     * Получение списка всех тренировок.
     *
     * @return список всех тренировок
     **/
    public List<Training> showAllTrainings(){
        return trainingDAO.showAll();
    }

    /**
     * Получение списка всех тренировок пользователя.
     *
     * @param personName имя пользователя
     * @return список тренировок пользователя
     **/
    public List<Training> showTrainingsByPersonName(String personName){
        Person person = personDAO.getPersonByName(personName);

        return trainingDAO.getTrainingsByPerson(person);
    }

    /**
     * Получение списка тренировок с отбором по пользователю, типу тренировки и дате.
     *
     * @param person пользователь
     * @param trainingType тип тренировки
     * @param date дата
     * @return список тренировок с отбором по пользователю, типу тренировки и дате.
     **/
    public List<Training> showTrainingsByPersonTypeDate(Person person, TrainingType trainingType, Date date){
        return trainingDAO.getTrainingsByPersonTypeDate(person, trainingType, date);
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
        Person person = personDAO.getPersonByName(personName);

        if (person == null){
            System.out.println("Person with name " + personName + " wasn't found!");

            return;
        }

        TrainingType trainingType = trainingTypeDAO.getTrainingTypeByName(trainingTypeName);

        if (trainingType == null){
            System.out.println("Training type with name " + trainingTypeName + " wasn't found!");

            return;
        }

        if (showTrainingsByPersonTypeDate(person, trainingType, date).size() > 0) {
            System.out.println("You have already created training of type: " + trainingTypeName + " in this date: " + date + "!");

            return;
        }

        Training training = new Training(++TrainingDAO.TRAININGS_COUNT ,person, date, trainingType, duration, amountOfCalories, additionalInformation);

        trainingDAO.save(training);

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
        Training training = trainingDAO.getTrainingById(id);

        Person person = personDAO.getPersonByName(personName);

        if (person == null){
            System.out.println("Person with name " + personName + " wasn't found!");

            return;
        }

        TrainingType trainingType = trainingTypeDAO.getTrainingTypeByName(trainingTypeName);

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

        trainingDAO.update(id , training);

        System.out.println("Updated training:");

        System.out.println(training);
    }

    /**
     * Удаление тренировки из базеы.
     *
     * @param id
     **/
    public void delete(int id) { trainingDAO.delete(id); }

}
