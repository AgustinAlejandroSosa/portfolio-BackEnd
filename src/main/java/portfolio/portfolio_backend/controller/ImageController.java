package portfolio.portfolio_backend.controller;

import java.nio.file.Files;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import io.jsonwebtoken.io.IOException;
import lombok.AllArgsConstructor;
import portfolio.portfolio_backend.StorageService.StorageService;
import portfolio.portfolio_backend.entity.Image;
import portfolio.portfolio_backend.service.ImageService;

@RequestMapping("/image")
@RestController
@AllArgsConstructor
public class ImageController {

  private final StorageService storageService;

  @Autowired
  private ImageService imageService;

  @PostMapping("")
  public Map<String, String> upload(@RequestParam("imageFile") MultipartFile image, HttpServletRequest request) {
    try {
      String url = imageService.getUrl(image, request);

      return Map.of("url", url);

    } catch (Exception e) {
      return null;
    }
  }

  @GetMapping("{filename:.+}")
  public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException, java.io.IOException {
    Resource file = storageService.loadAsResource(filename);
    String contentType = Files.probeContentType(file.getFile().toPath());
    return ResponseEntity
        .ok()
        .header(HttpHeaders.CONTENT_TYPE, contentType)
        .body(file);
  }

  @PutMapping("/edit/{id}")
  public ResponseEntity<Image> updateImage(@PathVariable Long id, @RequestBody Image image) {
    try {
      Image imageUpdated = imageService.update(id, image);
      return new ResponseEntity<Image>(imageUpdated, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<HttpStatus> deleteImage(@PathVariable Long id) {
    try {
      imageService.delete(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
