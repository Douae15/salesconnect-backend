package com.salesconnect.backend.config.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  // Company
  private String companyName;
  private String companyIndustry;
  private String companyPhone;
  private String companyEmail;
  private String companyAddress;
  private String companyCountry;

  // Admin User
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String phone;

}
