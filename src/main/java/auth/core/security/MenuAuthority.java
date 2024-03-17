package auth.core.security;

import auth.dto.MenuDTO;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MenuAuthority {

    public static List<MenuDTO> menuAuthorities = new ArrayList<MenuDTO>();
}
