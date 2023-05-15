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

import portfolio.portfolio_backend.dto.ProyectDto;
import portfolio.portfolio_backend.entity.Proyect;
import portfolio.portfolio_backend.entity.Image;
import portfolio.portfolio_backend.service.EducationService;
import portfolio.portfolio_backend.service.ProyectService;
import portfolio.portfolio_backend.service.ImageService;

@RequestMapping("/proyect")
@RestController
public class ProyectController {

  @Autowired
  private ProyectService proyectService;

  @Autowired
  private ImageService imageService;

  @Autowired
  private EducationService educationService;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("")
  public ResponseEntity<Proyect> create(@RequestParam("title") String title,
      @RequestParam("description") String description,
      @RequestParam("repositoryLink") String repositoryLink,
      @RequestParam("date") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDate date,
      @RequestParam(name = "imageFile", required = false) MultipartFile file) {
    try {
      String formattedDateTo = date.toString().replace("-", "/");

      Proyect newProyect = new Proyect();
      newProyect.setTitle(title);
      newProyect.setDescription(description);
      newProyect.setRepositoryLink(repositoryLink);
      newProyect.setDate(formattedDateTo);

      if (file != null) {

        Image image = imageService.getImage(file);
        if (image != null) {
          newProyect.addImage(image);
        } else {
          image = new Image(imageService.getUrl(file));
          newProyect.addImage(image);
        }
      }

      newProyect = proyectService.create(newProyect);

      return new ResponseEntity<Proyect>(newProyect, HttpStatus.CREATED);

    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/get")
  public ResponseEntity<List<ProyectDto>> getProyect() {
    try {
      List<ProyectDto> proyects = proyectService.getAllOrdered();
      return new ResponseEntity<List<ProyectDto>>(proyects, HttpStatus.OK);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<ProyectDto> getProyectById(@PathVariable Long id) {
    try {
      ProyectDto proyectDto = proyectService.getDtoById(id);
      if (proyectDto == null) {
        proyectDto = proyectService.getDtoById(id);
      }
      return new ResponseEntity<ProyectDto>(proyectDto, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping("/edit/{id}")
  public ResponseEntity<Proyect> updateProyect(
      @PathVariable("id") Long id,
      @RequestParam("title") String title,
      @RequestParam("description") String description,
      @RequestParam("repositoryLink") String repositoryLink,
      @RequestParam("date") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDate date,
      @RequestParam(name = "imageFile", required = false) MultipartFile file) {

    try {

      String formattedDate = date.toString().replace("-", "/");

      Proyect newProyect = new Proyect();
      newProyect.setTitle(title);
      newProyect.setDescription(description);
      newProyect.setDate(formattedDate);
      newProyect.setRepositoryLink(repositoryLink);

      if (file != null) {

        Image image = imageService.getImage(file);
        if (image != null) {
          newProyect.addImage(image);
        } else {
          image = new Image(imageService.getUrl(file));
          newProyect.addImage(image);
        }

        if (proyectService.getById(id) == null) {
          proyectService.update(id, newProyect);
          newProyect.setId(id);
        } else {
          newProyect = proyectService.update(id, newProyect);
        }

      } else {

        if (proyectService.getById(id) == null) {
          Image oldImage = educationService.getById(id).getImage();
          newProyect.addImage(oldImage);
          proyectService.update(id, newProyect);
          newProyect.setId(id);
        } else {
          Image oldImage = proyectService.getById(id).getImage();
          newProyect.addImage(oldImage);
          newProyect = proyectService.update(id, newProyect);
        }
      }

      return new ResponseEntity<Proyect>(newProyect, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<HttpStatus> deleteProyect(@PathVariable Long id) {
    try {
      educationService.delete(id);
      proyectService.delete(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
