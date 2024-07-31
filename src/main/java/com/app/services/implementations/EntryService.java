package com.app.services.implementations;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.domain.post.Entry;
import com.app.domain.user.ForoUser;
import com.app.exceptions.CreationException;
import com.app.repositories.EntryRepositoryImp;
import com.app.services.interfaces.IEntryService;

@Service
public class EntryService implements IEntryService{

  private EntryRepositoryImp entryRepository;
  public EntryService (EntryRepositoryImp entryRepository) {
    this.entryRepository = entryRepository;
  }

  @Override
  @Transactional
  public Entry createEntry(ForoUser user,String content){
    try{
      Entry entryCreated = new Entry();
      entryCreated.setUser(user);
      entryCreated.setContent(content);

      return entryRepository.save(entryCreated);
    }catch(Exception e){
      throw new CreationException("No se pudo crear el entry");
    }
  }

  @Override
  @Transactional
  public void addCommentToEntry(Long idEntry){
    if(!entryRepository.existsById(idEntry)){
      throw new CreationException("No se encontro el entry");
    }
    Optional<Entry> entryO = entryRepository.findById(idEntry);
    Entry entry = entryO.get();
    entry.setComments(entry.getComments()+1);
    entryRepository.save(entry);
  }

}
