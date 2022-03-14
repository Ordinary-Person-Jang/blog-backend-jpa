package DevJang.BlogBackendJPA.domain.content;

import DevJang.BlogBackendJPA.domain.dto.ContentDto;
import DevJang.BlogBackendJPA.domain.dto.ContentSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContentRepositoryCustom {
    Page<ContentDto> searchPage(ContentSearchCondition condition, Pageable pageable);

    Page<ContentDto> searchAll(Pageable pageable);

    ContentDto searchOne(Long contentId);
}
