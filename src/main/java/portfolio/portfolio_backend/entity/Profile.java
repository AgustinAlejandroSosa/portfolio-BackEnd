package portfolio.portfolio_backend.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Profile {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private String position;

  @Column(name = "description", length = 300)
  private String description;

  @OneToOne(targetEntity = Image.class, cascade = CascadeType.ALL)
  private Image image;

  public Profile() {
  }

  public Profile(String name, String position, String description, Image image) {
    this.name = name;
    this.position = position;
    this.description = description;
    addImage(image);
  }

  public Profile(String name, String position, String description) {
    this.name = name;
    this.position = position;
    this.description = description;
  }

  public void addImage(Image image) {
    if (this.image != image) {
      if (this.image != null) {
        this.image.removeProfile();
      }
      this.image = image;
      image.addProfile(this);
    }
  }

  public void removeImage() {
    if (this.image != null) {
      this.image = null;
      if (this.image.getProfile() != null) {
        image.removeProfile();
      }
    }
  }
}
