package com.sinosun.dss;

import com.alibaba.fastjson.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CIDrvTest extends CIDrv {
    int nPort = 0;
    int nSeq = 0;
    JSONObject retJsonObj;
    String strResult;

    String strUnlock = "1111111111111111";
    String strAccount = "12345";

    @Test
    public void test_1_ReadCIID() {
        // 读取密码器编号
        nSeq = nSeq + 1;
        strResult = this.ReadCIID( nPort, nSeq );
        System.out.println( "读取密码器编号" );
        System.out.println( strResult );
        retJsonObj = JSONObject.parseObject( strResult );
        if ( retJsonObj.getIntValue("RetCode" ) == 0 ) {
            System.out.println( "密码器编号：" + retJsonObj.getString("CIID") );
        } else {
            System.out.println( "处理失败，返回错误代码：0x"
                    + Integer.toHexString( retJsonObj.getIntValue( "RetCode" ) )
                    + ", "
                    + "错误信息：" + retJsonObj.getString( "RetMsg" ) );
        }

        System.out.println( "----------------------------------------------------------------" );
    }

    @Test
    public void test_2_IssueCI() {
        // 密码器发行
        nSeq = nSeq + 1;
        strResult = this.IssueCI( nPort, nSeq, strUnlock );
        System.out.println( "密码器发行" );
        System.out.println( strResult );
        retJsonObj = JSONObject.parseObject( strResult );
        if ( retJsonObj.getIntValue( "RetCode" ) == 0 ) {
            System.out.println( "密码器发行成功！" );
        } else {
            System.out.println( "处理失败，返回错误代码：0x"
                    + Integer.toHexString( retJsonObj.getIntValue( "RetCode" ) )
                    + ", "
                    + "错误信息：" + retJsonObj.getString( "RetMsg" ) );
        }

        System.out.println( "----------------------------------------------------------------" );
    }

    @Test
    public void test_3_UnlockCI() {
        // 密码器解锁
        nSeq = nSeq + 1;
        strResult = this.UnlockCI( nPort, nSeq, strUnlock );

        System.out.println( "密码器解锁" );
        System.out.println( strResult );
        retJsonObj = JSONObject.parseObject( strResult );
        if ( retJsonObj.getIntValue( "RetCode" ) == 0 ) {
            System.out.println( "密码器解锁成功！" );
        } else {
            System.out.println( "处理失败，返回错误代码：0x"
                    + Integer.toHexString( retJsonObj.getIntValue( "RetCode" ) )
                    + ", "
                    + "错误信息：" + retJsonObj.getString( "RetMsg" ) );
        }
        System.out.println( "----------------------------------------------------------------" );
    }

    @Test
    public void test_8_DelAcc() {
        // 删除账号
        strResult = this.DelAcc( nPort, nSeq, strAccount );
        System.out.println( "删除账号" );
        System.out.println( strResult );
        retJsonObj = JSONObject.parseObject( strResult );
        if ( retJsonObj.getIntValue( "RetCode" ) == 0 ) {
            System.out.println( "删除账号成功！" );
        } else {
            System.out.println( "处理失败，返回错误代码：0x"
                    + Integer.toHexString( retJsonObj.getIntValue( "RetCode" ) )
                    + ", "
                    + "错误信息：" + retJsonObj.getString( "RetMsg" ) );
        }
        System.out.println( "----------------------------------------------------------------" );
    }

    @Test
    public void test_4_AddAccount() {
        // 产生密钥对
        nSeq = nSeq + 1;
        strResult = this.GenerateKeyPair( nPort, nSeq, strAccount );
        System.out.println( "产生密钥对" );
        System.out.println( strResult );
        retJsonObj = JSONObject.parseObject( strResult );
        if ( retJsonObj.getIntValue( "RetCode" ) == 0 ) {
            System.out.println( "产生密钥对成功，\nChipID:"
                    + retJsonObj.getString( "ChipID" )
                    + "\nVK    :" + retJsonObj.getString( "VK" ) );
        } else {
            System.out.println( "处理失败，返回错误代码：0x"
                    + Integer.toHexString( retJsonObj.getIntValue( "RetCode" ) )
                    + ", "
                    + "错误信息：" + retJsonObj.getString( "RetMsg" ) );
        }
        System.out.println( "----------------------------------------------------------------" );

        // 下载AK
        String strAK = "11111111111111111111111111111111";
        String strKeyNo = "00";
        nSeq = nSeq + 1;
        strResult = this.DownLoadAK( nPort, nSeq, strAccount, strKeyNo, strAK );
        System.out.println( "下载AK" );
        System.out.println( strResult );
        retJsonObj = JSONObject.parseObject( strResult );
        if ( retJsonObj.getIntValue( "RetCode" ) == 0 ) {
            System.out.println( "下载AK支成功！" );
        } else {
            System.out.println( "处理失败，返回错误代码：0x"
                    + Integer.toHexString( retJsonObj.getIntValue( "RetCode" ) )
                    + ", "
                    + "错误信息：" + retJsonObj.getString( "RetMsg" ) );
        }
        System.out.println( "----------------------------------------------------------------" );
    }

    @Test
    public void test_5_GetMachineSign() {
        // 获取增发签名
        String strNewChipID = "11111111111111111111111111111111";
        String strNewVK = "11111111111111111111111111111111111111111111111111111111111111111111111111111111"
                + "11111111111111111111111111111111111111111111111111111111111111111111111111111111";
        nSeq = nSeq + 1;
        strResult = this.GetMachineSign( nPort,nSeq, strAccount, strNewChipID, strNewVK );

        System.out.println( "获取增发签名" );
        System.out.println( strResult );
        retJsonObj = JSONObject.parseObject( strResult );
        if ( retJsonObj.getIntValue( "RetCode" ) == 0 ) {
            System.out.println( "产生增发签名成功，增发签名：" + retJsonObj.get( "Sig" ) );
        } else {
            System.out.println( "处理失败，返回错误代码：0x"
                    + Integer.toHexString( retJsonObj.getIntValue( "RetCode" ) )
                    + ", "
                    + "错误信息：" + retJsonObj.getString( "RetMsg" ) );
        }
        System.out.println( "----------------------------------------------------------------" );
    }

    @Test
    public void test_6_ConnectCICalculate() {
        // 验证人员口令
        String strVerifyinfo = "00111111FFFFFFFFFFFFFFFFFFFFFF";
        nSeq = nSeq + 1;
        strResult = this.VerifyOperInfo( nPort, nSeq, strVerifyinfo );

        System.out.println( "验证签发人口令" );
        System.out.println( strResult );
        retJsonObj = JSONObject.parseObject( strResult );
        if ( retJsonObj.getIntValue( "RetCode" ) == 0 ) {
            System.out.println( "验证签发人口令成功！" );
        } else {
            System.out.println( "处理失败，返回错误代码：0x"
                    + Integer.toHexString( retJsonObj.getIntValue( "RetCode" ) )
                    + ", "
                    + "错误信息：" + retJsonObj.getString( "RetMsg" ) );
        }
        System.out.println( "----------------------------------------------------------------" );

        // 联机计算支付密码
        String strDate = "20200115";
        String strType = "1";
        char cType = strType.charAt(0);
        String strTicketNo = "00000001";
        String strAmount = "100";

        nSeq = nSeq + 1;
        strResult = this.ConnectCICalculate( nPort,nSeq, strAccount, cType, strDate, strTicketNo, strAmount );

        System.out.println( "联机计算支付密码" );
        System.out.println( strResult );
        retJsonObj = JSONObject.parseObject( strResult );
        if ( retJsonObj.getIntValue( "RetCode" ) == 0 ) {
            System.out.println( "联机计算支付密码成功，支付密码：" + retJsonObj.get( "PAYCODE" ) );
        } else {
            System.out.println( "处理失败，返回错误代码：0x"
                    + Integer.toHexString( retJsonObj.getIntValue( "RetCode" ) )
                    + ", "
                    + "错误信息：" + retJsonObj.getString( "RetMsg" ) );
        }
        System.out.println( "----------------------------------------------------------------" );
    }

    @Test
    public void test_7_ConnectCICalculateExtend() {
        // 验证人员口令
        String strVerifyinfo = "00111111FFFFFFFFFFFFFFFFFFFFFF";
        nSeq = nSeq + 1;
        strResult = this.VerifyOperInfo( nPort, nSeq, strVerifyinfo );

        System.out.println( "验证签发人口令" );
        System.out.println( strResult );
        retJsonObj = JSONObject.parseObject( strResult );
        if ( retJsonObj.getIntValue( "RetCode" ) == 0 ) {
            System.out.println( "验证签发人口令成功！" );
        } else {
            System.out.println( "处理失败，返回错误代码：0x"
                    + Integer.toHexString( retJsonObj.getIntValue( "RetCode" ) )
                    + ", "
                    + "错误信息：" + retJsonObj.getString( "RetMsg" ) );
        }
        System.out.println( "----------------------------------------------------------------" );

        // 带收方账号的联机计算支付密码
        String strRcvAcc = "88888888";
        String strDate = "20200115";
        String strType = "1";
        char cType = strType.charAt(0);
        String strTicketNo = "00000001";
        String strAmount = "100";

        nSeq = nSeq + 1;
        strResult = this.ConnectCICalculateExtend( nPort,nSeq, strAccount, strRcvAcc, cType, strDate, strTicketNo, strAmount );

        System.out.println( "带收方账号的联机计算支付密码" );
        System.out.println( strResult );
        retJsonObj = JSONObject.parseObject( strResult );
        if ( retJsonObj.getIntValue( "RetCode" ) == 0 ) {
            System.out.println( "带收方账号的联机计算支付密码成功，支付密码：" + retJsonObj.get( "PAYCODE" ) );
        } else {
            System.out.println( "处理失败，返回错误代码：0x"
                    + Integer.toHexString( retJsonObj.getIntValue( "RetCode" ) )
                    + ", "
                    + "错误信息：" + retJsonObj.getString( "RetMsg" ) );
        }
        System.out.println( "----------------------------------------------------------------" );
    }
}