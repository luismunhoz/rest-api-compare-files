package br.com.luismunhoz.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.HeadersBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.luismunhoz.model.FileDifference;
import br.com.luismunhoz.model.FileJson;
import br.com.luismunhoz.service.FileCompareService;
import br.com.luismunhoz.util.BinaryFileCompare;
import io.swagger.annotations.ApiOperation;

/*
 * 		Provide 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints
 *
 *		o <host>/v1/diff/<ID>/left and <host>/v1/diff/<ID>/right
 *		• The provided data needs to be diff-ed and the results shall be available on a third end point
 *		
 *		o <host>/v1/diff/<ID>
 *		• The results shall provide the following info in JSON format
 *			o If equal return that
 *			o If not of equal size just return that
 *			o If of same size provide insight in where the diffs are, actual diffs are not needed.
 *			§ So mainly offsets + length in the data
 */

@RestController
@RequestMapping("${application.path_v1}")
public class FileCompareController {
	
	@Autowired
	FileCompareService fileCompareService;
	
	@Value("${message.fileUpdated}")
	String fileUpdated;
	
	private static final Log logger = LogFactory.getLog(BinaryFileCompare.class);			

	@RequestMapping(value = "/{id}/left", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value="Upload the left file to make a comparison.", nickname="Upload Left File")
	public ResponseEntity<?> saveLeftFile(@PathVariable(value="id", required=true) String id,  @RequestBody FileJson data) throws Exception {
		logger.debug("FileCompareController - saveLeftFile. Id:"+id);
		Boolean firstUpload = fileCompareService.uploadLeftFile(id, IOUtils.toInputStream(data.getFileContent()));
		if(firstUpload) {
			return ResponseEntity.noContent().build();			
		}else {
			return new ResponseEntity<>(fileUpdated, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/{id}/right", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value="Upload the right file to make a comparison.", nickname="Upload Right File")
	public ResponseEntity<?> saveRightFile(@PathVariable(value="id", required=true) String id, @RequestBody FileJson data) throws Exception {
		logger.debug("FileCompareController - saveRightFile. Id:"+id);
		Boolean firstUpload = fileCompareService.uploadRightFile(id, IOUtils.toInputStream(data.getFileContent()));
		if(firstUpload) {
			return ResponseEntity.noContent().build();			
		}else {
			return new ResponseEntity<>(fileUpdated, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value="Compare the left and right files.", nickname="Compare Files")
	private ResponseEntity<FileDifference> compareFiles(@PathVariable(value="id", required=true) String id) throws Exception {
		logger.debug("FileCompareController - compareFiles. Id:"+id);
		FileDifference response = fileCompareService.compareFiles(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}

	@RequestMapping(value = "/bigfile/{id}/left", method = RequestMethod.POST)
	@ApiOperation(value="Large Files - Upload the left file to make a comparison.", nickname="Large Files - Upload Left File")
    private ResponseEntity<?> createLeft(@RequestParam("file") MultipartFile multipartFile, @PathVariable(value="id", required=true) String id) throws IOException{
		logger.debug("FileCompareController - createLeft. Id:"+id);
		Boolean firstUpload = true;
        InputStream fileStream = multipartFile.getInputStream();
		try {
			firstUpload = fileCompareService.uploadLeftFile(id, fileStream);
		} catch (Exception e) {
			e.printStackTrace();
		}     
		if(firstUpload) {
			return ResponseEntity.noContent().build();			
		}else {
			return new ResponseEntity<>(fileUpdated, HttpStatus.OK);
		}
    }
	
	@RequestMapping(value = "/bigfile/{id}/right", method = RequestMethod.POST)
	@ApiOperation(value="Large Files - Upload the right file to make a comparison.", nickname="Large Files - Upload Right File")
    private ResponseEntity<?> createRight(@RequestParam("file") MultipartFile multipartFile, @PathVariable(value="id", required=true) String id) throws IOException{
		logger.debug("FileCompareController - createRight. Id:"+id);
		Boolean firstUpload=true;
        InputStream fileStream = multipartFile.getInputStream();
		try {
			firstUpload = fileCompareService.uploadRightFile(id, fileStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(firstUpload) {
			return ResponseEntity.noContent().build();			
		}else {
			return new ResponseEntity<>(fileUpdated, HttpStatus.OK);
		}
    }
    	
}
