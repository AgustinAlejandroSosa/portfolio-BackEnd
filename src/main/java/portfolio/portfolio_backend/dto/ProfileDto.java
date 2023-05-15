package portfolio.portfolio_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileDto {

  private String name;
  private String position;
  private String description;
  private String imageUrl;

  public ProfileDto(String name,String position, String description){
    this.name = name ;
    this.position = position;
    this.description = description;
  }

  public ProfileDto() {
  }

}
