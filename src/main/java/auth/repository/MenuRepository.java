package auth.repository;

import auth.entity.Menu;
import auth.repository.impl.MenuCustomRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long>, MenuCustomRepository {

    List<Menu> findByParentIsNull(Sort menuOrder);

    List<Menu> findByParentMenuIdx(Long parentMenuIdx);

    int countByParentIsNull();

    int countByParentMenuIdx(Long menuIdx);
}
