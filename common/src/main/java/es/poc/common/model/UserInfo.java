package es.poc.common.model;

import java.util.List;
import java.util.Objects;

public class UserInfo {

  private String name;
  private String surname;
  private String email;


  public UserInfo() {
  }

  public UserInfo(String name, String surname, String email) {
    this.name = name;
    this.surname = surname;
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public String getEmail() {
    return email;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserInfo orderInfo = (UserInfo) o;
    return Objects.equals(name, orderInfo.name) &&
      Objects.equals(surname, orderInfo.surname) &&
      Objects.equals(email, orderInfo.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, surname, email);
  }

}
