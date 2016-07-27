package org.apache.diana.api.document;


import org.apache.diana.api.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Class that contains information to do a query to {@link DocumentCollectionManager}
 *
 * @see DocumentCollectionManager#find(DocumentQuery)
 * @see DocumentCondition
 * @see Sort
 */
public class DocumentQuery {

    private final String collection;

    private final List<DocumentCondition> conditions = new ArrayList<>();

    private final List<Sort> sorts = new ArrayList<>();

    private DocumentQuery(String collection) {
        this.collection = Objects.requireNonNull(collection, "column family is required");
    }

    /**
     * Creates a {@link DocumentQuery}
     *
     * @param documentCollection - the name of document collection to do a query
     * @return a {@link DocumentQuery} instance
     */
    public static DocumentQuery of(String documentCollection) {
        return new DocumentQuery(documentCollection);
    }

    /**
     * Add a new condition in the query
     *
     * @param condition condition to be added
     * @return the same instance with a condition added
     * @throws NullPointerException when condition is null
     */
    public DocumentQuery addCondition(DocumentCondition condition) {
        this.conditions.add(Objects.requireNonNull(condition, "condition is required"));
        return this;
    }

    /**
     * Add the order how the result will returned
     *
     * @param sort the order way
     * @return the same way with a sort added
     */
    public DocumentQuery addSort(Sort sort) {
        this.sorts.add(Objects.requireNonNull(sort, "Sort is required"));
        return this;
    }


    /**
     * The document collection name
     *
     * @return the document collection name
     */
    public String getCollection() {
        return collection;
    }

    /**
     * The conditions that contains in this {@link DocumentQuery}
     *
     * @return the conditions
     */
    public List<DocumentCondition> getConditions() {
        return Collections.unmodifiableList(conditions);
    }

    /**
     * The sorts that contains in this {@link DocumentQuery}
     *
     * @return the sorts
     */
    public List<Sort> getSorts() {
        return Collections.unmodifiableList(sorts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DocumentQuery that = (DocumentQuery) o;
        return Objects.equals(collection, that.collection) &&
                Objects.equals(conditions, that.conditions) &&
                Objects.equals(sorts, that.sorts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collection, conditions, sorts);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DocumentQuery{");
        sb.append("collection='").append(collection).append('\'');
        sb.append(", conditions=").append(conditions);
        sb.append(", sorts=").append(sorts);
        sb.append('}');
        return sb.toString();
    }
}
