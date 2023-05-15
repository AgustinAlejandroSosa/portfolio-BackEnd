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
public class Skill {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  private boolean hard;

  @OneToOne(targetEntity = Image.class, cascade = CascadeType.ALL)
  private Image image;

  public Skill() {
  }

  public Skill(String name, boolean hard, Image image) {
    this.name = name;
    this.hard = hard;
    this.image = image;
  }

  public Skill(String name, boolean hard) {
    this.name = name;
    this.hard = hard;
  }

  public void addImage(Image image) {
    if (this.image != image) {
      image.removeSkill();
      this.image = image;
      image.addSkill(this);
    }
  }

  public void removeImage() {
    if (this.image != null) {
      this.image = null;
      if (this.image.getSkill() != null) {
        this.image.removeSkill();
      }
    }
  }
}
