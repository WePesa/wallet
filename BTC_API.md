# API for Bitcoin 

1. Generate Address
2. GetBalance
3. Send Transaction
4. Making Outgoing Payments

## Methods to Create Address

  - Randomly generate a private key
  - Calculate public key from it
  - Hash and encode into address

1. Generating a new address

  http://localhost:3000/merchant/$guid/new_address?password=$main_password&second_password=$second_password&label=$label

  Query Parameters:
  
  -  `$main_password` Your Main Blockchain wallet password
  -  `$second_password` Your second Blockchain Wallet password if double encryption is enabled.
  -  `$api_code` blockchain.info wallet api code(optional)
  -  `$label` An optional label to attach to this address. It is recommended this is a human readable string e.g. "Order No : 1234". You May use this as a reference to check balance of an order (documented later)

    Response: 200 OK, application/json

    ``{ "address" : "The Bitcoin Address Generated" , "label" : "The Address Label"}``

    { "address" : "18fyqiZzndTxdVo7g9ouRogB4uFj86JJiy" , "label":  "Order No : 1234" }
    
    
2. Getting the balance of an address
Retrieve the balance of a bitcoin address. Querying the balance of an address by label is depreciated.

  http://localhost:3000/merchant/$guid/address_balance?password=$main_password&address=$address
  
  Query Parameters:

    - `$main_password` Your Main Blockchain wallet password
    - `$address` The bitcoin address to lookup
    - `$api_code` blockchain.info wallet api code(optional)

     Response: 200 OK, application/json

     ``{"balance" : Balance in Satoshi ,"address": "Bitcoin Address", "total_received" : Total Satoshi Received}``

     {"balance" : 50000000, "address" : "19r7jAbPDtfTKQ9VJpvDzFFxCjUJFKesVZ", "total_received" : 100000000}


3. Send Many Transactions
Send a transaction to multiple recipients in the same transaction.

  http://localhost:3000/merchant/$guid/sendmany?password=$main_password&second_password=$second_password&recipients=$recipients&fee=$fee
  
  Query Parameters:
  
    - `$main_password` Your Main Blockchain wallet password
    - `$second_password` Your second Blockchain Wallet password if double encryption is enabled.
    - `$recipients` Is a JSON Object using Bitcoin Addresses as keys and the amounts to send as values (See below).
    - `$from` Send from a specific Bitcoin Address (Optional)
    - `$fee` Transaction fee value in satoshi (Must be greater than default fee) (Optional)
    - `$api_code` blockchain.info wallet api code(optional)
    
    
  URI Encoding a JSON object in JavaScript:

  ``var myObject = { address1: 10000, address2: 50000 };``
  
  ``var myJSONString = JSON.stringify(myObject);``
  
  ``// `encodeURIComponent` is a global function``
  
  ``var myURIEncodedJSONString = encodeURIComponent(myJSONString);``
  
  ``// use `myURIEncodedJSONString` as the `recipients` parameter``


    Response: 200 OK, application/json

    {
        "1JzSZFs2DQke2B3S4pBxaNaMzzVZaG4Cqh": 100000000,
        "12Cf6nCcRtKERh9cQm3Z29c9MWvQuFSxvT": 1500000000,
        "1dice6YgEVBf88erBFra9BHf6ZMoyvG88": 200000000
     }

    The above example would send 1 BTC to 1JzSZFs2DQke2B3S4pBxaNaMzzVZaG4Cqh, 15 BTC to 12Cf6nCcRtKERh9cQm3Z29c9MWvQuFSxvT and 2 BTC to 1dice6YgEVBf88erBFra9BHf6ZMoyvG88 in the same transaction.

    Response: 200 OK, application/json

    ``{ "message" : "Response Message" , "tx_hash": "Transaction Hash" }``

    { "message" : "Sent To Multiple Recipients" , "tx_hash" : "f322d01ad784e5deeb25464a5781c3b20971c1863679ca506e702e3e33c18e9c" }



4. Making Outgoing Payments
Send bitcoin from your wallet to another bitcoin address. All transactions include a 0.0001 BTC miners fee.
All bitcoin values are in Satoshi i.e. divide by 100000000 to get the amount in BTC. The Base URL for all requests: https://blockchain.info/merchant/$guid/. $guid should be replaced with your Blockchain Wallet identifier (found on the login page).

  http://localhost:3000/merchant/$guid/payment?password=$main_password&second_password=$second_password&to=$address&amount=$amount&from=$from&fee=$fee
  
  Query Parameters:
  
    - `$main_password` Your Main Blockchain Wallet password
    - `$second_password` Your second Blockchain Wallet password if double encryption is enabled.
    - `$to` Recipient Bitcoin Address.
    - `$amount` Amount to send in satoshi.
    - `$from` Send from a specific Bitcoin Address (Optional)
    - `$fee` Transaction fee value in satoshi (Must be greater than default fee) (Optional)
    - `$api_code` blockchain.info wallet api code(optional)

    Response: 200 OK, application/json

    ``{ "message" : "Response Message" , "tx_hash": "Transaction Hash", "notice" : "Additional Message" }``

    { "message" : "Sent 0.1 BTC to 1A8JiWcwvpY7tAopUkSnGuEYHmzGYfZPiq" , "tx_hash" : "f322d01ad784e5deeb25464a5781c3b20971c1863679ca506e702e3e33c18e9c" , "notice" : "Some funds are pending confirmation and cannot be spent yet (Value 0.001 BTC)" }


## RPC
Bitcoind compatible RPC API. Full documentation available [here](https://blockchain.info/api/json_rpc_api)
