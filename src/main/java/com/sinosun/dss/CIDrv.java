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
		int VerifyOperInfo( int nPort, int nSeq, String strVerifyInfo );
		int ConnectCICalculate( int nPort, int nSeq,
                                String strAccount,
                                char cType,
                                String strDate,
                                String strTicketNo,
                                String strPayMoney,
                                byte[] bszPaycode );

        int ConnectCICalculateExtend( int nPort, int nSeq,
                                String strAccount,
                                String strRcvAcc,
                                char cType,
                                String strDate,
                                String strTicketNo,
                                String strPayMoney,
                                byte[] bszPaycode );
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

	public String VerifyOperInfo( int nPort, int nSeq, String strVerifyInfo ) {	
        JSONObject jsonObj = new JSONObject();

        nRetCode = CIDrvImpl.instance.VerifyOperInfo( nPort, nSeq, strVerifyInfo );

        jsonObj.put( "RetCode", nRetCode );
        jsonObj.put( "RetMsg", getErrMsg( nRetCode ) );
        return jsonObj.toString();
	}

	public String ConnectCICalculate( int nPort, 
						int nSeq, 
						String strAccount,
						char cType,
						String strDate,
						String strTicketNo,
						String strPayMoney ) {	
        JSONObject jsonObj = new JSONObject();
        byte[] bszPaycode = new byte[1024];

        nRetCode = CIDrvImpl.instance.ConnectCICalculate( nPort, nSeq,
                strAccount, cType, strDate, strTicketNo, strPayMoney, bszPaycode );

        if ( nRetCode == 0 ) {
            String strPaycode = new String(bszPaycode).substring(0, 16);
            jsonObj.put( "PAYCODE", strPaycode );
        }

        jsonObj.put( "RetCode", nRetCode );
        jsonObj.put( "RetMsg", getErrMsg( nRetCode ) );
        return  jsonObj.toString();
	}

    public String ConnectCICalculateExtend( int nPort,
                                      int nSeq,
                                      String strAccount,
                                      String strRcvAcct,
                                      char cType,
                                      String strDate,
                                      String strTicketNo,
                                      String strPayMoney ) {
        JSONObject jsonObj = new JSONObject();
        byte[] bszPaycode = new byte[1024];

        nRetCode = CIDrvImpl.instance.ConnectCICalculateExtend( nPort, nSeq,
                strAccount, strRcvAcct, cType, strDate, strTicketNo, strPayMoney, bszPaycode );

        if ( nRetCode == 0 ) {
            String strPaycode = new String(bszPaycode).substring(0, 16);
            jsonObj.put( "PAYCODE", strPaycode );
        }

        jsonObj.put( "RetCode", nRetCode );
        jsonObj.put( "RetMsg", getErrMsg( nRetCode ) );
        return  jsonObj.toString();
    }
}