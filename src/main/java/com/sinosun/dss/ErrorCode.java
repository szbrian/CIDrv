package com.sinosun.dss;

public enum  ErrorCode {
    ERR_SUCESS	                (0x00	, "正确"),
    // CIDRV返回错误
    CIDRV_ERR_PARAM             ( 0x800, "传入参数错误"),
    CIDRV_ERR_OPEN_FAILED       ( 0x801, "打开usb口或串口失败"),
    CIDRV_ERR_WRITE_FAILED      ( 0x810, "往usb口或串口写数据失败"),
    CIDRV_ERR_READ_FAILED       ( 0x811, "从usb口或串口读数据失败"),
    CIDRV_ERR_MSG_LENGTH        ( 0x812, "数据包长度错误"),
    CIDRV_ERR_SND_LENGTH        ( 0x813, "发送数据长度错误"),
    CIDRV_ERR_RCV_HEADER        ( 0x814, "接收数据包头错误"),
    CIDRV_ERR_RCV_LENGTH        ( 0x815, "接收数据包长度错误"),
    CIDRV_ERR_CRC               ( 0xFC, "接收数据包校验和错误" ),

    // 密码器返回错误
    ERR_FAILED_SELFTEST	        ( 0x01, "CU-200数据自检错误"),
    ERR_BAD_TAG             	( 0x02, "命令请求类型错误"),
    ERR_BAD_DATE	            ( 0x03, "命令请求数据错误"),
    ERR_BAD_MAIN_KEY        	( 0x04, "银行主密钥错误"),
    ERR_BAD_ADD_SIGN        	( 0x05, "增发签名错误"),
    ERR_FAIL	                ( 0x06, "加密芯片错误（芯片可能已损坏）"),
    ERR_BAD_REQ_PKG	            ( 0x10, "数据包接收不完整"),
    ERR_PKG_UNCHECK	            ( 0x11, "数据包校验和错误"),
    ERR_FAIL_MEM_READ	        ( 0x12, "支付密码器内存读错误"),
    ERR_FAIL_MEM_WRITE	        ( 0x13, "支付密码器内存写错误"),
    ERR_FAIL_MEM_ERASE	        ( 0x14, "支付密码器内存擦除错误"),
    ERR_FAIL_BOX_ID	            ( 0x20, "读支付密码器序列号错"),
    ERR_FAIL_CHIP_ID	        ( 0x21, "读芯片序列号错"),
    ERR_ACCU_FULL	            ( 0x22, "支付密码器账号已满"),
    ERR_ACCU_EXIST	            ( 0x23, "支付密码器账号已存在"),
    ERR_FAIL_CREATE_KEY	        ( 0x24, "支付密码器未生成密钥对"),
    ERR_ACCU_DIFF           	( 0x25, "账号不一致"),
    ERR_ACCU_UNEXIST	        ( 0x26, "账号不存在"),
    ERR_UNLOCK_FAILE	        ( 0x27, "随机解锁密码不匹配"),
    ERR_SIG_BAD_PWD         	( 0x52, "签发口令错误"),
    ERR_SIG_LOCK	            ( 0x53, "签发口令被锁"),
    ERR_SIG_NOAUTHOR	        ( 0x54, "签发员无此权限"),
    ERR_SIG_UNEXIST	            ( 0x55, "无该签发员"),
    ERR_CHK_BAD_PWD	            ( 0x56, "审核口令错误"),
    ERR_CHK_LOCK	            ( 0x57, "审核口令被锁"),
    ERR_CHK_NOAUTHOR	        ( 0x58, "审核员无此权限"),
    ERR_CHK_UNEXIST	            ( 0x59, "无该审核员"),
    ERR_AUT_BAD_PWD         	( 0x5a, "授权口令错误"),
    ERR_AUT_LOCK	            ( 0x5b, "授权口令被锁"),
    ERR_AUT_NOAUTHOR        	( 0x5c, "授权员无此权限"),
    ERR_AUT_UNEXIST         	( 0x60, "无该授权员"),
    ERR_AUT_MONEY_NOAUTHOR  	( 0x61, "授权员授权金额权限不够"),
    ERR_OTHERS	                ( 0x62, "其它错误"),
    ERR_MACH_UNDIST	            ( 0x63, "机具未发行"),
    ERR_MONEY_ZERO_EXCEPT_CHECK	( 0x68, "非支票票据金额不能为零"),
    ERR_BILL_INFO_BAD	        ( 0x65, "票据信息不合法"),
    ERR_BAD_COMMAND         	( 0x70, "未定义的命令序号"),
    ERR_USER_UNEXIST	        ( 0x71, "用户不存在"),
    ERR_BAD_PWD	                ( 0x72, "用户口令错误"),
    ERR_PWD_ALL_NUM	            ( 0x73, "用户输入口令必须是数字"),
    ERR_NOAUTHOR	            ( 0x74, "用户无此权限"),
    ERR_ACCU_NOAUTHOR       	( 0x75, "无操作该账号权限"),
    ERR_USER_EXIST          	( 0x76, "用户已存在"),
    ERR_USER_FULL           	( 0x77, "用户数目超过限制"),
    ERR_USER_LOCK	            ( 0x78, "用户已经锁定"),
    ERR_USER_NOLOG          	( 0x79, "用户未登陆"),
    ERR_NO_SELFTEST	            ( 0x7A, "芯片未自检"),
    ;

    private String strErrMsg = "";
    private int nErrCode;

    ErrorCode(int nErrCode, String strErrMsg) {
        this.nErrCode = nErrCode;
        this.strErrMsg = strErrMsg;
    }

    public int getErrCode() {
        return this.nErrCode;
    }

    public String getErrMsg() {
        return this.strErrMsg;
    }

    public static String getErrMsg(int nErrCode) {
        for ( ErrorCode _item : values() ) {
            if (_item.getErrCode() == nErrCode) {
                return _item.getErrMsg();
            }
        }
        return "未知错误";
    }
}