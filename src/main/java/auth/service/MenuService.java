package auth.service;

import auth.dto.MenuDTO;
import auth.entity.Menu;
import auth.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;

    public List<MenuDTO> getMenuList() {
        return menuRepository.findByParentIsNull().stream()
                .map(MenuDTO::new).collect(Collectors.toList());
    }

    public MenuDTO getMenuInfo(Long menuIdx) {
        return menuRepository.findById(menuIdx).map(menu ->
                MenuDTO.builder()
                    .menuIdx(menu.getMenuIdx())
                    .menuOrder(menu.getMenuOrder())
                    .menuNm(menu.getMenuNm())
                    .menuLink(menu.getMenuLink())
                    .viewAuthority(menu.getViewAuthority())
                    .saveAuthority(menu.getSaveAuthority())
                    .parentMenuIdx(menu.getParent() == null ? null : menu.getParent().getMenuIdx())
                    .regDt(menu.getRegDt()).build()).orElseThrow();
    }

    @Transactional
    public void saveMenu(MenuDTO menuDTO) {

        Menu parent;
        Long count;
        if (menuDTO.getParentMenuIdx() == null) {
            parent = null;
            count = menuRepository.countByParentIsNull();
        } else {
            parent = menuRepository.findById(menuDTO.getParentMenuIdx()).orElseThrow();
            count = menuRepository.countByParentMenuIdx(parent.getMenuIdx());
        }

        Menu menu = Menu.builder()
                .menuNm(menuDTO.getMenuNm())
                .menuLink(menuDTO.getMenuLink())
                .menuOrder(count + 1)
                .viewAuthority(menuDTO.getViewAuthority())
                .saveAuthority(menuDTO.getSaveAuthority())
                .parent(parent)
                .build();

        menuRepository.save(menu);
    }


    @Transactional
    public void updateMenu(MenuDTO menuDTO) {

        Optional<Menu> findMenu = menuRepository.findById(menuDTO.getMenuIdx());

        findMenu.ifPresent(menu -> {
            //상위 메뉴가 이전과 달라졌을 경우
            if ("Y".equals(menuDTO.getParentChangeYn())) {
                Menu parent;
                Long count;
                if (menuDTO.getParentMenuIdx() == null) {
                    parent = null;
                    count = menuRepository.countByParentIsNull();
                } else {
                    parent = menuRepository.findById(menuDTO.getParentMenuIdx()).orElseThrow();
                    count = menuRepository.countByParentMenuIdx(parent.getMenuIdx());
                }
                menu.update(count + 1,
                        menuDTO.getMenuNm(),
                        menuDTO.getMenuLink(),
                        menuDTO.getViewAuthority(),
                        menuDTO.getSaveAuthority(),
                        parent);
            } else {
                menu.update(menuDTO.getMenuNm(),
                        menuDTO.getMenuLink(),
                        menuDTO.getViewAuthority(),
                        menuDTO.getSaveAuthority());
            }
        });
    }

}
