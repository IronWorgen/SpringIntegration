package sem.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sem.model.User;
import sem.service.FileGateWay;
import sem.service.UserService;

import java.util.List;
@RestController
@RequiredArgsConstructor
public class UsersController {
    private  final UserService userService;
    private final FileGateWay fileGateWay;
    @PostMapping("/users/add")
    public  void  addUser(@RequestBody User user){
        User savedUser = userService.saveUser(user);
        fileGateWay.writeToFile("users.txt", savedUser.toString());

    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return  userService.getAllUsers();
    }
}
