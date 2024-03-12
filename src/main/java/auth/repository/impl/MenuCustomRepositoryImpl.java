package auth.repository.impl;

import auth.entity.Menu;
import auth.entity.QMenu;
import auth.repository.MenuCustomRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static auth.entity.QMenu.menu;

@Slf4j
public class MenuCustomRepositoryImpl implements MenuCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public MenuCustomRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Menu> findByViewAuthority(String authorityCd) {
        QMenu menuChild = new QMenu("menuChild");

        log.info("authorityCd {}", authorityCd);
        return jpaQueryFactory
                .selectFrom(menu)
                .leftJoin(menu.childMenu, menuChild).fetchJoin()
                .where(menu.parent.isNull(),
                        viewAuthorityIn(authorityCd),
                        childViewAuthorityIn(menuChild, authorityCd))
                .orderBy(menu.menuOrder.asc())
                .fetch();
    }

    private Predicate viewAuthorityIn(String authorityCd) {
        List<String> authorityCdList = setAuthorityList(authorityCd);
        return menu.viewAuthority.in(authorityCdList);
    }

    private Predicate childViewAuthorityIn(QMenu childMenu, String authorityCd) {
        List<String> authorityCdList = setAuthorityList(authorityCd);
        return childMenu.viewAuthority.in(authorityCdList).or(childMenu.isNull());
    }

    private List<String> setAuthorityList(String authorityCd) {
        List<String> authorityCdList = new ArrayList<>();
        authorityCdList.add("AUTH001");
        if ("AUTH002".equals(authorityCd)) {
            authorityCdList.add("AUTH002");
        } else if ("AUTH003".equals(authorityCd)) {
            authorityCdList.add("AUTH002");
            authorityCdList.add("AUTH003");
        }
        return authorityCdList;
    }
}
