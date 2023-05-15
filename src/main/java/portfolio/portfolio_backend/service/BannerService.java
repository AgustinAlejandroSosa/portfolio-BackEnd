package portfolio.portfolio_backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import portfolio.portfolio_backend.entity.Banner;
import portfolio.portfolio_backend.repository.BannerRepository;

@Service
@Slf4j
public class BannerService {

  @Autowired
  private BannerRepository bannerRepository;

  public Banner create(Banner banner) {
    try {
      return bannerRepository.save(banner);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public Banner get() {
    try {
      if (bannerRepository.findAll().size() > 0) {
        return bannerRepository.findAll().get(0);

      } else {
        return null;
      }

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public Banner getById(Long id) {
    try {
      Optional<Banner> result = bannerRepository.findById(id);
      if (result.isPresent()) {
        return result.get();
      } else {
        throw new Exception("there is not banner resource with such id");
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public Banner update(Long id, Banner banner) {
    try {
      Optional<Banner> result = bannerRepository.findById(id);
      if (result.isPresent()) {
        banner.setId(result.get().getId());
        return bannerRepository.save(banner);
      } else {
        throw new Exception("Error at update banner with id:" + id);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public void delete() {
    try {
      Banner banner = bannerRepository.findAll().get(0);
      bannerRepository.deleteById(banner.getId());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

}
