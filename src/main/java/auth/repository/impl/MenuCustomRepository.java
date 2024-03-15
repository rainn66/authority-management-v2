package auth.repository.impl;

import auth.entity.Menu;

import java.util.List;

public interface MenuCustomRepository {

    List<Menu> findByViewAuthority(String authorityCd);

}
