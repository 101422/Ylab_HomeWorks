package ru.vladimirvorobev.ylabhomework.services;

import org.springframework.stereotype.Service;
import ru.vladimirvorobev.ylabhomework.annotations.TrainingCreationLoggable;
import ru.vladimirvorobev.ylabhomework.annotations.TrainingDeletionLoggable;
import ru.vladimirvorobev.ylabhomework.annotations.TrainingEditionLoggable;
import ru.vladimirvorobev.ylabhomework.daoClasses.PersonDAOImpl;
import ru.vladimirvorobev.ylabhomework.daoClasses.TrainingAdditionalInformationDAOImpl;
import ru.vladimirvorobev.ylabhomework.daoClasses.TrainingDAOImpl;
import ru.vladimirvorobev.ylabhomework.daoClasses.TrainingTypeDAOImpl;
import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.models.Training;
import ru.vladimirvorobev.ylabhomework.models.TrainingAdditionalInformation;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Сервис по взаимодействию с таблицей тренировок в базе данных .
 **/
@Service
public class TrainingService {

    private final TrainingDAOImpl trainingDAOImpl;
    private final TrainingTypeDAOImpl trainingTypeDAOImpl;
    private final PersonDAOImpl personDAOImpl;
    private final TrainingAdditionalInformationDAOImpl trainingAdditionalInformationDAOImpl;

    public TrainingService(TrainingDAOImpl trainingDAOImpl, TrainingTypeDAOImpl trainingTypeDAOImpl,
                           TrainingAdditionalInformationDAOImpl trainingAdditionalInformationDAOImpl, PersonDAOImpl personDAOImpl) throws IOException {

        this.trainingDAOImpl = trainingDAOImpl;
        this.trainingTypeDAOImpl = trainingTypeDAOImpl;
        this.trainingAdditionalInformationDAOImpl = trainingAdditionalInformationDAOImpl;
        this.personDAOImpl = personDAOImpl;
    }

    /**
     * Сохранение типа тренировки в базе.
     *
     * @param trainingTypeName имя типа тренировкок
     **/
    public void createTrainingType(String trainingTypeName) {
        TrainingType trainingType = new TrainingType();
        trainingType.setName(trainingTypeName);
        trainingTypeDAOImpl.save(trainingType);
    }

