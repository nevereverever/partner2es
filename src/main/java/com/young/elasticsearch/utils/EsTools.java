package com.young.elasticsearch.utils;

import com.google.gson.Gson;
import com.young.elasticsearch.annotation.ESField;
import com.young.elasticsearch.annotation.ESID;
import com.young.elasticsearch.annotation.ESMetaData;
import com.young.elasticsearch.index.pojo.index.MetaData;
import com.young.elasticsearch.repository.storage.VisibleList;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*************************
 * @author: YoungLu
 * @date: 2019/12/18 15:32
 * @description: 工具类
 **************************/
public class EsTools {
    //存放类名和id对应信息的Map,用于快速过滤
    private static Map<Class, String> classIdMap = new ConcurrentHashMap<>();

    /**
     * 从对象中抽取被@ESID注解的成员变量的值
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static <M> M getESId(Object obj) throws Exception {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            ESID esid = f.getAnnotation(ESID.class);
            if (esid != null) {
                Object value = f.get(obj);
                if (value == null) {
                    return null;
                } else {
                    return (M) value;
                }
            }
        }
        return null;
    }

    /**
     * 将泛型对象转换为Json字符串,去除id以及为空的值
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String convertToJson(T t,List<String> meaningFieldsList) throws IllegalAccessException, NoSuchFieldException {
        Gson gson = new Gson();
        Map retMap = null;
        Class tClass = t.getClass();
        if ( meaningFieldsList == null || meaningFieldsList.size() == 0 ) {
            retMap = getFieldValueToMap(t);
        }else {
            retMap = new HashMap();
            for ( String meaningFieldName : meaningFieldsList ) {
                Field meaningField = tClass.getDeclaredField(meaningFieldName);
                meaningField.setAccessible(true);
                retMap.put(meaningFieldName,meaningField.get(t));
            }
        }

        return gson.toJson(retMap);
    }

    public static <T> String convertToJson(T t) throws IllegalAccessException, NoSuchFieldException {
        return convertToJson(t,null);
    }

    /**
     * 将字符串转换为对象
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T convertToObj(String source, Class clazz) {
        Gson gson = new Gson();
        return (T) gson.fromJson(source, clazz);
    }

    /**
     * 获取除了idh和被忽略字段外所有字段的名称
     * @param clazz 对象的类名
     * @return
     */
    public static List<String> getFieldNameFromObjWithoutIdAndNullField (Class clazz) {
        List<String> retList = new ArrayList<>();
        Field[] fs = clazz.getDeclaredFields();

        for ( Field field : fs ) {
            field.setAccessible(true);
            //要排除掉id字段
            if (field.getAnnotation(ESID.class) != null) {
                continue;
            }
            ESField esField = field.getAnnotation(ESField.class);
            if (esField != null && esField.exist() == false) {
                continue;
            }
            retList.add(field.getName());
        }

        return retList;
    }

    /**
     * 获取对象中所有的字段有值的map组合(排除掉id字段和@ESField exist属性为false的值)
     *
     * @return
     */
    public static <T> Map getFieldValueToMap(T t) throws IllegalAccessException {
        Map retMap = new HashMap();
        Field[] fs = t.getClass().getDeclaredFields();
        for (Field field : fs) {
            field.setAccessible(true);
            //要排除掉id字段
            if (field.getAnnotation(ESID.class) != null) {
                continue;
            }

            if (field.get(t) != null) {
                ESField esField = field.getAnnotation(ESField.class);
                //用了字段过滤,标记为非es字段的,要予以排除
                if (esField != null && esField.exist() == false) {
                    continue;
                }

                retMap.put(field.getName(), field.get(t));
            }
        }
        return retMap;
    }

