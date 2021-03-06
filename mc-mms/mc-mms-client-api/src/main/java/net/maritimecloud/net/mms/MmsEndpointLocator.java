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
package net.maritimecloud.net.mms;

import java.util.List;

import net.maritimecloud.net.EndpointInvocationFuture;
import net.maritimecloud.net.LocalEndpoint;

/**
 *
 * @author Kasper Nielsen
 */
public interface MmsEndpointLocator<T extends LocalEndpoint> {

    MmsEndpointLocator<T> withinDistanceOf(int meters);

    // EndpointInvocationFuture<T> findWithMMSINumber(int mmsiNumber);

    EndpointInvocationFuture<T> findNearest();

    /**
     * Find all endpoints.
     *
     * @return the endpoints
     */
    default EndpointInvocationFuture<List<T>> findAll() {
        return findAll(Integer.MAX_VALUE);
    }

    EndpointInvocationFuture<List<T>> findAll(int maximum);
}
