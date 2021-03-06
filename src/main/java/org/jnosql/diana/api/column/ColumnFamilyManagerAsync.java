/*
 *
 *  Copyright (c) 2017 Otávio Santana and others
 *   All rights reserved. This program and the accompanying materials
 *   are made available under the terms of the Eclipse Public License v1.0
 *   and Apache License v2.0 which accompanies this distribution.
 *   The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 *   and the Apache License v2.0 is available at http://www.opensource.org/licenses/apache2.0.php.
 *
 *   You may elect to redistribute this code under either of these licenses.
 *
 *   Contributors:
 *
 *   Otavio Santana
 *
 */

package org.jnosql.diana.api.column;


import org.jnosql.diana.api.ExecuteAsyncQueryException;
import org.jnosql.diana.api.NonUniqueResultException;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

/**
 * Interface used to interact with the persistence context to {@link ColumnEntity}
 * The ColumnFamilyManager API is used to create and remove persistent {@link ColumnEntity} instances,
 * to select entities by their primary key, and to select over entities. The main difference to {@link ColumnFamilyManager}
 * is because all the operation works asynchronously.
 */
public interface ColumnFamilyManagerAsync extends AutoCloseable {


    /**
     * Saves an entity asynchronously
     *
     * @param entity entity to be saved
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not support this feature
     * @throws NullPointerException          when entity is null
     */
    void insert(ColumnEntity entity) throws ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException;

    /**
     * Saves an entity asynchronously with time to live
     *
     * @param entity entity to be saved
     * @param ttl    time to live
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not support this feature
     * @throws NullPointerException          when the entity is null
     */
    void insert(ColumnEntity entity, Duration ttl) throws ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException;

    /**
     * Saves an entity asynchronously
     *
     * @param entity   entity to be saved
     * @param callBack the callback, when the process is finished will call this instance returning the saved entity
     *                 within parameters
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not support this feature
     * @throws NullPointerException          when either entity or callback are null
     */
    void insert(ColumnEntity entity, Consumer<ColumnEntity> callBack) throws ExecuteAsyncQueryException,
            UnsupportedOperationException, NullPointerException;


    /**
     * Saves an entities asynchronously, by default it's just run for each saving using
     * {@link ColumnFamilyManagerAsync#insert(ColumnEntity)},
     * each NoSQL vendor might replace to a more appropriate one.
     *
     * @param entities entity to be saved
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not support this feature
     * @throws NullPointerException          when entities is null
     */
    default void insert(Iterable<ColumnEntity> entities) throws ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException {
        Objects.requireNonNull(entities, "entities is required");
        StreamSupport.stream(entities.spliterator(), false).forEach(this::insert);
    }

    /**
     * Saves an entities asynchronously with time to live, by default it's just run for each saving using
     * {@link ColumnFamilyManagerAsync#insert(ColumnEntity, Duration)},
     * each NoSQL vendor might replace to a more appropriate one.
     *
     * @param entities entity to be saved
     * @param ttl      time to live
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not support this feature
     * @throws NullPointerException          when either entities or ttl are null
     */
    default void insert(Iterable<ColumnEntity> entities, Duration ttl) throws ExecuteAsyncQueryException,
            UnsupportedOperationException, NullPointerException {
        Objects.requireNonNull(entities, "entities is required");
        Objects.requireNonNull(ttl, "ttl is required");
        StreamSupport.stream(entities.spliterator(), false).forEach(c -> this.insert(c, ttl));
    }


    /**
     * Saves an entity asynchronously
     *
     * @param entity   entity to be saved
     * @param ttl      time to live
     * @param callBack the callback, when the process is finished will call this instance returning the saved entity
     *                 within parameters
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not support this feature
     * @throws NullPointerException          when either entity or ttl or callback are null
     */
    void insert(ColumnEntity entity, Duration ttl, Consumer<ColumnEntity> callBack) throws ExecuteAsyncQueryException,
            UnsupportedOperationException, NullPointerException;


    /**
     * Updates an entity asynchronously
     *
     * @param entity entity to be saved
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not support this feature
     * @throws NullPointerException          when entity is null
     */
    void update(ColumnEntity entity) throws ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException;

    /**
     * Updates an entities asynchronously, by default it's just run for each saving using
     * {@link ColumnFamilyManagerAsync#update(ColumnEntity)},
     * each NoSQL vendor might replace to a more appropriate one.
     *
     * @param entities entity to be saved
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not support this feature
     * @throws NullPointerException          when entities is null
     */
    default void update(Iterable<ColumnEntity> entities) throws ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException {
        Objects.requireNonNull(entities, "entities is required");
        StreamSupport.stream(entities.spliterator(), false).forEach(this::update);
    }

    /**
     * Updates an entity asynchronously
     *
     * @param entity   entity to be saved
     * @param callBack the callback, when the process is finished will call this instance returning
     *                 the updated entity within parameters
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not support this feature
     * @throws NullPointerException          when either entity, callback are null
     */
    void update(ColumnEntity entity, Consumer<ColumnEntity> callBack) throws
            ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException;


    /**
     * Deletes an entity asynchronously
     *
     * @param query select to delete an entity
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not support this feature
     * @throws NullPointerException          when select is null
     */
    void delete(ColumnDeleteQuery query) throws ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException;

    /**
     * Deletes an entity asynchronously
     *
     * @param query    select to delete an entity
     * @param callBack the callback, when the process is finished will call this instance returning
     *                 the null within parameters
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not support this feature
     * @throws NullPointerException          when either select or callback are null
     */
    void delete(ColumnDeleteQuery query, Consumer<Void> callBack) throws ExecuteAsyncQueryException,
            UnsupportedOperationException, NullPointerException;


    /**
     * Finds {@link ColumnEntity} from select asynchronously
     *
     * @param query    select to select entities
     * @param callBack the callback, when the process is finished will call this instance returning the
     *                 result of select within parameters
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not support this feature
     * @throws NullPointerException          when either select or callback are null
     */
    void select(ColumnQuery query, Consumer<List<ColumnEntity>> callBack) throws ExecuteAsyncQueryException,
            UnsupportedOperationException, NullPointerException;

    /**
     * Returns a single entity from select
     *
     * @param query    - select to figure out entities
     * @param callBack the callback
     * @throws NonUniqueResultException when the result has more than 1 entity
     * @throws NullPointerException     when select is null
     */
    default void singleResult(ColumnQuery query, Consumer<Optional<ColumnEntity>> callBack) throws NonUniqueResultException, NullPointerException {
        select(query, entities -> {
            if (entities.isEmpty()) {
                callBack.accept(Optional.empty());
                return;
            } else if (entities.size() == 1) {
                callBack.accept(Optional.of(entities.get(0)));
                return;
            }
            throw new NonUniqueResultException("The select returns more than one entity, select: " + query);
        });

    }

    /**
     * closes a resource
     */
    void close();

}
