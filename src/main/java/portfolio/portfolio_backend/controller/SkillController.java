package portfolio.portfolio_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import portfolio.portfolio_backend.dto.SkillDto;
import portfolio.portfolio_backend.entity.Image;
import portfolio.portfolio_backend.entity.Skill;
import portfolio.portfolio_backend.service.ImageService;
import portfolio.portfolio_backend.service.SkillService;

@RequestMapping("/skill")
@RestController
public class SkillController {

  @Autowired
  private SkillService skillService;

  @Autowired
  private ImageService imageService;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("")
  public ResponseEntity<Skill> create(@RequestParam("name") String name, @RequestParam("hard") boolean hard,
      @RequestParam(name = "logoFile", required = false) MultipartFile file) {

    try {
      Skill newSkill = new Skill(name, hard);

      if (file != null) {
        Image image = imageService.getImage(file);
        
        if (image == null) {
          image = new Image(imageService.getUrl(file));
        }else{
          if (image.getSkill() != null) {
            return new ResponseEntity<Skill>(newSkill, HttpStatus.CONFLICT);
          }
        }

        newSkill.addImage(image);
      }

      newSkill = skillService.create(newSkill);

      return new ResponseEntity<Skill>(newSkill, HttpStatus.CREATED);

    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<SkillDto> getSkillById(@PathVariable Long id) {
    try {
      Skill skill = skillService.getById(id);
      SkillDto response;

      if (skill.getImage() != null) {
        response = new SkillDto(skill.getId(), skill.getName(), skill.isHard(), skill.getImage().getUrl());
      } else {
        response = new SkillDto(skill.getId(), skill.getName(), skill.isHard());
      }

      return new ResponseEntity<SkillDto>(response, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/get")
  public ResponseEntity<List<SkillDto>> getSkill() {
    try {
      List<SkillDto> skills = skillService.getAllDto();
      return new ResponseEntity<List<SkillDto>>(skills, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/get/hard")
  public ResponseEntity<List<SkillDto>> getHardSkills() {
    try {
      List<SkillDto> skills = skillService.getHardSkills();
      return new ResponseEntity<List<SkillDto>>(skills, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/get/soft")
  public ResponseEntity<List<SkillDto>> getSoftSkills() {
    try {
      List<SkillDto> skills = skillService.getSoftSkills();
      return new ResponseEntity<List<SkillDto>>(skills, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping("/edit/{id}")
  public ResponseEntity<Skill> updateSkill(@PathVariable("id") Long id,
      @RequestParam(name = "logoFile", required = false) MultipartFile file, @RequestParam("name") String name,
      @RequestParam("hard") boolean hard) {
    try {

      Skill newSkill = new Skill();
      newSkill.setName(name);
      newSkill.setHard(hard);

      if (file != null) {
        Image image = imageService.getImage(file);

        if (image != null) {
          if (image.getSkill() != null) {
            return new ResponseEntity<Skill>(newSkill, HttpStatus.CONFLICT);
          }
          newSkill.addImage(image);
        } else {
          image = new Image(imageService.getUrl(file));
          newSkill.addImage(image);
        }

        newSkill = skillService.update(id, newSkill);
      } else {
        Image oldImage = skillService.getById(id).getImage();
        newSkill.addImage(oldImage);
        newSkill = skillService.update(id, newSkill);
      }

      return new ResponseEntity<Skill>(newSkill, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<HttpStatus> deleteSkill(@PathVariable Long id) {
    try {
      skillService.delete(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
