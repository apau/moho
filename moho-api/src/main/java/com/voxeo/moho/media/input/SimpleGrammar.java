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

package com.voxeo.moho.media.input;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

public class SimpleGrammar extends Grammar {

  public SimpleGrammar(final String string) {
    super(string);
  }

  @SuppressWarnings("deprecation")
  @Override
  public URI toURI() {
    try {
      return URI.create("data:" + URLEncoder.encode("application/grammar+voxeo," + toText(), "UTF-8"));
    }
    catch (final UnsupportedEncodingException e) {
      return URI.create("data:" + URLEncoder.encode("application/grammar+voxeo," + toText()));
    }
  }

}
