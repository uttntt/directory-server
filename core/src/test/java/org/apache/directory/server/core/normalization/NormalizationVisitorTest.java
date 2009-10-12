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
package org.apache.directory.server.core.normalization;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.List;

import org.apache.directory.shared.ldap.filter.EqualityNode;
import org.apache.directory.shared.ldap.filter.ExprNode;
import org.apache.directory.shared.ldap.filter.FilterParser;
import org.apache.directory.shared.ldap.filter.NotNode;
import org.apache.directory.shared.ldap.name.NameComponentNormalizer;
import org.apache.directory.shared.ldap.schema.normalizers.ConcreteNameComponentNormalizer;
import org.apache.directory.shared.ldap.schema.registries.AttributeTypeRegistry;
import org.apache.directory.shared.ldap.schema.registries.Registries;
import org.apache.directory.shared.ldap.util.ExceptionUtils;
import org.apache.directory.shared.schema.loader.ldif.JarLdifSchemaLoader;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * A class to test the normalizing Visitor
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 *
 */
public class NormalizationVisitorTest
{
    /** a filter node value normalizer and undefined node remover */
    private static FilterNormalizingVisitor normVisitor;
    
    /** A reference to the registries */
    private static Registries registries;
    
    @BeforeClass
    public static void init() throws Exception
    {
        registries = new Registries();
        JarLdifSchemaLoader loader = new JarLdifSchemaLoader();

        List<Throwable> errors = loader.loadAllEnabled( registries, true );
        
        if ( errors.size() != 0 )
        {
            fail( "Schema load failed : " + ExceptionUtils.printErrors( errors ) );
        }

        AttributeTypeRegistry attributeRegistry = registries.getAttributeTypeRegistry();
        NameComponentNormalizer ncn = new ConcreteNameComponentNormalizer( attributeRegistry );
        normVisitor = new FilterNormalizingVisitor( ncn, registries );
    }

    @Test
    public void testSimpleFilter() throws ParseException
    {
        ExprNode filter = FilterParser.parse( "(ou=  test  1 )" );
        ExprNode result = ( ExprNode ) filter.accept( normVisitor );
        
        assertNotNull( result );
        assertTrue( result instanceof EqualityNode<?> );
        EqualityNode<?> equalityNode = (EqualityNode<?>)result;
        
        assertEquals( "test 1", equalityNode.getValue().getNormalizedValue() );
        assertEquals( "2.5.4.11", equalityNode.getAttribute() );
    }
    
    
    @Test
    public void testBranchNormalizedVisitor() throws Exception
    {
        ExprNode filter = FilterParser.parse( "(!(|(uniqueMember=cn=user1,ou=Test,dc=example,dc=com)(member=cn=user2,ou=Test,dc=example,dc=com)))" );
        ExprNode result = ( ExprNode ) filter.accept( normVisitor );

        assertNotNull( result );
        assertTrue( result instanceof NotNode );
    }

}
