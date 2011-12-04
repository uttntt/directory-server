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
package org.apache.directory.server.component;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.directory.server.component.hub.ComponentManager;
import org.apache.directory.server.component.instance.CachedComponentInstance;
import org.apache.directory.server.component.instance.ComponentInstance;
import org.apache.directory.server.component.schema.ADSComponentSchema;
import org.apache.felix.ipojo.Factory;


/**
 * Class that represents a component for ApacheDS use.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public class ADSComponent
{
    /*
     * IPojo factory reference for component
     */
    private Factory factory;

    /*
     * Component's type
     */
    private String componentType = "user";

    /*
     * Name of the component(Its IPojo factory name in normalized form).
     */
    private String componentName;

    /*
     * Version of the component.
     * (Implementing factory's Bundle version/Subject to change).
     */
    private String componentVersion;

    /*
     * List holding all the created instances.
     */
    private List<ComponentInstance> activeInstances;

    /*
     * List holding all the cached instances.
     */
    private List<CachedComponentInstance> cachedInstances;

    /*
     * Generated/Choosed schema information
     */
    private ADSComponentSchema schema;

    /*
     * Default configuration to instantiate instances of that component.
     */
    private Properties defaultConfiguration;

    /*
     * internal component manager
     */
    private ComponentManager componentManager;


    public ADSComponent( ComponentManager componentManager )
    {
        this.componentManager = componentManager;

        activeInstances = new ArrayList<ComponentInstance>();
    }


    /**
     * Adds an instance to a instances list
     *
     * @param instance instance reference to add to a list
     */
    public void addInstance( ComponentInstance instance )
    {
        activeInstances.add( instance );
    }


    /**
     * Removes an instance from instances list
     *
     * @param instance to remove from the list
     */
    public void removeInstance( ComponentInstance instance )
    {
        activeInstances.remove( instance );
    }


    /**
     * Sets the cached instances.
     *
     * @param cachedList List of CachedComponentInstances to set for this component.
     */
    public void setCachedInstances( List<CachedComponentInstance> cachedList )
    {
        cachedInstances = cachedList;
    }


    /**
     * Gets the current instances of the component
     *
     * @return Cloned ComponentInstance list.
     */
    public List<ComponentInstance> getInstances()
    {
        return new ArrayList<ComponentInstance>( activeInstances );
    }


    /**
     * @return the factory
     */
    public Factory getFactory()
    {
        return factory;
    }


    /**
     * @param factory the factory to set
     */
    public void setFactory( Factory factory )
    {
        this.factory = factory;
    }


    /**
     * @return the componentType
     */
    public String getComponentType()
    {
        return componentType;
    }


    /**
     * @param componentType the componentType to set
     */
    public void setComponentType( String componentType )
    {
        this.componentType = componentType;
    }


    /**
     * @return the schema
     */
    public ADSComponentSchema getSchema()
    {
        return schema;
    }


    /**
     * @param schema the schema to set
     */
    public void setSchema( ADSComponentSchema schema )
    {
        this.schema = schema;
    }


    /**
     * @return the componentName
     */
    public String getComponentName()
    {
        return componentName;
    }


    /**
     * @param componentName the componentName to set
     */
    public void setComponentName( String componentName )
    {
        this.componentName = componentName;
    }


    /**
     * @return the componentVersion
     */
    public String getComponentVersion()
    {
        return componentVersion;
    }


    /**
     * @param componentVersion the componentVersion to set
     */
    public void setComponentVersion( String componentVersion )
    {
        this.componentVersion = componentVersion;
    }


    @Override
    public String toString()
    {
        return getComponentType() + ":" + getComponentName() + ":" + getComponentVersion();
    }


    /**
     * @return the defaultConfiguration
     */
    public Properties getDefaultConfiguration()
    {
        return defaultConfiguration;
    }


    /**
     * @param defaultConfiguration the defaultConfiguration to set
     */
    public void setDefaultConfiguration( Properties defaultConfiguration )
    {
        this.defaultConfiguration = defaultConfiguration;
    }

}
