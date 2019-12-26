package com.young.elasticsearch.index.configure.settings;

import com.young.elasticsearch.index.configure.settings.analysis.pojo.Analysis;
import com.young.elasticsearch.index.configure.settings.analysis.pojo.analyzer.*;
import com.young.elasticsearch.index.configure.settings.analysis.pojo.char_filter.*;
import com.young.elasticsearch.index.configure.settings.analysis.pojo.filter.*;
import com.young.elasticsearch.index.configure.settings.analysis.pojo.normalizer.Normalizer;
import com.young.elasticsearch.index.configure.settings.analysis.pojo.tokenizer.*;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * 初始化创建index时用于构建settings的工具类
 *
 * @author young
 * @date 2017-09-15
 */
public class EsSettings {

    /**
     * 指定分片设置以及分词器构造builder
     *
     * @param shardsNum   主分片数
     * @param replicasNum 副本分片数
     * @param analysis    分析器
     * @return tokenizerBuilder
     * @throws IOException
     */
    public static XContentBuilder settingsBuilder(int shardsNum, int replicasNum, Analysis analysis) throws IOException {
        XContentBuilder builder = null;
        builder = XContentFactory.jsonBuilder().startObject();

        // 主分片，副分片配置
        builder.field("number_of_shards", shardsNum).field("number_of_replicas", replicasNum);

        // 若analysis不为空，定义analysis
        if (analysis != null) {

            builder.startObject("analysis");

            /************************************
             * 1.定义char_filter
             ************************************/
            List<CharacterFilters> characterFiltersList = analysis.getCharacterFiltersList();
            characterFiltersBuilder(builder, characterFiltersList);

            /************************************
             * 2.定义tokenizer
             ************************************/
            List<Tokenizer> tokenizerList = analysis.getTokenizerList();
            tokenizersBuilder(builder, tokenizerList);

            /************************************
             * 3.定义filter
             ************************************/
            List<Filters> filtersList = analysis.getFilterList();
            filtersBuilder(builder, filtersList);

            /************************************
             * 4.定义规范器
             ************************************/
            List<Normalizer> normalizersList = analysis.getNormalizerList();

            normalizer(builder, normalizersList);

            /************************************
             * 5.定义分词器
             ************************************/
            List<Analyzer> analyzersList = analysis.getAnalyzerList();
            analyzer(builder, analyzersList);

            builder = builder.endObject();
        }

        builder = builder.endObject();// 结束定义settings
        return builder;
    }

    /**
     * 将规范器对象转换为规范器builder
     *
     * @param builder         构造器
     * @param normalizersList 规范器集合
     * @return 规范器构造对象
     * @throws IOException
     */
    private static XContentBuilder normalizer(XContentBuilder builder, List<Normalizer> normalizersList)
            throws IOException {
        if (normalizersList == null || normalizersList.size() == 0) {
            return null;
        }
        builder = builder.startObject("normalizer");

        for (Normalizer normalizer : normalizersList) {
            String normalizerName = normalizer.getName();
            if (StringUtils.isEmpty(normalizerName)) {
                throw new NullPointerException("规范器名称不能为空,请检查入参.");
            }
            // 规范器类型统一为"custom"
            builder = builder.startObject(normalizerName).field("type", normalizer.getNormalizerType());

            // 加入字符过滤
            CharacterFilters[] characterFilters = normalizer.getCharacterFilters();
            if (characterFilters != null && characterFilters.length != 0) {
                builder = builder.startArray("char_filter");
                for (CharacterFilters characterFilter : characterFilters) {
                    String characterFiltersName = characterFilter.getName();// 字符过滤器名称
                    builder = builder.value(characterFiltersName);
                }
                builder = builder.endArray();
            }

            // 加入过滤器
            Filters[] filters = normalizer.getFilter();
            if (filters != null && filters.length != 0) {
                builder = builder.startArray("filter");
                for (Filters filter : filters) {
                    builder = builder.value(filter.getName());
                }
                builder = builder.endArray();
            }
            builder = builder.endObject();
        }
        builder = builder.endObject();

        return builder;
    }

