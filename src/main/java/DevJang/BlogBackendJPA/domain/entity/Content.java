package DevJang.BlogBackendJPA.domain.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Content extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Long id;

    private String title;

    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public Content(String title, String contents, Member member) {
        this.title = title;
        this.contents = contents;
        if (member != null) {
            ChangeMember(member);
        }
    }

    private void ChangeMember(Member member) {
        this.member = member;
        member.getContents().add(this);
    }
}
