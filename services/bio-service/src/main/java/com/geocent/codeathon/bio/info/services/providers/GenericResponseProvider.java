package com.geocent.codeathon.bio.info.services.providers;

import com.geocent.codeathon.bio.info.Utils.JsonHelper;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author aaronwhitney
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
@Service
public class GenericResponseProvider<T> implements MessageBodyWriter<T>  {

    @Autowired
    private JsonHelper jsonHelper;

    private Logger logger = Logger.getLogger(this.getClass());

	
    @Override
    public boolean isWriteable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        if(Throwable.class.isAssignableFrom(type)){
            return false;
        }
        return true;
    }

    @Override
    public long getSize(T t, Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        //we are letting the container manage the content length
        return -1;
    }

    @Override
    public void writeTo(T t, Class<?> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, Object> mm, OutputStream out) throws IOException, WebApplicationException {
        
        logger.debug(t);
        String json = jsonHelper.convertToJson(t);
        
        out.write(json.getBytes());
        
    }
    	
}
