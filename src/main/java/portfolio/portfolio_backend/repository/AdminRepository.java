package portfolio.portfolio_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import portfolio.portfolio_backend.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long>{
  
}
