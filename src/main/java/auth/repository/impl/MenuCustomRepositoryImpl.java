package auth.repository.impl;

import auth.entity.Menu;
import auth.entity.QMenu;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.Collection;
import java.util.List;

import static auth.entity.QMenu.menu;

public class MenuCustomRepositoryImpl implements MenuCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public MenuCustomRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Menu> findByViewAuthority(Collection<String> authorityCdList) {
        //self join
        QMenu menuChild = new QMenu("menuChild");

        return jpaQueryFactory
                .selectFrom(menu)
                .leftJoin(menu.childMenu, menuChild).fetchJoin()
                .where(menu.parent.isNull(),
                        menu.viewAuthority.in(authorityCdList),
                        menuChild.viewAuthority.in(authorityCdList).or(menuChild.isNull()))
                .orderBy(menu.menuOrder.asc())
                .fetch();
    }
}
