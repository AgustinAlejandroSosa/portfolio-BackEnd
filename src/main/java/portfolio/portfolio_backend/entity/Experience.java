package portfolio.portfolio_backend.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Experience {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String title;

  private String dateSince;

  private String dateTo;

  private String description;

  @OneToOne(targetEntity = Image.class, cascade = CascadeType.ALL)
  private Image image;

  public Experience(){}

  public void addImage(Image image){
    if(this.image != image){
      this.image = image;
      image.addExperience(this);
    }
  }

  public void removeImage(){
    if (this.image != null){
      this.image = null;
    }
  }
  
}