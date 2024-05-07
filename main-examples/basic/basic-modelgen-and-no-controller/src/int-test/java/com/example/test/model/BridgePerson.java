package com.example.test.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * OtherPerson
 */

@jakarta.annotation.Generated(value = "io.github.xdamah.codegen.XDamahCodeGen", date = "2024-01-28T15:54:07.306359500+05:30[Asia/Calcutta]")


public class BridgePerson   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("firstName")
  private String firstName = null;

  @JsonProperty("lastName")
  private String lastName = null;

  @JsonProperty("email")
  private String email = null;



  @JsonProperty("age")
  private Integer age = null;



  @JsonProperty("registrationDate")
  private String registrationDate = null;

  @JsonProperty("pic")
  private byte[] pic = null;

  @JsonProperty("pics")
  
  private List<byte[]> pics = null;



  @JsonProperty("someTimeData")
  private String someTimeData = null;

  @JsonProperty("anotherPerson")
  private BridgePerson anotherPerson = null;

  @JsonProperty("children")
  
  private List<BridgePerson> children = null;

  public BridgePerson id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(description = "")
    public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BridgePerson firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * Get firstName
   * @return firstName
   **/
  @Schema(description = "")
    public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public BridgePerson lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Get lastName
   * @return lastName
   **/
  @Schema(required = true, description = "")
    public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public BridgePerson email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   **/
  @Schema(description = "")
    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }





  public BridgePerson age(Integer age) {
    this.age = age;
    return this;
  }

  /**
   * Get age
   * minimum: 18
   * maximum: 30
   * @return age
   **/
  @Schema(description = "")
    public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  

  public BridgePerson registrationDate(String registrationDate) {
    this.registrationDate = registrationDate;
    return this;
  }

  /**
   * Get registrationDate
   * @return registrationDate
   **/
  @Schema(description = "")
    public String getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(String registrationDate) {
    this.registrationDate = registrationDate;
  }

  public BridgePerson pic(byte[] pic) {
    this.pic = pic;
    return this;
  }

  /**
   * Get pic
   * @return pic
   **/
  @Schema(description = "")
    public byte[] getPic() {
    return pic;
  }

  public void setPic(byte[] pic) {
    this.pic = pic;
  }

  public BridgePerson pics(List<byte[]> pics) {
    this.pics = pics;
    return this;
  }

  public BridgePerson addPicsItem(byte[] picsItem) {
    if (this.pics == null) {
      this.pics = new ArrayList<byte[]>();
    }
    this.pics.add(picsItem);
    return this;
  }

  /**
   * Get pics
   * @return pics
   **/
  @Schema(description = "")
    public List<byte[]> getPics() {
    return pics;
  }

  public void setPics(List<byte[]> pics) {
    this.pics = pics;
  }

  

  public BridgePerson someTimeData(String someTimeData) {
    this.someTimeData = someTimeData;
    return this;
  }

  /**
   * Get someTimeData
   * @return someTimeData
   **/
  @Schema(description = "")
    public String getSomeTimeData() {
    return someTimeData;
  }

  public void setSomeTimeData(String someTimeData) {
    this.someTimeData = someTimeData;
  }

  public BridgePerson anotherPerson(BridgePerson anotherPerson) {
    this.anotherPerson = anotherPerson;
    return this;
  }

  /**
   * Get anotherPerson
   * @return anotherPerson
   **/
  @Schema(description = "")
    public BridgePerson getAnotherPerson() {
    return anotherPerson;
  }

  public void setAnotherPerson(BridgePerson anotherPerson) {
    this.anotherPerson = anotherPerson;
  }

  public BridgePerson children(List<BridgePerson> children) {
    this.children = children;
    return this;
  }

  public BridgePerson addChildrenItem(BridgePerson childrenItem) {
    if (this.children == null) {
      this.children = new ArrayList<BridgePerson>();
    }
    this.children.add(childrenItem);
    return this;
  }

  /**
   * Get children
   * @return children
   **/
  @Schema(description = "")
    public List<BridgePerson> getChildren() {
    return children;
  }

  public void setChildren(List<BridgePerson> children) {
    this.children = children;
  }


 


  @Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + Arrays.hashCode(pic);
	result = prime * result + Objects.hash(age, anotherPerson, children,  email, firstName, id,
			lastName, pics, registrationDate,  someTimeData);
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	BridgePerson other = (BridgePerson) obj;
	return Objects.equals(age, other.age) && Objects.equals(anotherPerson, other.anotherPerson)
			&& Objects.equals(children, other.children) 
			&& Objects.equals(email, other.email) 
			&& Objects.equals(firstName, other.firstName) && Objects.equals(id, other.id)
			&& Objects.equals(lastName, other.lastName) && Arrays.equals(pic, other.pic)
			&& Objects.equals(pics, other.pics) && Objects.equals(registrationDate, other.registrationDate)

			&& Objects.equals(someTimeData, other.someTimeData);
}

@Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OtherPerson {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
   
    sb.append("    age: ").append(toIndentedString(age)).append("\n");
    
    sb.append("    registrationDate: ").append(toIndentedString(registrationDate)).append("\n");
    sb.append("    pic: ").append(toIndentedString(pic)).append("\n");
    sb.append("    pics: ").append(toIndentedString(pics)).append("\n");

    sb.append("    someTimeData: ").append(toIndentedString(someTimeData)).append("\n");
    sb.append("    anotherPerson: ").append(toIndentedString(anotherPerson)).append("\n");
    sb.append("    children: ").append(toIndentedString(children)).append("\n");
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
