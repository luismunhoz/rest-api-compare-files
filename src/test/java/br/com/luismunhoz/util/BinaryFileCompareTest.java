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

@RunWith(SpringRunner.class)
@SpringBootTest(classes={CompareFilesApplication.class})
public class BinaryFileCompareTest {
	
	@Autowired
	DiskFileManager diskFileManager;
	
	@Autowired
	BinaryFileCompare target;
	
	@Before
	public void setup() {
		
		InputStream inputStream = DiskFileManagerTest.class
		          .getResourceAsStream("/shakespeare.txt");
		diskFileManager.saveFile("test_id", FileSide.LEFT, inputStream);
		
		inputStream = DiskFileManagerTest.class
		          .getResourceAsStream("/shakespeare.txt");
		diskFileManager.saveFile("test_id", FileSide.RIGHT, inputStream);
	}

	@Test
	public void test() {
		FileDifference response = target.compare("test_id");	
		assertThat(response.getStatus(), is("Files are equal"));
	}

}
