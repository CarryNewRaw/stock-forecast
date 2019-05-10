package com.ls.stockforecast.utils;

public class SysConstant {
    public static final String COMMON_MODULE = "COMMON";    // 通用模块名
    public static final String COMMON_PLATFORM_URL = "PLATFORM_URL";    // 通用平台url
    public static final String COMMON_ACCOUNT_SID = "ACCOUNT_SID";    // 通用平台sid
    public static final String COMMON_AUTH_TOKEN = "AUTH_TOKEN";    // 通用平台token
    public static final String COMMON_APP_CODE_DRIVER = "APP_CODE_DRIVER";    // 通用平台驾驶员公众号识别码
    public static final String COMMON_APP_CODE_CAPTAIN = "APP_CODE_CAPTAIN";    // 通用平台车队长公众号识别码
    public static final String COMMON_APP_CODE_COLONEL = "APP_CODE_COLONEL";    // 通用平台团队长公众号识别码

    public static final String COMMON_PLATFORM_SMS_URL = "PLATFORM_SMS_URL";    // 通用平台短信url
    public static final String COMMON_APP_SMS_CODE = "APP_SMS_CODE";    // 通用平台短信识别码

    public static final int COMMON_NO = 0;   // 通用：否
    public static final int COMMON_YES = 1;   // 通用：是

    public static final int COMMON_SUCCESS = 1;   // 通用：成功
    public static final int COMMON_FAIL = 2;   // 通用：失败

    public static final int CAPTAIN_TYPE_ORDINARY = 0;   // 车队长类型 普通
    public static final int CAPTAIN_TYPE_INSURANCE = 1;   // 车队长类型 保险机构
    public static final int CAPTAIN_TYPE_COMPANY = 2;   // 车队长类型 企业


    public static final String WECHAT_MODULE = "WECHAT";    // 微信模块名
    public static final String WXID_DRIVER = "WXID_DRIVER"; // 微信驾驶员公众号id
    public static final String WXID_CAPTAIN = "WXID_CAPTAIN";   // 我是车队长公众号id
    public static final String WXID_COLONEL = "WXID_COLONEL";   // 我是团队长公众号id

    public static final String WECHAT_COLONEL = "WECHAT_COLONEL";   // 公众号类型 团队长
    public static final String WECHAT_CAPTAIN = "WECHAT_CAPTAIN";   // 公众号类型 车队长
    public static final String WECHAT_DRIVER = "WECHAT_DRIVER";   // 公众号类型 驾驶员

    public static final String WXNAME_DRIVER = "WXNAME_DRIVER"; // 微信驾驶员公众号name
    public static final String WXNAME_CAPTAIN = "WXNAME_CAPTAIN";   // 我是车队长公众号name
    public static final String WXNAME_COLONEL = "WXNAME_COLONEL";   // 我是团队长公众号name
    public static final String WECHAT_VEHICLE_SEARCH_COUNT = "VEHICLE_SEARCH_COUNT";   // 微信模糊搜车最大返回条数
    public static final String WECHAT_COMPANY_SEARCH_COUNT = "COMPANY_SEARCH_COUNT";   // 微信模糊搜公司最大返回条数
    public static final String DRIVER_DEAL_BIND_URL = "DRIVER_DEAL_BIND_URL";   // 驾驶员绑定后给车队长发消息的微信地址
    public static final String TEMPLATE_CAPTAIN_BIND_SUCCESS = "TEMPLATE_CAPTAIN_BIND_SUCCESS";   // 驾驶员绑定消息发送给车队长的成功模板
    public static final String TEMPLATE_CAPTAIN_BIND_APPLY = "TEMPLATE_CAPTAIN_BIND_APPLY";   // 驾驶员设置车辆后给车队长发送消息模板


    public static final String VEHICLE_STATUS = "VEHICLE_STATUS";   // 车辆状态
    public static final String VEHICLE_STATUS_NO_LOCATION = "1";   // 车辆状态 未上线
    public static final String VEHICLE_STATUS_DRIVING = "2";   // 车辆状态 行驶中
    public static final String VEHICLE_STATUS_STOPPING = "3";   // 车辆状态 熄火
    public static final String VEHICLE_STATUS_OFFLINE = "4";   // 车辆状态 离线
    public static final String VEHICLE_STATUS_DEVICE_ERROR = "5";   // 车辆状态 疑似设备故障

