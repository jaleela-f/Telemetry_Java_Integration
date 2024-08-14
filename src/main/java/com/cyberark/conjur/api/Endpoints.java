package com.cyberark.conjur.api;

import com.cyberark.conjur.util.Args;
import com.cyberark.conjur.util.Properties;

import java.io.Serializable;
import java.net.URI;

/**
 * An <code>Endpoints</code> instance provides endpoint URIs for the various conjur services.
 */
public class Endpoints implements Serializable {

    private static final long serialVersionUID = 1L;
    private final URI authnUri;
    private final URI secretsUri;
    private final String source;

    public Endpoints(final URI authnUri, final URI secretsUri,final String source){
        this.authnUri = Args.notNull(authnUri, "authnUri");
        this.secretsUri = Args.notNull(secretsUri, "secretsUri");
        this.source = source;
    }

    public Endpoints(String authnUri, String secretsUri,String source){
        this(URI.create(authnUri), URI.create(secretsUri),source);
    }

    public URI getAuthnUri(){ return authnUri; }

    public URI getSecretsUri() {
        return secretsUri;
    }

    public static Endpoints fromSystemProperties(){
        String account = Properties.getMandatoryProperty(Constants.CONJUR_ACCOUNT_PROPERTY);
        String applianceUrl = Properties.getMandatoryProperty(Constants.CONJUR_APPLIANCE_URL_PROPERTY);
        String authnUrl = Properties.getMandatoryProperty(Constants.CONJUR_AUTHN_URL_PROPERTY, applianceUrl + "/authn");

        return new Endpoints(
                getAuthnServiceUri(authnUrl, account),
                getServiceUri("secrets", account, "variable"),null
        );
    }

    public static Endpoints fromCredentials(Credentials credentials,String source){
        String account = Properties.getMandatoryProperty(Constants.CONJUR_ACCOUNT_PROPERTY);
        return new Endpoints(
                getAuthnServiceUri(credentials.getAuthnUrl(), account),
                getServiceUri("secrets", account, "variable"),source
        );
    }

    private static URI getAuthnServiceUri(String authnUrl, String accountName) {
        return URI.create(String.format("%s/%s", authnUrl, accountName));
    }

    private static URI getServiceUri(String service, String accountName, String path){
        return URI.create(String.format("%s/%s/%s/%s", Properties.getMandatoryProperty(Constants.CONJUR_APPLIANCE_URL_PROPERTY), service, accountName, path));
    }
    
    public String getSource(){return source;}

    @Override
    public String toString() {
        return "Endpoints{" +
                "authnUri=" + authnUri +
                "secretsUri=" + secretsUri +
                "source=" + source +
                '}';
    }
}
