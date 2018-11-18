package com.mobiquityinc.packer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.mobiquity.exception.APIException;

class PackerTests {
	
	@Test
	void test_FileNotFound() throws APIException {
		Executable closureToTest = ()-> Packer.pack("filenotfound");
		assertThrows(APIException.class,closureToTest);
	}
	
	@Test
	void test_inputWithValuesAboveMax() throws APIException {
		String expectedResult = "1";
		String result = Packer.pack("src/test/resources/valuesabovemax.txt");
		assertEquals(expectedResult, result);
	}
	
	@Test
	void test_invalidWeightLimit() throws APIException {
		Executable closureToTest = ()-> Packer.pack("src/test/resources/invalidweightlimit.txt");
		assertThrows(APIException.class,closureToTest);
	}
	
	@Test
	void test_invalidItemData() throws APIException {
		Executable closureToTest = ()-> Packer.pack("src/test/resources/invaliddatatype.txt");
		assertThrows(APIException.class,closureToTest);
	}
	
	
	@Test
	void test_success() throws APIException {
		String expectedResult = "4\n-\n7,2\n8,9";
		String result = Packer.pack("src/test/resources/testinput.txt");
		assertEquals(expectedResult, result);
	}

}