    public static final String METER_MAX_WEIGHT = "METER_MAX_WEIGHT";    // 加权平均最大值

    public static final int DATAFLAG_DELETE = 1;   // 逻辑删除标识 删除
    public static final int DATAFLAG_NOT_DELETE = 0;   // 逻辑删除标识 未删除

    public static final int STATUS_DEFAULT = 0;    // 默认
    public static final int STATUS_EFFECTIVE = 1;  // 有效
    public static final int STATUS_INEFFECTIVE = 2;    // 无效



    public static final int APPROVESTATUS_DEFAULT = 0;   // 未审批
    public static final int APPROVESTATUS_ACCEPT = 1;   // 审批通过
    public static final int APPROVESTATUS_REJECT = -1;   // 审批驳回

    public static final String CAPTAIN_REPORT_RECENT_LIMIT = "CAPTAIN_REPORT_RECENT_LIMIT";   // 车队长日报近期分数，显示数量
    public static final String COLONEL_RISK_RECENT_LIMIT = "COLONEL_RISK_RECENT_LIMIT";   // 团队长风险等级近期分数，显示数量
    public static final int CAPTAIN_REPORT_RECENT_LIMIT_DEFAULT = 7;   // 车队长日报近期分数，显示数量，默认值
    public static final int COLONEL_RISK_RECENT_LIMIT_DEFAULT = 7;   // 团队长风险等级近期分数，显示数量，默认值

    public static final String COLONEL_NAME = "COLONEL_NAME";   // 团队长姓名

    public static final String COLONEL_TYPE = "COLONEL_TYPE";   // 团队长类型
    public static final String COLONEL_TYPE_LEADER = "分管领导";   // 团队长类型 分管领导
    public static final String COLONEL_TYPE_COLONEL = "团队长";   // 团队长类型 团队长
    public static final String COLONEL_TYPE_SALESMAN = "业务员";   // 团队长类型 业务员

    public static final int COLONEL_TYPE_LEADER_VALUE = 1;   // 团队长类型 分管领导
    public static final int COLONEL_TYPE_COLONEL_SALESMAN_VALUE = 2;   // 团队长类型 团队长/业务员
    public static final int COLONEL_TYPE_COLONEL_VALUE = 3;   // 团队长类型 团队长
    public static final int COLONEL_TYPE_SALESMAN_VALUE = 4;   // 团队长类型 业务员
    public static final int COLONEL_TYPE_CAPTAIN_VALUE = 0;   // 团队长类型 车队长（提醒下级接口的临时类型）

    public static final String SERIOUS_FAULT_PER_OVER = "SERIOUS_FAULT_PER_OVER";   // 团队长提醒列表故障车辆大于多少时显示为严重等级（红色标签）
    public static final String SERIOUS_UNBIND_PER_OVER = "SERIOUS_UNBIND_PER_OVER";   // 团队长提醒列表未绑定车辆超过多少时显示为严重等级（红色标签）

    public static final String COLONEL_REMIND_HIGHSCORE = "COLONEL_REMIND_HIGHSCORE";   // 团队长高低分提醒，高分阈值
    public static final String TEMPLATE_CAPTAIN_REMIND = "TEMPLATE_CAPTAIN_REMIND";   // 团队长公众号提醒车队长模板
    public static final String TEMPLATE_CAPTAIN_BINDING_REMIND_SMS = "TEMPLATE_CAPTAIN_BINDING_REMIND_SMS";   // 车队长提醒绑定公众号短信模板

    public static final String TEMPLATE_COLONEL_REMIND = "TEMPLATE_COLONEL_REMIND";   // 团队长公众号提醒团队长、业务员模板
    public static final String TEMPLATE_COLONEL_BINDING_REMIND_SMS = "TEMPLATE_COLONEL_BINDING_REMIND_SMS";   // 团队长提醒绑定公众号短信模板