    public void saveTrainingType(TrainingType trainingType) {
        trainingTypeDAOImpl.save(trainingType);
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
     * Получение типа тренировки по имени.
     *
     * @param name имя типа тренировки
     * @return тип тренировки
     **/
    public Optional<TrainingType> findTrainingTypeByName(String name){
        Optional<TrainingType> foundedTrainingType = trainingTypeDAOImpl.findByName(name);

        return Optional.ofNullable(foundedTrainingType.orElse(null));
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
        Optional<Person> foundedPerson = personDAOImpl.findByName(personName);

        return trainingDAOImpl.findByPerson(foundedPerson.orElse(null));
    }

    /**
     * Получение списка дополнительной информации о тренировке
     *
     * @param training имя пользователя
     * @return список дополнительной информации
     **/
    public List<TrainingAdditionalInformation> findTrainingAdditionalInformationByTraining(Training training){
        return trainingAdditionalInformationDAOImpl.findByTraining(training);
    }

    /**
     * Получение списка тренировок с отбором по пользователю, типу тренировки и дате.
     *
     * @param person пользователь
     * @param trainingType тип тренировки
     * @param date дата
     * @return список тренировок с отбором по пользователю, типу тренировки и дате.
     **/
    public Optional<Training> findTrainingsByPersonTypeDate(Person person, TrainingType trainingType, Date date){
        Optional<Training> foundedTraining = trainingDAOImpl.findByPersonAndTrainingTypeAndDate(person, trainingType, date);

        return Optional.ofNullable(foundedTraining.orElse(null));
    }

 /*   *//**
     * Получение списка тренировки по пользователю, типу тренировки и дате.
     *
     * @param personName пользователь имя
     * @param trainingTypeName тип тренировки имя
     * @param date дата
     * @return список тренировок с отбором по пользователю, типу тренировки и дате.
     **/
    public Optional<Training> findTrainingsByPersonNameTypeNameDate(String personName, String trainingTypeName, Date date){
        Optional<Training> foundedTraining = null;

        Optional<Person> optionalPerson = personDAOImpl.findByName(personName);

        Person person;

        if (optionalPerson.isEmpty()){
            System.out.println("Person with name " + personName + " wasn't found!");

            return Optional.ofNullable(foundedTraining.orElse(null));
        }
        else {
            person = optionalPerson.get();
        }

        Optional<TrainingType> optionalTrainingType = trainingTypeDAOImpl.findByName(trainingTypeName);

        TrainingType trainingType;

        if (optionalTrainingType.isEmpty()){
            System.out.println("Training type with name " + trainingTypeName + " wasn't found!");

            return Optional.ofNullable(foundedTraining.orElse(null));
        }
        else {
            trainingType = optionalTrainingType.get();
        }

        foundedTraining = trainingDAOImpl.findByPersonAndTrainingTypeAndDate(person, trainingType, date);

        return Optional.ofNullable(foundedTraining.orElse(null));
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
    @TrainingCreationLoggable
    public void createTraining(String personName, Date date, String trainingTypeName, int duration, int amountOfCalories, List<HashMap<String, String>> additionalInformation) throws InstantiationException, IllegalAccessException {
        Optional<Person> optionalPerson = personDAOImpl.findByName(personName);

        Person person;

        if (optionalPerson.isEmpty()){
            System.out.println("Person with name " + personName + " wasn't found!");

            return;
        }
        else {
            person = optionalPerson.get();
        }

        Optional<TrainingType> optionalTrainingType = trainingTypeDAOImpl.findByName(trainingTypeName);

        TrainingType trainingType;

        if (optionalTrainingType.isEmpty()){
            System.out.println("Training type with name " + trainingTypeName + " wasn't found!");

            return;
        }
        else {
            trainingType = optionalTrainingType.get();
        }

        if (findTrainingsByPersonTypeDate(person, trainingType, date).isPresent()) {
            System.out.println("You have already created training of type: " + trainingTypeName + " in this date: " + date + "!");

            return;
        }

        Training training = new Training();

        training.setPerson(person);
        training.setDate(date);
        training.setTrainingType(trainingType);
        training.setDuration(duration);
        training.setAmountOfCalories(amountOfCalories);

        trainingDAOImpl.save(training);

        System.out.println("Created training:");

        System.out.println(training);

        System.out.println("Additional information:");

        Optional<Training> optionalFoundedTraining = trainingDAOImpl.findByPersonAndTrainingTypeAndDate(person, trainingType, date);

        Training foundedTraining;

        if (optionalFoundedTraining.isEmpty()){
            System.out.println("Training wasn't found!");

            return;
        }
        else {
            foundedTraining = optionalFoundedTraining.get();
        }

        additionalInformation.forEach( (additionalInformationElement) -> {

            additionalInformationElement.forEach((key, value) ->  {

                TrainingAdditionalInformation trainingAdditionalInformation = new TrainingAdditionalInformation();

                trainingAdditionalInformation.setKey(key);
                trainingAdditionalInformation.setValue(value);

                trainingAdditionalInformationDAOImpl.save(trainingAdditionalInformation, foundedTraining);

                System.out.println(trainingAdditionalInformation);
            });
        });
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
    @TrainingEditionLoggable
    public void updateTraining(int id, String personName, Date date, String trainingTypeName, int duration, int amountOfCalories, List<HashMap<String, String>> additionalInformation) throws InstantiationException, IllegalAccessException {
        Optional<Training> optionalTraining = trainingDAOImpl.findById(id);

        Training training;

        if (optionalTraining.isEmpty()){
            System.out.println("Training wasn't found!");

            return;
        }
        else {
            training = optionalTraining.get();
        }

        Optional<Person> optionalPerson = personDAOImpl.findByName(personName);

        Person person;

        if (optionalPerson.isEmpty()){
            System.out.println("Person with name " + personName + " wasn't found!");

            return;
        }
        else {
            person = optionalPerson.get();
        }

        Optional<TrainingType> optionalTrainingType = trainingTypeDAOImpl.findByName(trainingTypeName);

        TrainingType trainingType;

        if (optionalTrainingType.isEmpty()){
            System.out.println("Training type with name " + trainingTypeName + " wasn't found!");

            return;
        }
        else {
            trainingType = optionalTrainingType.get();
        }

        training.setPerson(person);
        training.setDate(date);
        training.setTrainingType(trainingType);
        training.setDuration(duration);
        training.setAmountOfCalories(amountOfCalories);

        trainingDAOImpl.update(id , training);

        System.out.println("Updated training:");

        System.out.println(training);

        System.out.println("Additional information:");

        Optional<Training> optionalFoundedTraining = trainingDAOImpl.findByPersonAndTrainingTypeAndDate(person, trainingType, date);

        Training foundedTraining;

        if (optionalFoundedTraining.isEmpty()){
            System.out.println("Training wasn't found!");

            return;
        }
        else {
            foundedTraining = optionalFoundedTraining.get();
        }

        additionalInformation.forEach( (additionalInformationElement) -> {

            additionalInformationElement.forEach((key, value) ->  {

                TrainingAdditionalInformation trainingAdditionalInformation = new TrainingAdditionalInformation();

                trainingAdditionalInformation.setKey(key);
                trainingAdditionalInformation.setValue(value);

                trainingAdditionalInformationDAOImpl.save(trainingAdditionalInformation, foundedTraining);

                System.out.println(trainingAdditionalInformation);
            });
        });
    }

    /**
     * Изменение и сохранение тренировки в базе по имени пользователя, типу тренировки и дате.
     *
     * @param oldPersonName имя пользователя для поиска редакритуемой тренировки
     * @param oldTrainingTypeName имя типа тренировок для поиска редакритуемой тренировки
     * @param oldDate дата для поиска редакритуемой тренировки
     * @param personName имя пользователь
     * @param date дата
     * @param trainingTypeName имя типа тренировок
     * @param duration продолжительность
     * @param amountOfCalories количество калорий
     * @param additionalInformation список из Map с дополнительными сведениями о тренировке
     **/
    @TrainingEditionLoggable
    public void updateTrainingByPersonTypeDate(String oldPersonName, String oldTrainingTypeName, Date oldDate, String personName, Date date, String trainingTypeName, int duration, int amountOfCalories, List<HashMap<String, String>> additionalInformation) throws InstantiationException, IllegalAccessException {
        Optional<Person> optionaloldPerson = personDAOImpl.findByName(oldPersonName);

        Person oldPerson;

        if (optionaloldPerson.isEmpty()){
            System.out.println("Person with name " + oldPersonName + " wasn't found!");

            return;
        }
        else {
            oldPerson = optionaloldPerson.get();
        }

        Optional<TrainingType> optionaloldTrainingType = trainingTypeDAOImpl.findByName(oldTrainingTypeName);

        TrainingType oldTrainingType;

        if (optionaloldTrainingType.isEmpty()){
            System.out.println("Training type with name " + oldTrainingTypeName + " wasn't found!");

            return;
        }
        else {
            oldTrainingType = optionaloldTrainingType.get();
        }

        Optional<Training> optionalTraining = trainingDAOImpl.findByPersonAndTrainingTypeAndDate(oldPerson, oldTrainingType, oldDate);

        Training training;

        if (optionalTraining.isEmpty()){
            System.out.println("Training wasn't found!");

            return;
        }
        else {
            training = optionalTraining.get();
        }

        Optional<Person> optionalPerson = personDAOImpl.findByName(personName);

        Person person;

        if (optionalPerson.isEmpty()){
            System.out.println("Person with name " + personName + " wasn't found!");

            return;
        }
        else {
            person = optionalPerson.get();
        }

        Optional<TrainingType> optionalTrainingType = trainingTypeDAOImpl.findByName(trainingTypeName);

        TrainingType trainingType;

        if (optionalTrainingType.isEmpty()){
            System.out.println("Training type with name " + trainingTypeName + " wasn't found!");

            return;
        }
        else {
            trainingType = optionalTrainingType.get();
        }

        training.setPerson(person);
        training.setDate(date);
        training.setTrainingType(trainingType);
        training.setDuration(duration);
        training.setAmountOfCalories(amountOfCalories);

        trainingDAOImpl.update(training.getId() , training);

        System.out.println("Updated training:");

        System.out.println(training);

        System.out.println("Additional information:");

        Optional<Training> optionalFoundedTraining = trainingDAOImpl.findByPersonAndTrainingTypeAndDate(person, trainingType, date);

        Training foundedTraining;

        if (optionalFoundedTraining.isEmpty()){
            System.out.println("Training wasn't found!");

            return;
        }
        else {
            foundedTraining = optionalFoundedTraining.get();
        }

        additionalInformation.forEach( (additionalInformationElement) -> {

            additionalInformationElement.forEach((key, value) ->  {

                TrainingAdditionalInformation trainingAdditionalInformation = new TrainingAdditionalInformation();

                trainingAdditionalInformation.setKey(key);
                trainingAdditionalInformation.setValue(value);

                trainingAdditionalInformationDAOImpl.save(trainingAdditionalInformation, foundedTraining);

                System.out.println(trainingAdditionalInformation);
            });
        });
    }

    /**
     * Удаление тренировки из базеы по имени пользователя, типу тренировки и дате..
     *
     * @param oldPersonName имя пользователя для поиска редакритуемой тренировки
     * @param oldTrainingTypeName имя типа тренировок для поиска редакритуемой тренировки
     * @param oldDate дата для поиска редакритуемой тренировки
     **/
    public void deleteTrainingByPersonTypeDate(String oldPersonName, String oldTrainingTypeName, Date oldDate){
        Optional<Person> optionaloldPerson = personDAOImpl.findByName(oldPersonName);

        Person oldPerson;

        if (optionaloldPerson.isEmpty()){
            System.out.println("Person with name " + oldPersonName + " wasn't found!");

            return;
        }
        else {
            oldPerson = optionaloldPerson.get();
        }

        Optional<TrainingType> optionaloldTrainingType = trainingTypeDAOImpl.findByName(oldTrainingTypeName);

        TrainingType oldTrainingType;

        if (optionaloldTrainingType.isEmpty()){
            System.out.println("Training type with name " + oldTrainingTypeName + " wasn't found!");

            return;
        }
        else {
            oldTrainingType = optionaloldTrainingType.get();
        }

        Optional<Training> optionalTraining = trainingDAOImpl.findByPersonAndTrainingTypeAndDate(oldPerson, oldTrainingType, oldDate);

        Training training;

        if (optionalTraining.isEmpty()){
            System.out.println("Training wasn't found!");

            return;
        }
        else {
            training = optionalTraining.get();
        }

        deleteTraining(training.getId());
    }

    /**
     * Удаление тренировки из базеы.
     *
     * @param id
     **/
    @TrainingDeletionLoggable
    public void deleteTraining(int id) { trainingDAOImpl.delete(id); }

}
