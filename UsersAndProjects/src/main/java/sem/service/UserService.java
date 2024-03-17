package sem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import sem.model.User;
import sem.repository.UserRepository;
import sem.service.logging.ApplicationLogger;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private  final UserRepository userRepository;
    @Autowired
    @Qualifier("ExampleJavaLogger")
    private  ApplicationLogger logger;
    /**
     * добавить пользователя в БД
     * @param user пользователь для добавления
     */
    public  User  saveUser(User user){
        user = userRepository.save(user);
        logger.log("добавлен пользователь"+user);
        return  user;
    }

    /**
     * найти всех пользователей
     * @return список пользователей
     */
    public List<User> getAllUsers(){
        logger.log("получен список пользователей");
        return  userRepository.findAll();
    }
}
