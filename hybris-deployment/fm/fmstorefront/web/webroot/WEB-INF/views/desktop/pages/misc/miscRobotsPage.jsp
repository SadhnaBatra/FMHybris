<%@ page contentType="text/plain" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

# For all robots
User-agent: *
Allow: /

# Block access to specific groups of pages
Disallow: fmmotorparts.com/fmstorefront/federalmogul/en/USD/cart
Disallow: fmmotorparts.com/fmstorefront/federalmogul/en/USD/checkout
Disallow: fmmotorparts.com/fmstorefront/federalmogul/en/USD/my-account
Disallow: fmmotorparts.com/fmstorefront/federalmogul/en/USD/login.jsp
#Disallow: <c:url value="/fmstorefront" />


#Request-rate: 1/10              # maximum rate is one page every 10 seconds
#Crawl-delay: 10                 # 10 seconds between page requests
#Visit-time: 0400-0845           # only visit between 04:00 and 08:45 UTC

# Allow search crawlers to discover the sitemap
Sitemap: fmmotorparts.com/fmstorefront/sitemap.xml


# Block CazoodleBot as it does not present correct accept content headers
User-agent: CazoodleBot
Disallow: /

# Block MJ12bot as it is just noise
User-agent: MJ12bot
Disallow: /

# Block dotbot as it cannot parse base urls properly
User-agent: dotbot/1.0
Disallow: /

# Block Gigabot
User-agent: Gigabot
Disallow: /
