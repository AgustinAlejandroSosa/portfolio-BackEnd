package portfolio.portfolio_backend.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
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

import portfolio.portfolio_backend.dto.ExperienceDto;
import portfolio.portfolio_backend.entity.Experience;
import portfolio.portfolio_backend.entity.Image;
import portfolio.portfolio_backend.service.EducationService;
import portfolio.portfolio_backend.service.ExperienceService;
import portfolio.portfolio_backend.service.ImageService;

@RequestMapping("/experience")
@RestController
public class ExperienceController {

  @Autowired
  private ExperienceService experienceService;

  @Autowired
  private ImageService imageService;

  @Autowired
  private EducationService educationService;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("")
  public ResponseEntity<Experience> create(@RequestParam("title") String title,
      @RequestParam("description") String description,
      @RequestParam("dateSince") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDate dateSince,
      @RequestParam("dateTo") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDate dateTo,
      @RequestParam(name = "imageFile", required = false) MultipartFile file) {
    try {
      String formattedDateSince = dateSince.toString().replace("-", "/");
      String formattedDateTo = dateTo.toString().replace("-", "/");

      Experience newExperience = new Experience();
      newExperience.setTitle(title);
      newExperience.setDescription(description);
      newExperience.setDateSince(formattedDateSince);
      newExperience.setDateTo(formattedDateTo);

      if (file != null) {

        Image image = imageService.getImage(file);
        if (image != null) {
          newExperience.addImage(image);
        } else {
          image = new Image(imageService.getUrl(file));
          newExperience.addImage(image);
        }
      }

      newExperience = experienceService.create(newExperience);

      return new ResponseEntity<Experience>(newExperience, HttpStatus.CREATED);

    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/get")
  public ResponseEntity<List<ExperienceDto>> getExperience() {
    try {
      List<ExperienceDto> experiences = experienceService.getAllOrdered();
      return new ResponseEntity<List<ExperienceDto>>(experiences, HttpStatus.OK);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<ExperienceDto> getExperienceById(@PathVariable Long id) {
    try {
      ExperienceDto experienceDto = educationService.getDtoById(id);
      if (experienceDto == null) {
        experienceDto = experienceService.getDtoById(id);
      }
      return new ResponseEntity<ExperienceDto>(experienceDto, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping("/edit/{id}")
  public ResponseEntity<Experience> updateExperience(
      @PathVariable("id") Long id,
      @RequestParam("title") String title,
      @RequestParam("description") String description,
      @RequestParam("dateSince") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDate dateSince,
      @RequestParam("dateTo") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDate dateTo,
      @RequestParam(name = "imageFile", required = false) MultipartFile file) {

    try {

      String formattedDateSince = dateSince.toString().replace("-", "/");
      String formattedDateTo = dateTo.toString().replace("-", "/");

      Experience newExperience = new Experience();
      newExperience.setTitle(title);
      newExperience.setDescription(description);
      newExperience.setDateSince(formattedDateSince);
      newExperience.setDateTo(formattedDateTo);

      if (file != null) {

        Image image = imageService.getImage(file);
        if (image != null) {
          newExperience.addImage(image);
        } else {
          image = new Image(imageService.getUrl(file));
          newExperience.addImage(image);
        }

        if (experienceService.getById(id) == null) {
          educationService.update(id, newExperience);
          newExperience.setId(id);
        } else {
          newExperience = experienceService.update(id, newExperience);
        }

      } else {

        if (experienceService.getById(id) == null) {
          Image oldImage = educationService.getById(id).getImage();
          newExperience.addImage(oldImage);
          educationService.update(id, newExperience);
          newExperience.setId(id);
        } else {
          Image oldImage = experienceService.getById(id).getImage();
          newExperience.addImage(oldImage);
          newExperience = experienceService.update(id, newExperience);
        }
      }

      return new ResponseEntity<Experience>(newExperience, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<HttpStatus> deleteExperience(@PathVariable Long id) {
    try {
      educationService.delete(id);
      experienceService.delete(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
