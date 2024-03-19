package com.example.model;

import java.util.Objects;

/**
 * CarRequest
 */

public class CarRequest extends BaseRequest {

  private String from = null;


  private String to = null;


  private String vehicleType = null;

  public CarRequest from(String from) {
    this.from = from;
    return this;
  }


    public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public CarRequest to(String to) {
    this.to = to;
    return this;
  }


    public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public CarRequest vehicleType(String vehicleType) {
    this.vehicleType = vehicleType;
    return this;
  }


    public String getVehicleType() {
    return vehicleType;
  }

  public void setVehicleType(String vehicleType) {
    this.vehicleType = vehicleType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CarRequest carRequest = (CarRequest) o;
    return Objects.equals(this.from, carRequest.from) &&
        Objects.equals(this.to, carRequest.to) &&
        Objects.equals(this.vehicleType, carRequest.vehicleType) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, to, vehicleType, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CarRequest {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
    sb.append("    to: ").append(toIndentedString(to)).append("\n");
    sb.append("    vehicleType: ").append(toIndentedString(vehicleType)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
