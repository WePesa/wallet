(function () {
'use strict';

/* Handle nodejs exceptions */
if (typeof process != 'undefined') {
  process.on('uncaughtException', function(err) {
    console.log('Caught exception: ' + err);
  });
}

var module = angular.module('WeP.base', [
  'ngAnimate',
  'ngSanitize',
  'ngTouch',
  'ngTable',
  'ngRoute',
  'ngCookies',
  'ui.bootstrap',
  'ui.validate',
  'pascalprecht.translate',
  'infinite-scroll',
  'ngClipboard',
  'noCAPTCHA'
]);

module.config(function(noCAPTCHAProvider, $translateProvider) {
  noCAPTCHAProvider.setSiteKey('6Le7pBITAAAAANPHWrIsoP_ZvlxWr0bSjOPrlszc');
  noCAPTCHAProvider.setTheme('light');
  noCAPTCHAProvider.setLanguage($translateProvider.preferredLanguage());
});

module.run(function ($log, $rootScope, $translate, plugins, serverService) {
  $log.log('WeP.base application started');
  if (isNodeJS) {
    var win = require('nw.gui').Window.get();
    win.on('close', function (event) {

      var self = this;
      plugins.get('alerts').confirm({
        title: 'Close WePesawallet',
        message: 'Are you sure you want to exit WePesa?'
      }).then(
        function (confirmed) {
          if (confirmed) {
            var count = 0;
            angular.forEach(['TYPE_NXT','TYPE_FIM'], function (id) {
              if (serverService.isRunning(id)) {
                serverService.addListener(id, 'exit', function () {
                  count--;
                  if (count == 0) {
                    self.close(true);
                  }
                });

                count++;
                serverService.stopServer(id);
              }
            });
            if (count == 0) {
              self.hide();
              self.close(true);
            }
            else {
              plugins.get('alerts').wait({
                title: "Please wait",
                message: "Shutting down"
              });
            }
          }
        }
      );
    });
  }
});

module.config(function($translateProvider, $httpProvider) {
  $translateProvider.useSanitizeValueStrategy(null);
  $translateProvider.useStaticFilesLoader({ prefix: './i18n/', suffix: '.json' });
  $translateProvider.preferredLanguage('en');
  $translateProvider.useLocalStorage();

  delete $httpProvider.defaults.headers.common['X-Requested-With'];
});

module.config(['ngClipProvider', function(ngClipProvider) {
  ngClipProvider.setPath("ZeroClipboard.swf");
}]);

})();
