package DevJang.BlogBackendJPA.web.api;

import DevJang.BlogBackendJPA.domain.dto.ContentDto;
import DevJang.BlogBackendJPA.domain.service.ContentService;
import DevJang.BlogBackendJPA.web.form.content.CreateContentForm;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/content")
public class ContentApiController {
    private final ContentService contentService;
    @PostMapping("/new")
    public Object saveContent(@RequestBody @Validated CreateContentForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생 error = {}", bindingResult);
            return bindingResult.getAllErrors();
        }

        Long result = contentService.saveContent(form);

        return new ContentResponse(result);
    }

    @GetMapping("/landing")
    public Page<ContentDto> LandingPage(Pageable pageable) {
        log.info("API 컨트롤러 호출");
        return contentService.landging(pageable);
    }

    @GetMapping("/view")
    public ContentDto viewPage(@RequestParam("id") Long contentId) {
        return contentService.contentView(contentId);
    }

    @Data
    static class ContentResponse {
        private Long id;

        public ContentResponse(Long id) {
            this.id = id;
        }
    }
}
