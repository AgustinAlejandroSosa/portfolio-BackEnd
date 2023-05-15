package portfolio.portfolio_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import portfolio.portfolio_backend.entity.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
  @Query("SELECT s FROM Skill s WHERE hard = 0")
  public List<Skill> getSoftSkills();

  @Query("SELECT s FROM Skill s WHERE hard = 1")
  public List<Skill> getHardSkills();
}
