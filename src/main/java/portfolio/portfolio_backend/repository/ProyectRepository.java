package portfolio.portfolio_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import portfolio.portfolio_backend.entity.Proyect;

public interface ProyectRepository extends JpaRepository<Proyect, Long> {

  @Query("SELECT p FROM Proyect p ORDER BY ID DESC")
  public List<Proyect> getAllOrdered();
}
