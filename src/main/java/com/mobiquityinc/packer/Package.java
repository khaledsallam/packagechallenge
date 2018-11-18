package com.mobiquityinc.packer;

import java.util.ArrayList;
import java.util.List;

public class Package {
	private int weightLimit;
	private List<PackageItem> itemsChoices;
	
	public Package() {
		itemsChoices = new ArrayList<>();
	}
	
	public Package(int weightLimit) {
		itemsChoices = new ArrayList<>();
		this.weightLimit = weightLimit;
	}

	/**
	 * @return the weightLimit
	 */
	public int getWeightLimit() {
		return weightLimit;
	}

	/**
	 * @param weightLimit the weightLimit to set
	 */
	public void setWeightLimit(int weightLimit) {
		this.weightLimit = weightLimit;	
	}

	/**
	 * @return the list of items choices
	 */
	public List<PackageItem> getItemsChoices() {
		return itemsChoices;
	}

	/**
	 * @param itemsChoices the items choices to set
	 */
	public void setItemsChoices(List<PackageItem> itemsChoices) {
		this.itemsChoices = itemsChoices;
	}
	
}
