package sem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sem.model.User;
import sem.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private  final UserRepository userRepository;
    /**
     * добавить пользователя в БД
     * @param user пользователь для добавления
     */
    public  User  saveUser(User user){
        return  userRepository.save(user);
    }

    /**
     * найти всех пользователей
     * @return список пользователей
     */
    public List<User> getAllUsers(){
        return  userRepository.findAll();
    }
}
