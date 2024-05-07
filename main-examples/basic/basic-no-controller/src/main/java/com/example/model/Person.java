package com.example.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class Person   {

  private Long id = null;

  @NotNull

  @NotBlank

  @Size(min = 2, max = 20, message = "firstname has size limits")
  private String firstName = null;

  @NotNull

      @NotBlank

      @Size(min = 2)
  private String lastName = null;


  @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")

      private String email;
  
      
  @Min(18)
  
  @Max(30)
  private Integer age ;

 

 
  private LocalDate registrationDate = null;


  private byte[] pic = null;

  
  private List<byte[]> pics = null;

  



  private OffsetDateTime someTimeData = null;


  private Person anotherPerson = null;


  
  private List<Person> children = null;

  public Person id(Long id) {
    this.id = id;
    return this;
  }


    public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Person firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

    public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public Person lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

 
    public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Person email(String email) {
    this.email = email;
    return this;
  }


    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

 

  public Person age(Integer age) {
    this.age = age;
    return this;
  }


    public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  

  public Person registrationDate(LocalDate registrationDate) {
    this.registrationDate = registrationDate;
    return this;
  }


    public LocalDate getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(LocalDate registrationDate) {
    this.registrationDate = registrationDate;
  }

  public Person pic(byte[] pic) {
    this.pic = pic;
    return this;
  }


    public byte[] getPic() {
    return pic;
  }

  public void setPic(byte[] pic) {
    this.pic = pic;
  }

  public Person pics(List<byte[]> pics) {
    this.pics = pics;
    return this;
  }

  public Person addPicsItem(byte[] picsItem) {
    if (this.pics == null) {
      this.pics = new ArrayList<byte[]>();
    }
    this.pics.add(picsItem);
    return this;
  }

 
    public List<byte[]> getPics() {
    return pics;
  }

  public void setPics(List<byte[]> pics) {
    this.pics = pics;
  }

  

  public Person someTimeData(OffsetDateTime someTimeData) {
    this.someTimeData = someTimeData;
    return this;
  }


    public OffsetDateTime getSomeTimeData() {
    return someTimeData;
  }

  public void setSomeTimeData(OffsetDateTime someTimeData) {
    this.someTimeData = someTimeData;
  }

  public Person anotherPerson(Person anotherPerson) {
    this.anotherPerson = anotherPerson;
    return this;
  }

    public Person getAnotherPerson() {
    return anotherPerson;
  }

  public void setAnotherPerson(Person anotherPerson) {
    this.anotherPerson = anotherPerson;
  }

  public Person children(List<Person> children) {
    this.children = children;
    return this;
  }

  public Person addChildrenItem(Person childrenItem) {
    if (this.children == null) {
      this.children = new ArrayList<Person>();
    }
    this.children.add(childrenItem);
    return this;
  }


    public List<Person> getChildren() {
    return children;
  }

  public void setChildren(List<Person> children) {
    this.children = children;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Person person = (Person) o;
    return Objects.equals(this.id, person.id) &&
        Objects.equals(this.firstName, person.firstName) &&
        Objects.equals(this.lastName, person.lastName) &&
        Objects.equals(this.email, person.email) &&

        Objects.equals(this.age, person.age) &&

        Objects.equals(this.registrationDate, person.registrationDate) &&
        Objects.equals(this.pic, person.pic) &&
        Objects.equals(this.pics, person.pics) &&

        Objects.equals(this.someTimeData, person.someTimeData) &&
        Objects.equals(this.anotherPerson, person.anotherPerson) &&
        Objects.equals(this.children, person.children);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, email, age, registrationDate, pic, pics, someTimeData, anotherPerson, children);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Person {\n");
    
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
