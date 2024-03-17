package sem.controllers;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sem.model.Project;
import sem.model.User;
import sem.service.FileGateWay;
import sem.service.UserProjectService;

import java.util.List;

/**
 * Создайте контроллер (UserProjectController), который будет содержать следующие методы:
 * <p>
 * public ResponseEntity<List> getUsersByProjectId(Long projectId) - метод, обрабатывающий GET-запрос для получения списка пользователей, связанных с определенным проектом
 * public ResponseEntity<List> getProjectsByUserId(Long userId) - метод, обрабатывающий GET-запрос для получения списка проектов, связанных с определенным пользователем
 * public ResponseEntity addUserToProject(Long userId, Long projectId) - метод, обрабатывающий POST-запрос для добавления пользователя к проекту
 * public ResponseEntity removeUserFromProject(Long userId, Long projectId) - метод, обрабатывающий POST-запрос для удаления пользователя из проекта
 */
@RestController
@RequiredArgsConstructor
public class UserProjectController {
    private final UserProjectService userProjectService;
    private final Counter requestCounter = Metrics.counter("requestCounter");





    /**
     * метод, обрабатывающий GET-запрос для получения списка пользователей, связанных с определенным проектом
     *
     * @param projectId id проекта
     * @return
     */
    @GetMapping("/users/{projectId}")
    public ResponseEntity<List<User>> getUsersByProjectId(@PathVariable Long projectId) {
        List<User> users = userProjectService.getUsersByProjectId(projectId);
        requestCounter.increment();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    /**
     * метод, обрабатывающий GET-запрос для получения списка проектов, связанных с определенным пользователем
     *
     * @param userId id пользователя
     * @return
     */
    @GetMapping("/projects/{userId}")
    public ResponseEntity<List<Project>> getProjectsByUserId(@PathVariable Long userId) {
        List<Project> projects = userProjectService.getProjectsByUserId(userId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }


    /**
     * метод, обрабатывающий POST-запрос для добавления пользователя к проекту
     *
     * @param userId    id пользователя
     * @param projectId id проекта
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity addUserToProject(@RequestParam(name = "userId") Long userId,
                                           @RequestParam(name = "projectId") Long projectId) {
        if (userProjectService.isUserAndProjectExist(userId, projectId)) {
            userProjectService.addUserToProject(userId, projectId);
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    /**
     * метод, обрабатывающий POST-запрос для удаления пользователя из проекта
     *
     * @param userId    id пользователя
     * @param projectId id проекта
     * @return
     */
    @PostMapping("/remove")
    public ResponseEntity removeUserFromProject(@RequestParam(name = "userId") Long userId,
                                                @RequestParam(name = "projectId") Long projectId) {
        userProjectService.removeUserFromProject(userId, projectId);
        return new ResponseEntity(HttpStatus.OK);
    }



}
