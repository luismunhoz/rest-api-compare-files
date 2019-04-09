package br.com.luismunhoz.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.luismunhoz.CompareFilesApplication;
import br.com.luismunhoz.model.FileDifference;
import br.com.luismunhoz.model.TextFileDifference;


@RunWith(SpringRunner.class)
@SpringBootTest(classes={CompareFilesApplication.class})
public class FileCompareServiceTest {
	
	@Autowired
	FileCompareService fileCompareService;

	@Test
	public void testTextsAreEqual() throws Exception {
		  InputStream leftTest = FileCompareServiceTest.class.getResourceAsStream("/shakespeare_b64.txt");
		  fileCompareService.uploadLeftFile("test_id", leftTest);
		  leftTest.close();
		  
		  InputStream rightTest = FileCompareServiceTest.class.getResourceAsStream("/shakespeare_b64.txt");
		  fileCompareService.uploadRightFile("test_id", rightTest);
		  rightTest.close();
		  
		  FileDifference response = fileCompareService.compareFiles("test_id");
		  assertThat(response.getStatus(), is("Files are equal."));
	}
	
	@Test
	public void testTextsAreDifferents() throws Exception {
		  InputStream leftTest = FileCompareServiceTest.class.getResourceAsStream("/textoBigger_b64.txt");
		  fileCompareService.uploadLeftFile("test_id", leftTest);
		  leftTest.close();
		  
		  InputStream rightTest = FileCompareServiceTest.class.getResourceAsStream("/textoSmall_b64.txt");
		  fileCompareService.uploadRightFile("test_id", rightTest);
		  rightTest.close();
		  
		  FileDifference response = fileCompareService.compareFiles("test_id");
		  assertThat(response.getStatus(), is("Files are different."));
		  
		  assertThat(((TextFileDifference)response).getLines().get(0).getNumber(), is(3));
		  assertThat(((TextFileDifference)response).getLines().get(0).getLeftLine(), is("Unlearned in the world’s false subtleties."));
		  System.out.println(((TextFileDifference)response).getLines().get(0).toString());	  
	}

	@Test
	public void testImageAreEqual() throws Exception {
		  InputStream leftTest = FileCompareServiceTest.class.getResourceAsStream("/image-test-1.jpg.base64.txt");
		  fileCompareService.uploadLeftFile("test_id", leftTest);
		  leftTest.close();
		  
		  InputStream rightTest = FileCompareServiceTest.class.getResourceAsStream("/image-test-1.jpg.base64.txt");
		  fileCompareService.uploadRightFile("test_id", rightTest);
		  rightTest.close();
		  
		  FileDifference response = fileCompareService.compareFiles("test_id");
		  assertThat(response.getStatus(), is("Files are equal"));
		  System.out.println(((FileDifference)response).toString());
	}
	

}
