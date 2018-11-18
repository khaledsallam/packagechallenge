package com.mobiquityinc.packer;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mobiquity.exception.APIException;


/**
 * 
 * @author Khaled Sallam
 * 
 * Packer class responsible for determining which things to put into the package so that the 
 * total weight is less than or equal to the package limit and the total cost is as large as possible.
 * 
 */

public class Packer {
	
	private static final Logger logger = Logger.getLogger(Packer.class.getName()); 
	private static final int MAX_WEIGHT = 100;
	private static final int MAX_COST = 100;

	private Packer() {
		
	}
	
	public static String pack(String filePath) throws APIException {
		
		List<Package> packageList = new ArrayList<>();
		
		StringBuilder output = new StringBuilder("");
		
		try (FileInputStream fis = new FileInputStream(filePath);BufferedReader br = new BufferedReader(new InputStreamReader(fis))){	
			logger.log(Level.INFO,"Reading file from path: {0}",filePath);			
			String line = null;
			while ((line = br.readLine()) != null) {
				packageList.add(createPackageObj(line));
			}
	
		} catch (FileNotFoundException e) {			
			throw new APIException(APIException.FILE_NOT_FOUND);
		} catch (IOException e) {
			throw new APIException("Exception caught while reading the file");
		}
		
		for(Package p : packageList) {
			output.append(fillPackage(p)).append("\n");
		}
		
		logger.log(Level.INFO, "Output:\n{0}", output);
					
		
		return output.toString().trim();
		
	}
	
	
	private static Package createPackageObj(String line) throws APIException{
		
		String[] splittedLine = null;
		int weightLimit=0;
		try {
			splittedLine = line.split(":");
			int wl = Integer.parseInt(splittedLine[0].trim());
			weightLimit = wl > MAX_WEIGHT ? MAX_WEIGHT : wl;
		} catch (NumberFormatException e) {
			logger.log(Level.SEVERE,"Weight Limit should be a number",e);
			throw new APIException(APIException.INVALID_PARAMS);
		}
		
		Package pack = new Package(weightLimit);
		Pattern pattern = Pattern.compile("\\((?<item>.*?)\\)");
		Matcher matcher = pattern.matcher(splittedLine[1].trim());
		
		while (matcher.find()) {
			String[] itemStrArr = matcher.group("item").split(",");
			
			try {
				int indexNumber = Integer.parseInt(itemStrArr[0].trim()); 
				double weight = Double.parseDouble(itemStrArr[1].trim());
				int cost = Integer.parseInt(itemStrArr[2].substring(1).trim());
				
				if(weight > MAX_WEIGHT || cost > MAX_COST) {
					logger.log(Level.FINEST, "Item cost or weight is greater than expected. Hence, skipping item {0}" , itemStrArr);
					continue;
				}
				
				PackageItem packageItem = new PackageItem(indexNumber,weight,cost);
				pack.getItemsChoices().add(packageItem);	
			} catch(NumberFormatException e) {
				logger.log(Level.SEVERE, "indexNumber, weight, cost should be numbers {0}" , itemStrArr);
				throw new APIException(APIException.INVALID_PARAMS);
			}
		}
		return pack;			
	} 
	
	
	private static String fillPackage(Package p) {
		
		int weightLimit = p.getWeightLimit() * 100;
		
		int noOfItems = p.getItemsChoices().size();
		
		//Sort items by weight (ascendingly)
		Collections.sort(p.getItemsChoices());

		//store max cost at each n-th item
		int[][] matrix = new int [noOfItems+1][weightLimit + 1];
		
		//first line is initialized to 0
		for(int i=0;i<=weightLimit;i++) {
			matrix[0][i] = 0;
		}
		
		//iterate on items
		for(int i=1;i<=noOfItems;i++) {
			PackageItem item = p.getItemsChoices().get(i-1);
			//iterate on each capacity
			for (int j=0;j<=weightLimit;j++) {
				if(item.getWeight() * 100 > j) {
					matrix[i][j] = matrix[i-1][j];
				} else {
					matrix[i][j] = Math.max(matrix[i-1][j], matrix[i-1][j- (int) (item.getWeight() * 100)] + item.getCost());
				}
			}
		}
		
		int maxCost = matrix[noOfItems][weightLimit];		
		
		String packedItemsStr = "-";
		
		for(int i = noOfItems; i > 0 && maxCost > 0; i--) {
			if(maxCost != matrix[i-1][weightLimit]) {
				PackageItem itemToCheck = p.getItemsChoices().get(i-1);
				
				packedItemsStr = packedItemsStr.equals("-") ? ""+itemToCheck.getIndexNumber() : packedItemsStr + "," + itemToCheck.getIndexNumber();
				
				maxCost -= itemToCheck.getCost();
				weightLimit -= itemToCheck.getWeight() * 100;
			}
		}
		
		return packedItemsStr;
	}

}
