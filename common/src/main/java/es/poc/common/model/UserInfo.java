package es.poc.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class UserInfo {

  private String name;
  private String surname;
  private String email;



}
