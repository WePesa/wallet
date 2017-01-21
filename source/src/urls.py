from django.conf.urls import include, url
from django.contrib import admin
from wallet import urls

urlpatterns = [
    # Examples:
    # url(r'^$', 'src.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),

    url(r'^admin/', include(admin.site.urls)),
    url(r'^wallet/', include(wallet.urls))
]
