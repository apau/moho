package com.voxeo.moho.text.imified;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import com.voxeo.moho.ApplicationContext;
import com.voxeo.moho.ApplicationContextImpl;
import com.voxeo.moho.TextableEndpoint;
import com.voxeo.moho.imified.ImifiedEndpoint;

public class ImifiedEndpointImpl implements ImifiedEndpoint {
  private static final Logger LOG = Logger.getLogger(ImifiedEndpointImpl.class);

  // this is the botkey if this is a bot. userkey if this is a end user.
  protected String _key;

  // Possible values are:Jabber, AIM, MSN, Yahoo, Gtalk, Twitter or SMS
  protected String _network;

  // screen name.
  protected String _address;

  protected ApplicationContext _ctx;

  protected String _imifiedUserName;

  // bot password, should be set by application.
  protected String _imifiedPasswd;

  private String _imifiedAPI;

  private DefaultHttpClient _httpClient;

  public ImifiedEndpointImpl(final ApplicationContext ctx, final String key) {
    super();
    _key = key;
    _ctx = ctx;

    final ApplicationContextImpl impl = (ApplicationContextImpl) ctx;
    _imifiedAPI = impl.getImifiedApiURL();
    _httpClient = impl.getHttpClient();
  }

  @Override
  public void sendText(final TextableEndpoint from, final String text) throws IOException {
    sendText(from, text, null);
  }

  @Override
  public void sendText(final TextableEndpoint from, final String text, final String type) throws IOException {
    ImifiedEndpointImpl bot = null;
    if (from instanceof ImifiedEndpointImpl) {
      bot = (ImifiedEndpointImpl) from;
    }
    else {
      throw new IllegalArgumentException("The from endpoint is not an ImifiedEndpoint instance.");
    }

    final HttpPost post = new HttpPost(_imifiedAPI);

    final String up = bot.getImifiedUserName() + ":" + bot.getImifiedPasswd();
    final String value = "Basic " + new String(Base64.encodeBase64(up.getBytes("UTF-8")), "UTF-8");
    post.addHeader("Authorization", value);

    final StringBuilder sb = new StringBuilder();
    sb.append("botkey=").append(bot.getKey());
    sb.append("&apimethod=send&userkey=").append(getKey());
    sb.append("&msg=").append(URLEncoder.encode(text, "UTF-8"));

    final HttpEntity en = new StringEntity(sb.toString());
    post.setEntity(en);
    post.setHeader("Content-Type", "application/x-www-form-urlencoded");
    post.setHeader("Connection", "keep-alive");

    if (LOG.isDebugEnabled()) {
      LOG.debug("Sending message from " + bot.getAddress() + " to " + getAddress() + ", content:" + sb.toString());
    }
    HttpEntity resEntity = null;
    try {
      final HttpResponse response = _httpClient.execute(post);
      resEntity = response.getEntity();
      final BufferedReader reader = new BufferedReader(new InputStreamReader(resEntity.getContent()));

      if (resEntity.getContentType().getValue().trim().toLowerCase().startsWith("application/xml")) {
        String firstLine = null;
        do {
          firstLine = reader.readLine();
        }
        while (firstLine.trim().length() == 0);

        if (!(firstLine.indexOf("stat=\"ok\"") > 0)) {
          final StringBuilder output = new StringBuilder();
          output.append(firstLine).append("\r\n");
          String line = null;
          while ((line = reader.readLine()) != null) {
            output.append(line).append("\r\n");
          }
          throw new IOException("Imified returned error:" + output.toString());
        }
      }
      else {
        throw new IOException("Imified returned error message:" + reader.readLine());
      }

      if (LOG.isDebugEnabled()) {
        LOG.debug("Message sent.");
      }
    }
    finally {
      if (resEntity != null) {
        resEntity.consumeContent();
      }
    }
  }

  @Override
  public String getName() {
    return _address;
  }

  @Override
  public String getURI() {
    return _address;
  }

  public String getAddress() {
    return _address;
  }

  public String getKey() {
    return _key;
  }

  public String getNetwork() {
    return _network;
  }

  public void setNetwork(final String network) {
    _network = network;
  }

  public void setAddress(final String address) {
    _address = address;
  }

  @Override
  public void setKey(final String key) {
    _key = key;
  }

  public String getImifiedUserName() {
    return _imifiedUserName;
  }

  public void setImifiedUserName(final String imifiedUserName) {
    _imifiedUserName = imifiedUserName;
  }

  public String getImifiedPasswd() {
    return _imifiedPasswd;
  }

  public void setImifiedPasswd(final String imifiedPasswd) {
    _imifiedPasswd = imifiedPasswd;
  }

}
