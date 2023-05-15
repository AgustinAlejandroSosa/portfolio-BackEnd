package portfolio.portfolio_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import portfolio.portfolio_backend.entity.Experience;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long>{
  @Query("SELECT e FROM Experience e ORDER BY ID DESC")
  public List<Experience> getAllOrdered();
}
