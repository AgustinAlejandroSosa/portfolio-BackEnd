package portfolio.portfolio_backend.controller;

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
import portfolio.portfolio_backend.entity.Banner;
import portfolio.portfolio_backend.entity.Image;
import portfolio.portfolio_backend.service.BannerService;
import portfolio.portfolio_backend.service.ImageService;

@RestController
@RequestMapping("/banner")
public class BannerController {

  @Autowired
  private BannerService bannerService;

  @Autowired
  private ImageService imageService;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("")
  public ResponseEntity<Banner> create(@RequestParam("imageFile") MultipartFile file) {
    try {

      String url = imageService.getUrl(file);

      Image image = imageService.findByUrl(url);

      Banner oldBanner = bannerService.get();

      if (image == null) {
        image = new Image(url);
      }

      Banner newBanner = new Banner(image);

      Banner response;

      if (oldBanner != null) {
        response = bannerService.update(oldBanner.getId(), newBanner);
      } else {
        response = bannerService.create(newBanner);
      }

      return new ResponseEntity<Banner>(response, HttpStatus.CREATED);

    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/get")
  public ResponseEntity<Image> getBanner() {
    try {
      Banner banner = bannerService.get();

      Image response;

      if (banner == null) {
        response = null;
      } else {
        response = banner.getImage();
      }

      return new ResponseEntity<Image>(response, HttpStatus.OK);

    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping("/edit/{id}")
  public ResponseEntity<Banner> updateBanner(@PathVariable Long id, @RequestBody Banner banner) {
    try {
      Banner bannerUpdated = bannerService.update(id, banner);
      return new ResponseEntity<Banner>(bannerUpdated, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping("/delete")
  public ResponseEntity<HttpStatus> deleteBanner() {
    try {
      bannerService.delete();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
