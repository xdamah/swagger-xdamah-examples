package com.example.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Trip
 */


public class Trip   {

  public Trip() {
		super();

	}

private String tripName = null;


private List<BaseRequest> requests = null;

  public Trip tripName(String tripName) {
    this.tripName = tripName;
    return this;
  }

  /**
   * Get tripName
   * @return tripName
   **/

    public String getTripName() {
    return tripName;
  }

  public void setTripName(String tripName) {
    this.tripName = tripName;
  }

  public Trip requests(List<BaseRequest> requests) {
    this.requests = requests;
    return this;
  }

  public Trip addRequestsItem(BaseRequest requestsItem) {
    if (this.requests == null) {
      this.requests = new ArrayList<BaseRequest>();
    }
    this.requests.add(requestsItem);
    return this;
  }

  /**
   * Get requests
   * @return requests
   **/

    public List<BaseRequest> getRequests() {
    return requests;
  }

  public void setRequests(List<BaseRequest> requests) {
    this.requests = requests;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Trip trip = (Trip) o;
    return Objects.equals(this.tripName, trip.tripName) &&
        Objects.equals(this.requests, trip.requests);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tripName, requests);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Trip {\n");
    
    sb.append("    tripName: ").append(toIndentedString(tripName)).append("\n");
    sb.append("    requests: ").append(toIndentedString(requests)).append("\n");
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
