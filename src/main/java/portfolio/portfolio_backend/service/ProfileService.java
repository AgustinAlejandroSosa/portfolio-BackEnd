package portfolio.portfolio_backend.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import portfolio.portfolio_backend.entity.Profile;
import portfolio.portfolio_backend.repository.ProfileRepository;

@Service
@Slf4j
public class ProfileService {

  @Autowired
  private ProfileRepository profileRepository;

  public Profile create(Profile profile) {
    try {
      return profileRepository.save(profile);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public Profile get() {
    try {
      if (profileRepository.findAll().size() > 0) {

        return profileRepository.findAll().get(0);
      } else {
        return null;
      }

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public Profile getById(Long id) {
    try {
      Optional<Profile> result = profileRepository.findById(id);
      if (result.isPresent()) {
        return result.get();
      } else {
        throw new Exception("there is not profile resource with such id");
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public Profile update(Long id, Profile profile) {
    try {
      Optional<Profile> result = profileRepository.findById(id);
      if (result.isPresent()) {
        profile.setId(result.get().getId());
        return profileRepository.save(profile);
      } else {
        throw new Exception("Error at update profile with id:" + id);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public void delete(Long id) {
    try {
      profileRepository.deleteById(id);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }
}
