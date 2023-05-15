package portfolio.portfolio_backend.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import portfolio.portfolio_backend.dto.ExperienceDto;
import portfolio.portfolio_backend.entity.Education;
import portfolio.portfolio_backend.entity.Experience;
import portfolio.portfolio_backend.repository.EducationRepository;

@Service
@Slf4j
public class EducationService {

  @Autowired
  private EducationRepository educationRepository;

  public Education create(Education education) {
    try {
      return educationRepository.save(education);
    } catch (Exception e) {
      return null;
    }
  }

  public List<ExperienceDto> mapExperiencesToDto(List<Education> educations) {

    List<ExperienceDto> experiencesDto = new ArrayList<>();

    for (Education education : educations) {
      if (education.getImage() != null) {
        ExperienceDto experienceDto = new ExperienceDto(education.getId(), education.getImage().getUrl(),
            education.getTitle(),
            education.getDescription(), education.getDateSince(), education.getDateTo());
        experiencesDto.add(experienceDto);
      } else {
        ExperienceDto experienceDto = new ExperienceDto(education.getId(), education.getTitle(),
            education.getDescription(),
            education.getDateSince(), education.getDateTo());
        experiencesDto.add(experienceDto);
      }
    }
    return experiencesDto;
  }

  public List<ExperienceDto> getAllOrdered() {
    try {
      List<Education> educations = educationRepository.getAllOrdered();

      if (educations.isEmpty()) {
        return Collections.emptyList();
      } else {
        List<ExperienceDto> experiencesDto = mapExperiencesToDto(educations);
        return experiencesDto;
      }
    } catch (Exception e) {
      return null;
    }
  }

  public List<Education> getAll() {
    try {
      return educationRepository.findAll();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public ExperienceDto getDtoById(Long id) {
    try {
      Optional<Education> result = educationRepository.findById(id);
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
      return null;
    }
  }

  public Education getById(Long id) {
    try {
      Optional<Education> result = educationRepository.findById(id);
      if (result.isPresent()) {
        return result.get();
      } else {
        throw new Exception("there is not education resource with such id");
      }
    } catch (Exception e) {
      return null;
    }
  }

  public Education update(Long id, Education education) {
    try {
      Optional<Education> result = educationRepository.findById(id);
      if (result.isPresent()) {
        return educationRepository.save(education);
      } else {
        throw new Exception("Error at update education with id:" + id);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public Education update(Long id, Experience experience) {
    try {
      Optional<Education> result = educationRepository.findById(id);
      if (result.isPresent()) {
        Education education = result.get();
        education.setTitle(experience.getTitle());
        education.setDateSince(experience.getDateSince());
        education.setDateTo(experience.getDateTo());
        education.setDescription(experience.getDescription());
        if (experience.getImage() != null) {
          education.addImage(experience.getImage());
          experience.getImage().removeExperience();
        }
        return educationRepository.save(education);
      } else {
        throw new Exception("Error at update education with id:" + id);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public void delete(Long id) {
    try {
      educationRepository.deleteById(id);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }
}
