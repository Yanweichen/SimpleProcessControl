package com.ywc.base;

/**
 * APi返回状态定义
 *
 * @author rankai .
 */
public enum ResultEnum {
    /**
     * api接口成功
     */
    SUCCESS(200),
    /**
     * 没有权限
     */
    NO_AUTHORITY(401),
    /**
     * 未找到
     */
    NOT_FOUND(404),
    /**
     * 服务器错误
     */
    SERVICE_ERROR(500),
    /**
     * token失效
     */
    TOKEN_ERROR(5001),
    /**
     * 参数错误
     */
    PARAM_ERROR(5002),
    /**
     * 用户已存在
     */
    USER_EXIST(5003),
    /**
     * 用户或者密码错误
     */
    USER_PWD_ERROR(5004),
    /**
     * 账号未激活
     */
    NO_ACTIVATION_EXCEPTION(5005),
    /**
     * 账号不存在
     */
    UNKNOWN_ACCOUNT(5006),
    /**
     * 账号被锁定
     */
    LOCKED_ACCOUNT(5007),
    /**
     * 登陆密码错误次数过多
     */
    TOO_MANY_ERRORS(5008),
    /**
     * 验证码错误
     */
    CAPTCHA_ERROR(5009),
    /**
     * 验证码超时
     */
    CAPTCHA_TIMEOUT(5010),
    /**
     * 没有登陆
     */
    NO_LOGIN(5011),
    /**
     * 没有权限
     */
    NO_PERMITTION(5012),
    /**
     * 不是合法的邮箱或手机号
     */
    NUMBER_ERROR(5013),
    /**
     * 填写的绑定邮箱错误
     */
    BOUND_MAILBOX_ERROR(5014),
    /**
     * 填入的绑定手机号错误
     */
    BOUND_PHONE_ERROR(5015),
    /**
     * 修改密码:原密码错误
     */
    OLD_PASSWORD_ERROR(5016),
    /**
     * 账号未激活
     */
    DISABLED_ACCOUNT(5017),
    /**
     * 账号或密码为空
     */
    ACCOUNT_OR_PASSWORD_NULL(5018),
    /**
     * 必填参数不能为空
     */
    REQUIRED_PARAMETER_EMPTY(5019),
    /**
     * 操作失败
     */
    OPERATION_FAILED(5020),
    /**
     * 请求超时
     */
    API_TIMEOUT(5021),
    /**
     * 账号在其他地方登陆
     */
    LOGIN_KICKOUT(5022),
    /**
     * 接口签名不匹配
     */
    APPSIN_INCOMPATIBLE(5023),
    /**
     * 客户输入验证码为空
     */
    CAPTCHA_NULL(5024),

    /**
     * 身份证格式错误
     */
    ID_CARD_ERROR(5025),

    /**
     * 登陆chap时
     */
    LOGIN_TIMEOUT(5026),

    /**
     * 进件提交(申请)失败
     */
    APP_APPLY_DEFEATED(5027),

    /**
     * 日期参数错误
     */
    DATE_FORMAT_ERROR(5028),
    /**
     * 错误的appid
     */
    ERROR_APPID(5029),
    /**
     * 错误的逾期额度
     */
    ERROR_OVERDUE_AMOUNT(5030),
    /**
     * 错误的逾期时间
     */
    ERROR_OVERDUE_TIME(5031),
    /**
     * 错误的进件状态  无法逾期
     */
    ERROR_APP_STATUS(5032),
    /**
     * 获取进件审批流程失败
     */
    APP_APPROVE_DEFEATED(5033),

    /**
     * 删除机构失败
     */
    ORG_DELETE_ERROR(5034),
    /**
     * 错误的用户角色
     */
    ERROR_USER_ROLY(5035),
    /**
     * 有逾期进件的客户无法移出风险名单
     */
    OVERDUE_APPLICAION_CUSTOMER(5036),
    /**
     * 黑名单审批表中已经有了该客户，无法再次插入
     */
    CANNOT_INSERT(5037),
    /**
     * 贷后实施方式不符
     */
    NOT_MATCH_POSTLOAN(5038),
    /**
     * 贷后状态更新时候不能未完成
     */
    UNACCEPT_RESULT(5039),

    /**
     * 登陆码超时
     */
    LOGIN_CODE(5040),

    /**
     * 邮箱或者手机号不存在
     */
    EMAIL_PHONE_ERROR(5041),
    /**
     * 用户的打钱额度没有分配完
     */
    NOT_DISTRIBUTION_AMOUNT(5042),
    /**
     * 错误的状态
     */
    ERROR_REPAY_STATUS(5043),

    /**
     * 需要修改罚息
     */
    NEED_CHANGE_PENALTY(5044),
    /**
     * 罚息金额不能变小
     */
    PENALTY_CANNOT_SMALL(5045),
    /**
     * 分配错误
     */
    ERROR_DISTRIBUTION(5056),
    /**
     * 该进件有未审核的打款 请先处理
     */
    PROCESS_FIRST(5057),
    /**
     * 错误的提前还款金额
     */
    ERROR_PREPAY(5058),
    /**
     * 用户邮箱和输入邮箱不一致-忘记密码
     */
    DATA_INCONSIST_EMAIL(5058),
    /**
     * 用户手机和输入手机不一致-忘记密码
     */
    DATA_INCONSIST_PHONE(5059),
    /**
     * 已存在‘通过’或‘打回’的相同流水号的打款信息
     */
    EXITS_SAME_SERIAL(5060),

    /**
     * 用户为初始密码
     */
    INIT_PASSWORD(5061),

     /**
     * 默认属性不可修改
     */
    CANNOT_CHANGE_DEF(5062),

    /**
     * 不能上传空文件或文件无法解析
     */
    EMPTY_PROCESS_ERROR(5063),
    /**
     * 流水号不能重复
     */
    SAME_SERIAL_NUM(5064)
    ;

    private int value; // 自定义数据域，private为了封装

    private ResultEnum(int val) {
        this.value = val;
    }

    /**
     * 获取对应的值
     *
     * @return 对应值value
     */
    public int getValue() {
        return value;
    }
}
