package br.com.luismunhoz.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import br.com.luismunhoz.exception.FileTypeException;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

@Component
public class JMimeMagicDetectFileType implements DetectFileType {
	
	private static final Log logger = LogFactory.getLog(JMimeMagicDetectFileType.class);

	@Override
	public Boolean isTextFile(byte[] fileContent) throws FileTypeException {
        MagicMatch match = null;
		try {
			match = Magic.getMagicMatch(fileContent);
		} catch (MagicParseException e) {
			throw new FileTypeException("Erro", e);
		} catch (MagicMatchNotFoundException e) {
			throw new FileTypeException("Erro", e);
		} catch (MagicException e) {
			throw new FileTypeException("Erro", e);
		}
		logger.debug("isTextFile =>"+match.getMimeType());
        return match.getMimeType().equalsIgnoreCase("text/plain");         
	}
	
}
