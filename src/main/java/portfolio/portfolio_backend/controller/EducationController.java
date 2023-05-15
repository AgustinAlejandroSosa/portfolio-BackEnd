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
import portfolio.portfolio_backend.dto.ProfileDto;
import portfolio.portfolio_backend.entity.Education;
import portfolio.portfolio_backend.entity.Image;
import portfolio.portfolio_backend.service.EducationService;
import portfolio.portfolio_backend.service.ImageService;

@RequestMapping("/education")
@RestController
public class EducationController {

  @Autowired
  private EducationService educationService;

  @Autowired
  private ImageService imageService;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("")
  public ResponseEntity<Education> create(@RequestParam("title") String title,
      @RequestParam("description") String description,
      @RequestParam("dateSince") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDate dateSince,
      @RequestParam("dateTo") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDate dateTo,
      @RequestParam(name = "imageFile", required = false) MultipartFile file) {
    try {
      String formattedDateSince = dateSince.toString().replace("-", "/");
      String formattedDateTo = dateTo.toString().replace("-", "/");

      Education newEducation = new Education();
      newEducation.setTitle(title);
      newEducation.setDescription(description);
      newEducation.setDateSince(formattedDateSince);
      newEducation.setDateTo(formattedDateTo);

      if (file != null) {

        Image image = imageService.getImage(file);
        if (image != null) {
          newEducation.addImage(image);
        } else {
          image = new Image(imageService.getUrl(file));
          newEducation.addImage(image);
        }
      }

      newEducation = educationService.create(newEducation);

      return new ResponseEntity<Education>(newEducation, HttpStatus.CREATED);

    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/get")
  public ResponseEntity<List<ExperienceDto>> getEducation() {
    try {
      List<ExperienceDto> educations = educationService.getAllOrdered();
      return new ResponseEntity<List<ExperienceDto>>(educations, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<ExperienceDto> getEducationById(@PathVariable Long id) {
    try {
      ExperienceDto experienceDto = educationService.getDtoById(id);
      return new ResponseEntity<ExperienceDto>(experienceDto, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping("/edit/{id}")
  public ResponseEntity<Education> updateEducation(
      @PathVariable("id") Long id,
      @RequestParam("title") String title,
      @RequestParam("description") String description,
      @RequestParam("dateSince") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDate dateSince,
      @RequestParam("dateTo") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDate dateTo,
      @RequestParam(name = "imageFile", required = false) MultipartFile file) {

    try {

      String formattedDateSince = dateSince.toString().replace("-", "/");
      String formattedDateTo = dateTo.toString().replace("-", "/");

      Education newEducation = new Education();
      newEducation.setTitle(title);
      newEducation.setDescription(description);
      newEducation.setDateSince(formattedDateSince);
      newEducation.setDateTo(formattedDateTo);

      if (file != null) {

        Image image = imageService.getImage(file);
        if (image != null) {
          newEducation.addImage(image);
        } else {
          image = new Image(imageService.getUrl(file));
          newEducation.addImage(image);
        }
      } else {
        Image oldImage = educationService.getById(id).getImage();
        newEducation.addImage(oldImage);
      }

      newEducation = educationService.update(id, newEducation);

      return new ResponseEntity<Education>(newEducation, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<HttpStatus> deleteEducation(@PathVariable Long id) {
    try {
      educationService.delete(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
