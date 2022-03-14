package DevJang.BlogBackendJPA.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ContentDto {
    private Long id;
    private String title;
    private String content;
    private String nickName;
    private LocalDateTime createDate;

    @QueryProjection
    public ContentDto(Long id, String title, String content, String nickName, LocalDateTime createDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.nickName = nickName;
        this.createDate = createDate;
    }
}
