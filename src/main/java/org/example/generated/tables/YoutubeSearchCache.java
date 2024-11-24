/*
 * This file is generated by jOOQ.
 */
package org.example.generated.tables;


import java.time.LocalDateTime;
import java.util.Collection;

import org.example.generated.Keys;
import org.example.generated.Public;
import org.example.generated.tables.records.YoutubeSearchCacheRecord;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.JSONB;
import org.jooq.Name;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * The table <code>public.youtube_search_cache</code>.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class YoutubeSearchCache extends TableImpl<YoutubeSearchCacheRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.youtube_search_cache</code>
     */
    public static final YoutubeSearchCache YOUTUBE_SEARCH_CACHE = new YoutubeSearchCache();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<YoutubeSearchCacheRecord> getRecordType() {
        return YoutubeSearchCacheRecord.class;
    }

    /**
     * The column <code>public.youtube_search_cache.id</code>.
     */
    public final TableField<YoutubeSearchCacheRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.youtube_search_cache.query</code>.
     */
    public final TableField<YoutubeSearchCacheRecord, String> QUERY = createField(DSL.name("query"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.youtube_search_cache.results</code>.
     */
    public final TableField<YoutubeSearchCacheRecord, JSONB> RESULTS = createField(DSL.name("results"), SQLDataType.JSONB, this, "");

    /**
     * The column <code>public.youtube_search_cache.created_at</code>.
     */
    public final TableField<YoutubeSearchCacheRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.LOCALDATETIME(6).defaultValue(DSL.field(DSL.raw("CURRENT_TIMESTAMP"), SQLDataType.LOCALDATETIME)), this, "");

    private YoutubeSearchCache(Name alias, Table<YoutubeSearchCacheRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private YoutubeSearchCache(Name alias, Table<YoutubeSearchCacheRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.youtube_search_cache</code> table
     * reference
     */
    public YoutubeSearchCache(String alias) {
        this(DSL.name(alias), YOUTUBE_SEARCH_CACHE);
    }

    /**
     * Create an aliased <code>public.youtube_search_cache</code> table
     * reference
     */
    public YoutubeSearchCache(Name alias) {
        this(alias, YOUTUBE_SEARCH_CACHE);
    }

    /**
     * Create a <code>public.youtube_search_cache</code> table reference
     */
    public YoutubeSearchCache() {
        this(DSL.name("youtube_search_cache"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<YoutubeSearchCacheRecord, Integer> getIdentity() {
        return (Identity<YoutubeSearchCacheRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<YoutubeSearchCacheRecord> getPrimaryKey() {
        return Keys.YOUTUBE_SEARCH_CACHE_PKEY;
    }

    @Override
    public YoutubeSearchCache as(String alias) {
        return new YoutubeSearchCache(DSL.name(alias), this);
    }

    @Override
    public YoutubeSearchCache as(Name alias) {
        return new YoutubeSearchCache(alias, this);
    }

    @Override
    public YoutubeSearchCache as(Table<?> alias) {
        return new YoutubeSearchCache(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public YoutubeSearchCache rename(String name) {
        return new YoutubeSearchCache(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public YoutubeSearchCache rename(Name name) {
        return new YoutubeSearchCache(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public YoutubeSearchCache rename(Table<?> name) {
        return new YoutubeSearchCache(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public YoutubeSearchCache where(Condition condition) {
        return new YoutubeSearchCache(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public YoutubeSearchCache where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public YoutubeSearchCache where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public YoutubeSearchCache where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public YoutubeSearchCache where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public YoutubeSearchCache where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public YoutubeSearchCache where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public YoutubeSearchCache where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public YoutubeSearchCache whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public YoutubeSearchCache whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
