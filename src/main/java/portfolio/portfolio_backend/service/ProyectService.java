package portfolio.portfolio_backend.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import portfolio.portfolio_backend.dto.ProyectDto;
import portfolio.portfolio_backend.entity.Proyect;
import portfolio.portfolio_backend.repository.ProyectRepository;

@Service
@Slf4j
public class ProyectService {

  @Autowired
  private ProyectRepository proyectRepository;

  public Proyect create(Proyect proyect) {
    try {
      return proyectRepository.save(proyect);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public List<ProyectDto> mapProyectsToDto(List<Proyect> proyects) {

    List<ProyectDto> proyectsDto = new ArrayList<>();

    for (Proyect proyect : proyects) {
      if (proyect.getImage() != null) {
        ProyectDto proyectDto = new ProyectDto(proyect.getId(), proyect.getImage().getUrl(),
            proyect.getTitle(),
            proyect.getDescription(), proyect.getDate(), proyect.getRepositoryLink());
        proyectsDto.add(proyectDto);
      } else {
        ProyectDto proyectDto = new ProyectDto(proyect.getId(), proyect.getTitle(),
            proyect.getDescription(), proyect.getDate(), proyect.getRepositoryLink());
        proyectsDto.add(proyectDto);
      }
    }

    return proyectsDto;
  }

  public List<ProyectDto> getAllOrdered() {
    try {
      List<Proyect> proyects = proyectRepository.getAllOrdered();

      if (proyects.isEmpty()) {
        return Collections.emptyList();
      } else {
        List<ProyectDto> proyectsDto = mapProyectsToDto(proyects);
        return proyectsDto;
      }

    } catch (Exception e) {
      return null;
    }
  }

  public List<Proyect> getAll() {
    try {
      List<Proyect> proyects = proyectRepository.findAll();
      if (proyects.isEmpty()) {
        return Collections.emptyList();
      } else {
        return proyects;

      }

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public Proyect getById(Long id) {
    try {
      Optional<Proyect> result = proyectRepository.findById(id);
      if (result.isPresent()) {
        return result.get();
      } else {
        throw new Exception("there is not proyect resource with such id");
      }
    } catch (Exception e) {
      log.error(e.getMessage());
      return null;
    }
  }

  public ProyectDto getDtoById(Long id) {
    try {
      Optional<Proyect> result = proyectRepository.findById(id);
      if (result.isPresent()) {
        if (result.get().getImage() != null) {
          ProyectDto proyectDto = new ProyectDto(result.get().getId(), result.get().getImage().getUrl(),
              result.get().getTitle(), result.get().getDescription(),
              result.get().getDate(), result.get().getRepositoryLink());
          return proyectDto;
        } else {
          ProyectDto proyectDto = new ProyectDto(result.get().getId(),
              result.get().getTitle(), result.get().getDescription(),
              result.get().getDate(), result.get().getRepositoryLink());
          return proyectDto;
        }
      } else {
        throw new Exception("there is not education resource with such id");
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public Proyect update(Long id, Proyect proyect) {
    try {
      Optional<Proyect> result = proyectRepository.findById(id);
      if (result.isPresent()) {
        proyect.setId(result.get().getId());
        return proyectRepository.save(proyect);
      } else {
        throw new Exception("Error at update proyect with id:" + id);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public void delete(Long id) {
    try {
      proyectRepository.deleteById(id);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

}
