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

package com.voxeo.moho.media.input;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

public class JSGFGrammar extends Grammar {

  private static final Logger LOG = Logger.getLogger(JSGFGrammar.class);

  public JSGFGrammar(final String text) {
    super(text);
  }

  @SuppressWarnings("deprecation")
  @Override
  public URI toURI() {
    try {
      return URI.create("data:" + URLEncoder.encode("application/x-jsgf," + toText(), "UTF-8"));
    }
    catch (final UnsupportedEncodingException e) {
      LOG.warn("", e);
      return URI.create("data:" + URLEncoder.encode("application/x-jsgf," + toText()));
    }
  }
}
