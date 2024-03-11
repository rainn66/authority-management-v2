package auth.repository.impl;

import auth.entity.Menu;
import auth.entity.QMenu;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static auth.entity.QMenu.menu;

@SpringBootTest
@Transactional
class MenuCustomRepositoryImplTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Test
    public void testMenuSelfJoin() {
        QMenu menuChild = new QMenu("menuChild");

        List<Menu> menus = jpaQueryFactory
                .selectFrom(menu)
                .leftJoin(menu.childMenu, menuChild).fetchJoin()
                .where(menu.parent.isNull(),
                        viewAuthorityIn("AUTH002"),
                        childViewAuthorityIn(menuChild, "AUTH002"))
                .orderBy(menu.menuIdx.asc())
                .fetch();

        int count = 1;
        for (Menu menu1 : menus) {
            System.out.println("count = " + count);
            count++;
            System.out.println("menu1.getMenuNm = " + menu1.getMenuNm() + " " + menu1.getViewAuthority());
            if (menu1.getChildMenu() != null) {
                for (Menu childMenu : menu1.getChildMenu()) {
                    System.out.println("childMenu = " + childMenu.getMenuNm() + " " + childMenu.getViewAuthority());
                }
            }
        }

    }

    private Predicate viewAuthorityIn(String authorityCd) {
        List<String> authorityCdList = new ArrayList<>();
        authorityCdList.add("AUTH001");
        if ("AUTH002".equals(authorityCd)) {
            authorityCdList.add("AUTH002");
        } else if ("AUTH003".equals(authorityCd)) {
            authorityCdList.add("AUTH002");
            authorityCdList.add("AUTH003");
        }
        return menu.viewAuthority.in(authorityCdList);
    }

    private Predicate childViewAuthorityIn(QMenu menuChild, String authorityCd) {
        List<String> authorityCdList = new ArrayList<>();

        authorityCdList.add("AUTH001");
        if ("AUTH002".equals(authorityCd)) {
            authorityCdList.add("AUTH002");
        } else if ("AUTH003".equals(authorityCd)) {
            authorityCdList.add("AUTH002");
            authorityCdList.add("AUTH003");
        }
        return menuChild.viewAuthority.in(authorityCdList).or(menuChild.isNull());
    }

}