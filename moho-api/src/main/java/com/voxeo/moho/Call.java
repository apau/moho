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

package com.voxeo.moho;

import java.util.Map;

import javax.media.mscontrol.join.Joinable.Direction;

import com.voxeo.moho.event.InviteEvent;

/**
 * <p>
 * A call is a leg of communication from an {@link Endpoint} to the Moho {@link Application}.
 * A call must have signal controlled by the Moho application, optionally media
 * as well.
 * </p>
 * <p>
 * A call is created when the Moho Application {@link InviteEvent#accept() accept} a InviteEvent. 
 * Furthermore, a call may join to Media Server via {@link #getMediaService()}, or {@link #join} to other Endpoints.
 * </p>
 * <p>
 * A call is an {@link com.voxeo.moho.event.EventSource EventSource} that
 * generates both {@link com.voxeo.moho.event.SignalEvent SignalEvent} and
 * {@link com.voxeo.moho.event.MediaEvent MediaEvent}. However, all the events
 * from a call are dispatched in a single thread to simplify application
 * programming.
 * </p>
 * 
 * @author wchen
 */
public interface Call extends MultiStreamParticipant {
  public enum State {
    /** the Call object is initialized **/
    INITIALIZED,

    /** call is accepted with early media */
    INPROGRESS,

    /** call is accepted without early media */
    ACCEPTED,

    /** call is connected */
    CONNECTED,

    /** call is disconnected */
    DISCONNECTED,

    /** call is failed */
    FAILED
  }

  /**
   * join this call to media server in
   * {@link javax.media.mscontrol.join.Joinable.Direction.DUPLEX DUPLEX}.
   * 
   * @throws IllegalStateException
   *           if the call has been disconnected.
   */
  Joint join();

  /**
   * join this call to media server in the specified <code>direction</code>.
   * 
   * @param direction
   * 			direction of media stream flow from call to media server.
   * 				{@link javax.media.mscontrol.join.Joinable.Direction.SEND SEND} means from call to media server; 
   * 				{@link javax.media.mscontrol.join.Joinable.Direction.RECV RECV} means from media server to call; 
   * 				{@link javax.media.mscontrol.join.Joinable.Direction.DUPLEX DUPLEX} means bidirectional.
   * @throws IllegalStateException
   *           if the call has been {@link com.voxeo.moho.Call.DISCONNECTED DISCONNECTED}.
   */
  Joint join(Direction direction);

  /**
   * Connect this call to endpoint <code>other</code>. The signaling protocol
   * used is based on the endpoint <code>type</code>.
   * 
   * @param direction
   *          direction of media stream flow from call to <code>other</code>: {@link javax.media.mscontrol.join.Joinable.Direction.DUPLEX DUPLEX}, {@link javax.media.mscontrol.join.Joinable.Direction.SEND SEND}, or {@link javax.media.mscontrol.join.Joinable.Direction.RECV RECV}.
   * @param other
   *          endpoint to join with.
   * @param type
   *          media connection mode in {@link Participant.JoinType#BRIDGE BRIDGE} or {@link Participant.JoinType#DIRECT DIRECT}.
   * @return status of join.
   * @throws IllegalStateException
   *           if the call has been {@link com.voxeo.moho.Call.DISCONNECTED DISCONNECTED}.
   */
  Joint join(Direction direction, CallableEndpoint other, Participant.JoinType type);

  /**
   * Connect this call to endpoint <code>other</code>. The signaling protocol
   * used is based on the endpoint <code>type</code>.
   * 
   * @param direction
   *          direction of media stream flow from call to <code>other</code>: {@link javax.media.mscontrol.join.Joinable.Direction.DUPLEX DUPLEX}, {@link javax.media.mscontrol.join.Joinable.Direction.SEND SEND}, or {@link javax.media.mscontrol.join.Joinable.Direction.RECV RECV}
   * @param other 
   *          the endpoint to join with.
   * @param type 
   *          media connection mode in {@link Participant.JoinType#BRIDGE BRIDGE} or {@link Participant.JoinType#DIRECT DIRECT} 
   * @param headers 
   *          additional protocol specific headers sent to endpoint <code>other</code>.
   * @return status of join.
   * @throws IllegalStateException
   *           if the call has been {@link com.voxeo.moho.Call.DISCONNECTED DISCONNECTED}.
   */
  Joint join(Direction direction, CallableEndpoint other, Participant.JoinType type, Map<String, String> headers);

  /**
   * supervised mode delivers more events to the listener
   * 
   * @return <code>true</code> if call is supervised; <code>false</code> if not.
   */
  boolean isSupervised();

  /**
   * @param supervised
   *          <code>true</code> to turn on supervised mode.
   */
  void setSupervised(boolean supervised);

  /**
   * return the media service attached to this call
   * 
   * @param reinvite
   *          <code>true</code> for Moho Framework to automatically re-invite this call to
   *          {@link Participant.JoinType#BRIDGE BRIDGE} mode if currently
   *          joint in {@link Participant.JoinType#DIRECT DIRECT} mode.
   * @throws MediaException
   *           media server error.
   * @throws IllegalStateException
   *           if the call is in {@link Participant.JoinType#DIRECT DIRECT} mode
   *           and <code>reinvite</code> is <code>false</code>, or if the call is not answered.
   */
  MediaService getMediaService(boolean reinvite);

  /**
   * return the media service attached to this call. Equivalent of
   * {@link #getMediaService(boolean) getMediaService(true)}.
   * 
   * @throws MediaException
   *           media server error.
   * @throws IllegalStateException
   *           if the call is in {@link Participant.JoinType#DIRECT DIRECT} mode
   *           and <code>reinvite</code> is <code>false</code>, or if the call is not answered.
   */
  MediaService getMediaService();

  /**
   * @return the current signaling state of the call
   */
  State getCallState();

  /**
   * @return the peer participants (from call control point of view)
   */
  Call[] getPeers();

  /**
   * mute this call, make it listen-only.
   */
  void mute();

  /**
   * unmute this call.
   */
  void unmute();

  /**
   * hold this call.
   */
  void hold();

  /**
   * send a sendrecv SDP and resume to send media data.
   */
  void unhold();
}
