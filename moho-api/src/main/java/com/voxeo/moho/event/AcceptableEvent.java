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

package com.voxeo.moho.event;

import java.util.Map;

import com.voxeo.moho.SignalException;

/**
 * This interface designate that the <code>accept</code> method can be invoked.
 */
public interface AcceptableEvent {

  public enum AcceptableEventState implements EventState {
    ACCEPTED;
  }

  /**
   * Accept the event.
   * 
   * @throws SignalException
   *           when there is any signal error.
   * @throws IllegalStateException
   *           when the event has been accpeted.
   */
  public void accept() throws SignalException, IllegalStateException;

  /**
   * Accept the event with additional headers.
   * 
   * @param headers
   *          additional signaling protocol specific headers to be sent with the
   *          response.
   * @throws SignalException
   *           when there is any signal error.
   * @throws IllegalStateException
   *           when the event has been accpeted.
   */
  public abstract void accept(final Map<String, String> headers) throws SignalException, IllegalStateException;

}
