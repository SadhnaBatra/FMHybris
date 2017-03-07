function hybrisApiLogout(url) {
    Cookies.remove('HybrisAuthToken');

    window.location.href = url;
    
}

