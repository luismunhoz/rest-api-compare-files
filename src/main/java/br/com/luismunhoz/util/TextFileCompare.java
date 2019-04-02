package br.com.luismunhoz.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

import br.com.luismunhoz.exception.FileException;
import br.com.luismunhoz.model.FileDifference;
import br.com.luismunhoz.model.LineDifference;
import br.com.luismunhoz.model.TextFileDifference;

public class TextFileCompare implements FileCompare {

	@Value("${application.fileLocation}")
	String fileLocation;

	private static final Log logger = LogFactory.getLog(BinaryFileCompare.class);

	@Override
	public FileDifference compare(String id) throws FileException {
		long start = System.nanoTime();
		TextFileDifference fileDiffs = new TextFileDifference();
		List<LineDifference> diffs = new ArrayList<LineDifference>();
		String fileLeftPath = fileLocation + System.getProperty("file.separator") + "left_" + id;
		String fileRightPath = fileLocation + System.getProperty("file.separator") + "right_" + id;
		
		File left = new File(fileLeftPath);
		File right = new File(fileRightPath);

		try (BufferedReader brLeft = new BufferedReader(new FileReader(left));
			 BufferedReader brRight = new BufferedReader(new FileReader(right))) {

			String lineLeft = null;
			String lineRight = null;
			int line = 0;
			while((lineLeft = brLeft.readLine()) != null || (lineRight = brRight.readLine()) != null) {
				if(!lineLeft.equals(lineRight)) {
					System.out.println("Line:"+line);
					System.out.println("Left:"+lineLeft);
					System.out.println("Right:"+lineRight);
				}
				line++;
			}
						
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		long end = System.nanoTime();
		logger.info("Execution time: " + (end - start) / 1000000 + "ms");

		return fileDiffs;

	}

}
