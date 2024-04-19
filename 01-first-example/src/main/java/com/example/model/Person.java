package com.example.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.custom.SampleCustomType;


public class Person   {

  private Long id = null;


  private String firstName = null;


  private String lastName = null;


  private String email = null;

  private String email1 = null;

  private Integer age = null;


  private String creditCardNumber = null;

 
  private LocalDate registrationDate = null;


  private byte[] pic = null;

  
  private List<byte[]> pics = null;

  
  private SampleCustomType sampleCustomTypeData = null;


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

  public Person email1(String email1) {
    this.email1 = email1;
    return this;
  }

    public String getEmail1() {
    return email1;
  }

  public void setEmail1(String email1) {
    this.email1 = email1;
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

  public Person creditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
    return this;
  }


    public String getCreditCardNumber() {
    return creditCardNumber;
  }

  public void setCreditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
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

  public Person sampleCustomTypeData(SampleCustomType sampleCustomTypeData) {
    this.sampleCustomTypeData = sampleCustomTypeData;
    return this;
  }


    public SampleCustomType getSampleCustomTypeData() {
    return sampleCustomTypeData;
  }

  public void setSampleCustomTypeData(SampleCustomType sampleCustomTypeData) {
    this.sampleCustomTypeData = sampleCustomTypeData;
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
        Objects.equals(this.email1, person.email1) &&
        Objects.equals(this.age, person.age) &&
        Objects.equals(this.creditCardNumber, person.creditCardNumber) &&
        Objects.equals(this.registrationDate, person.registrationDate) &&
        Objects.equals(this.pic, person.pic) &&
        Objects.equals(this.pics, person.pics) &&
        Objects.equals(this.sampleCustomTypeData, person.sampleCustomTypeData) &&
        Objects.equals(this.someTimeData, person.someTimeData) &&
        Objects.equals(this.anotherPerson, person.anotherPerson) &&
        Objects.equals(this.children, person.children);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, email, email1, age, creditCardNumber, registrationDate, pic, pics, sampleCustomTypeData, someTimeData, anotherPerson, children);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Person {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    email1: ").append(toIndentedString(email1)).append("\n");
    sb.append("    age: ").append(toIndentedString(age)).append("\n");
    sb.append("    creditCardNumber: ").append(toIndentedString(creditCardNumber)).append("\n");
    sb.append("    registrationDate: ").append(toIndentedString(registrationDate)).append("\n");
    sb.append("    pic: ").append(toIndentedString(pic)).append("\n");
    sb.append("    pics: ").append(toIndentedString(pics)).append("\n");
    sb.append("    sampleCustomTypeData: ").append(toIndentedString(sampleCustomTypeData)).append("\n");
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
