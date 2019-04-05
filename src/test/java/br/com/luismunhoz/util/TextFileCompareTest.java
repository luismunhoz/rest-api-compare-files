package br.com.luismunhoz.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.luismunhoz.CompareFilesApplication;
import br.com.luismunhoz.model.FileDifference;
import br.com.luismunhoz.model.FileSide;
import br.com.luismunhoz.model.TextFileDifference;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={CompareFilesApplication.class})
public class TextFileCompareTest {
	
	@Autowired
	DiskFileManager diskFileManager;
	
	@Autowired
	TextFileCompare target;
		
	@Test
	public void testWithEqualFiles() {
		InputStream inputStream = DiskFileManagerTest.class
		          .getResourceAsStream("/shakespeare.txt");
		diskFileManager.saveFile("test_id", FileSide.LEFT, inputStream);
		
		inputStream = DiskFileManagerTest.class
		          .getResourceAsStream("/shakespeare.txt");
		diskFileManager.saveFile("test_id", FileSide.RIGHT, inputStream);
		
		FileDifference fileDifferences = target.compare("test_id");
		assertThat(fileDifferences.getStatus(),is("Files are equal."));		
	}
	
	@Test
	public void testWithLeftFileBigger() {
		InputStream inputStream = DiskFileManagerTest.class
		          .getResourceAsStream("/shakespeareBigger.txt");
		diskFileManager.saveFile("test_id", FileSide.LEFT, inputStream);
		
		inputStream = DiskFileManagerTest.class
		          .getResourceAsStream("/shakespeare.txt");
		diskFileManager.saveFile("test_id", FileSide.RIGHT, inputStream);
		
		FileDifference fileDifferences = target.compare("test_id");
		
		assertThat(fileDifferences.getStatus(),is("Files are different."));		
		assertThat(((TextFileDifference)fileDifferences).getLines().size(), is(3));
		assertThat(((TextFileDifference)fileDifferences).getLines().get(0).getLeftLine(), is("more lines - one"));		
		System.out.println(fileDifferences.toString());
		System.out.println(((TextFileDifference)fileDifferences).toString());
		System.out.println(((TextFileDifference)fileDifferences).getLines().get(0).toString());
	}
	
	@Test
	public void testWithRightFileBigger() {
		InputStream inputStream = DiskFileManagerTest.class
		          .getResourceAsStream("/shakespeare.txt");
		diskFileManager.saveFile("test_id", FileSide.LEFT, inputStream);
		
		inputStream = DiskFileManagerTest.class
		          .getResourceAsStream("/shakespeareBigger.txt");
		diskFileManager.saveFile("test_id", FileSide.RIGHT, inputStream);
		
		FileDifference fileDifferences = target.compare("test_id");

		assertThat(fileDifferences.getStatus(),is("Files are different."));		
		assertThat(((TextFileDifference)fileDifferences).getLines().size(), is(3));
		assertThat(((TextFileDifference)fileDifferences).getLines().get(0).getRightLine(), is("more lines - one"));
	}
	
	

}
