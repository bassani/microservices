package org.bassani.examplebff.service;

import org.bassani.examplebff.repository.MenuRepository;
import org.bassani.examplebff.utils.KeyCloakUtils;
import org.bassani.examplemodellib.domain.response.MenuResponse;
import org.bassani.examplemodellib.exception.MenuNotFoundException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public List<MenuResponse> getAll() {
        List<MenuResponse> menu = null;
        try {
            Long profileId = Long.valueOf(KeyCloakUtils.getCDPerfil());
            menu = menuRepository.getAllMenusByProfile(profileId);
        } catch (FeignException.NotFound e) {
            throw new MenuNotFoundException();
        }
        return menu;
    }

    public MenuResponse getMenu(Integer id) {
        MenuResponse menu = null;
        try {
            menu = menuRepository.getMenu(id);
        } catch (FeignException.NotFound e) {
            throw new MenuNotFoundException();
        }
        return menu;
    }
}
