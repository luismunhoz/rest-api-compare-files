package br.com.luismunhoz.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

import br.com.luismunhoz.exception.FileException;
import br.com.luismunhoz.model.BinaryFileDifference;
import br.com.luismunhoz.model.FileDifference;

public class BinaryFileCompare implements FileCompare {
	
	@Value("${application.fileLocation}")
	String fileLocation;
	
	private static final Log logger = LogFactory.getLog(BinaryFileCompare.class);	

	@Override
	public FileDifference compare(String id) throws FileException {
        long start = System.nanoTime();
        BinaryFileDifference fileDiffs = new BinaryFileDifference();
        List<Integer> diffs = new ArrayList<Integer>();
        BufferedInputStream fis1 = null;
        BufferedInputStream fis2 = null;
		try {
			fis1 = new BufferedInputStream(new FileInputStream(fileLocation + System.getProperty("file.separator") +"left_" + id));
			fis2 = new BufferedInputStream(new FileInputStream(fileLocation + System.getProperty("file.separator") +"right_" + id));

	        int b1 = 0, b2 = 0, pos = 1;
	        while (b1 != -1 && b2 != -1) {
	            if (b1 != b2) {
	            	diffs.add(pos);
	            }
	            pos++;
	            b1 = fis1.read();
	            b2 = fis2.read();
	        }
	        fileDiffs.setDiffs(diffs);
	        if (b1 != b2) {
	        	fileDiffs.setSize("Files have different length");
	        } else {
	        	fileDiffs.setSize("Files have same length");
	        }
	        fis1.close();
	        fis2.close();
	        long end = System.nanoTime();
	        logger.info("Execution time: " + (end - start)/1000000 + "ms");
		} catch (IOException e) {
			throw new FileException("Erro", e);
		}

		return fileDiffs;
	}

}
