package DevJang.BlogBackendJPA.domain.content;

import DevJang.BlogBackendJPA.domain.dto.ContentDto;
import DevJang.BlogBackendJPA.domain.dto.QContentDto;
import DevJang.BlogBackendJPA.domain.entity.Authority;
import DevJang.BlogBackendJPA.domain.entity.Content;
import DevJang.BlogBackendJPA.domain.entity.Member;
import DevJang.BlogBackendJPA.domain.member.MemberRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static DevJang.BlogBackendJPA.domain.entity.QContent.content;
import static DevJang.BlogBackendJPA.domain.entity.QMember.member;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ContentRepositoryCustomImplTest {
    @Autowired
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ContentRepository contentRepository;

    @BeforeEach
    public void before() {
        Member member1 = new Member("ks.jang@gmail.com", "DevJang", "jang1234", Authority.ROLE_USER);
        em.persist(member1);

        contentRepository.save(new Content("title1", "content", member1));
        contentRepository.save(new Content("title2", "content", member1));
        contentRepository.save(new Content("title3", "content", member1));
    }

    @Test
    public void findAll() throws Exception {
        List<ContentDto> fetch = queryFactory
                .select(new QContentDto(
                        content.id,
                        content.title,
                        content.contents,
                        member.nickName,
                        content.createDate
                )).from(content)
                .orderBy(content.createDate.desc()).
                        fetch();

        for (ContentDto contentDto : fetch) {
            System.out.println("contentDto = " + contentDto);
        }
        Long total = queryFactory
                .select(content.count())
                .from(content)
                .fetchOne();


    }
}