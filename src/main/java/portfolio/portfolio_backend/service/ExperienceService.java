package portfolio.portfolio_backend.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import portfolio.portfolio_backend.dto.ExperienceDto;
import portfolio.portfolio_backend.entity.Experience;
import portfolio.portfolio_backend.repository.ExperienceRepository;

@Service
@Slf4j
public class ExperienceService {

  @Autowired
  private ExperienceRepository experienceRepository;

  public Experience create(Experience experience) {
    try {
      return experienceRepository.save(experience);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public List<ExperienceDto> mapExperiencesToDto(List<Experience> experiences) {

    List<ExperienceDto> experiencesDto = new ArrayList<>();

    for (Experience experience : experiences) {
      if (experience.getImage() != null) {
        ExperienceDto experienceDto = new ExperienceDto(experience.getId(), experience.getImage().getUrl(),
            experience.getTitle(),
            experience.getDescription(), experience.getDateSince(), experience.getDateTo());
        experiencesDto.add(experienceDto);
      } else {
        ExperienceDto experienceDto = new ExperienceDto(experience.getId(), experience.getTitle(),
            experience.getDescription(),
            experience.getDateSince(), experience.getDateTo());
        experiencesDto.add(experienceDto);
      }
    }

    return experiencesDto;
  }

  public List<ExperienceDto> getAllOrdered() {
    try {
      List<Experience> experiences = experienceRepository.getAllOrdered();

      if (experiences.isEmpty()) {
        return Collections.emptyList();
      } else {
        List<ExperienceDto> experiencesDto = mapExperiencesToDto(experiences);
        return experiencesDto;
      }

    } catch (Exception e) {
      return null;
    }
  }

  public List<Experience> getAll() {
    try {
      List<Experience> experiences = experienceRepository.findAll();
      if (experiences.isEmpty()) {
        return Collections.emptyList();
      } else {
        return experiences;

      }

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public Experience getById(Long id) {
    try {
      Optional<Experience> result = experienceRepository.findById(id);
      if (result.isPresent()) {
        return result.get();
      } else {
        throw new Exception("there is not experience resource with such id");
      }
    } catch (Exception e) {
      log.error(e.getMessage());
      return null;
    }
  }

  public ExperienceDto getDtoById(Long id) {
    try {
      Optional<Experience> result = experienceRepository.findById(id);
      if (result.isPresent()) {
        if (result.get().getImage() != null) {
          ExperienceDto experienceDto = new ExperienceDto(result.get().getId(), result.get().getImage().getUrl(),
              result.get().getTitle(), result.get().getDescription(), result.get().getDateSince(),
              result.get().getDateTo());
          return experienceDto;
        } else {
          ExperienceDto experienceDto = new ExperienceDto(result.get().getId(),
              result.get().getTitle(), result.get().getDescription(), result.get().getDateSince(),
              result.get().getDateTo());
          return experienceDto;
        }
      } else {
        throw new Exception("there is not education resource with such id");
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public Experience update(Long id, Experience experience) {
    try {
      Optional<Experience> result = experienceRepository.findById(id);
      if (result.isPresent()) {
        experience.setId(result.get().getId());
        return experienceRepository.save(experience);
      } else {
        throw new Exception("Error at update experience with id:" + id);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public void delete(Long id) {
    try {
      experienceRepository.deleteById(id);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }
}
