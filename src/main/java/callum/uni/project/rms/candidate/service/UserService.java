package callum.uni.project.rms.candidate.service;


import callum.project.uni.rms.parent.exception.InternalServiceException;
import callum.project.uni.rms.parent.exception.NotFoundException;
import callum.uni.project.rms.candidate.service.repository.UserRepository;
import callum.uni.project.rms.candidate.mapper.UserMapper;
import callum.uni.project.rms.candidate.model.source.User;
import callum.uni.project.rms.candidate.model.target.Candidates;
import callum.uni.project.rms.candidate.model.target.TargetUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void updateUserProjectDetails(Long userId, Long roleId) {
        try {
            userRepository.updateUserCurrentRoleId(userId, roleId);
        } catch (HibernateException e) {
            throw new InternalServiceException("Error updating user", e);
        }
    }

    public Candidates retrieveUserByFullName(String fullName) {
        try {
            List<User> candidates = userRepository.findAllByFullNameContaining(fullName);

            return buildCandidates(candidates);
        } catch (HibernateException e) {
            throw new InternalServiceException("Error retrieving user", e);
        }
    }

    public Candidates retrieveAllByBusinessUnit(Long businessUnit) {
        try {
            List<User> candidates = userRepository.findAllByBusinessUnitId(businessUnit);

            return buildCandidates(candidates);
        } catch (HibernateException e) {
            throw new InternalServiceException("Error retrieving user", e);
        }
    }

    public TargetUser retrieveUserByInternalId(Long userId) {
        try {
            Optional<User> user = userRepository.findById(userId);

            return UserMapper.mapDbModelToTarget(user.orElseThrow(() ->
                    new NotFoundException("Expected user not found")));
        } catch (HibernateException e) {
            throw new InternalServiceException("Error finding user", e);
        }
    }

    private Candidates buildCandidates(List<User> users){
        return Candidates.builder()
                .candidates(users.parallelStream()
                        .map(UserMapper::mapDbModelToTarget)
                        .collect(Collectors.toList()))
                .build();
    }
}
