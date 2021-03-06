package com.voxeo.moho.text.sip;

import java.io.IOException;

import javax.servlet.sip.SipServletRequest;

import com.voxeo.moho.SignalException;
import com.voxeo.moho.TextableEndpoint;
import com.voxeo.moho.event.EventSource;
import com.voxeo.moho.sip.SIPEndpointImpl;
import com.voxeo.moho.sip.SIPTextEvent;

public class SIPTextEventImpl extends SIPTextEvent {

  public SIPTextEventImpl(final EventSource source, final SipServletRequest req) {
    super(source, req);
  }

  @Override
  public String getText() {
    Object content = null;
    try {
      content = _req.getContent();
    }
    catch (final IOException e) {
      throw new SignalException(e);
    }
    return content == null ? null : String.valueOf(content);
  }

  @Override
  public String getTextType() {
    return _req.getContentType();
  }

  @Override
  public TextableEndpoint getSource() {
    return new SIPEndpointImpl(_ctx, _req.getFrom());
  }

  @Override
  public TextableEndpoint getDestination() {
    return new SIPEndpointImpl(_ctx, _req.getTo());
  }

}
