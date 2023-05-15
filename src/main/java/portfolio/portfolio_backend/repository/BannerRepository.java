package portfolio.portfolio_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import portfolio.portfolio_backend.entity.Banner;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long>{
  
}
