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

import java.util.ListIterator;

import javax.servlet.sip.SipServletRequest;

import com.voxeo.moho.event.InviteEvent;

public abstract class SIPInviteEvent extends InviteEvent {

  public abstract SipServletRequest getSipRequest();

  public abstract String getHeader(String name);

  public abstract ListIterator<String> getHeaders(String name);

}
