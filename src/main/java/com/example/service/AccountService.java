package com.example.service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    /**
     * Persist a new Account.
     * @param Account a transient Account entity.
     * @return a persisted Account entity.
     */
    public Account registerAccount(Account account) throws IllegalArgumentException{
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be blank.");
        }

        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters long.");
        }

        Optional<Account> existingAccount = accountRepository.findByUsername(account.getUsername());
        if (existingAccount.isPresent()) {
            throw new IllegalArgumentException("Username already exists.");
        }
        return accountRepository.save(account);
    }

    /**
     * Persist a new Account.
     * @param Account a transient Account entity.
     * @return a persisted Account entity.
     */
    public Account findAccount(Account account){
        Optional<Account> existingAccount = accountRepository.findByUsername(account.getUsername());
        return accountRepository.save(account);
    }

    /**
     * @return all persisted Account entities.
     */
    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }
//     /**
//      * Assign a student to a Account while retrieving only the Account entity.
//      * @param AccountId the Id of an already existing Account entity.
//      * @param student an already persisted student entity.
//      */
//     public void addStudentToAccount(long AccountId, Student student){
//         Optional<Account> AccountOptional = AccountRepository.findById(AccountId);
//         if(AccountOptional.isPresent()){
//             Account Account = AccountOptional.get();
//             Account.getStudents().add(student);
//             AccountRepository.save(Account);
//         }
//     }
//     /**
//      * Get the students of a Account while retrieving only the Account entity.
//      * @param AccountId the Id of an already existing Account entity.
//      * @return the persisted student entities
//      */
//     public List<Student> getStudentsOfAccount(long AccountId){
//         Optional<Account> AccountOptional = AccountRepository.findById(AccountId);
//         if(AccountOptional.isPresent()){
//             Account Account = AccountOptional.get();
//             return Account.getStudents();
//         }
//         return null;
//     }
//     /**
//      * Unassign a student from a Account while retrieving only the clasroom entity.
//      * @param AccountId the Id of an already existing Account entity.
//      * @param studentId the Id of the student entity to be removed from a Account.
//      * @return the persisted student entities
//      */
//     public void removeStudentFromAccount(long AccountId, long studentId){
//         Optional<Account> AccountOptional = AccountRepository.findById(AccountId);
//         if(AccountOptional.isPresent()){
//             Account Account = AccountOptional.get();
// //            lambda expression applied to the collection - this is the same as iterating through the student list
// //            and removing if(student.getId() == studentId)
//             Account.getStudents().removeIf(student -> student.getId() == studentId);
//             AccountRepository.save(Account);
//         }
//     }
}
