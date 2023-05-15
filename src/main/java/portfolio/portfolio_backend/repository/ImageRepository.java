package portfolio.portfolio_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import portfolio.portfolio_backend.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{
  
  @Query("SELECT i FROM Image i WHERE i.url LIKE :url")
  public Image findImageByUrl(@Param("url")String url);

  
}