    public static final String VEHICLE_COLOR = "VEHICLE_COLOR";   // 车辆颜色

    public static final String REPAIR_WAY = "REPAIR_WAY";   // adas报警提醒类型

    public static final int REPAIR_WAY_PHONE = 1;   // adas报警提醒类型 电话
    public static final int REPAIR_WAY_WECHAT = 2;   // adas报警提醒类型 微信
    public static final int REPAIR_WAY_TTS = 3;   // adas报警提醒类型 tts
    public static final int REPAIR_WAY_MESSAGE = 4;   // adas报警提醒类型 短信
    public static final int REPAIR_WAY_IGNORE = 9;   // adas报警提醒类型 忽略

    public static final String VEHICLE_ADAS_RECENT_LIMIT = "VEHICLE_ADAS_RECENT_LIMIT";   // 车辆adas展示，显示数量

    public static final String TEMPLATE_DRIVER_BIND_SUCCESS = "TEMPLATE_DRIVER_BIND_SUCCESS";   // 驾驶员绑定成功通知
    public static final String TEMPLATE_DRIVER_BIND_FAILURE = "TEMPLATE_DRIVER_BIND_FAILURE";   // 驾驶员绑定失败通知

    public static final String CAPTAIN_SAFETY_LIMIT = "CAPTAIN_SAFETY_LIMIT";   // 车队长安全教育指数统计每次显示条数
    public static final String CAPTAIN_SAFETY_UNBIND_VEHICLE_LIMIT = "CAPTAIN_SAFETY_UNBIND_VEHICLE_LIMIT";   // 车队长安全教育未绑定车辆每次显示条数
    public static final String CAPTAIN_DRIVERS_LIMIT = "CAPTAIN_DRIVERS_LIMIT";   // 车队长下驾驶员列表每次显示条数

    public static final String COMMON_URL = "COMMON_URL";   // 调通用平台时的url

    public static final int CAPTAIN_VEHICLE_RELATION_TYPE_WHITE = 0;   // 车队长与车辆关系类型 白名单
    public static final int CAPTAIN_VEHICLE_RELATION_TYPE_ORG = 1;   // 车队长与车辆关系类型 机构
    public static final int CAPTAIN_VEHICLE_RELATION_TYPE_LKYW = 2;   // 车队长与车辆关系类型 两客一危

    public static final String SAFETY_DRIVER_LINK = "SAFETY_DRIVER_LINK";   // 驾驶员安全教育推送链接前端地址
    public static final String SAFETY_CAPTAIN_LINK = "SAFETY_CAPTAIN_LINK";   // 车队长安全教育推送链接前端地址
    public static final String SAFETY_COLONEL_LEADER_LINK = "SAFETY_COLONEL_LEADER_LINK";   // 领导安全教育推送链接前端地址
    public static final String SAFETY_COLONEL_COLONEL_LINK = "SAFETY_COLONEL_COLONEL_LINK";   // 团队长安全教育推送链接前端地址
    public static final String SAFETY_COLONEL_SALESMAN_LINK = "SAFETY_COLONEL_SALESMAN_LINK";   // 业务员安全教育推送链接前端地址

    public static final String TEMPLATE_SAFETY_DRIVER = "TEMPLATE_SAFETY_DRIVER";   // 安全教育推送-驾驶员
    public static final String TEMPLATE_SAFETY_CAPTAIN = "TEMPLATE_SAFETY_CAPTAIN";   // 安全教育推送-车队长
    public static final String TEMPLATE_SAFETY_COLONEL = "TEMPLATE_SAFETY_COLONEL";   // 安全教育推送-团队长

    public static final String SAFETY_COLONEL_LIST_LINK = "SAFETY_COLONEL_LIST_LINK";   // 团队长安全教育列表前端链接地址
    public static final String SAFETY_CAPTAIN_LIST_LINK = "SAFETY_CAPTAIN_LIST_LINK";   // 车队长安全教育列表前端链接地址

    public static final String SAFETY_CAPTAIN_STATISTICS_LINK = "SAFETY_CAPTAIN_STATISTICS_LINK";   // 车队长安全教育统计前端链接地址

}