    /**
     * 根据类获取注解的索引信息
     *
     * @param clazz 类
     * @return 索引信息
     */
    public static MetaData getIndexInformation(Class<?> clazz) {
        String indexName = null;
        String[] searchIndexNames = null;
        String indexType = null;
        String indexPrefix = null;
        String indexSuffixDateFormat = null;
        int numberOfShards = 0;
        int numberOfReplicas = 0;
        boolean printLog = false;

        if (clazz.getAnnotation(ESMetaData.class) != null) {
            ESMetaData esMetaData = clazz.getAnnotation(ESMetaData.class);
            indexName = esMetaData.indexName();
            searchIndexNames = esMetaData.searchIndexNames();
            indexType = esMetaData.indexType();
            indexPrefix = esMetaData.indexPrefix();
            indexSuffixDateFormat = esMetaData.indexSuffixDateFormat();
            numberOfShards = esMetaData.numberOfShards();
            numberOfReplicas = esMetaData.numberOfReplicas();
            printLog = esMetaData.printLog();

            if (StringUtils.isEmpty(indexType)) {
                indexType = "_doc";
            }
            MetaData metaData = new MetaData(indexName, indexType);
            metaData.setNumberOfShards(numberOfShards);
            metaData.setNumberOfReplicas(numberOfReplicas);
            metaData.setIndexPrefix(indexPrefix);
            metaData.setIndexSuffixDateFormat(indexSuffixDateFormat);
            metaData.setPrintLog(printLog);
            if (arrayISNULL(searchIndexNames)) {
                metaData.setSearchIndexNames(new String[]{indexName});
            } else {
                metaData.setSearchIndexNames(searchIndexNames);
            }
            return metaData;
        }
        return null;
    }

    /**
     * 判断数组是否为空
     *
     * @param objs
     * @return
     */
    public static boolean arrayISNULL(Object[] objs) {
        if (objs == null || objs.length == 0) {
            return true;
        }
        boolean flag = false;
        for (int i = 0; i < objs.length; i++) {
            if (!StringUtils.isEmpty(objs[i])) {
                flag = true;
            }
        }
        if (flag) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 拆分集合
     *
     * @param oriList 源集合
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> oriList) {
        if (oriList.size() <= EsConstant.BULK_SAVE_COUNT) {
            List<List<T>> splitList = new ArrayList<>();
            splitList.add(oriList);
            return splitList;
        }
        int limit = (oriList.size() + EsConstant.BULK_SAVE_COUNT - 1) / EsConstant.BULK_SAVE_COUNT;
        final List<List<T>> splitList = new ArrayList<>();
        Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {
            splitList.add(oriList.stream().skip(i * EsConstant.BULK_SAVE_COUNT).limit(EsConstant.BULK_SAVE_COUNT).collect(Collectors.toCollection(VisibleList::new)));
        });
        return splitList;
    }



    /**
     * 将_id的值重新赋值给对象中指定字段
     * @param aClass 类名
     * @param hitObj 对象
     * @param _id 文档的id值
     * @param <T>
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static <T> void correctIDToObj(Class<?> aClass, T hitObj, String _id) throws NoSuchFieldException, IllegalAccessException {
        if (StringUtils.isEmpty(_id)) {
            return;
        }
        //内存中有直接在内存取,将这个id字段的值设置为传过来的字段值
        if (classIdMap.containsKey(aClass)) {
            Field field = aClass.getDeclaredField(classIdMap.get(aClass));
            field.setAccessible(true);
            field.set(hitObj, _id);
            return;
        }

        //内存中没有则先进行过滤,得到被@ESID注解的字段名称,并放入内存
        for (int i = 0; i < aClass.getDeclaredFields().length; i++) {
            Field field = aClass.getDeclaredFields()[i];
            field.setAccessible(true);
            if (field.getAnnotation(ESID.class) != null) {
                classIdMap.put(aClass, field.getName());
                field.set(hitObj, _id);
            }
        }
    }


    /**
     * 得到将高亮的map中字段挑出来,拼接好并放进对象中
     * @param map
     * @param clazz
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object highLightMapToObj(Map<String, HighlightField> map , Class clazz ,List<String> ignoredProperties) throws IllegalAccessException, InstantiationException {
        if (map == null) {
            return null;
        }
        Object obj = clazz.newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            HighlightField highlightField = map.get(field.getName());
            if ( !StringUtils.isEmpty(highlightField) ) {
                field.setAccessible(true);
                StringBuilder highLightTextBuilder = new StringBuilder();
                if ( highlightField.fragments().length > 0) {
                    Text[] highLightTexts = highlightField.getFragments();
                    //拼接高亮片段
                    int highLightTextsLength = highLightTexts.length;
                    int i = 0;
                    do {
                        highLightTextBuilder.append(highLightTexts[i].string());
                        highLightTextBuilder.append("...");
                        i++;
                    }while ( i < highLightTextsLength - 1 );
                    field.set(obj, highLightTextBuilder.toString());
                }
            }else {
                ignoredProperties.add(field.getName());
            }
        }
        return obj;
    }


}
