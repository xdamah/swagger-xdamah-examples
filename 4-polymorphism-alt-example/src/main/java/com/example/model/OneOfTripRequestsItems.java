package com.example.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* OneOfTripRequestsItems
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = FlightRequest.class, name = "FlightRequest"),
  @JsonSubTypes.Type(value = CarRequest.class, name = "CarRequest"),
  @JsonSubTypes.Type(value = HotelRequest.class, name = "HotelRequest")
})
public interface OneOfTripRequestsItems {

}
