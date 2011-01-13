/* $License$ */

/**
 * <p>
 * Defines key interfaces in Moho framework. 
 * </p>
 * <p>
 * Moho uses a event-driven programming model. There're four types of events in Moho:
 * 	<ul>{@link com.voxeo.moho.event.SignalEvent}: system event representing SIP Signaling event, such as INVITE, REFER, BYE.</ul>
 * 	<ul>{@link com.voxeo.moho.event.MediaEvent}: system event representing notification and execution status of media functions.</ul>
 * 	<ul>{@link com.voxeo.moho.event.TextEvent}: system event representing Text message event.</ul>
 *  <ul>application event(base interface?): user-defined event. </ul>
 * </p>
 * <p>
 * The start of a Moho application is a class that implements {@link com.voxeo.moho.Application} interface. This class implements a callback method 
 * for initiating event: {@link com.voxeo.moho.event.SignalEvent} for incoming call, or {@link com.voxeo.moho.event.TextEvent} for incoming text message.
 * </p>
 * <p>
 * In the case of a call, accepting({@link com.voxeo.moho.event.InviteEvent#acceptCall()}) Invite creates a {@link com.voxeo.moho.Call} leg between caller and Moho Application.
 * Moho Application may {@link com.voxeo.moho.Call#join()} the call leg to media server to access {@link com.voxeo.moho.MediaService}.
 * </p>
 */

package com.voxeo.moho;
