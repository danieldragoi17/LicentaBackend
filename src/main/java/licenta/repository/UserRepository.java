package licenta.repository;

import licenta.model.RoleEnum;
import licenta.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

        Optional<UserEntity> findUserEntityByEmail(String email);

        Optional<UserEntity> findByIdAndRole(Integer integer, RoleEnum roleEnum);

        UserEntity findUserByEmailAndPassword(String email, String password);
}
