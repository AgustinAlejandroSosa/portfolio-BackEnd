package portfolio.portfolio_backend.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Proyect {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String title;
  private String description;
  private String date;
  private String repositoryLink;

  @OneToOne(targetEntity = Image.class, cascade = CascadeType.ALL)
  private Image image;

  public Proyect() {
  }

  public Proyect(String title, String description, String date, String repositoryLink) {
    this.title = title;
    this.description = description;
    this.date = date;
    this.repositoryLink = repositoryLink;
  }

  public void addImage(Image image) {
    if (this.image != image) {
      this.image = image;
      image.addProyect(this);
    }
  }

  public void removeImage() {
    if (this.image != null) {
      this.image = null;
    }
  }
}
