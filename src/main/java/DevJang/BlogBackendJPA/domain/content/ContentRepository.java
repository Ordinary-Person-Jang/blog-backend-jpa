package DevJang.BlogBackendJPA.domain.content;

import DevJang.BlogBackendJPA.domain.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long>, ContentRepositoryCustom {
}
