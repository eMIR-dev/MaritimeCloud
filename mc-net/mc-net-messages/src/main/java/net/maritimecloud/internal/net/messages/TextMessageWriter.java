/* Copyright (c) 2011 Danish Maritime Authority.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.maritimecloud.internal.net.messages;

import java.util.Arrays;
import java.util.Iterator;

/**
 * 
 * @author Kasper Nielsen
 */
public class TextMessageWriter {
    public final StringBuilder sb = new StringBuilder();

    boolean notFirst;

    public TextMessageWriter() {
        sb.append("[");
    }

    void checkFirst() {
        if (notFirst) {
            sb.append(", ");
        }
        notFirst = true;
    }

    public TextMessageWriter writeInt(int i) {
        checkFirst();
        sb.append(i);
        return this;
    }

    public TextMessageWriter writeLong(long l) {
        checkFirst();
        sb.append(l);
        return this;
    }

    public TextMessageWriter writeStringArray(String... s) {
        checkFirst();
        sb.append("[");
        for (Iterator<String> iterator = Arrays.asList(s).iterator(); iterator.hasNext();) {
            w(iterator.next());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return this;
    }

    public TextMessageWriter writeDouble(double d) {
        checkFirst();
        // TODO quote string
        sb.append(d);
        return this;
    }

    public TextMessageWriter writeBoolean(boolean d) {
        checkFirst();
        // TODO quote string
        sb.append(d);
        return this;
    }

    public TextMessageWriter writeString(String s) {
        checkFirst();
        // TODO escape string
        w(s);
        return this;
    }

    private void w(String s) {
        if (s == null) {
            sb.append("\\\"\"");
        } else {
            sb.append('"').append(s).append('"');
        }
    }
}