    /**
     * 定义分词
     *
     * @param builder       构造器
     * @param analyzersList 分词器集合
     * @return 构造对象
     * @throws IOException
     */
    private static void analyzer(XContentBuilder builder, List<Analyzer> analyzersList)
            throws IOException {
        // 如果分词器为空，直接返回
        if (analyzersList == null || analyzersList.size() == 0) {
            return;
        }

        /************************
         * 定义analyzer
         ***********************/
        analyzersBuilder(builder, analyzersList);// 构建anlyzer
    }

    /**
     * 定义tokenizer
     *
     * @param builder       构造对象
     * @param tokenizerList 标记器对象
     * @return tokenizer构造对象
     * @throws IOException
     */
    private static void tokenizersBuilder(XContentBuilder builder, List<Tokenizer> tokenizerList)
            throws IOException {
        // 令牌解析器集合为空,怎么进来怎么出去
        if (tokenizerList == null || tokenizerList.size() == 0) {
            return;
        }
        // 开始tokenizer定义
        builder = builder.startObject("tokenizer");

        for (Tokenizer tokenizer : tokenizerList) {
            // 令牌解析器名称
            String tokenizerName = tokenizer.getName();
            // 令牌解析器类型
            TokenizerType tokenizerType = tokenizer.getType();

            // 令牌解析器名称不可为空
            if (tokenizerName == null || "".equals(tokenizerName)) {
                throw new NullPointerException("自定义令牌解析器名称不能为空!");
            }

            // 令牌类型不可为空
            if (tokenizerType == null) {
                throw new NullPointerException("自定义令牌解析器类型值不能为空!");
            }
            // 不允许定义不存在的令牌解析器
            if (!TokenizerType.IK.equals(tokenizerType) && !TokenizerType.STANDARD.equals(tokenizerType)
                    && !TokenizerType.WHITESPACE.equals(tokenizerType) && !TokenizerType.LOWERCASE.equals(tokenizerType)
                    && !TokenizerType.CLASSIC.equals(tokenizerType)
                    && !TokenizerType.UAX_URL_EMAIL.equals(tokenizerType) && !TokenizerType.NGRAM.equals(tokenizerType)
                    && !TokenizerType.EDGE_NGRAM.equals(tokenizerType) && !TokenizerType.KEYWORD.equals(tokenizerType)
                    && !TokenizerType.PATTERN.equals(tokenizerType)
                    && !TokenizerType.SIMPLE_PATTERN.equals(tokenizerType)
                    && !TokenizerType.SIMPLE_PATTERN_SPLIT.equals(tokenizerType)
                    && !TokenizerType.PATH_HIRRARCHY.equals(tokenizerType)
                    && !TokenizerType.LETTER.equals(tokenizerType) && !TokenizerType.THAI.equals(tokenizerType)) {
                throw new NullPointerException("自定义令牌解析器出错：不存在" + tokenizerType.getValue() + "类型的令牌解析器!");
            }
            // 定义名称和类型
            builder = builder.startObject(tokenizerName).field("type", tokenizerType.getValue());
            // 以下解析器存在参数
            if (TokenizerType.STANDARD.equals(tokenizerType)) {
                // 转为子类型
                StandardTokenizer standardTokenizer = (StandardTokenizer) tokenizer;
                builder = builder.field("max_token_length", standardTokenizer.getMax_token_length());
            } else if (TokenizerType.WHITESPACE.equals(tokenizerType)) {
                // 转为子类型
                WhitespaceTokenizer whitespaceTokenizer = (WhitespaceTokenizer) tokenizer;
                builder = builder.field("max_token_length", whitespaceTokenizer.getMax_token_length());
            } else if (TokenizerType.UAX_URL_EMAIL.equals(tokenizerType)) {
                // 转为子类型
                UAXURLEmailTokenizer uaxurlEmailTokenizer = (UAXURLEmailTokenizer) tokenizer;
                builder = builder.field("max_token_length", uaxurlEmailTokenizer.getMax_token_length());
            } else if (TokenizerType.CLASSIC.equals(tokenizerType)) {
                // 转为子类型
                ClassicTokenizer classicTokenizer = (ClassicTokenizer) tokenizer;
                builder = builder.field("max_token_length", classicTokenizer.getMax_token_length());
            } else if (TokenizerType.NGRAM.equals(tokenizerType)) {
                // 转为子类型
                NGramTokenizer nGramTokenizer = (NGramTokenizer) tokenizer;
                builder = builder.field("min_gram", nGramTokenizer.getMin_gram()).field("max_gram",
                        nGramTokenizer.getMax_gram());
                // 分词形式不为空，添加该参数
                if (nGramTokenizer.getToken_chars() != null && nGramTokenizer.getToken_chars().length != 0) {
                    builder = builder.array("token_chars", nGramTokenizer.getToken_chars());
                }
            } else if (TokenizerType.EDGE_NGRAM.equals(tokenizerType)) {
                // 转为子类型
                EdgeNGramTokenizer nGramTokenizer = (EdgeNGramTokenizer) tokenizer;
                builder = builder.field("min_gram", nGramTokenizer.getMin_gram()).field("max_gram",
                        nGramTokenizer.getMax_gram());
                // 分词形式不为空，添加该参数
                if (nGramTokenizer.getToken_chars() != null && nGramTokenizer.getToken_chars().length != 0) {
                    builder = builder.array("token_chars", nGramTokenizer.getToken_chars());
                }
            } else if (TokenizerType.KEYWORD.equals(tokenizerType)) {
                // 转为子类型
                KeywordTokenizer keywordTokenizer = (KeywordTokenizer) tokenizer;
                builder = builder.field("buffer_size", keywordTokenizer.getBuffer_size());
            } else if (TokenizerType.PATTERN.equals(tokenizerType)) {
                PatternTokenizer patternTokenizer = (PatternTokenizer) tokenizer;
                builder = builder.field("pattern", patternTokenizer.getPattern()).field("group",
                        patternTokenizer.getGroup());
                if (patternTokenizer.getFlags() != null) {
                    builder = builder.field("flags", patternTokenizer.getFlags());
                }
            } else if (TokenizerType.SIMPLE_PATTERN.equals(tokenizerType)) {
                SimplePatternTokenizer simplePatternTokenizer = (SimplePatternTokenizer) tokenizer;
                builder = builder.field("pattern", simplePatternTokenizer.getPattern());
            } else if (TokenizerType.SIMPLE_PATTERN_SPLIT.equals(tokenizerType)) {
                SimplePatternSplitTokenizer simplePatternSplitTokenizer = (SimplePatternSplitTokenizer) tokenizer;
                builder = builder.field("pattern", simplePatternSplitTokenizer.getPattern());
            } else if (TokenizerType.PATH_HIRRARCHY.equals(tokenizerType)) {
                PathHierarchyTokenizer pathHierarchyTokenizer = (PathHierarchyTokenizer) tokenizer;
                builder = builder.field("type", pathHierarchyTokenizer.getType().getValue());
                builder = builder.field("delimiter", pathHierarchyTokenizer.getDelimiter())
                        .field("buffer_size", pathHierarchyTokenizer.getBuffer_size())
                        .field("reverse", pathHierarchyTokenizer.isReverse())
                        .field("skip", pathHierarchyTokenizer.getSkip());
            }
            // 结束单个令牌解析器的定义
            builder = builder.endObject();
        }

        // 结束令牌解析器的定义
        builder = builder.endObject();
    }

