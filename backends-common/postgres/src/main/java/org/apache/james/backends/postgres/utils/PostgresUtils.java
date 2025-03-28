/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/

package org.apache.james.backends.postgres.utils;

import java.util.Optional;
import java.util.function.Predicate;

import org.jooq.exception.DataAccessException;

import com.google.common.base.Preconditions;

public class PostgresUtils {
    private static final String UNIQUE_CONSTRAINT_VIOLATION_MESSAGE = "duplicate key value violates unique constraint";

    public static final Predicate<Throwable> UNIQUE_CONSTRAINT_VIOLATION_PREDICATE =
        throwable -> throwable instanceof DataAccessException && throwable.getMessage().contains(UNIQUE_CONSTRAINT_VIOLATION_MESSAGE);

    public static final int QUERY_BATCH_SIZE_DEFAULT_VALUE = 5000;
    public static final int QUERY_BATCH_SIZE = Optional.ofNullable(System.getProperty("james.postgresql.query.batch.size"))
        .map(Integer::valueOf)
        .map(batchSize -> {
            Preconditions.checkArgument(batchSize > 0, "james.postgresql.query.batch.size must be positive");
            return batchSize;
        })
        .orElse(QUERY_BATCH_SIZE_DEFAULT_VALUE);
}
