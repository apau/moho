/**
 * Copyright 2010 Voxeo Corporation Licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.voxeo.moho.sip;

import java.io.IOException;

import javax.media.mscontrol.networkconnection.SdpPortManagerEvent;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;

import com.voxeo.moho.MediaException;
import com.voxeo.moho.NegotiateException;

public class Media2NIJoinDelegate extends JoinDelegate {

  protected SIPIncomingCall _call;

  protected Media2NIJoinDelegate(final SIPIncomingCall call) {
    _call = call;
  }

  @Override
  protected void doJoin() throws MediaException {
    _call.setSIPCallState(SIPCall.State.ANSWERING);
    _call.processSDPOffer(_call.getSipInitnalRequest());
  }

  @Override
  protected void doSdpEvent(final SdpPortManagerEvent event) {
    if (event.getEventType().equals(SdpPortManagerEvent.OFFER_GENERATED)
        || event.getEventType().equals(SdpPortManagerEvent.ANSWER_GENERATED)) {
      if (event.isSuccessful()) {
        final byte[] sdp = event.getMediaServerSdp();
        _call.setLocalSDP(sdp);
        final SipServletResponse res = _call.getSipInitnalRequest().createResponse(SipServletResponse.SC_OK);
        try {
          res.setContent(sdp, "application/sdp");
          res.send();
        }
        catch (final IOException e) {
          setError(e);
          _call.fail();
          throw new RuntimeException(e);
        }
      }
      else {
        SIPHelper.handleErrorSdpPortManagerEvent(event, _call.getSipInitnalRequest());
        setError(new NegotiateException(event));
        _call.fail();
      }
    }
  }

  @Override
  protected void doAck(final SipServletRequest req, final SIPCallImpl call) {
    try {
      _call.setSIPCallState(SIPCall.State.ANSWERED);
      _call.processSDPAnswer(req);
      done();
    }
    catch (final Exception e) {
      setError(e);
      _call.fail();
      throw new RuntimeException(e);
    }
  }

}