    /**
     * 定义filter
     *
     * @param builder     构造对象
     * @param filtersList 过滤器对象
     * @return filter构造对象
     * @throws IOException
     */
    private static void filtersBuilder(XContentBuilder builder, List<Filters> filtersList)
            throws IOException {
        if (filtersList == null || filtersList.size() == 0) {
            // filtersList为空,怎么进来怎么出去
            return;
        }

        builder = builder.startObject("filter");

        for (Filters filter : filtersList) {
            String filterName = filter.getName();// 过滤器名称
            // 自定义过滤器名称不可为空
            if (filterName == null || "".equals(filterName)) {
                throw new NullPointerException("自定义过滤器名称不可为空!");
            }
            builder = builder.startObject(filterName);

            FilterType filterType = filter.getType();// 过滤器类型
            if (filterType == null) {
                throw new NullPointerException("自定义过滤器类型不可为空!");
            }

            builder = builder.field("type", filterType.getValue());
            // 拼音过滤器
            if (FilterType.PINYIN.equals(filter.getType())) {
                // 转为子类型pinyinfilter并定义相关参数
                PinYinFilter pinYinFilter = (PinYinFilter) filter;
                builder = builder.field("keep_first_letter", pinYinFilter.isKeep_first_letter())
                        .field("keep_separate_first_letter", pinYinFilter.isKeep_separate_first_letter())
                        .field("limit_first_letter_length", pinYinFilter.getLimit_first_letter_length())
                        .field("keep_full_pinyin", pinYinFilter.isKeep_full_pinyin())
                        .field("keep_joined_full_pinyin", pinYinFilter.isKeep_joined_full_pinyin())
                        .field("keep_none_chinese", pinYinFilter.isKeep_none_chinese())
                        .field("keep_none_chinese_together", pinYinFilter.isKeep_none_chinese_together())
                        .field("keep_none_chinese_in_first_letter", pinYinFilter.isKeep_none_chinese_in_first_letter())
                        .field("keep_none_chinese_in_joined_full_pinyin",
                                pinYinFilter.isKeep_none_chinese_in_joined_full_pinyin())
                        .field("none_chinese_pinyin_tokenize", pinYinFilter.isNone_chinese_pinyin_tokenize())
                        .field("keep_original", pinYinFilter.isKeep_original())
                        .field("lowercase", pinYinFilter.isLowercase())
                        .field("trim_whitespace", pinYinFilter.isTrim_whitespace())
                        .field("remove_duplicated_term", pinYinFilter.isRemove_duplicated_term())
                        .field("ignore_pinyin_offset", pinYinFilter.isIgnore_pinyin_offset());

            } else if (FilterType.NGRAM.equals(filter.getType())) {
                NGramFilter nGramFilter = (NGramFilter) filter;
                // 定义NGram过滤器
                builder = builder.field("max_gram", nGramFilter.getMax_gram()).field("min_gram",
                        nGramFilter.getMin_gram());
            } else if (FilterType.LENGTH.equals(filter.getType())) {
                LengthFilter lenthFilter = (LengthFilter) filter;
                // 定义长度过滤器
                builder = builder.field("min", lenthFilter.getMin()).field("max", lenthFilter.getMax());
            } else if (FilterType.ASCIIFOLDING.equals(filter.getType())) {
                ASCIIFoldingFilter asciiFoldingFilter = (ASCIIFoldingFilter) filter;
                // 定义ascii折叠过滤器参数
                builder = builder.field("preserve_original", asciiFoldingFilter.isPreserve_original());
            }
            // 结束单个过滤器定义
            builder = builder.endObject();
        }

        // 结束filter闭包
        builder = builder.endObject();// 结束定义filter
    }

