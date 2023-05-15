package portfolio.portfolio_backend.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import portfolio.portfolio_backend.dto.ProfileDto;
import portfolio.portfolio_backend.entity.Image;
import portfolio.portfolio_backend.entity.Profile;
import portfolio.portfolio_backend.service.ImageService;
import portfolio.portfolio_backend.service.ProfileService;

@RequestMapping("/profile")
@RestController
public class ProfileController {

  @Autowired
  ProfileService profileService;

  @Autowired
  ImageService imageService;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("")
  public ResponseEntity<ProfileDto> saveData(@RequestBody ProfileDto profileDto) {
    try {

      Profile oldProfile = profileService.get();
      Profile newProfile = new Profile(profileDto.getName(), profileDto.getPosition(), profileDto.getDescription());

      if (oldProfile != null) {
        newProfile.addImage(oldProfile.getImage());
        profileService.update(oldProfile.getId(), newProfile);
      } else {
        profileService.create(newProfile);
      }

      ProfileDto response = new ProfileDto(newProfile.getName(), newProfile.getPosition(), newProfile.getDescription());

      return new ResponseEntity<ProfileDto>(response, HttpStatus.CREATED);

    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/savePhoto")
  public Map<String, String> savePhoto(@RequestParam("imageFile") MultipartFile file) {
    try {

      String url = imageService.getUrl(file);

      Image image = imageService.findByUrl(url);

      if (image == null) {
        image = new Image(url);
        imageService.create(image);

        Profile oldProfile = profileService.get();

        if (oldProfile != null) {
          oldProfile.addImage(image);
          profileService.update(oldProfile.getId(), oldProfile);
        } else {
          Profile newProfile = new Profile();
          newProfile.addImage(image);
          profileService.create(newProfile);
        }

      } else {

        Profile oldProfile = profileService.get();

        oldProfile.addImage(image);
        profileService.update(oldProfile.getId(), oldProfile);
      }

      return Map.of("url", url);

    } catch (Exception e) {
      return null;
    }
  }

  @GetMapping("/get")
  public ResponseEntity<ProfileDto> getProfile() {
    try {
      Profile profile = profileService.get();
      ProfileDto response;

      if (profile == null) {
        response = null;
      } else {
        if (profile.getImage() != null) {
          response = new ProfileDto(profile.getName(), profile.getPosition(), profile.getDescription(),
              profile.getImage().getUrl());
        } else {
          response = new ProfileDto(profile.getName(), profile.getPosition(), profile.getDescription());
        }
      }

      return new ResponseEntity<ProfileDto>(response, HttpStatus.OK);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping("/edit/{id}")
  public ResponseEntity<Profile> updateProfile(@PathVariable Long id, @RequestBody Profile profile) {
    try {
      Profile profileUpdated = profileService.update(id, profile);
      return new ResponseEntity<Profile>(profileUpdated, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<HttpStatus> deleteProfile(@PathVariable Long id) {
    try {
      profileService.delete(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
