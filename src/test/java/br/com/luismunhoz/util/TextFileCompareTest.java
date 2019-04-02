package br.com.luismunhoz.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;

import br.com.luismunhoz.model.TextFileDifference;

@PropertySource("classpath:application.properties")
public class TextFileCompareTest {

	@Test
	public void test() {
		TextFileCompare target = new TextFileCompare();
		target.compare("1234");
		
	}

}
