package portfolio.portfolio_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import portfolio.portfolio_backend.entity.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education,Long>{
  @Query("SELECT e FROM Education e ORDER BY ID DESC")
  public List<Education> getAllOrdered();
}
