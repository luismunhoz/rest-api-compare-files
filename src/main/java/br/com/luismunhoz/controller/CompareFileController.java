package br.com.luismunhoz.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
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

/*
 * Provide 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints
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
@RequestMapping("${application.path_v1}/{id}")
public class CompareFileController {

	@RequestMapping(value = "/left", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<HeadersBuilder> saveLeftFile(@RequestBody FileJson data) throws Exception {
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/right", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<HeadersBuilder> saveRightFile(@PathVariable Long id, @RequestBody FileJson data) throws Exception {
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<FileDifference> compareFiles(@PathVariable Long id) throws Exception {
		FileDifference response = null;
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
    private String create(@RequestParam("image") MultipartFile multipartFile) throws IOException{
        /*** 
           * Don't do this.
           * it loads all of the bytes in java heap memory that leads to OutOfMemoryError. We'll use stream instead. 
        **/
        // byte[] fileBytes = multipartFile.getBytes(); 
        InputStream fileStream = multipartFile.getInputStream();
        File targetFile = new File("C:\\diff\\files\\targetFile.tmp");
        FileUtils.copyInputStreamToFile(fileStream, targetFile);
        
        return "redirect:/?message=Successful!";
    }	
}
