/*
 *   Copyright 2004 The Apache Software Foundation
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package org.apache.eve.output ;


import java.io.IOException ;

import java.nio.ByteBuffer ;

import org.apache.eve.listener.ClientKey ;


/**
 * Service interface used to manage output.
 *
 * @author <a href="mailto:directory-dev@incubator.apache.org">
 * Apache Directory Project</a>
 * @version $Rev: 9555 $
 */
public interface OutputManager
{
    /** Old Avalon requirement for a service role */
    String ROLE = OutputManager.class.getName() ;

    /**
     * Writes or rather sends a peice of PDU data to a client.
     * 
     * @param key the key of the client to send the buffer to
     * @param buf the buffer of PDU data
     * @throws IOException if there is a failure while sending the data
     */
    void write( ClientKey key, ByteBuffer buf ) throws IOException ;
}
