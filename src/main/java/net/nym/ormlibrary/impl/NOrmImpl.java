/*
 * Copyright (c) 2017  Ni YueMing<niyueming@163.com>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 */

package net.nym.ormlibrary.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.SQLiteHelper;
import com.litesuits.orm.db.assit.WhereBuilder;
import com.litesuits.orm.db.model.ColumnsValue;

import net.nym.ormlibrary.BuildConfig;
import net.nym.ormlibrary.operation.NOrm;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author niyueming
 * @date 2017-01-09
 * @time 10:42
 */

public final class NOrmImpl implements NOrm,SQLiteHelper.OnUpdateListener {

    private static NOrmImpl my;
    private LiteOrm mLiteOrm;
    private static String mDbName = "default.db";
    private static int mDbVersion = 1;

    private NOrmImpl(Context context, String dbName, int dbVersion){
        mDbName = dbName;
        mDbVersion = dbVersion;
        initLiteOrm(context, dbName, dbVersion);

    }

    /**
     *
     * @param context
     * @param dbName
     * @param dbVersion
     * @return
     *
     * @hide
     */
    private static NOrm getInstance(Context context,String dbName,int dbVersion){
        if (my == null){
            synchronized (NOrmImpl.class){
                if (my == null){
                    my = new NOrmImpl(context,dbName,dbVersion);
                }
            }
        }
        return my;
    }

    public static NOrm getInstance(Context context){
        return getInstance(context,mDbName,mDbVersion);
    }

    private void initLiteOrm(Context context, String dbName, int dbVersion) {
        DataBaseConfig config = new DataBaseConfig(context);
        config.dbName = dbName;
        config.dbVersion = dbVersion;
        config.onUpdateListener = this;
        config.debugged = BuildConfig.DEBUG;
//        mLiteOrm = LiteOrm.newCascadeInstance(config);    //支持联级操作
        mLiteOrm = LiteOrm.newSingleInstance(config);
    }

    public LiteOrm getLiteOrm(Context context){
        if (mLiteOrm == null){
            initLiteOrm(context,mDbName,mDbVersion);
        }
        return mLiteOrm;
    }

    /**
     * {@link com.litesuits.orm.db.assit.SQLiteHelper.OnUpdateListener}
     */
    @Override
    public void onUpdate(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public SQLiteDatabase openOrCreateDatabase() {
        return mLiteOrm.openOrCreateDatabase();
    }

    @Override
    public long save(Object entity) {
        if (entity == null){
            return -1;
        }
        return mLiteOrm.save(entity);
    }

    @Override
    public <T> int save(Collection<T> collection) {
        return mLiteOrm.save(collection);
    }

    @Override
    public long insert(Object entity) {
        if (entity == null){
            return -1;
        }
        return mLiteOrm.insert(entity);
    }

    @Override
    public <T> int insert(Collection<T> collection) {
        return mLiteOrm.insert(collection);
    }

    @Override
    public int update(Object entity) {
        if (entity == null){
            return -1;
        }
        return mLiteOrm.update(entity);
    }

    @Override
    public int update(Object entity, String[] columns){
        return mLiteOrm.update(entity,new ColumnsValue(columns),null);
    }

    @Override
    public <T> int update(Collection<T> collection) {
        return mLiteOrm.update(collection);
    }

    @Override
    public <T> int update(Collection<T> collection, String[] columns){
        return mLiteOrm.update(collection,new ColumnsValue(columns),null);
    }

    @Override
    public <T> int update(Class<T> claxx,String where, Object[] whereArgs, String[] columns, Object[] values){
        if (columns == null || values == null){
            return mLiteOrm.update(WhereBuilder.create(claxx,where,whereArgs),null,null);
        }
        if(columns.length != values.length) {
            throw new IllegalArgumentException("length of columns and values must be the same");
        }
        return mLiteOrm.update(WhereBuilder.create(claxx,where,whereArgs),new ColumnsValue(columns,values),null);
    }

    @Override
    public int delete(Object entity) {
        if (entity == null){
            return -1;
        }
        return mLiteOrm.delete(entity);
    }

    @Override
    public <T> int delete(Class<T> claxx) {
        return mLiteOrm.delete(claxx);
    }

    @Override
    public <T> int deleteAll(Class<T> claxx) {
        return mLiteOrm.deleteAll(claxx);
    }

    @Override
    public <T> int delete(Class<T> claxx, long start, long end, String orderAscColu) {
        return mLiteOrm.delete(claxx,start,end,orderAscColu);
    }

    @Override
    public <T> int delete(Collection<T> collection) {
        return mLiteOrm.delete(collection);
    }

    @Override
    public <T> int delete(Class<T> claxx, String where, Object[] whereArgs){
        WhereBuilder builder = WhereBuilder.create(claxx,where,whereArgs);
        return mLiteOrm.delete(builder);
    }
    @Override
    public <T> ArrayList<T> query(Class<T> claxx) {
        return mLiteOrm.query(claxx);
    }


    @Override
    public <T> ArrayList<T> query(Class<T> claxx,String where, Object... whereArgs){
        return query(QueryBuilder.create(claxx).where(where,whereArgs));
    }

    public <T> ArrayList<T> query(QueryBuilder<T> qb){
        return mLiteOrm.query(qb);
    }

    @Override
    public <T> T queryById(long id, Class<T> clazz) {
        return mLiteOrm.queryById(id,clazz);
    }

    @Override
    public <T> T queryById(String id, Class<T> clazz) {
        return mLiteOrm.queryById(id,clazz);
    }

    @Override
    public <T> long queryCount(Class<T> claxx) {
        return mLiteOrm.queryCount(claxx);
    }

    @Override
    public boolean dropTable(Class<?> claxx) {
        return mLiteOrm.dropTable(claxx);
    }

    @Override
    public boolean dropTable(String tableName) {
        return mLiteOrm.dropTable(tableName);
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return mLiteOrm.getReadableDatabase();
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return mLiteOrm.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String path, SQLiteDatabase.CursorFactory factory) {
        return mLiteOrm.openOrCreateDatabase(path,factory);
    }

    @Override
    public boolean deleteDatabase() {
        return mLiteOrm.deleteDatabase();
    }

    @Override
    public boolean deleteDatabase(File file) {
        return mLiteOrm.deleteDatabase(file);
    }

    @Override
    public void close() {
        mLiteOrm.close();
    }
}
