package com.example.model;

import java.util.Objects;
import com.example.model.Trip;

import java.util.List;

/**
 * StoredTrip
 */

public class StoredTrip extends Trip  {

  private Long tripId = null;

  public StoredTrip tripId(Long tripId) {
    this.tripId = tripId;
    return this;
  }

  /**
   * Get tripId
   * @return tripId
   **/

    public Long getTripId() {
    return tripId;
  }

  public void setTripId(Long tripId) {
    this.tripId = tripId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StoredTrip storedTrip = (StoredTrip) o;
    return Objects.equals(this.tripId, storedTrip.tripId) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tripId, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StoredTrip {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    tripId: ").append(toIndentedString(tripId)).append("\n");
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
