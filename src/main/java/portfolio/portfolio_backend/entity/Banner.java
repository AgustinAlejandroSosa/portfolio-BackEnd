package portfolio.portfolio_backend.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Banner {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne(targetEntity = Image.class, cascade = CascadeType.ALL)
  private Image image;

  public Banner(Image image) {
    addImage(image);
    image.addBanner(this);
  }

  public void addImage(Image image){
    if (this.image != image){
      this.image = image;
    }
  }

  public void removeImage(Image image){
    if(this.image == image){
      this.image = null;
      image.removeBanner(this);
    }
  }

  public Banner() {
  }
}
