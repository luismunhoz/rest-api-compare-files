package br.com.luismunhoz.util;

import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.luismunhoz.CompareFilesApplication;
import br.com.luismunhoz.exception.FileException;
import br.com.luismunhoz.model.FileSide;
import br.com.luismunhoz.model.FilesToCompare;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={CompareFilesApplication.class})
public class DiskFileManagerTest {
	
	@Autowired
	DiskFileManager target;
			
	@Test
	public void saveFileTest() throws Exception {
		
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("shakespeare.txt");
		
		target.saveFile("test_id", FileSide.LEFT, inputStream);
		
		assertTrue(target.haveFile("test_id", FileSide.LEFT));
		
		inputStream.close();
	}
	
	@Test
	public void loadFileTest() throws Exception {
		
		InputStream inputStream = DiskFileManagerTest.class
		          .getResourceAsStream("/shakespeare.txt");
		target.saveFile("test_id", FileSide.LEFT, inputStream);
		inputStream.close();
		
		inputStream = DiskFileManagerTest.class
		          .getResourceAsStream("/shakespeare.txt");
		target.saveFile("test_id", FileSide.RIGHT, inputStream);
		inputStream.close();
		
		FilesToCompare files = target.loadFile("test_id");
		
		assertTrue(DiskFileManagerTest.contentEquals(files.getLeftFile(), files.getRightFile()));
		System.out.println(files.toString());
		
	}
	
	@Test(expected = FileException.class)
	public void loadFileTestException() {

		FilesToCompare files = target.loadFile("test_nonono");
		System.out.println(files.toString());
	}

	@Test
	public void haveFileTest() {
		
		InputStream inputStream = DiskFileManagerTest.class
		          .getResourceAsStream("/shakespeare.txt");	

		target.saveFile("test_id", FileSide.LEFT, inputStream);
		assertTrue(target.haveFile("test_id",FileSide.LEFT));
	}
	
	 public static boolean contentEquals(InputStream input1, InputStream input2) throws IOException
	  {
	    if (!(input1 instanceof BufferedInputStream))
	    {
	      input1 = new BufferedInputStream(input1);
	    }
	    if (!(input2 instanceof BufferedInputStream))
	    {
	      input2 = new BufferedInputStream(input2);
	    }

	    int ch = input1.read();
	    while (-1 != ch)
	    {
	      int ch2 = input2.read();
	      if (ch != ch2)
	      {
	        return false;
	      }
	      ch = input1.read();
	    }

	    int ch2 = input2.read();
	    return (ch2 == -1);
	  }	

}
