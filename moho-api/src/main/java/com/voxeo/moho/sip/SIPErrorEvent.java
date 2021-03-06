/**
 * Copyright 2010 Voxeo Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License.
 *
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.voxeo.moho.sip;

import javax.servlet.sip.SipServletResponse;

import com.voxeo.moho.event.ErrorEvent;
import com.voxeo.moho.event.EventSource;

public abstract class SIPErrorEvent extends ErrorEvent {

  protected SipServletResponse _res;

  protected SIPErrorEvent(final EventSource source, final SipServletResponse res) {
    super(source);
    _res = res;
  }

  public SipServletResponse getSipResponse() {
    return _res;
  }

  @Override
  public ErrorType getErrorType() {
    switch (_res.getStatus()) {
      case SipServletResponse.SC_BUSY_HERE:
      case SipServletResponse.SC_BUSY_EVERYWHERE:
        return ErrorType.BUSY;

      case SipServletResponse.SC_REQUEST_TIMEOUT:
        return ErrorType.TIMEOUT;

      case SipServletResponse.SC_DECLINE:
        return ErrorType.DECLINE;
      default:
        return ErrorType.OTHER;
    }
  }
}
