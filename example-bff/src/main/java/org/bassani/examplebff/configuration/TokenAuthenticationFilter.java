package org.bassani.examplebff.configuration;

import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplebff.utils.KeyCloakUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Objects;

public class TokenAuthenticationFilter  extends GenericFilterBean implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private KeyCloakService keyCloakService;

    @Override
    protected void initFilterBean() {
        keyCloakService = applicationContext.getBean(KeyCloakService.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private void updateTokenInformation() {

        if(Objects.nonNull(KeyCloakUtils.getAuthentication())) {

            String cdPerfil = KeyCloakUtils.getCDPerfil();
            String cdOperador = KeyCloakUtils.getCDOperador();

            if (StringUtils.isBlank(cdPerfil) || StringUtils.isBlank(cdOperador)) {
                keyCloakService.updateTokenAttribute();
            }
        }
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //fill token fields
        updateTokenInformation();

        chain.doFilter(request, response);
    }
}