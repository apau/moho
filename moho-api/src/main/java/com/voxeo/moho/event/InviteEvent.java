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

import com.voxeo.moho.Call;
import com.voxeo.moho.CallableEndpoint;
import com.voxeo.moho.Endpoint;
import com.voxeo.moho.MediaException;
import com.voxeo.moho.SignalException;
import com.voxeo.utils.EventListener;

/**
 * Invitation is an incoming call alert. This is a key event to start the call
 * control.
 * 
 * @author wchen
 */
public abstract class InviteEvent extends SignalEvent implements RejectableEvent {

  private static final long serialVersionUID = 8264519475273617594L;

  protected InviteEvent() {
    super(null);
  }

  /**
   * States of the InviteEvent
   * 
   * @author wchen
   */
  public enum InviteEventState implements EventState {
    /**
     * the initial state of InviteEvent.
     */
    ALERTING,
    /**
     * when one of the accept method is called.
     */
    ACCEPTING,
    /**
     * when one of acceptWithEarlyMedia method is called.
     */
    PROGRESSING,
    /**
     * when one of the reject method is called.
     */
    REJECTING,
    /**
     * when one of the redirect method is called.
     */
    REDIRECTING
  }

  /**
   * @return address of the sender of the invitation
   */
  public abstract Endpoint getInvitor();

  /**
   * @return address of the receiver of the invitation
   */
  public abstract CallableEndpoint getInvitee();

  /**
   * Accept the INVITE event.
   * 
   * @return the {@link Call Call} resulted by accepting the invitation.
   * @throws SignalException
   *           when there is any signal error.
   * @throws IllegalStateException
   *           when the event has been accepted.
   */
  public Call accept() {
    return this.accept((Observer) null);
  }

  /**
   * Accept the event.
   * 
   * @return the {@link Call Call} resulted by accepting the invitation.
   * @throws SignalException
   *           when there is any signal error.
   * @throws IllegalStateException
   *           when the event has been accepted.
   */
  public Call accept(final Map<String, String> headers) {
    return this.accept((Map<String, String>) null, (Observer) null);
  }

  /**
   * Accept the invitation with a set of {@link Observer Observer}s.
   * 
   * @param observers
   *          the {@link Observer Observer}s to be added to the {@link Call
   *          Call}
   * @return the {@link Call Call} resulted by accepting the invitation.
   * @throws SignalException
   *           when there is any signal error.
   * @throws IllegalStateException
   *           when the event has been accepted.
   */
  public Call accept(final Observer... observers) throws SignalException, IllegalStateException {
    return this.accept(null, observers);
  }

  /**
   * Accept the invitation with a set of {@link com.voxeo.utils.EventListener
   * EventListener}s.
   * 
   * @param listeners
   *          the {@link com.voxeo.utils.EventListener EventListener}s to be
   *          added to the {@link Call Call}
   * @return the {@link Call Call} resulted by accepting the invitation.
   * @throws SignalException
   *           when there is any signal error.
   * @throws IllegalStateException
   *           when the event has been accepted.
   */
  public Call accept(final EventListener<?>... listeners) throws SignalException, IllegalStateException {
    return this.accept(null, listeners);
  }

  /**
   * Accept the invitation with a set of {@link com.voxeo.utils.EventListener
   * EventListener}s and additional headers.
   * 
   * @param headers
   *          additional signaling protocol specific headers to be sent with the
   *          response.
   * @param listeners
   *          the {@link com.voxeo.utils.EventListener EventListener}s to be
   *          added to the {@link Call Call}
   * @return the {@link Call Call} resulted by accepting the invitation.
   * @throws SignalException
   *           when there is any signal error.
   * @throws IllegalStateException
   *           when the event has been accepted.
   */
  public abstract Call accept(final Map<String, String> headers, final EventListener<?>... listeners)
      throws SignalException, IllegalStateException;

  /**
   * Accept the invitation with a set of {@link Observer Observer}s and
   * additional headers.
   * 
   * @param headers
   *          additional signaling protocol specific headers to be sent with the
   *          response.
   * @param observer
   *          the {@link Observer Observer}s s to be added to the {@link Call
   *          Call}
   * @return the {@link Call Call} resulted by accepting the invitation.
   * @throws SignalException
   *           when there is any signal error.
   * @throws IllegalStateException
   *           when the event has been accepted.
   */
  public abstract Call accept(final Map<String, String> headers, final Observer... observer)
      throws SignalException, IllegalStateException;

