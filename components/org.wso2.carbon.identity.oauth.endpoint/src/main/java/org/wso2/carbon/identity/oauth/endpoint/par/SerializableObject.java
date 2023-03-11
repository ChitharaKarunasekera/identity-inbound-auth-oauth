package org.wso2.carbon.identity.oauth.endpoint.par;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public class SerializableObject implements Serializable {

    private OAuthAuthzRequest oAuthAuthzRequest;

    public SerializableObject(OAuthAuthzRequest oAuthAuthzRequest) {
        this.oAuthAuthzRequest = oAuthAuthzRequest;
    }

    public OAuthAuthzRequest getoAuthAuthzRequest() {
        return oAuthAuthzRequest;
    }

    public void setoAuthAuthzRequest(OAuthAuthzRequest oAuthAuthzRequest) {
        this.oAuthAuthzRequest = oAuthAuthzRequest;
    }
}
