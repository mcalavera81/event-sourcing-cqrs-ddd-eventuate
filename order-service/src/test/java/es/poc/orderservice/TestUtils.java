package es.poc.orderservice;

import es.poc.common.model.UserInfo;

public class TestUtils {

  public static UserInfo newUserInfo() {
    return UserInfo.of("name", "surname", "email@domain.com");
  }
}
