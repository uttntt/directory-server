/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License. 
 *  
 */
package org.apache.directory.shared.kerberos.codec;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.nio.ByteBuffer;

import org.apache.directory.junit.tools.Concurrent;
import org.apache.directory.junit.tools.ConcurrentJunitRunner;
import org.apache.directory.shared.asn1.ber.Asn1Container;
import org.apache.directory.shared.asn1.ber.Asn1Decoder;
import org.apache.directory.shared.asn1.codec.DecoderException;
import org.apache.directory.shared.asn1.codec.EncoderException;
import org.apache.directory.shared.kerberos.codec.encAsRepPart.EncAsRepPartContainer;
import org.apache.directory.shared.kerberos.messages.EncAsRepPart;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Test the decoder for a EncAsRepPart
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
@RunWith(ConcurrentJunitRunner.class)
@Concurrent()
public class EncAsRepPartDecoderTest
{
    /**
     * Test the decoding of a EncAsRepPart message
     */
    @Test
    public void testDecodeFullEncAsRepPart() throws Exception
    {
        Asn1Decoder kerberosDecoder = new Asn1Decoder();

        ByteBuffer stream = ByteBuffer.allocate( 0xA2 );
        
        stream.put( new byte[]
        {
            0x79, (byte)0x81, (byte)0x9F,
              0x30, (byte)0x81, (byte)0x9C,
                (byte)0xA0, 0x11,
                  0x30, 0x0F, 
                    (byte)0xA0, 0x03, 
                      0x02, 0x01, 0x11, 
                    (byte)0xA1, 0x08, 
                      0x04, 0x06, 
                        0x61, 0x62, 0x63, 0x64, 0x65, 0x66,
                (byte)0xA1, 0x36,
                  0x30, 0x34, 
                    0x30, 0x18, 
                      (byte)0xA0, 0x03, 
                        0x02, 0x01, 0x02, 
                      (byte)0xA1, 0x11, 
                        0x18, 0x0F, 
                          0x32, 0x30, 0x31, 0x30, 0x31, 0x31, 0x32, 0x35, 0x31, 0x36, 0x31, 0x32, 0x35, 0x39, 0x5A,
                    0x30, 0x18,
                      (byte)0xA0, 0x03,
                        0x02, 0x01, 0x02,
                      (byte)0xA1, 0x11,
                        0x18, 0x0F, 
                          0x32, 0x30, 0x31, 0x30, 0x31, 0x31, 0x32, 0x35, 0x31, 0x36, 0x31, 0x32, 0x35, 0x39, 0x5A,
                (byte)0xA2, 0x03,
                  0x02, 0x01, 0x01,
                (byte)0xA4, 0x07,
                  0x03, 0x05, 0x00, 0x40, 0x00, 0x00, 0x00,
                (byte)0xA5, 0x11,
                  0x18, 0x0F, 
                    0x32, 0x30, 0x31, 0x30, 0x31, 0x31, 0x32, 0x35, 0x31, 0x36, 0x31, 0x32, 0x35, 0x39, 0x5A,
                (byte)0xA7, 0x11, 
                  0x18, 0x0F, 
                    0x32, 0x30, 0x31, 0x30, 0x31, 0x31, 0x32, 0x35, 0x31, 0x36, 0x31, 0x32, 0x35, 0x39, 0x5A,
                (byte)0xA9, 0x06,
                  0x1B, 0x04, 'a', 'b', 'c', 'd',
                (byte)0xAA, 0x13,
                  0x30, 0x11, 
                    (byte)0xA0, 0x03, 
                      0x02, 0x01, 0x01, 
                    (byte)0xA1, 0x0A, 
                      0x30, 0x08,
                        0x1B, 0x06, 
                          0x61, 0x62, 0x63, 0x64, 0x65, 0x66,
        });

        stream.flip();

        // Allocate a EncAsRepPart Container
        EncAsRepPartContainer encAsRepPartContainer = new EncAsRepPartContainer( stream );
        
        // Decode the EncAsRepPart PDU
        try
        {
            kerberosDecoder.decode( stream, encAsRepPartContainer );
        }
        catch ( DecoderException de )
        {
            fail( de.getMessage() );
        }

        EncAsRepPart encAsRepPart = encAsRepPartContainer.getEncAsRepPart();
        
        // Check the encoding
        int length = encAsRepPart.computeLength();

        // Check the length
        assertEquals( 0xA2, length );
        
        // Check the encoding
        ByteBuffer encodedPdu = ByteBuffer.allocate( length );
        
        try
        {
            encodedPdu = encAsRepPart.encode( encodedPdu );
            
            // Check the length
            assertEquals( 0xA2, encodedPdu.limit() );
        }
        catch ( EncoderException ee )
        {
            fail();
        }
    }
    
    
    /**
     * Test the decoding of a EncAsRepPart with nothing in it
     */
    @Test( expected = DecoderException.class)
    public void testEncAsRepPartEmpty() throws DecoderException
    {
        Asn1Decoder kerberosDecoder = new Asn1Decoder();

        ByteBuffer stream = ByteBuffer.allocate( 0x02 );
        
        stream.put( new byte[]
            { 0x79, 0x00 } );

        stream.flip();

        // Allocate a EncAsRepPart Container
        Asn1Container encAsRepPartContainer = new EncAsRepPartContainer( stream );

        // Decode the EncAsRepPart PDU
        kerberosDecoder.decode( stream, encAsRepPartContainer );
        fail();
    }
    
    
    /**
     * Test the decoding of a EncAsRepPart with empty EncKdcRepPart tag
     */
    @Test( expected = DecoderException.class)
    public void testEncAsRepPartEmptyEncKdcRepPart() throws DecoderException
    {
        Asn1Decoder kerberosDecoder = new Asn1Decoder();

        ByteBuffer stream = ByteBuffer.allocate( 0x04 );
        
        stream.put( new byte[]
            { 
                0x79, 0x02,
                  0x30, 0x00
            } );

        stream.flip();

        // Allocate a EncAsRepPart Container
        Asn1Container encAsRepPartContainer = new EncAsRepPartContainer( stream );

        // Decode the EncAsRepPart PDU
        kerberosDecoder.decode( stream, encAsRepPartContainer );
        fail();
    }
}
