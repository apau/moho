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

import com.voxeo.moho.event.Observer;
import com.voxeo.moho.event.TextEvent;

/**
 * <p>
 * This is where a Moho application starts. A Moho application must have one
 * implementation class of Application.
 * </p>
 * <p>
 * A typical entry point is upon receiving one of these event types:
 *  <ol>
 *      <li>{@link com.voxeo.moho.event.InviteEvent} for an incoming call</li>
 *      <li>{@link com.voxeo.moho.event.TextEvent} for an incoming text message </li>
 *  </ol>
 * </p>
 * <p>
 * The Application implementation class must be a public class with a public
 * default constructor. This class is responsible for
 * </p>
 * <block>
 * <ul>
 * <li>lifecycle management by implementing
 * {@link com.voxeo.moho.Application#init(ApplicationContext) init()} and
 * {@link com.voxeo.moho.Application#destroy() destroy()} methods</li>
 * <li>inbound signal and text events by implementing callback methods to handle 
 * {@link com.voxeo.moho.event.SignalEvent SignalEvent} and {@link TextEvent}. See
 * {@link com.voxeo.moho.event.Observer Observer} for information about the
 * method signature requirements.</li>
 * </ul>
 * </block>
 * <p>
 * For example:
 * </p>
 * <block><code><pre>
 *   public MyMohoApplication implements Application {
 *     public MyMohoApplication() {
 *       // construction
 *     }
 *     
 *     public init(ApplicationContext context} {
 *       // get application parameters
 *       // initialize application
 *     }
 *     
 *     // callback for INVITE event
 *     public void handleInvite(InviteEvent invitation) {
 *        Call call = invitation.accept();
 *     }
 *     
 *     // callback for TEXT event
 *     public void handleText(TextEvent event) {
 *     	  String msg=e.getText();
 *     }
 *     
 *     public destroy() {
 *       // uninitialize application
 *     }
 *   }
 * </pre></code></block>
 * <p>
 * Any event handling method will be called by multiple threads concurrently. It
 * is the implementation's responsibility to manage the concurrency issue in
 * event handling methods.
 * </p>
 * 
 * @author wchen
 */
public interface Application extends Observer {

  /**
   * Invoked by Moho Framework to initialize the application with an application
   * context
   * 
   * @param ctx
   *          application context
   */
  void init(ApplicationContext ctx);

  /**
   * Invoked by Moho Framework to uninitialize the application
   */
  void destroy();
}
