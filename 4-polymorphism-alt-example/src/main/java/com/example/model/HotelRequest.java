package com.example.model;

import java.util.Objects;
import com.example.model.BaseRequest;

import java.time.OffsetDateTime;

/**
 * HotelRequest
 */


public class HotelRequest extends BaseRequest implements AddrequestTripidBody, OneOfTripRequestsItems {

  private String hotelName = null;


  private String roomType = null;

  private String location = null;

  public HotelRequest hotelName(String hotelName) {
    this.hotelName = hotelName;
    return this;
  }

  /**
   * Get hotelName
   * @return hotelName
   **/

    public String getHotelName() {
    return hotelName;
  }

  public void setHotelName(String hotelName) {
    this.hotelName = hotelName;
  }

  public HotelRequest roomType(String roomType) {
    this.roomType = roomType;
    return this;
  }

  /**
   * Get roomType
   * @return roomType
   **/

    public String getRoomType() {
    return roomType;
  }

  public void setRoomType(String roomType) {
    this.roomType = roomType;
  }

  public HotelRequest location(String location) {
    this.location = location;
    return this;
  }

  /**
   * Get location
   * @return location
   **/

    public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HotelRequest hotelRequest = (HotelRequest) o;
    return Objects.equals(this.hotelName, hotelRequest.hotelName) &&
        Objects.equals(this.roomType, hotelRequest.roomType) &&
        Objects.equals(this.location, hotelRequest.location) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hotelName, roomType, location, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HotelRequest {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    hotelName: ").append(toIndentedString(hotelName)).append("\n");
    sb.append("    roomType: ").append(toIndentedString(roomType)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
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
