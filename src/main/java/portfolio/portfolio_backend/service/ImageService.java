package portfolio.portfolio_backend.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import portfolio.portfolio_backend.StorageService.StorageService;
import portfolio.portfolio_backend.entity.Image;
import portfolio.portfolio_backend.repository.ImageRepository;

@Service
@Slf4j
@AllArgsConstructor
public class ImageService {

  @Autowired
  private ImageRepository imageRepository;

  private final StorageService storageService;

  private final HttpServletRequest request;

  public Image getImage(MultipartFile file) {
    String url = getUrl(file);

    return findByUrl(url);
  }

  public String getUrl(MultipartFile image) {

    String path = storageService.store(image);
    String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
    String url = ServletUriComponentsBuilder
        .fromHttpUrl(host)
        .path("/image/")
        .path(path)
        .toUriString();
    return url;
  }

  @Transactional
  public Image findByUrl(String url) {
    try {
      return imageRepository.findImageByUrl(url);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  @Transactional
  public Image create(Image image) {
    try {
      return imageRepository.save(image);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  @Transactional
  public List<Image> getAll() {
    try {
      return imageRepository.findAll();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  @Transactional
  public Image getById(Long id) {
    try {
      Optional<Image> result = imageRepository.findById(id);
      if (result.isPresent()) {
        return result.get();
      } else {
        throw new Exception("there is not image resource with such id");
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  @Transactional
  public Image update(Long id, Image image) {
    try {
      Optional<Image> result = imageRepository.findById(id);
      if (result.isPresent()) {
        image.setId(result.get().getId());
        return imageRepository.save(image);
      } else {
        throw new Exception("Error at update image with id:" + id);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  @Transactional
  public void delete(Long id) {
    try {
      imageRepository.deleteById(id);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }
}
