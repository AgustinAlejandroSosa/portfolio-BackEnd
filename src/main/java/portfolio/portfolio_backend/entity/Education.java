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
public class Education {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String title;

  private String dateSince;

  private String dateTo;

  private String description;

  @OneToOne(targetEntity = Image.class, cascade = CascadeType.ALL)
  private Image image;

  public Education() {
  }

  public Education(Long id, String title, String dateSince, String dateTo, String description){
    this.id = id;
    this.title = title;
    this.dateSince = dateSince;
    this.dateTo = dateTo;
    this.description = description;
  }

  public void addImage(Image image) {
    if (this.image != image) {
      this.image = image;
      image.addEducation(this);
    }
  }
}