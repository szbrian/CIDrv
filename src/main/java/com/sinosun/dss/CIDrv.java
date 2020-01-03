package com.sinosun.dss;

import com.alibaba.fastjson.JSONObject;
import com.sun.jna.Library;
import com.sun.jna.Native;

public class CIDrv {
    static int nRetCode = 0;
    public interface CIDrvImpl extends Library {
        CIDrvImpl instance = (CIDrvImpl) Native.loadLibrary("CI980Drv", CIDrvImpl.class);
        int ReadCIID(int nPort, int nSeq, byte[] bszCIID);
        int IssueCI(int nPort, int nSeq, String strUnlockKey);
        int UnlockCI(int nPort, int nSeq, String strUnlockKey);
        int GenerateKeyPair(int nPort, int nSeq,
                            String strAccount,
                            byte[] bszChipid,
                            byte[] bszVK);
        int DownLoadAK(int nPort, int nSeq, String strAccount,
                       String strKeyNo, String strAK);
        int DelAcc(int nPort, int nSeq, String strAccount);
        int GetMachineSign(int nPort, int nSeq,
                           String strAccount,
                           String strNewChipID,
                           String strNewVK,
                           byte[] bszSig);
    }

    public int getLastErrorCode() {
        return nRetCode;
    }

    public String getErrMsg( int nErrCode ) {
        return  ErrorCode.getErrMsg( nRetCode );
    }

    public String ReadCIID( int nPort, int nSeq ) {
        JSONObject jsonObj = new JSONObject();
        byte[] bszCIID = new byte[1024];

        nRetCode = CIDrvImpl.instance.ReadCIID( nPort, nSeq, bszCIID );
        if ( nRetCode == 0 ) {
            String strCIID = new String(bszCIID).substring(0, 10);
            jsonObj.put( "CIID", strCIID );
        }

        jsonObj.put( "RetCode", nRetCode );
        jsonObj.put( "RetMsg", getErrMsg( nRetCode ) );
        return jsonObj.toString();
    }

    public String IssueCI( int nPort, int nSeq, String strUnlockKey ) {
        JSONObject jsonObj = new JSONObject();

        nRetCode = CIDrvImpl.instance.IssueCI( nPort, nSeq, strUnlockKey );

        jsonObj.put( "RetCode", nRetCode );
        jsonObj.put( "RetMsg", getErrMsg( nRetCode ) );
        return jsonObj.toString();
    }

    public String UnlockCI( int nPort, int nSeq, String strUnlockKey ) {
        JSONObject jsonObj = new JSONObject();

        nRetCode = CIDrvImpl.instance.UnlockCI( nPort, nSeq, strUnlockKey );

        jsonObj.put( "RetCode", nRetCode );
        jsonObj.put( "RetMsg", getErrMsg( nRetCode ) );
        return jsonObj.toString();
    }

    public String DelAcc( int nPort, int nSeq, String strAccount ) {
        JSONObject jsonObj = new JSONObject();

        nRetCode = CIDrvImpl.instance.DelAcc( nPort, nSeq, strAccount );

        jsonObj.put( "RetCode", nRetCode );
        jsonObj.put( "RetMsg", getErrMsg( nRetCode ) );
        return jsonObj.toString();
    }

    public String GenerateKeyPair( int nPort, int nSeq, String strAccount ) {
        JSONObject jsonObj = new JSONObject();
        byte[] bszChipid = new byte[1024];
        byte[] bszVK = new byte[1024];

        nRetCode = CIDrvImpl.instance.GenerateKeyPair( nPort, nSeq, strAccount, bszChipid, bszVK );
        if ( nRetCode == 0 ) {
            String strChipid = new String( bszChipid ).substring(0, 32);
            String strVK = new String( bszVK ).substring(0, 160);
            jsonObj.put( "VK", strVK );
            jsonObj.put( "ChipID", strChipid );
        }

        jsonObj.put( "RetCode", nRetCode );
        jsonObj.put( "RetMsg", getErrMsg( nRetCode ) );
        return  jsonObj.toString();
    }

    public String DownLoadAK( int nPort, int nSeq, String strAccount, String strKeyNo, String strAK ) {
        JSONObject jsonObj = new JSONObject();

        nRetCode = CIDrvImpl.instance.DownLoadAK( nPort, nSeq, strAccount, strKeyNo, strAK );

        jsonObj.put( "RetCode", nRetCode );
        jsonObj.put( "RetMsg", getErrMsg( nRetCode ) );
        return  jsonObj.toString();
    }

    public String GetMachineSign( int nPort, int nSeq,
                                  String strAccount,
                                  String strNewChipID,
                                  String strNewVK ) {
        JSONObject jsonObj = new JSONObject();
        byte[] bszSig = new byte[1024];

        nRetCode = CIDrvImpl.instance.GetMachineSign( nPort, nSeq, strAccount, strNewChipID, strNewVK, bszSig );

        if ( nRetCode == 0 ) {
            String strSig = new String(bszSig).substring(0, 16);
            jsonObj.put( "Sig", strSig );
        }

        jsonObj.put( "RetCode", nRetCode );
        jsonObj.put( "RetMsg", getErrMsg( nRetCode ) );
        return  jsonObj.toString();
    }

