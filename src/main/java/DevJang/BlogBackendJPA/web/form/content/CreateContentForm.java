package DevJang.BlogBackendJPA.web.form.content;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CreateContentForm {

    @NotBlank
    @Size(min = 2 , max = 100)
    private String title;
    @NotBlank
    @Size(max = 5000)
    private String content;
    @NotBlank
    private String nickName;
}
