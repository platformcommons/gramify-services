package com.platformcommons.platform.service.iam.dto.brbase;

import java.util.Objects;

public class UserVO {
    private Integer userId;
    private String login;
    private String firstName;
    private String lastName;
    private String iconPic;

    public UserVO(Integer id, String login, String firstName, String lastName, String iconpic) {
        this.userId = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.iconPic = iconpic;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIconPic() {
        return iconPic;
    }

    public void setIconPic(String iconPic) {
        this.iconPic = iconPic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserVO userVO = (UserVO) o;
        return Objects.equals(userId, userVO.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
