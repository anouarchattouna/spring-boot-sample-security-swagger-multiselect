package org.anotherdev.sample.domain;

/**
 * @author Anouar
 */
public class ApiInfo {

  private String apiKey;

  private String apiValue;

  public ApiInfo(String apiKey, String apiValue) {
    super();
    this.apiKey = apiKey;
    this.apiValue = apiValue;
  }

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public String getApiValue() {
    return apiValue;
  }

  public void setApiValue(String apiValue) {
    this.apiValue = apiValue;
  }

  @Override
  public String toString() {
    return "ApiInfo [apiKey=" + apiKey + ", apiValue=" + apiValue + "]";
  }

}
