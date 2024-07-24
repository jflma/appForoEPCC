package com.app.services.implementations.user;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.domain.user.Person;
import com.app.repositories.user.PersonRepositoryImp;
import com.app.services.interfaces.user.IPersonService;

@Service
public class PersonService implements IPersonService{
  private PersonRepositoryImp personRepository;

  public PersonService(@Autowired PersonRepositoryImp personRepository) {
    this.personRepository = personRepository;
  }

  @Override
  public Person createPerson(String firstName, String lastName, String email, LocalDate birthDay) {
    Person newPerson = Person.builder()
                             .firstName(firstName)
                             .lastName(lastName)
                             .email(email)
                             .dateOfBirthDay(birthDay)
                             .build();

    return personRepository.save(newPerson);
    
  }

}