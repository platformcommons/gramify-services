package com.platformcommons.platform.service.iam.domain.vo;

import java.math.BigDecimal;
import java.util.Objects;

public class UserVO {
    private Long userId;

    private Long crossUserId;
    private String login;
    private String firstName;
    private String lastName;
    private String iconPic;
    private String fullName;


    private BigDecimal userSystemUUID;

    public UserVO(Long userId, String login, String firstName, String lastName, String iconpic) {
        this.userId = userId;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.iconPic = iconpic;
    }

    public UserVO(Long userId, Long crossUserId, String login, String firstName, String lastName, String iconPic,BigDecimal userSystemUUID) {
        this.userId = userId;
        this.crossUserId = crossUserId;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.iconPic = iconPic;
        this.userSystemUUID = userSystemUUID;
    }


    public UserVO(Long userId, Long crossUserId, String login, String firstName, String lastName, String iconPic, String fullName, BigDecimal userSystemUUID) {
        this.userId = userId;
        this.crossUserId = crossUserId;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.iconPic = iconPic;
        this.fullName = fullName;
        this.userSystemUUID = userSystemUUID;
    }

    public UserVO() {
    }

    public Long getCrossUserId() {
        return crossUserId;
    }

    public void setCrossUserId(Long crossUserId) {
        this.crossUserId = crossUserId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIconPic() {
        return this.iconPic;
    }

    public void setIconPic(String iconPic) {
        this.iconPic = iconPic;
    }


    public BigDecimal getUserSystemUUID() {
        return userSystemUUID;
    }

    public void setUserSystemUUID(BigDecimal userSystemUUID) {
        this.userSystemUUID = userSystemUUID;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            UserVO userVO = (UserVO) o;
            return Objects.equals(this.userId, userVO.userId);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.userId});
    }
}
