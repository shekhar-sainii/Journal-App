package net.engeeringdigest.journal.service;

import lombok.extern.slf4j.Slf4j;
import net.engeeringdigest.journal.entity.JournalEntry;
import net.engeeringdigest.journal.entity.User;
import net.engeeringdigest.journal.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JournalEntryService {
    @Autowired
    private UserService userService;

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try {
            User user = userService.findByUserName(userName);

            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);

        } catch (Exception e) {
            log.error("Exception ",e);
        }
    }

    public void saveEntry(JournalEntry journalEntry){
        try {
            journalEntryRepository.save(journalEntry);
        } catch (Exception e) {
            log.error("Exception ",e);
        }
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        boolean removed = false;
       try {
           User user = userService.findByUserName(userName);
           removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
           if(removed){
               userService.saveUser(user);
               journalEntryRepository.deleteById(id);

           }
           return removed;
       } catch (Exception e) {
           throw new RuntimeException(e);
       }

    }


}
