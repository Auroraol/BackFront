/*
 * Copyright 2011-2013 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.xiaolyuh.redis.serializer;


import com.github.xiaolyuh.support.NestedRuntimeException;

/**
 * Generic exception indicating a serialization/deserialization error.
 *
 * @author Costin Leau
 */
public class SerializationException extends NestedRuntimeException {

    /**
     * Constructs a new <code>SerializationException</code> instance.
     *
     * @param msg   msg
     * @param cause 原因
     */
    public SerializationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Constructs a new <code>SerializationException</code> instance.
     *
     * @param msg msg
     */
    public SerializationException(String msg) {
        super(msg);
    }
}
