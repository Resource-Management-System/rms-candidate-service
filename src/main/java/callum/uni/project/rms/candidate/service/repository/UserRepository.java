package callum.uni.project.rms.candidate.service.repository;

import callum.uni.project.rms.candidate.model.source.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update User u set u.currentRoleId =:newRoleId where u.id =:userId")
    void updateUserCurrentRoleId(@Param("userId") Long userId, @Param("newRoleId") Long newRoleId);

    @Modifying(clearAutomatically = true)
    @Query("update User u set u.currentRoleId = null where u.id =:userId")
    void updateUserLeaveRole(@Param("userId") Long userId);


    List<User> findAllByBusinessUnitId(Long businessUnitId);

    List<User> findAllByFullNameContaining( String fullName);
}
