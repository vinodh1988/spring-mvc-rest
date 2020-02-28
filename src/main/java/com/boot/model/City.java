package com.boot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class City {
  @Id
  private Integer id;
  @Column
  private String name;
  @Column
  private String countrycode;
  @Column
  private String district;
  @Column
  private  Integer population;
  
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getCountrycode() {
	return countrycode;
}
public void setCountrycode(String countryCode) {
	this.countrycode = countryCode;
}
public String getDistrict() {
	return district;
}
public void setDistrict(String district) {
	this.district = district;
}
public Integer getPopulation() {
	return population;
}
public void setPopulation(Integer population) {
	this.population = population;
}
  
  
  
}
