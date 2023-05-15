package portfolio.portfolio_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProyectDto {

  private Long id;
  private String imageUrl;
  private String title;
  private String description;
  private String date;
  private String repositoryLink;

  public ProyectDto(Long id, String title, String description, String date, String repositoryLink) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.date = date;
    this.repositoryLink = repositoryLink;
  }
}
