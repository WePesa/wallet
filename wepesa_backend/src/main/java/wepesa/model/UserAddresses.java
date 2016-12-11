package wepesa.model;

/**
 * Created by harsh on 11/12/16.
 */
public class UserAddresses {

    private String btcAddress;
    private String ethAddress;
    private String zCashAddress;

    public String getBtcAddress() {
        return btcAddress;
    }

    public void setBtcAddress(String btcAddress) {
        this.btcAddress = btcAddress;
    }

    public String getEthAddress() {
        return ethAddress;
    }

    public void setEthAddress(String ethAddress) {
        this.ethAddress = ethAddress;
    }

    public String getzCashAddress() {
        return zCashAddress;
    }

    public void setzCashAddress(String zCashAddress) {
        this.zCashAddress = zCashAddress;
    }
}
