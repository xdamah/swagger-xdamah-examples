package com.example.model;

import java.util.Objects;

/**
 * FlightRequest
 */


public class FlightRequest extends BaseRequest {

  private String fromAirport = null;


  private String toAirport = null;


  private String seatType = null;

  public FlightRequest fromAirport(String fromAirport) {
    this.fromAirport = fromAirport;
    return this;
  }

  /**
   * Get fromAirport
   * @return fromAirport
   **/

    public String getFromAirport() {
    return fromAirport;
  }

  public void setFromAirport(String fromAirport) {
    this.fromAirport = fromAirport;
  }

  public FlightRequest toAirport(String toAirport) {
    this.toAirport = toAirport;
    return this;
  }

  /**
   * Get toAirport
   * @return toAirport
   **/

    public String getToAirport() {
    return toAirport;
  }

  public void setToAirport(String toAirport) {
    this.toAirport = toAirport;
  }

  public FlightRequest seatType(String seatType) {
    this.seatType = seatType;
    return this;
  }

  /**
   * Get seatType
   * @return seatType
   **/
  public String getSeatType() {
    return seatType;
  }

  public void setSeatType(String seatType) {
    this.seatType = seatType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FlightRequest flightRequest = (FlightRequest) o;
    return Objects.equals(this.fromAirport, flightRequest.fromAirport) &&
        Objects.equals(this.toAirport, flightRequest.toAirport) &&
        Objects.equals(this.seatType, flightRequest.seatType) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fromAirport, toAirport, seatType, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FlightRequest {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    fromAirport: ").append(toIndentedString(fromAirport)).append("\n");
    sb.append("    toAirport: ").append(toIndentedString(toAirport)).append("\n");
    sb.append("    seatType: ").append(toIndentedString(seatType)).append("\n");
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
