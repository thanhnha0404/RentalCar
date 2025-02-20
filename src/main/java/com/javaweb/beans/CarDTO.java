package com.javaweb.beans;

import java.util.Date;
import java.util.List;


public class CarDTO {
	private int id;
	private String name;	
	private String description;
	private String status;
	private byte[] picture; 
	private String indentify;
	private int addressCarId;
	private Date dateOfStart;
    private int brandId;
    private int staffQuanLiId;
	private int price;
	private List<Integer> contractIds;
    private List<Integer> maintenanceBillIds;
    
    
	public int getAddressCarId() {
		return addressCarId;
	}
	public void setAddressCarId(int addressCarId) {
		this.addressCarId = addressCarId;
	}
	public int getBrandId() {
		return brandId;
	}
	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public byte[] getPicture() {
		return picture;
	}
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	public String getIndentify() {
		return indentify;
	}
	public void setIndentify(String indentify) {
		this.indentify = indentify;
	}
	public Date getDateOfStart() {
		return dateOfStart;
	}
	public void setDateOfStart(Date dateOfStart) {
		this.dateOfStart = dateOfStart;
	}
	public int getStaffQuanLiId() {
		return staffQuanLiId;
	}
	public void setStaffQuanLiId(int staffQuanLiId) {
		this.staffQuanLiId = staffQuanLiId;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public List<Integer> getContractIds() {
		return contractIds;
	}
	public void setContractIds(List<Integer> contractIds) {
		this.contractIds = contractIds;
	}
	public List<Integer> getMaintenanceBillIds() {
		return maintenanceBillIds;
	}
	public void setMaintenanceBillIds(List<Integer> maintenanceBillIds) {
		this.maintenanceBillIds = maintenanceBillIds;
	}
}
