package DevJang.BlogBackendJPA.domain.service;

import DevJang.BlogBackendJPA.domain.content.ContentRepository;
import DevJang.BlogBackendJPA.domain.dto.ContentDto;
import DevJang.BlogBackendJPA.domain.entity.Content;
import DevJang.BlogBackendJPA.domain.entity.Member;
import DevJang.BlogBackendJPA.domain.member.MemberRepository;
import DevJang.BlogBackendJPA.web.form.content.CreateContentForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long saveContent(CreateContentForm form) {
        Member writeMember = memberRepository.findMemberByNickName(form.getNickName());
        Content content = contentRepository.save(
                new Content(form.getTitle(), form.getContent(), writeMember));
        return content.getId();
    }

    public Page<ContentDto> landging(Pageable pageable) {
        return contentRepository.searchAll(pageable);
    }

    public ContentDto contentView(Long contentId) {
        return contentRepository.searchOne(contentId);
    }
}
