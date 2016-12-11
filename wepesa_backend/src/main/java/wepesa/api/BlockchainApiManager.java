package wepesa.api;

public class BlockchainApiManager
{
    private static BlockchainApiManager instance;

    public static BlockchainApiManager getInstance()
    {
        if (instance == null)
        {
            instance = new BlockchainApiManager();
        }

        return instance;
    }

    /* Blockchain APIs */

    private BlockchainApiManager()
    {

    }

}
