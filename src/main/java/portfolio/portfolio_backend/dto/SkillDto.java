package portfolio.portfolio_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SkillDto {
  private Long id;
  private String name;
  private boolean hard;
  private String imageUrl;

  public SkillDto(){}

  public SkillDto(Long id,String name,boolean hard){
    this.name = name;
    this.id = id;
    this.hard = hard;
  }
}
