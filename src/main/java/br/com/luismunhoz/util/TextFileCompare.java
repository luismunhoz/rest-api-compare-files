package br.com.luismunhoz.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.luismunhoz.exception.FileException;
import br.com.luismunhoz.model.FileDifference;
import br.com.luismunhoz.model.FilesToCompare;
import br.com.luismunhoz.model.LineDifference;
import br.com.luismunhoz.model.TextFileDifference;

@Component
public class TextFileCompare extends FileCompare {

	@Value("${application.fileLocation}")
	String fileLocation;

	@Autowired
	FileManager fileManager;

	private static final Log logger = LogFactory.getLog(BinaryFileCompare.class);

	@Override
	public FileDifference compare(String id) throws FileException {
		long start = System.nanoTime();
		TextFileDifference fileDiffs = new TextFileDifference();
		List<LineDifference> diffs = new ArrayList<LineDifference>();

		FilesToCompare files = fileManager.loadFile(id);
		InputStream isLeft = files.getLeftFile();
		InputStream isRight = files.getRightFile();
		
		try {
			BufferedReader brLeft = new BufferedReader(new InputStreamReader(isLeft, "UTF-8"));		
			BufferedReader brRight = new BufferedReader(new InputStreamReader(isRight, "UTF-8"));


			String lineLeft = brLeft.readLine();
			String lineRight = brRight.readLine();
			int line = 0;
			while (lineLeft != null || lineRight!= null) {
								
				if (lineLeft!=null && !lineLeft.equals(lineRight)) {
					LineDifference lineDiff = new LineDifference();
					lineDiff.setNumber(line);
					lineDiff.setLeftLine(lineLeft!=null?lineLeft:"");
					lineDiff.setRightLine(lineRight!=null?lineRight:"");
					diffs.add(lineDiff);
				}
				if (lineLeft==null && lineRight!=null) {
					LineDifference lineDiff = new LineDifference();
					lineDiff.setNumber(line);
					lineDiff.setLeftLine(lineLeft!=null?lineLeft:"");
					lineDiff.setRightLine(lineRight!=null?lineRight:"");
					diffs.add(lineDiff);
				}
				line++;
				lineLeft = brLeft.readLine();
				lineRight = brRight.readLine();
				
			}
			if(diffs.size()>0) {
				fileDiffs.setLines(diffs);
				fileDiffs.setStatus("Files are different.");
			}else {
				fileDiffs.setStatus("Files are equal.");
			}
		} catch (Exception e) {
			throw new FileException("", e);
		}

		long end = System.nanoTime();
		logger.info("TextFileCompare - Execution time: " + (end - start) / 1000000 + "ms");

		return fileDiffs;

	}

}