  /**
   * accept the invitation with early media (SIP 183)
   * 
   * @throws SignalException
   *           when there is any signal error.
   * @throws MediaException
   *           when there is any media server error.
   * @throws IllegalStateException
   *           when the event has been accepted.
   */
  public Call acceptWithEarlyMedia() throws SignalException, MediaException, IllegalStateException {
    return this.acceptWithEarlyMedia((Map<String, String>) null);
  }

  /**
   * accept the invitation with early media (SIP 183)
   * 
   * @param headers
   *          additional signaling protocol specific headers to be sent with the
   *          response.
   * @return the {@link Call Call} resulted by accepting the invitation.
   * @throws SignalException
   *           when there is any signal error.
   * @throws MediaException
   *           when there is any media server error.
   * @throws IllegalStateException
   *           when the event has been accepted.
   */
  public Call acceptWithEarlyMedia(final Map<String, String> headers) throws SignalException, MediaException,
      IllegalStateException {
    return this.acceptWithEarlyMedia(headers, (Observer) null);
  }

  /**
   * accept the invitation with early media (SIP 183)
   * 
   * @param observers
   *          the {@link Observer Observer}s s to be added to the {@link Call
   *          Call}
   * @return the {@link Call Call} resulted by accepting the invitation.
   * @throws SignalException
   *           when there is any signal error.
   * @throws MediaException
   *           when there is any media server error.
   * @throws IllegalStateException
   *           when the event has been accepted.
   */
  public Call acceptWithEarlyMedia(final Observer... observers) throws SignalException, MediaException,
      IllegalStateException {
    return this.acceptWithEarlyMedia(null, observers);
  }

  /**
   * accept the invitation with early media (SIP 183) with additional headers
   * 
   * @param listeners
   *          the {@link com.voxeo.utils.EventListener EventListener}s to be
   *          added to the {@link Call Call}
   * @return the {@link Call Call} resulted by accepting the invitation.
   * @throws SignalException
   *           when there is any signal error.
   * @throws MediaException
   *           when there is any media server error.
   * @throws IllegalStateException
   *           when the event has been accepted.
   */
  public Call acceptWithEarlyMedia(final EventListener<?>... listeners) throws SignalException, MediaException,
      IllegalStateException {
    return this.acceptWithEarlyMedia(null, listeners);
  }

  /**
   * accept the invitation with early media (SIP 183)
   * 
   * @param headers
   *          additional signaling protocol specific headers to be sent with the
   *          response.
   * @param listeners
   *          the {@link com.voxeo.utils.EventListener EventListener}s to be
   *          added to the {@link Call Call}
   * @return the {@link Call Call} resulted by accepting the invitation.
   * @throws SignalException
   *           when there is any signal error.
   * @throws MediaException
   *           when there is any media server error.
   * @throws IllegalStateException
   *           when the event has been accpeted.
   */
  public abstract Call acceptWithEarlyMedia(final Map<String, String> headers, final EventListener<?>... listeners)
      throws SignalException, MediaException, IllegalStateException;

  /**
   * accept the invitation with early media (SIP 183)
   * 
   * @param headers
   *          additional signaling protocol specific headers to be sent with the
   *          response.
   * @param observers
   *          the {@link Observer Observer}s s to be added to the {@link Call
   *          Call}
   * @return the {@link Call Call} resulted by accepting the invitation.
   * @throws SignalException
   *           when there is any signal error.
   * @throws MediaException
   *           when there is any media server error.
   * @throws IllegalStateException
   *           when the event has been accpeted.
   */
  public abstract Call acceptWithEarlyMedia(final Map<String, String> headers, final Observer... observers)
      throws SignalException, MediaException, IllegalStateException;

  /**
   * redirect the INVITE to <code>other</code> via 302
   * 
   * @param other
   *          the endpoint to redirect to.
   * @throws SignalException
   *           when there is any signal error.
   */
  public void redirect(final Endpoint other) throws SignalException, IllegalArgumentException {
    this.redirect(other, null);
  }

  public abstract void redirect(Endpoint other, Map<String, String> headers) throws SignalException,
      IllegalArgumentException;

  /**
   * reject the invitation with reason
   * 
   * @param reason
   * @throws SignalException
   *           when there is any signal error.
   */
  public void reject(final Reason reason) throws SignalException {
    this.reject(reason, null);
  }

  @Override
  protected synchronized void checkState() {
    if (getState() != InviteEventState.ALERTING) {
      throw new IllegalStateException("Event already " + getState());
    }
  }
}