    public static void main( String[] args ) {
        //System.out.println( "Hello World!" );
        JSONObject retJsonObj = null;
        String strResult = "";

        CIDrv cidrv = new CIDrv();
        String strAccount = "12345";

        // 读取密码器编号
        strResult = cidrv.ReadCIID( 7, 0 );
        System.out.println( "读取密码器编号" );
        System.out.println( strResult );
        retJsonObj = JSONObject.parseObject( strResult );
        if ( retJsonObj.getIntValue("RetCode") == 0 ) {
            System.out.println( "密码器编号：" + retJsonObj.getString("CIID") );
        } else {
            System.out.println( "读取密码器编号失败，返回错误代码：0x"
                    + Integer.toHexString( cidrv.getLastErrorCode() ) );
        }
        System.out.println( "----------------------------------------------------------------" );

        // 密码器发行
        strResult = cidrv.IssueCI( 7, 1, "1111111111111111" );
        System.out.println( "密码器发行" );
        System.out.println( strResult );
        retJsonObj.clear();
        retJsonObj = JSONObject.parseObject( strResult );
        if ( retJsonObj.get("RetCode").toString().equals("0") ) {
            System.out.println( "密码器发行成功！" );
        } else {
            System.out.println("密码器发行失败，返回错误代码：0x"
                    + Integer.toHexString( cidrv.getLastErrorCode() ) );
        }
        System.out.println( "----------------------------------------------------------------" );

        // 密码器解锁
        strResult = cidrv.UnlockCI( 7, 2, "1111111111111111" );
        System.out.println( "密码器解锁" );
        System.out.println( strResult );
        retJsonObj = JSONObject.parseObject( strResult );
        if ( retJsonObj.get("RetCode").toString().equals("0") ) {
            System.out.println( "密码器解锁成功！" );
        } else {
            System.out.println( "密码器解锁失败，返回错误代码：0x"
                    + Integer.toHexString( cidrv.getLastErrorCode() ) );
        }
        System.out.println( "----------------------------------------------------------------" );

        // 产生密钥对
        strResult = cidrv.GenerateKeyPair( 7, 3, strAccount );
        System.out.println( "产生密钥对" );
        System.out.println( strResult );
        retJsonObj = JSONObject.parseObject( strResult );
        if ( retJsonObj.get("RetCode").toString().equals("0") ) {
            System.out.println( "产生密钥对成功，\nChipID:"
                    + (String)retJsonObj.get( "ChipID" )
                    + "\nVK    :" + (String)retJsonObj.get( "VK" ) );
        } else {
            System.out.println( "产生密钥对失败，返回错误代码：0x"
                    + Integer.toHexString( cidrv.getLastErrorCode() ) );
        }
        System.out.println( "----------------------------------------------------------------" );

        // 下载AK
        String strAK = "11111111111111111111111111111111";
        strResult = cidrv.DownLoadAK( 7, 4, strAccount, "00", strAK );
        System.out.println( "下载AK" );
        System.out.println( strResult );
        retJsonObj = JSONObject.parseObject( strResult );
        if ( retJsonObj.get("RetCode").toString().equals("0") ) {
            System.out.println( "下载AK支成功！" );
        } else {
            System.out.println( "下载AK支失败，返回错误代码：0x"
                    + Integer.toHexString( cidrv.getLastErrorCode() ) );
        }
        System.out.println( "----------------------------------------------------------------" );

        // 获取增发签名
        String strNewChipID = "11111111111111111111111111111111";
        String strNewVK = "11111111111111111111111111111111111111111111111111111111111111111111111111111111"
                + "11111111111111111111111111111111111111111111111111111111111111111111111111111111";
        strResult = cidrv.GetMachineSign( 7,5, strAccount, strNewChipID, strNewVK );
        System.out.println( "获取增发签名" );
        System.out.println( strResult );
        retJsonObj = JSONObject.parseObject( strResult );
        if ( retJsonObj.get("RetCode").toString().equals("0") ) {
            System.out.println( "产生增发签名成功，增发签名：" + retJsonObj.get( "Sig" ) );
        } else {
            System.out.println( "产生增发签名失败，返回错误代码：0x"
                    + Integer.toHexString( cidrv.getLastErrorCode() ) );
        }
        System.out.println( "----------------------------------------------------------------" );

        // 删除账号
        strResult = cidrv.DelAcc( 7, 6, strAccount );
        System.out.println( "删除账号" );
        System.out.println( strResult );
        retJsonObj = JSONObject.parseObject( strResult );
        if ( retJsonObj.get("RetCode").toString().equals("0") ) {
            System.out.println( "删除账号成功！" );
        } else {
            System.out.println( "删除账号失败，返回错误代码：0x"
                    + Integer.toHexString( cidrv.getLastErrorCode() ) );
        }
    }
}