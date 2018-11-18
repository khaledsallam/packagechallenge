package com.mobiquityinc.packer;

import java.util.Objects;

public class PackageItem implements Comparable<PackageItem>{
	
	private int indexNumber;
	private double weight;
	private int cost;
	
	
	public PackageItem(int indexNumber,double weight,int cost) {
		this.indexNumber = indexNumber;
		this.weight = weight;
		this.cost = cost;
	}
	/**
	 * @return the indexNumber
	 */
	public int getIndexNumber() {
		return indexNumber;
	}
	
	/**
	 * @param indexNumber the indexNumber to set
	 */
	public void setIndexNumber(int indexNumber) {
		this.indexNumber = indexNumber;
	}
	
	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
	/**
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}
	/**
	 * @param cost the cost to set
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	
	@Override
	public String toString() {
		return "indexNumber: " + this.indexNumber +", weight: "+ this.weight + ", cost: "+ this.cost;
	}
	
	@Override
	public int compareTo(PackageItem packageItem) {
		//ascending order		
		return (int)(this.weight * 100 - packageItem.getWeight() * 100);
	}
	
   @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof PackageItem)) {
            return false;
        }

        PackageItem packageItem = (PackageItem) o;

        return this.indexNumber == packageItem.indexNumber && this.weight == packageItem.weight &&
        		this.cost == packageItem.cost;
    }

    @Override
    public int hashCode() {
        return Objects.hash(indexNumber, weight, cost);
    }

	
	
	
}
