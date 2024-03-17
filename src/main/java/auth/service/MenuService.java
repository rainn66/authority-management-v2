package auth.service;

import auth.core.exception.MessageException;
import auth.core.util.CustomMap;
import auth.dto.MenuDTO;
import auth.entity.Menu;
import auth.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;

    public List<MenuDTO> getMenuList() {
        return menuRepository.findAll().stream().map(MenuDTO::new).collect(Collectors.toList());
    }

    //사이드메뉴 가져오기
    public List<MenuDTO> getSideMenuList(Collection<String> authorityCdList) {
        /* //조회권한 미적용 쿼리
        return menuRepository.findByParentIsNull(Sort.by(Sort.Direction.ASC, "menuOrder")).stream()
                .map(MenuDTO::new).collect(Collectors.toList());*/
        return menuRepository.findByViewAuthority(authorityCdList).stream()
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
                    .regDt(menu.getRegDt()).build())
                .orElseThrow(() -> new MessageException("메뉴를 찾을 수 없습니다."));
    }

    @Transactional
    public void saveMenu(MenuDTO menuDTO) {

        Menu parent;
        int count;
        if (menuDTO.getParentMenuIdx() == null) {
            parent = null;
            count = menuRepository.countByParentIsNull();
        } else {
            parent = menuRepository.findById(menuDTO.getParentMenuIdx()).orElseThrow(() -> new MessageException("상위메뉴를 찾을 수 없습니다."));
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
            //상위 메뉴가 이전과 달라졌을 경우(최상위 메뉴는 타 메뉴의 하위로 이동 불가능)
//            if (!menuDTO.getBfParentMenuIdx().equals(menuDTO.getParentMenuIdx())) {
            if ("Y".equals(menuDTO.getParentMenuChgYn())) {
                Menu parent;
                int count;
                if (menuDTO.getParentMenuIdx() == null) {
                    parent = null;
                    count = menuRepository.countByParentIsNull();
                } else {
                    parent = menuRepository.findById(menuDTO.getParentMenuIdx()).orElseThrow(() -> new MessageException("상위메뉴를 찾을 수 없습니다."));
                    count = menuRepository.countByParentMenuIdx(parent.getMenuIdx());
                }
                menu.update(count + 1,
                        menuDTO.getMenuNm(),
                        menuDTO.getMenuLink(),
                        menuDTO.getViewAuthority(),
                        menuDTO.getSaveAuthority(),
                        parent);

                List<Menu> bfMenuList = menuRepository.findByParentMenuIdx(menuDTO.getBfParentMenuIdx());
                int orderVal = 1;
                for (Menu m:bfMenuList) {
                    m.updateOrder(orderVal);
                    orderVal++;
                }
            } else {
                menu.update(menuDTO.getMenuNm(),
                        menuDTO.getMenuLink(),
                        menuDTO.getViewAuthority(),
                        menuDTO.getSaveAuthority());
            }
        });
    }

    @Transactional
    public void deleteMenu(Long menuIdx) {
        menuRepository.deleteById(menuIdx);
    }

    @Transactional
    public void updateOrder(Long[] menuIdxList) {
        int orderVal = 1;
        for (Long menuIdx : menuIdxList) {
            Menu menu = menuRepository.findById(menuIdx).orElseThrow(() -> new MessageException("순서 변경 중 " + menuIdx + " 번 메뉴를 찾을 수 없습니다."));
            menu.updateOrder(orderVal);
            orderVal++;
        }
    }

    @Transactional
    public void updateAllMenuAuth(List<CustomMap> param) {
        for (CustomMap map: param) {
            Long menuIdx = map.getLong("menuIdx");
            Optional<Menu> findMenu = menuRepository.findById(menuIdx);
            findMenu.ifPresent(menu -> {
                menu.update(map.getStr("viewAuthority"), map.getStr("saveAuthority"));
            });
        }
    }
}
