package com.cyberark.conjur.api;

import javax.net.ssl.SSLContext;

/**
 * Entry point for the Conjur API client.
 */
public class Conjur {

    private Variables variables;
    private String topSourceName = null;
    private static String finalSource = null;

    /**
     * Create a Conjur instance that uses credentials from the system properties
     */
    public Conjur(){
        this(Credentials.fromSystemProperties());
    }
    
    /**
     * Create a Conjur instance that uses credentials from the system properties
     * @param source which tell Conjur who is doing API calls
     */
    public Conjur(String source){
        this(Credentials.fromSystemProperties(), source);
    }
    
    /**
     * Create a Conjur instance that uses credentials from the system properties
     * @param sslContext the {@link SSLContext} to use for connections to Conjur server
     */
    public Conjur(SSLContext sslContext){
        this(Credentials.fromSystemProperties(), sslContext, null);
    }

    

    /**
     * Create a Conjur instance that uses credentials from the system properties
     * @param sslContext the {@link SSLContext} to use for connections to Conjur server
     * @param source which tell Conjur who is doing API calls
     */
    public Conjur(SSLContext sslContext, String source ){
        this(Credentials.fromSystemProperties(), sslContext, source);
    }
    /**
     * Create a Conjur instance that uses a ResourceClient &amp; an AuthnClient constructed with the given credentials
     * @param username username for the Conjur identity to authenticate as
     * @param password password or api key for the Conjur identity to authenticate as
     */
    public Conjur(String username, String password) {
        this(new Credentials(username, password));
    }

    /**
     * Create a Conjur instance that uses a ResourceClient & an AuthnClient constructed with the given credentials
     * @param username username for the Conjur identity to authenticate as
     * @param password password or api key for the Conjur identity to authenticate as
     * @param sslContext the {@link SSLContext} to use for connections to Conjur server
     */
    public Conjur(String username, String password, SSLContext sslContext) {
        this(new Credentials(username, password), sslContext, null);
    }
    
    /**
     * Create a Conjur instance that uses a ResourceClient & an AuthnClient constructed with the given credentials
     * @param username username for the Conjur identity to authenticate as
     * @param password password or api key for the Conjur identity to authenticate as
     * @param sslContext the {@link SSLContext} to use for connections to Conjur server
     * @param source which tell Conjur who is doing API calls
     */
    public Conjur(String username, String password, SSLContext sslContext, String source) {
        this(new Credentials(username, password), sslContext, source);
    }
    

    /**
     * Create a Conjur instance that uses a ResourceClient &amp; an AuthnClient constructed with the given credentials
     * @param username username for the Conjur identity to authenticate as
     * @param password password or api key for the Conjur identity to authenticate as
     * @param authnUrl the conjur authentication url
     */
    public Conjur(String username, String password, String authnUrl) {
        this(new Credentials(username, password, authnUrl));
    }

    /**
     * Create a Conjur instance that uses a ResourceClient &amp; an AuthnClient constructed with the given credentials
     * @param username username for the Conjur identity to authenticate as
     * @param password password or api key for the Conjur identity to authenticate as
     * @param authnUrl the conjur authentication url
     * @param sslContext the {@link SSLContext} to use for connections to Conjur server
     */
    public Conjur(String username, String password, String authnUrl, SSLContext sslContext) {
        this(new Credentials(username, password, authnUrl), sslContext,null);
    }
    
    /**
     * Create a Conjur instance that uses a ResourceClient & an AuthnClient constructed with the given credentials
     * @param username username for the Conjur identity to authenticate as
     * @param password password or api key for the Conjur identity to authenticate as
     * @param authnUrl the conjur authentication url
     * @param sslContext the {@link SSLContext} to use for connections to Conjur server
     * @param source which tell Conjur who is doing API calls
     */
    public Conjur(String username, String password, String authnUrl, SSLContext sslContext, String source) {
        this(new Credentials(username, password, authnUrl), sslContext, source);
    }

    /**
     * Create a Conjur instance that uses a ResourceClient &amp; an AuthnClient constructed with the given credentials
     * @param credentials the conjur identity to authenticate as
     */
    public Conjur(Credentials credentials) {
        this(credentials, null,null);
    }
    
    /**
     * Create a Conjur instance that uses a ResourceClient & an AuthnClient constructed with the given credentials
     * @param credentials the conjur identity to authenticate as
     * @param source which tell Conjur who is doing API calls
     */
    public Conjur(Credentials credentials, String source) {
        this(credentials, null, source);
    }

    /**
     * Create a Conjur instance that uses a ResourceClient & an AuthnClient constructed with the given credentials
     * @param credentials the conjur identity to authenticate as
     * @param sslContext the {@link SSLContext} to use for connections to Conjur server
     */
    public Conjur(Credentials credentials, SSLContext sslContext, String source ) {
        variables = new Variables(credentials, sslContext, getVersionName( source ) );
    }

    /**
     * Create a Conjur instance that uses a ResourceClient &amp; an AuthnClient constructed with the given credentials
     * @param token the conjur authorization token to use
     */
    public Conjur(Token token) {
        this(token, null);
    }
   

    /**
     * Create a Conjur instance that uses a ResourceClient &amp; an AuthnClient constructed with the given credentials
     * @param token the conjur authorization token to use
     * @param sslContext the {@link SSLContext} to use for connections to Conjur server
     */
    public Conjur(Token token, SSLContext sslContext) {
        variables = new Variables(token, sslContext,getVersionName(null));
    }

    /**
     * Get a Variables instance configured with the same parameters as this instance.
     * @return the variables instance
     */
    public Variables variables() {
        return variables;
    }
    
    /**
     * Set top source of data. Used to point to SDK used on top of current SDK
     * @param name of SDK which is using current SDK
     */
 
    public void setTopSourceName( String name )
    {
        topSourceName = name;
    }
 
    /**
     * @param source is the information from developer about name of source
     * @return Properly formatted version format
     */
 
    private String getVersionName( String source )
    {
        if( finalSource != null )
        {
            return finalSource;
        }
 
        finalSource = "";
        if( topSourceName != null ){ finalSource = topSourceName + ";"; }
 
        finalSource += "javasdk/" + getClass().getPackage().getImplementationVersion();
 
        if( source != null ){ finalSource += "(" + source + ")"; }
        return finalSource;
    }
}
