package ru.vladimirvorobev.ylabhomework.dataBase;

/**
 * Класс констант SQL-запросов.
 **/
public final class SQLQueryConstants {

    private SQLQueryConstants() {
    }

    public static final String GET_PERSON_BY_NAME = "SELECT * FROM entities.person WHERE name=?";
    public static final String SAVE_PERSON = "INSERT INTO entities.person(id, name, password, role) VALUES(nextval('person_id_autoincrement'),?,?,?)";
    public static final String GET_TRAINING_ADDITIONAL_INFORMATION_BY_TRAINING_ID = "SELECT * FROM util.training_additional_information WHERE training_id=?";
    public static final String SAVE_TRAINING_ADDITIONAL_INFORMATION = "INSERT INTO util.training_additional_information(id, key, value, training_id) VALUES(nextval('training_additional_information_id_autoincrement'),?,?,?)";
    public static final String GET_ALL_TRAININGS = "SELECT entities.training.* , person.name as person_name, person.password as person_password, person.role as person_role, training_type.name as training_type_name FROM entities.training JOIN entities.person ON training.person_id = person.id JOIN util.training_type ON training.training_type_id = training_type.id ";
    public static final String GET_TRAININGS_BY_PERSON_ID = "SELECT entities.training.* , person.name as person_name, person.password as person_password, person.role as person_role, training_type.name as training_type_name FROM entities.training JOIN entities.person ON training.person_id = person.id JOIN util.training_type ON training.training_type_id = training_type.id  WHERE person_id=? ";
    public static final String GET_TRAINING_BY_PERSON_ID_AND_TRAINING_TYPE_ID_AND_DATE = "SELECT entities.training.* , person.name as person_name, person.password as person_password, person.role as person_role, training_type.name as training_type_name FROM entities.training JOIN entities.person ON training.person_id = person.id JOIN util.training_type ON training.training_type_id = training_type.id  WHERE person_id=? AND training_type_id=? AND date=? ";
    public static final String GET_TRAINING_BY_ID = "SELECT entities.training.* , person.name as person_name, person.password as person_password, person.role as person_role, training_type.name as training_type_name FROM entities.training JOIN entities.person ON training.person_id = person.id JOIN util.training_type ON training.training_type_id = training_type.id  WHERE training.id=? ";
    public static final String SAVE_TRAINING = "INSERT INTO entities.training(id, date, duration, amount_of_calories, person_id, training_type_id) VALUES(nextval('training_id_autoincrement'),?,?,?,?,?)";
    public static final String UPDATE_TRAINING = "UPDATE entities.training set date=?, duration=?, amount_of_calories=?, person_id=?, training_type_id=? WHERE id=?";
    public static final String DELETE_TRAINING = "DELETE FROM entities.training WHERE id=?";
    public static final String GET_ALL_TRAINING_TYPES = "SELECT * FROM util.training_type";
    public static final String GET_ALL_AUDIT_INFO = "SELECT * FROM util.audit_info";
    public static final String GET_TRAINING_TYPE_BY_NAME = "SELECT * FROM util.training_type WHERE name=?";
    public static final String SAVE_TRAINING_TYPE =  "INSERT INTO util.training_type(id, name) VALUES (nextval('training_type_id_autoincrement'),?)";
    public static final String SAVE_AUDIT_INFO =  "INSERT INTO util.audit_info(id, event, timestamp) VALUES (nextval('training_type_id_autoincrement'),?,?)";
    public static final String DELETE_ALL_PERSONS = "TRUNCATE entities.person CASCADE";
    public static final String DELETE_ALL_TRAININGS = "TRUNCATE entities.training CASCADE";
    public static final String DELETE_ALL_TRAINING_TYPES = "TRUNCATE util.training_type CASCADE";
    public static final String DELETE_ALL_TRAINING_ADDITIONAL_INFORMATION = "TRUNCATE util.training_additional_information CASCADE";
    public static final String DELETE_ALL_AUDIT_INFO = "DELETE FROM util.audit_info";

}
