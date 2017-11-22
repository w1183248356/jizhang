package com.jizhang;

/**
 * Created by pengchong on 2017/5/24.
 */

public class Constants {

    public static class IntentExtraKey {
        public static final String TITLE = "title";

        public static final String BILL_ID = "bill_id";
        /** 单据类型 */
        public static final String BILL_TYPE = "bill_type";
        /** 操作类型 */
        public static final String OPERATE_TYPE = "operate_type";
        /** 进入详情的入口类型 */
        public static final String TYPE_INTO_DETAIL = "type_into_detail";

        public static final String EXPENSE_TYPE = "expense_type";

    }
    public static class Database {
        public static final String DB_NAME = "finance_db";
    }

    /** 单据类型之借款类型 */
    public static final String BILL_TYPE_LEND = "CTRL_LEND";

    public static final int TODO_DETAIL_TYPE_DIS_TRANS = 107;//长途交通费
    public static final int TODO_DETAIL_TYPE_HOTEL = 115;//住宿费
    public static final int TODO_DETAIL_TYPE_CITY_TRANS = 114;//市内交通费
    public static final int TODO_DETAIL_TYPE_ALLOWANCE = 116;//出差补助费用

    /** 进入详情的类型 */
    public static class TypeIntoDetail {
        /**
         * 待办 - 未审核
         */
        public static final int TYPE_TODO_UNCHECK = 1;
        /**
         * 待办- 已审核
         */
        public static final int TYPE_TODO_CHECKED = 2;
        /**
         * 当前模块
         */
        public static final int TYPE_SELF = 3;

    }
    /**
     * 界面操作类型
     */
    public static class ViewOperateType {
        /** 查看 */
        public static final int VIEW = 0x01;
        /** 新增 */
        public static final int ADD = VIEW << 1;
        /** 编辑 */
        public static final int EDIT = ADD << 1;
        /** 删除 */
        public static final int DELETE = EDIT << 1;
    }

    public static class CommonSelection {
        public static final String FIELD_TEXT = "field_text";
        public static final String FIELD_KEY  = "field_key";
        public static final String REQ_URL    = "req_url";
        public static final String REQ_PARAM  = "req_param";
        public static final String RESULT_EXTRA = "result_extra";

    }

    public static class UploadBillType {
        public static final String BILL_TYPE_EXPENSE = "ctrl_payout";
        public static final String BILL_TYPE_LEND  = "ctrlLend";
    }

    /**
     * 单据状态
     */
    public enum BillState {
        Unsubmit("未提交"), Checking("审核中"), Checked("已审核"), Local("未上传");
        private final String name;
        BillState(String name) {
            this.name = name;
        }

        public static BillState convert(int i) {
            BillState[] states = BillState.values();
            if (i >= states.length || i < 0) {
                throw new IllegalArgumentException("参数设定的超出范围");
            }
            return states[i];
        }

        public String text() {
            return this.name;
        }
    }

    public static class SubTable{
        //长途交通费
        public static final String TELE_TRANS = "ctrl_travel";
        //市内交通费
        public static final String CITY_TRANS = "ctrl_city_traffic_detail";
        //住宿费
        public static final String HOTEL_TRANS = "ctrl_stay_detail";
        //出差补贴
        public static final String SUBSIDY_TRANS = "ctrl_subsidy_detail";
    }

}
