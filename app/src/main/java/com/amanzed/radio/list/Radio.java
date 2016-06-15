package com.amanzed.radio.list;

import java.io.Serializable;
import java.util.Comparator;

public class Radio implements Serializable{
	Integer id; String name; String url; String website; String about; String country;
	public Radio(){

	}
	public Radio(Integer id, String name, String url, String website, String about, String country){
		this.id = id;
		this.name = name;
		this.url = url;
		this.website = website;
		this.about = about;
		this.country = country;				
	}
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	public static Comparator<Radio> RadioNameComparator 
	= new Comparator<Radio>() {

		public int compare(Radio radio1, Radio radio2) {

			String radioName1 = radio1.getName().toUpperCase();
			String radioName2 = radio2.getName().toUpperCase();

			//ascending order
			return radioName1.compareTo(radioName2);

			//descending order
			//return radioName2.compareTo(radioName1);
		}

	};
}
