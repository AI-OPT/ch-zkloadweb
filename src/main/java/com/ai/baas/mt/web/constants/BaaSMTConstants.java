package com.ai.baas.mt.web.constants;

public final class BaaSMTConstants {
    private BaaSMTConstants() {
    }
    public static final String PAGESIZE = "pageSize";
    public static final String PAGENO = "pageNo";

    public static final class ExceptionCode {
        private ExceptionCode() {
        }

        public static final java.lang.String SUCCESS = "000000";// 成功

        public static final java.lang.String SYSTEM_ERROR = "999999";// 系统错误

        public static final java.lang.String PARAM_IS_NULL = "888888";// 参数为空

        public static final java.lang.String NO_RESULT = "000001";// 无结果

        public static final java.lang.String PARAM_TYPE_NOT_RIGHT = "000002";// 参数类型错误

        public static final java.lang.String NO_DATA_OR_CACAE_ERROR = "000003";// 无数据或缓存错误

        public static final java.lang.String PARAM_VALUE_NOT_RIGHT = "000004";// 参数取值错误

        public static final java.lang.String PARAM_VALUE_EXIST_ERROR = "000005";// 参数值重复错误

        public static final java.lang.String RESULT_IS_NULL = "000006";// 结果为空
    }
    
    public static final class URLConstant{
    	private URLConstant(){}
    	public static final String BAAS_PT_INDEX_URL_KEY = "/baas_pt_index_url";
    }


}