    /**
     * 构建analyzer
     *
     * @param analyzersList 分析器集合
     * @throws IOException
     */
    private static void analyzersBuilder(XContentBuilder builder, List<Analyzer> analyzersList)
            throws IOException {
        builder = builder.startObject("analyzer");
        // 遍历analyzer
        for (Analyzer analyzer : analyzersList) {
            String analyzerName = analyzer.getName();// 得到分词器名称
            AnalyzerType analyzerType = analyzer.getType();

            if (analyzerName == null || "".equals(analyzerName)) {
                throw new NullPointerException("分词器名称不能为空！");
            }

            if (analyzerType == null) {
                throw new NullPointerException("分词器类型不能为空！");
            }

            builder = builder.startObject(analyzerName).field("type", analyzer.getType().getValue());
            if ("custom".equals(analyzer.getType().getValue())) {
                CustomAnalyzer customAnalyzer = (CustomAnalyzer) analyzer;
                // 字符过滤器不为空
                if (customAnalyzer.getCharacterFilters() != null && customAnalyzer.getCharacterFilters().length != 0) {
                    // 开始构建char_filter
                    builder = builder.startArray("char_filter");

                    for (CharacterFilters c : customAnalyzer.getCharacterFilters()) {
                        builder = builder.value(c.getName());
                    }
                    // 结束构建char_filter
                    builder = builder.endArray();
                }
                // tokenizer不为空
                if (customAnalyzer.getTokenizer() != null) {
                    // 名字不为空，取tokenizer的名字作为令牌解析器
                    if (customAnalyzer.getTokenizer().getName() != null
                            && !"".equals(customAnalyzer.getTokenizer().getName())) {
                        builder = builder.field("tokenizer", customAnalyzer.getTokenizer().getName());
                    } else {
                        // 否则，取tokenizer的类型作为令牌解析器
                        // 类型为 “ik”的，使用ik中的子类型定义tokenizer
                        if ("ik".equals(customAnalyzer.getTokenizer().getType().getValue())) {
                            IKTokenizer ikTokenizer = (IKTokenizer) customAnalyzer.getTokenizer();
                            builder = builder.field("tokenizer", ikTokenizer.getIkType());
                        } else {
                            builder = builder.field("tokenizer", customAnalyzer.getTokenizer().getType().getValue());
                        }
                    }
                }

                // filter不为空
                if (customAnalyzer.getFilter() != null && customAnalyzer.getFilter().length != 0) {
                    builder = builder.startArray("filter");
                    for (Filters filter : customAnalyzer.getFilter()) {
                        builder = builder.value(filter.getName());
                    }
                    builder = builder.endArray();
                }

            } else {
                // 标准分词器，需要强转后得到所需参数值
                if ("standard".equals(analyzer.getType().getValue())) {
                    StandardAnalyzer standardAnalyzer = (StandardAnalyzer) analyzer;

                    builder = builder.field("max_token_length", standardAnalyzer.getMax_token_length())
                            .field("stopwords", standardAnalyzer.getStopwords());
                    if (standardAnalyzer.getStopwords_path() != null) {
                        builder = builder.field("stopwords_path", standardAnalyzer.getStopwords_path());
                    }
                    // 停用词分词器，需要强转后得到所需参数值
                } else if ("stop".equals(analyzer.getType().getValue())) {
                    StopAnalyzer stopAnalyzer = (StopAnalyzer) analyzer;

                    builder = builder.array("stopwords", stopAnalyzer.getStopwords());

                    if (stopAnalyzer.getStopwords_path() != null) {
                        builder = builder.field("stopwords_path", stopAnalyzer.getStopwords_path());
                    }
                    // 模式分词器，需要强转后得到所需参数值
                } else if ("pattern".equals(analyzer.getType().getValue())) {
                    PatternAnalyzer patternAnalyzer = (PatternAnalyzer) analyzer;

                    builder = builder.field("pattern", patternAnalyzer.getPattern())
                            .field("lowercase", patternAnalyzer.isLowercase())
                            .array("stopwords", patternAnalyzer.getStopwords());

                    if (patternAnalyzer.getStopwords_path() != null) {
                        builder = builder.field("stopwords_path", patternAnalyzer.getStopwords_path());
                    }

                    if (patternAnalyzer.getFlags() != null) {
                        builder = builder.field("flags", patternAnalyzer.getFlags());
                    }
                    // 指纹分词器，需要强转后得到所需参数值
                } else if ("fingerprint".equals(analyzer.getType().getValue())) {
                    FingerprintAnalyzer fingerprintAnalyzer = (FingerprintAnalyzer) analyzer;

                    builder = builder.field("separator", fingerprintAnalyzer.getSeparator())
                            .field("max_output_size", fingerprintAnalyzer.getMax_output_size())
                            .array("stopwords", fingerprintAnalyzer.getStopwords());

                    if (fingerprintAnalyzer.getStopwords_path() != null) {
                        builder = builder.field("stopwords_path", fingerprintAnalyzer.getStopwords_path());
                    }
                }
            }

            // 结束定义单个analyzer
            builder = builder.endObject();
        }
        // 结束定义analyzer
        builder.endObject();
    }

