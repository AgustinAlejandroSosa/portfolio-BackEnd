package portfolio.portfolio_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExperienceDto {

    private Long id;
    private String imageUrl;
    private String title;
    private String description;
    private String dateSince;
    private String dateTo;

    public ExperienceDto(Long id,String title, String description, String dateSince, String dateTo) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateSince = dateSince;
        this.dateTo = dateTo;
    }
}
