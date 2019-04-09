package br.com.luismunhoz.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.luismunhoz.exception.FileException;
import br.com.luismunhoz.model.BinaryFileDifference;
import br.com.luismunhoz.model.DiffOffSet;
import br.com.luismunhoz.model.FileDifference;
import br.com.luismunhoz.model.FilesToCompare;

@Component
public class BinaryFileCompare extends FileCompare {	
	@Value("${application.fileLocation}")
	String fileLocation;
	
	@Value("${application.shouldFindDifferencesToDifferentSizes}")
	Boolean shouldFindDifferences;
	
	@Value("${message.resultFilesHaveDifferentLength}")
	String resultFilesHaveDifferentLength;
	
	@Value("${message.resultFilesAreEqual}")
	String resultFilesAreEqual;
	
	@Value("${message.resultFilesHaveSameLength}")
	String resultFilesHaveSameLength;
	
	@Autowired
	FileManager fileManager;
	
	private static final Log logger = LogFactory.getLog(BinaryFileCompare.class);	

	@Override
	public FileDifference compare(String id) throws FileException {
        long start = System.nanoTime();
        BinaryFileDifference fileDiffs = new BinaryFileDifference();
        List<DiffOffSet> diffs = new ArrayList<DiffOffSet>();
		try {
			
			FilesToCompare files = fileManager.loadFile(id);
			InputStream fis1 = files.getLeftFile();
			InputStream fis2 = files.getRightFile();
			
		    if (!(fis1 instanceof BufferedInputStream))
		    {
		    	fis1 = new BufferedInputStream(fis1);
		    }
		    if (!(fis2 instanceof BufferedInputStream))
		    {
		    	fis2 = new BufferedInputStream(fis2);
		    }
		    DiffOffSet diff = null;
			int offset = -1;
			int lastB1 =-1, lastB2 = -1;
	        int b1 = 0, b2 = 0, pos = 1;
	        while (b1 != -1 || b2 != -1) {
	            if (b1 != b2) {
	            	if(offset==-1) {
		            	diff = new DiffOffSet();
		            	offset = pos;
		            	diff.setOffset(offset);	            		
	            	}
	            }else {
	            	if(offset>-1) {
	            		diff.setLength(pos-offset);
	            		diffs.add(diff);
	            		offset = -1;
	            	}
	            }
	            pos++;
	            b1 = fis1.read();
	            b2 = fis2.read();
	            if(b1==-1 && lastB1==-1) {
	            	lastB1 = pos-1;
	            }
	            if(b2==-1 && lastB2==-1) {
	            	lastB2 = pos-1;
	            }
	            if((b1==-1 || b2==-1) && !shouldFindDifferences) {
	            	break;
	            }
	        }
        	if(offset>-1) {
        		diff.setLength(pos-offset);
        		diffs.add(diff);
        		offset = -1;
        	}
        	if(shouldFindDifferences) {
    	        fileDiffs.setDiffs(diffs);        		
        	}
	        if (lastB1 != lastB2) {
	        	fileDiffs.setStatus(resultFilesHaveDifferentLength);
	        } else {
	        	if(diffs.size()<=0) {
		        	fileDiffs.setStatus(resultFilesAreEqual);	        		
	        	}else {
		        	fileDiffs.setStatus(resultFilesHaveSameLength);	        		
	        	}
	        }
	        fis1.close();
	        fis2.close();
	        long end = System.nanoTime();
	        logger.info("BinaryFileCompare - Execution time: " + (end - start)/1000000 + "ms");
		} catch (IOException e) {
			throw new FileException("Erro", e);
		}

		return fileDiffs;
	}

}
