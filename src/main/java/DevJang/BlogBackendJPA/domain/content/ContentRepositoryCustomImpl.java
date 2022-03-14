package DevJang.BlogBackendJPA.domain.content;

import DevJang.BlogBackendJPA.domain.dto.ContentDto;
import DevJang.BlogBackendJPA.domain.dto.ContentSearchCondition;
import DevJang.BlogBackendJPA.domain.dto.QContentDto;
import DevJang.BlogBackendJPA.domain.entity.QMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static DevJang.BlogBackendJPA.domain.entity.QContent.*;
import static DevJang.BlogBackendJPA.domain.entity.QMember.*;
import static org.springframework.util.StringUtils.*;

@RequiredArgsConstructor
public class ContentRepositoryCustomImpl implements ContentRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    @Override
    public Page<ContentDto> searchPage(ContentSearchCondition condition, Pageable pageable) {
        List<ContentDto> fetch = queryFactory
                .select(new QContentDto(
                        content.id,
                        content.title,
                        content.contents,
                        member.nickName,
                        content.createDate
                )).from(content)
                .join(content.member, member)
                .where(nickNameEq(condition.getNickName()),
                        titleEq(condition.getTitle()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(content.createDate.desc()).
                        fetch();

        Long total = queryFactory.select(content.count())
                .from(content)
                .where(nickNameEq(condition.getNickName()),
                        titleEq(condition.getTitle()))
                .fetchOne();

        return new PageImpl<>(fetch, pageable, total);
    }

    private BooleanExpression nickNameEq(String nickName) {
        return hasText(nickName) ? member.nickName.like(nickName) : null;
    }

    private BooleanExpression titleEq(String title) {
        return hasText(title) ? content.title.like(title) : null;
    }

    @Override
    public Page<ContentDto> searchAll(Pageable pageable) {
        List<ContentDto> fetch = queryFactory
                .select(new QContentDto(
                        content.id,
                        content.title,
                        content.contents,
                        member.nickName,
                        content.createDate
                )).from(content)
                .join(content.member, member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(content.createDate.desc()).
                        fetch();

        Long total = queryFactory
                .select(content.count())
                .from(content)
                .fetchOne();
        return new PageImpl<>(fetch, pageable, total);
    }

    @Override
    public ContentDto searchOne(Long contentId) {
        ContentDto contentDto = queryFactory
                .select(new QContentDto(
                        content.id,
                        content.title,
                        content.contents,
                        member.nickName,
                        content.createDate
                )).from(content)
                .join(content.member, member)
                .where(content.id.eq(contentId))
                .fetchOne();
        return contentDto;
    }
}
