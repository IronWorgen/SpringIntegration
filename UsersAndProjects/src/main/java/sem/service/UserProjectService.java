package sem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sem.model.Project;
import sem.model.User;
import sem.model.UsersProject;
import sem.repository.ProjectRepository;
import sem.repository.UserRepository;
import sem.repository.UsersProjectRepository;

import java.util.List;

/**
 * Создайте сервисный класс (UserProjectService), который будет содержать следующие методы:
 * <p>
 * public List getUsersByProjectId(Long projectId) - метод, возвращающий список пользователей, связанных с определенным проектом
 * public List getProjectsByUserId(Long userId) - метод, возвращающий список проектов, связанных с определенным пользователем
 * public void addUserToProject(Long userId, Long projectId) - метод, добавляющий пользователя к проекту
 * public void removeUserFromProject(Long userId, Long projectId) - метод, удаляющий пользователя из проекта
 * Создайте контроллер (UserProjectController), который будет содержать следующие методы:
 */
@Service
@RequiredArgsConstructor
public class UserProjectService {
    private final UsersProjectRepository usersProjectRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;



    /**
     * метод, возвращающий список пользователей, связанных с определенным проектом
     *
     * @param projectId id проекта
     * @return список пользователей
     */
    public List<User> getUsersByProjectId(Long projectId) {
        List<Long> usersIdList = usersProjectRepository.findAllUserIdByProjectId(projectId);
        return userRepository.findUserByIdIn(usersIdList);
    }


    /**
     * метод, возвращающий список проектов, связанных с определенным пользователем
     *
     * @param userId id пользователя
     * @return список проектов
     */
    public List<Project> getProjectsByUserId(Long userId) {
        List<Long> projectIDList = usersProjectRepository.findAllProjectIdByUserId(userId);
        return projectRepository.findProjectByIdIn(projectIDList);
    }


    /**
     * метод, добавляющий пользователя к проекту
     *
     * @param userId    id пользователя
     * @param projectId id проекта
     */
    public void addUserToProject(Long userId, Long projectId) {
        usersProjectRepository.save(new UsersProject(userId, projectId));
    }


    /**
     * метод, удаляющий пользователя из проекта
     *
     * @param userId    id пользователя
     * @param projectId id проекта
     */
    public void removeUserFromProject(Long userId, Long projectId) {
        UsersProject usersProject = usersProjectRepository.findByUserIdAndProjectId(userId, projectId);
        if (usersProject != null) {
            usersProjectRepository.delete(usersProject);
        }

    }


    /**
     * проверка участвует ли пользователь в заданном проекте
     *
     * @param userId    id пользователя
     * @param projectId id проекта
     * @return true -> UserProject с заданными параметрами найден/ false ->  UserProject с заданными параметрами не существует
     */
    public boolean isUserAndProjectExist(Long userId, Long projectId) {
        return (userRepository.findById(userId) != null) && (projectRepository.findById(projectId) != null);
    }



}
