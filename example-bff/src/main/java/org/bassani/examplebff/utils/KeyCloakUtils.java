package org.bassani.examplebff.utils;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class KeyCloakUtils {

    private static final String GROUPS = "groups";
    private static final String CD_OPERADOR = "CD_OPERADOR";
    private static final String CD_PERFIL = "CD_PERFIL";
    private static final String CD_AREA = "CD_AREA";

    public static SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }

    public static Authentication getAuthentication() {
        return getSecurityContext().getAuthentication();
    }

    public static KeycloakPrincipal getUser() {
        return (KeycloakPrincipal) getAuthentication().getPrincipal();
    }
    public static String getKeycloakUserId() {
        return getUser().getName();
    }

    public static AccessToken getAccessToken() {
        return getUser().getKeycloakSecurityContext().getToken();
    }

    public static List<String> getScopes() {
        return Arrays.asList(getAccessToken().getScope().split(" "));
    }

    public static Set<String> getRoles() {
        return getAccessToken().getRealmAccess().getRoles();
    }

    public static String getEmail() {
        return getAccessToken().getEmail();
    }

    public static String getName() {
        return getAccessToken().getName();
    }

    public static String getID() {
        return getAccessToken().getId();
    }

    public static String getProfile() {
        return getAccessToken().getProfile();
    }

    public static Map<String, Object> getOtherClaims() {
        return getAccessToken().getOtherClaims();
    }

    public static Optional<String> getGroup(){
        Optional<String> group = Optional.empty();
        if(claimsHasField(GROUPS)) {
            group = ((List<String>) getOtherClaims().get(GROUPS)).stream().findFirst();
        }
        return group;
    }

    public static String getCDOperador(){
        String cdOperador = null;
        if(claimsHasField(CD_OPERADOR)){
            cdOperador = getOtherClaims().get(CD_OPERADOR).toString();
        }
        return cdOperador;
    }

    public static String getCDPerfil(){
        String cdPerfil = null;
        if(claimsHasField(CD_PERFIL)) {
            cdPerfil = getOtherClaims().get(CD_PERFIL).toString();
        }
        return cdPerfil;
    }

    public static String getCDArea(){
        String cdArea = null;
        if(claimsHasField(CD_AREA)){
            cdArea = getOtherClaims().get(CD_AREA).toString();
        }
        return cdArea;
    }

    private static boolean claimsHasField(String field) {
        return !getOtherClaims().isEmpty() && getOtherClaims().get(field) != null;
    }

    public static void addCdOperatorCurrentToken(Long cdOperador){
        getOtherClaims().put(CD_OPERADOR, cdOperador);
    }

    public static void addCdPerfilCurrentToken(Long cdPerfil){
        getOtherClaims().put(CD_PERFIL, cdPerfil);
    }

    public static void addCdAreaCurrentToken(Long cdArea){
        getOtherClaims().put(CD_AREA, cdArea);
    }

}

