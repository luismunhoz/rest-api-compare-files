package br.com.luismunhoz.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.luismunhoz.exception.FileTypeException;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

@Component
public class JMimeMagicDetectFileType implements DetectFileType {
	
	@Value("${message.error.detectFileType}")
	String errorDetectFileType;
	
	private static final Log logger = LogFactory.getLog(JMimeMagicDetectFileType.class);

	@Override
	public Boolean isTextFile(InputStream fileContent) throws FileTypeException {
        MagicMatch match = null;
		try {
			match = Magic.getMagicMatch(IOUtils.toByteArray(fileContent));
		} catch (MagicParseException e) {
			throw new FileTypeException(errorDetectFileType, e);
		} catch (MagicMatchNotFoundException e) {
			throw new FileTypeException(errorDetectFileType, e);
		} catch (MagicException e) {
			throw new FileTypeException(errorDetectFileType, e);
		} catch (IOException e) {
			throw new FileTypeException(errorDetectFileType, e);
		}
		logger.debug("isTextFile =>"+match.getMimeType());
        return match.getMimeType().equalsIgnoreCase("text/plain");         
	}
	
}