    /**
     * 构建char_filter
     *
     * @param builder              构造对象
     * @param characterFiltersList 特性过滤器集合
     * @return char_filter构造对象
     * @throws IOException
     */
    private static void characterFiltersBuilder(XContentBuilder builder,
                                                           List<CharacterFilters> characterFiltersList)
            throws IOException {
        // 分词器的字符过滤器为空直接返回源builder
        if (characterFiltersList == null || characterFiltersList.size() == 0) {
            return;
        }
        // 开始char_filter定义
        builder = builder.startObject("char_filter");

        for (CharacterFilters characterFilter : characterFiltersList) {
            String characterFilterName = characterFilter.getName();// 过滤器名称
            if (characterFilterName == null || "".equals(characterFilterName)) {
                throw new NullPointerException("自定义字符过滤器名称不能为空！");
            }
            // 定义字符过滤器名称
            builder = builder.startObject(characterFilterName);
            CharFilterType charFilterType = characterFilter.getType();// 得到字符过滤器类型
            if (charFilterType == null) {
                throw new NullPointerException("自定义字符过滤器类型不能为空！");
            }

            if (!CharFilterType.HTML_STRIP.equals(charFilterType) && !CharFilterType.MAPPING.equals(charFilterType)
                    && !CharFilterType.PATTERN_REPLACE.equals(charFilterType)) {
                throw new NullPointerException("自定义字符过滤器出错：不存在" + charFilterType.getValue() + "类型的字符过滤器!");
            }
            // 定义字符过滤器类型
            builder = builder.field("type", charFilterType.getValue());

            // 若为html字符过滤类型,定义相关参数
            if (CharFilterType.HTML_STRIP.equals(charFilterType)) {
                HTMLStripCharFilter htmlStrip = (HTMLStripCharFilter) characterFilter;
                // escaped_tags不为空，加入到builder
                if (htmlStrip.getEscaped_tags() != null && htmlStrip.getEscaped_tags().length != 0) {
                    builder = builder.array("escaped_tags", htmlStrip.getEscaped_tags());
                }
            } else if (CharFilterType.MAPPING.equals(charFilterType)) {
                // 若为mapping字符过滤类型,定义相关参数
                MappingCharFilter mapping = (MappingCharFilter) characterFilter;

                // mappings映射不为空，加入mappings到builder
                if (mapping.getMappings() != null && mapping.getMappings().length != 0) {
                    builder = builder.array("mappings", mapping.getMappings());
                }
                // mappings_path不为空，加入到builder
                if (mapping.getMappings_path() != null) {
                    builder = builder.field("mappings_path", mapping.getMappings_path());
                }
            } else if (CharFilterType.PATTERN_REPLACE.equals(charFilterType)) {
                PatternReplaceCharFilter patternReplace = (PatternReplaceCharFilter) characterFilter;
                // 正则pattern不为空，加入到builder
                if (patternReplace.getPattern() != null) {
                    builder = builder.field("pattern", patternReplace.getPattern());
                }
                // 替换的字符串replacement不为空，加入到builder
                if (patternReplace.getReplacement() != null) {
                    builder = builder.field("replacement", patternReplace.getReplacement());
                }
                // 加入控制正则表达式的匹配行为的参数不为空，加入到builder
                if (patternReplace.getFlags() != null) {
                    builder = builder.field("flags", patternReplace.getFlags());
                }

            }
            // 结束单个char_filter定义
            builder = builder.endObject();

        }
        // 结束char_filter定义
        builder = builder.endObject();
    }

}
