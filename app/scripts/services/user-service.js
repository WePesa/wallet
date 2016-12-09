(function () {
'use strict';
var module = angular.module('WeP.base');
module.factory('UserService', function ($q, nxt, KeyService, $rootScope) {

  var SERVICE = {

    currentAccount: null,
    currentWallet: null,
    userAccounts: [],
    isCurrentAccount: function (id_rs) {
      return this.currentAccount && this.currentAccount.id_rs == id_rs;
    },

    setCurrentAccount: function (account) {
      if (this.currentAccount) {
        $rootScope.$emit('onCloseCurrentAccount', this.currentAccount);
      }
      $rootScope.currentAccount = this.currentAccount = account;

      var api = nxt.get(account.id_rs);
      this.currentAccount.symbol_lower = api.engine.symbol_lower;
      $rootScope.$emit('onOpenCurrentAccount', this.currentAccount);

      api.engine.socket().getAccount({account:account.id_rs}).then(
        function (a) {
          $rootScope.$evalAsync(function () {
            SERVICE.currentAccount.name = a.accountName;
          });
        }
      );
      return this.currentAccount
    },

    setCurrentWallet: function (wallet) {
      if (this.currentWallet) {
        $rootScope.$emit('onCloseCurrentWallet', this.currentWallet);
      }
      this.currentWallet = wallet;
      $rootScope.$emit('onOpenCurrentWallet', this.currentWallet);

      this.userAccounts.length = 0;
      $rootScope.userAccounts = this.userAccounts = (wallet.entries||[]);
      this.userAccounts.forEach(function (a) {
        a.label = a.id_rs;
        var api = nxt.get(a.id_rs);
        if (!a.publicKey) {
          a.publicKey = api.crypto.secretPhraseToPublicKey(a.secretPhrase);
        }
        if (!a.type) {
          a.type = api.type;
        }
      });
      this.loadAccountData(new Iterator(this.userAccounts));
    },

    logout: function () {
      if (this.currentAccount) {
        $rootScope.$emit('onCloseCurrentAccount', this.currentAccount);
      }
      $rootScope.currentAccount = this.currentAccount = null;
      this.currentWallet = null;
      $rootScope.userAccounts = this.userAccounts = [];
      KeyService.lock();
      nxt.fim().lock();
      if (ENABLE_DUAL_ENGINES) {
        nxt.nxt().lock();
      }
    },

    loadAccountData: function (iterator) {
      if (!iterator.hasMore()) return;
      try {
        var account = iterator.next();
        var api = nxt.get(account.id_rs);
        var socket = api.engine.socket();
        if (!socket) {
          this.loadAccountData(iterator);
          return;
        }
        else {
          socket.callAPIFunction({ requestType: 'getAccount', account: account.id_rs }).then(
            function (a) {
              $rootScope.$evalAsync(function () {

                account.name = a.accountName;
                account.description = a.description;
                account.balance = nxt.util.convertToNXT(a.unconfirmedBalanceNQT);
                if (a.accountColorName) {
                  account.balance += ' '+a.accountColorName;
                  account.symbol = a.accountColorName;
                  account.accountColorId = a.accountColorId;
                }
                else {
                  account.balance += ' '+api.engine.symbol;
                  account.symbol = api.engine.symbol;
                  account.accountColorId = '0';
                }
                account.label = account.name || account.id_rs;
              });

              this.loadAccountData(iterator);
            }.bind(this)
          );
        }
      }
      catch (e) {
        console.log('Error UserService.loadAccountData()', e);
        this.loadAccountData(iterator);
      }
    },

    refresh: function (id_rs) {
      for (var i=0; i<this.userAccounts.length; i++) {
        if (this.userAccounts[i].id_rs == id_rs) {
          this.loadAccountData(new Iterator([this.userAccounts[i]]));
          break;
        }
      }
    }
  };

  $rootScope.isCurrentAccount = SERVICE.isCurrentAccount;
  return SERVICE;
});
})();
