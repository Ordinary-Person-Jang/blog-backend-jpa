package DevJang.BlogBackendJPA.domain.member;

import DevJang.BlogBackendJPA.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByEmail(String email);

    List<Member> findByNickName(String nickName);

    Member findMemberByEmail(String email);

    Member findMemberByNickName(String nickName);

    List<Member> findMemberByLoginId(String loginId);
}
