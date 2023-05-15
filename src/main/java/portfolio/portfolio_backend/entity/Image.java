package portfolio.portfolio_backend.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String url;

  public Image(String url) {
    this.url = url;
  }

  public Image() {
  }

  @JsonIgnore
  @OneToOne(mappedBy = "image", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Profile profile;

  @JsonIgnore
  @OneToOne(mappedBy = "image", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Experience experience;

  @JsonIgnore
  @OneToOne(mappedBy = "image", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Skill skill;

  @JsonIgnore
  @OneToOne(mappedBy = "image", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Education education;

  @JsonIgnore
  @OneToOne(mappedBy = "image", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Banner banner;

  @JsonIgnore
  @OneToOne(mappedBy = "image", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Proyect proyect;

  public void addBanner(Banner banner) {
    if (this.banner != banner) {
      this.banner = banner;
    }
  }

  public void removeBanner(Banner banner) {
    if (this.banner == banner) {
      this.banner = null;
      if (this.banner.getImage() != null) {
        banner.removeImage(this);
      }
    }
  }

  public void addEducation(Education education) {
    if (this.education != education) {
      this.education = education;
    }
  }

  public void addExperience(Experience experience) {
    if (this.experience != experience) {
      this.experience = experience;
    }
  }

  public void addSkill(Skill skill) {
    if (this.skill != skill) {
      this.skill = skill;
    }
  }

  public void addProfile(Profile profile) {
    if (this.profile != profile) {
      this.profile = profile;
      profile.addImage(this);
    }
  }

  public void removeProfile() {
    if (this.profile != null) {
      if (this.profile.getImage() != null) {
        this.profile.removeImage();
      }
      this.profile = null;
    }
  }

  public void removeExperience() {
    if (this.experience != null) {
      if (this.experience.getImage() != null) {
        this.experience.removeImage();
      }
      this.experience = null;
    }
  }

  public void removeSkill() {
    if (this.skill != null) {
      if (this.getSkill() != null) {
        this.skill.removeImage();
      }
      this.skill = null;
    }
  }

  public void addProyect(Proyect proyect) {
    if (this.proyect != proyect) {
      this.proyect = proyect;
      proyect.addImage(this);
    }
  }

  public void removeProyect() {
    if (this.proyect != null) {
      if (this.proyect.getImage() != null) {
        this.proyect.removeImage();
      }
      this.proyect = null;
    }
  }
}
