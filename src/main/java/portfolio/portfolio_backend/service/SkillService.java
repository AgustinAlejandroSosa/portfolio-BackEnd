package portfolio.portfolio_backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import portfolio.portfolio_backend.dto.SkillDto;
import portfolio.portfolio_backend.entity.Skill;
import portfolio.portfolio_backend.repository.SkillRepository;

@Service
@Slf4j
public class SkillService {

  @Autowired
  private SkillRepository skillRepository;

  public Skill create(Skill skill) {
    try {
      return skillRepository.save(skill);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public List<Skill> getAll() {
    try {
      return skillRepository.findAll();

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public List<SkillDto> getAllDto() {
    try {
      List<SkillDto> skills = mapToSkillDtos(skillRepository.findAll());
      return skills;

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public List<SkillDto> getHardSkills() {
    try {
      List<SkillDto> skills = mapToSkillDtos(skillRepository.getHardSkills());
      return skills;

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public List<SkillDto> getSoftSkills() {
    try {
      List<SkillDto> skills = mapToSkillDtos(skillRepository.getSoftSkills());
      return skills;

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public Skill getById(Long id) {
    try {
      Optional<Skill> result = skillRepository.findById(id);
      if (result.isPresent()) {
        return result.get();
      } else {
        throw new Exception("there is not skill resource with such id");
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public Skill update(Long id, Skill skill) {
    try {
      Optional<Skill> result = skillRepository.findById(id);
      if (result.isPresent()) {
        skill.setId(result.get().getId());
        return skillRepository.save(skill);
      } else {
        throw new Exception("Error at update skill with id:" + id);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public void delete(Long id) {
    try {
      skillRepository.deleteById(id);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  public List<SkillDto> mapToSkillDtos(List<Skill> skills) {
    try {
      List<SkillDto> skillsMapped = new ArrayList<>();
      for (Skill skill : skills) {
        SkillDto skillDto;
        if (skill.getImage() != null) {
          skillDto = new SkillDto(skill.getId(), skill.getName(), skill.isHard(), skill.getImage().getUrl());

        } else {
          skillDto = new SkillDto(skill.getId(), skill.getName(), skill.isHard());
        }
        skillsMapped.add(skillDto);
      }

      return skillsMapped;

    } catch (Exception e) {
      return null;
    }
  }
}
