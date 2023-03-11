package org.wso2.carbon.identity.oauth.endpoint.par;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;

import java.io.*;

import org.wso2.carbon.identity.application.authentication.framework.exception.SessionSerializerException;
import org.wso2.carbon.identity.application.authentication.framework.store.JavaSessionSerializer;
import org.wso2.carbon.identity.application.authentication.framework.store.SessionSerializer;

public class ParAuthRequestSerializer {

    public byte[] serializeSessionObject(Object value) throws SessionSerializerException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            throw new SessionSerializerException("Error while serializing the session object", e);
        }

        byte[] bytes = baos.toByteArray();
        return bytes;
        //return new ByteArrayInputStream(baos.toByteArray());
    }


    public Object deSerializeSessionObject(InputStream inputStream) throws SessionSerializerException {
        try {
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new SessionSerializerException("Error while de serializing the session object", e);
        }
    }


//    --------------------------------------------------------------------------------------
//    private OAuthAuthzRequest parAuthRequest;
//
//    SerializableParAuthRequest(OAuthAuthzRequest parAuthRequest) throws IOException {
//
//        this.parAuthRequest = parAuthRequest;
////        FileOutputStream fileOut = new FileOutputStream("file.ser");
////        ObjectOutputStream out = new ObjectOutputStream(fileOut);
////        out.writeObject(this);
////        out.close();
//    }
}
