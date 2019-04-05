# WAES Assignment - Compare Files

General-purpose

	Provide 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints
	o <host>/v1/diff/{id}/left and <host>/v1/diff/{ID}/right
	• The provided data needs to be diff-ed and the results shall be available on a third end point
	
	o <host>/v1/diff/{ID}
	• The results shall provide the following info in JSON format
		o If equal return that
		o If not of equal size just return that
		o If of same size provide insight in where the diffs are, actual diffs are not needed.
		§ So mainly offsets + length in the data

	Added two more endpoints for uploading files via MultipartFile:
		o <host>/v1/diff/bigfile/{id}/left
		o <host>/v1/diff/bigfile/{id}/rigth
		For comparison, use the single endpoint.

## Install

	From the project root directory:
    $ mvn install

## Usage

#### Configure Strategy

The application can be configured in relation to the points:
  - File Type Detection (detectFileType)
  
  		Besides the requested binary comparison, I also created the possibility of comparing texts.
		
		To use text comparison, it is necessary to detect the type of file that was loaded.
		
		With detectFileType = true, if the two files (left and right) are of type text, the
		
		program will perform a line-to-line comparison.
		
  - Search for different bytes, even when the files are of different sizes (shouldFindDifferencesToDifferentSizes):
  
		It allows, in the case of binary files, even with different sizes to be swept both files 
		
		and returned the differences found (offset + length)
		


#### Authenticate Requests

One point of improvement would be the implementation of authentication for use of the endpoints.

#### Asynchronous service

The endpoint that returns the comparison result, for better scalability, should be asynchronous.
You could return comparisons synchronously if the process has a processing time of less than 15 seconds.
For the other comparisons, it would be better to return a ticket for a future query of the result.

#### Tests

The test suite is located in the `\src\test` directory.  All features are
expected to have corresponding test cases.  Ensure that the complete test suite
passes by executing:

	From the project root directory:
	$ mvn test 


#### Coverage

All feature development is expected to have test coverage. The actual coverage is 87,2%


## License

[The MIT License](http://opensource.org/licenses/MIT)

Copyright (c) 2019 Luis Munhoz <[https://www.linkedin.com/in/luismunhoz/](https://www.linkedin.com/in/luismunhoz/)>
