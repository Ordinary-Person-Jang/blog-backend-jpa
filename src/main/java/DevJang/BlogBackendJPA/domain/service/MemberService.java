package DevJang.BlogBackendJPA.domain.service;

import DevJang.BlogBackendJPA.domain.entity.Authority;
import DevJang.BlogBackendJPA.domain.entity.Member;
import DevJang.BlogBackendJPA.domain.member.MemberRepository;
import DevJang.BlogBackendJPA.web.form.member.CreateMemberForm;
import DevJang.BlogBackendJPA.web.form.member.UpdateMemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final BCryptPasswordEncoder encoder;
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(CreateMemberForm form) {
        Member member =
                new Member(form.getEmail(), encoder.encode(form.getPassword()), form.getNickName(), Authority.ROLE_USER, form.getEmail());
        ValidateDuplicateMember(member);
        checkNickName(member.getNickName());
        memberRepository.save(member);
        return member.getId();
    }

    public void checkEmail(String email) {
        Member member = new Member(email);
        ValidateDuplicateMember(member);
    }

    public void checkNickName(String nickname) {
        Optional<Member> result = memberRepository.findByNickName(nickname).stream().findFirst();
        if (!result.isEmpty()) {
            throw new IllegalStateException("이미 사용중인 닉네임입니다");
        }
    }

    private void ValidateDuplicateMember(Member member) {
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail()).stream().findFirst();
        if (!findMember.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    @Transactional
    public Long updateMemberInfo(UpdateMemberForm form) {
        Member findMember = memberRepository.findMemberByEmail(form.getEmail());
        findMember.ChangeMemeberInfo(form.getPassword(), form.getNickName());
        return findMember.getId();
    }


    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).orElse(null);
    }
}
