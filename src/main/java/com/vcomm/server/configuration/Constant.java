package com.vcomm.server.configuration;

public class Constant {
  private Constant() {

  }
  public static final String ADMIN_API_V1 = "/admin/api/v1";
  public static final String API_V1 = "/api/v1";
  public static final String AUTH_V1 = "/auth/v1";


  private static final String[] PERMITTED_FILE_TYPES = {"image/jpeg"};


  public static boolean isFilePermitted(String type) {
    for (String contentType:PERMITTED_FILE_TYPES) {
      if(contentType.equals(type)) {
        return true;
      }
    }
    return false;
  }

}
