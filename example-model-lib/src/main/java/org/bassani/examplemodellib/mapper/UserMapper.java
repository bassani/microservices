package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.request.KeyCloakUserDetailRequest;
import br.com.example.purchasesimulatormodellib.domain.request.UserRequest;
import br.com.example.purchasesimulatormodellib.domain.response.KeyCloakUserDetailResponse;
import br.com.example.purchasesimulatormodellib.domain.response.OperatorResponse;
import br.com.example.purchasesimulatormodellib.domain.response.UserResponse;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    static UserMapper userMapper() {
        return INSTANCE;
    }

    @Mapping(target = "registrationNumber", expression = "java(keyCloakUser.getAttributes() != null ? " + "this" +
            ".getFirstElement(keyCloakUser.getAttributes().getRegistrationNumber()) : null)")
    @Mapping(target = "keyCloakUserId", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "areaCode", expression = "java(keyCloakUser.getAttributes() != null ? " + "this.getFirstElement" +
            "(keyCloakUser.getAttributes().getCdArea()) : null)")
    @Mapping(target = "phoneNumber", expression = "java(keyCloakUser.getAttributes() != null ? " + "this" +
            ".getFirstElement(keyCloakUser.getAttributes().getPhoneNumer()) : null)")
    @Mapping(target = "accessProfile", expression = "java(keyCloakUser.getAttributes() != null ? " + "this" +
            ".getFirstElement(keyCloakUser.getAttributes().getCdPerfil()) : null)")
    @Mapping(target = "activationStatus", source = "enabled")
    @Mapping(target = "vacationReturnDate", expression = "java(keyCloakUser.getAttributes() != null ? " + "this" +
            ".getFirstElement(keyCloakUser.getAttributes().getVacationReturnDate()) : null)")
    UserResponse keyCloakUserToResponse(KeyCloakUserDetailResponse keyCloakUser);

    default <T> T getFirstElement(List<T> listAttributes) {
        return !CollectionUtils.isEmpty(listAttributes) ? listAttributes.get(0) : null;
    }

    @Mapping(target = "username", source = "email")
    @Mapping(target = "enabled", source = "activationStatus")
    @Mapping(target = "firstName", expression = "java(this.getFirstName(userRequest.getFirstName(), " + "userRequest" +
            ".getFullName()))")
    @Mapping(target = "lastName", expression = "java(this.getLastName(userRequest.getLastName(), " + "userRequest" +
            ".getFullName()))")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "attributes.registrationNumber", source = "registrationNumber")
    @Mapping(target = "attributes.cdPerfil", source = "accessProfile")
    @Mapping(target = "attributes.cdArea", source = "areaCode")
    @Mapping(target = "attributes.phoneNumer", source = "phoneNumber")
    @Mapping(target = "attributes.vacationReturnDate", source = "vacationReturnDate")
    KeyCloakUserDetailRequest requestToKeyCloakUser(UserRequest userRequest);

    @Named("firstName")
    default String getFirstName(String firstName, String fullName) {
        if (fullName == null && firstName != null) {
            return firstName;
        } else if (fullName == null) {
            return null;
        } else {
            String fullNameTrim = fullName.trim();
            return fullNameTrim.contains(" ") ? fullNameTrim.substring(0, fullNameTrim.lastIndexOf(" ")) : fullNameTrim;
        }
    }

    @Named("lastName")
    default String getLastName(String lastName, String fullName) {
        if (fullName == null && lastName != null) {
            return lastName;
        } else if (fullName == null) {
            return null;
        } else {
            String fullNameTrim = fullName.trim();
            int indexWhiteSpace = fullNameTrim.lastIndexOf(" ");
            if (indexWhiteSpace < 0) {
                return StringUtils.EMPTY;
            }
            return fullNameTrim.substring(indexWhiteSpace + 1);
        }
    }

    @Mapping(target = "positionCode", ignore = true)
    @Mapping(target = "name", expression = "java(keyCloakUser.getFirstName() + keyCloakUser.getLastName())")
    @Mapping(target = "keyCloakUserId", source = "id")
    @Mapping(target = "code", source = "attributes.registrationNumber")
    @Mapping(target = "areaCode", source = "attributes.cdArea")
    OperatorResponse keyCloakUserToOperatorResponse(KeyCloakUserDetailResponse keyCloakUser);

}
