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

package com.voxeo.moho.sip.fake;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class MockServletContext implements ServletContext {
  private Map<String, Object> _attributes;

  @Override
  final public Object getAttribute(String arg0) {
    if (_attributes == null) {
      _attributes = new HashMap<String, Object>();
    }
    return _attributes.get(arg0);
  }

  @Override
  public Enumeration getAttributeNames() {
    return null;
  }

  @Override
  public ServletContext getContext(String arg0) {
    return null;
  }

  @Override
  public String getContextPath() {
    return null;
  }

  @Override
  public String getInitParameter(String arg0) {
    return null;
  }

  @Override
  public Enumeration getInitParameterNames() {
    return null;
  }

  @Override
  public int getMajorVersion() {
    return 0;
  }

  @Override
  public String getMimeType(String arg0) {
    return null;
  }

  @Override
  public int getMinorVersion() {
    return 0;
  }

  @Override
  public RequestDispatcher getNamedDispatcher(String arg0) {
    return null;
  }

  @Override
  public String getRealPath(String arg0) {
    return null;
  }

  @Override
  public RequestDispatcher getRequestDispatcher(String arg0) {
    return null;
  }

  @Override
  public URL getResource(String arg0) throws MalformedURLException {
    return null;
  }

  @Override
  public InputStream getResourceAsStream(String arg0) {
    return null;
  }

  @Override
  public Set getResourcePaths(String arg0) {
    return null;
  }

  @Override
  public String getServerInfo() {
    return null;
  }

  @Override
  public Servlet getServlet(String arg0) throws ServletException {
    return null;
  }

  @Override
  public String getServletContextName() {
    return null;
  }

  @Override
  public Enumeration getServletNames() {
    return null;
  }

  @Override
  public Enumeration getServlets() {
    return null;
  }

  @Override
  public void log(String arg0) {

  }

  @Override
  public void log(Exception arg0, String arg1) {

  }

  @Override
  public void log(String arg0, Throwable arg1) {

  }

  @Override
  final public void removeAttribute(String arg0) {
    if (_attributes == null) {
      _attributes = new HashMap<String, Object>();
    }
    _attributes.remove(arg0);
  }

  @Override
  final public void setAttribute(String arg0, Object arg1) {
    if (_attributes == null) {
      _attributes = new HashMap<String, Object>();
    }
    _attributes.put(arg0, arg1);
  }

}
