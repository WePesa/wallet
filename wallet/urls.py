from django.conf.urls import url

urlpatterns = [
    url(r'^$', 'wallets', name='wallet_list'),
    url(r'^create/$', 'create', name='wallet_create'),
    url(r'^wallet/(?P<pk>\d+)/$', 'detail', name='wallet_detail'),
    url(r'^wallet/(?P<pk>\d+)/withdraw$', 'withdraw', name='wallet_withdraw'),
]
