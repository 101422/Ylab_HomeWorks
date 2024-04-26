package ru.vladimirvorobev.ylabhomework.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.vladimirvorobev.ylabhomework.dto.PersonDTO;
import ru.vladimirvorobev.ylabhomework.dto.TrainingTypeDTO;
import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;

@Mapper
public interface PersonMapper {

/*    @Mapping(target = "name", source = "name")
    @Mapping(target = "password", source = "password")
    PersonDTO personToPersonDTO(Person person);*/

    PersonDTO personToPersonDTO(Person person);
    Person personDTOToPerson(PersonDTO personDTO);

}
