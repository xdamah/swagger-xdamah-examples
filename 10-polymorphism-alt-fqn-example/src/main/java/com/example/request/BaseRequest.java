package com.example.request;

import java.time.OffsetDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.validation.constraints.NotNull;

@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME,
		  include = JsonTypeInfo.As.PROPERTY,
		  property = "type")
		@JsonSubTypes({
		  @JsonSubTypes.Type(value = FlightRequest.class, name = "FlightRequest"),
		  @JsonSubTypes.Type(value = CarRequest.class, name = "CarRequest"),
		  @JsonSubTypes.Type(value = HotelRequest.class, name = "HotelRequest")
		})
public class BaseRequest   {
@NotNull
  private String type = null;


  private OffsetDateTime startDateTime = null;


  private OffsetDateTime endDateTime = null;

  public BaseRequest type(String type) {
    this.type = type;
    return this;
  }

 
    public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public BaseRequest startDateTime(OffsetDateTime startDateTime) {
    this.startDateTime = startDateTime;
    return this;
  }


    public OffsetDateTime getStartDateTime() {
    return startDateTime;
  }

  public void setStartDateTime(OffsetDateTime startDateTime) {
    this.startDateTime = startDateTime;
  }

  public BaseRequest endDateTime(OffsetDateTime endDateTime) {
    this.endDateTime = endDateTime;
    return this;
  }


    public OffsetDateTime getEndDateTime() {
    return endDateTime;
  }

  public void setEndDateTime(OffsetDateTime endDateTime) {
    this.endDateTime = endDateTime;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BaseRequest baseRequest = (BaseRequest) o;
    return Objects.equals(this.type, baseRequest.type) &&
        Objects.equals(this.startDateTime, baseRequest.startDateTime) &&
        Objects.equals(this.endDateTime, baseRequest.endDateTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, startDateTime, endDateTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BaseRequest {\n");
    
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    startDateTime: ").append(toIndentedString(startDateTime)).append("\n");
    sb.append("    endDateTime: ").append(toIndentedString(endDateTime)).append("\n");
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
