---
layout: default
title: CAS - U2F - FIDO Universal 2nd Factor Authentication
category: Multifactor Authentication
---

{% include variables.html %}

# DynamoDb U2F - FIDO Universal Registration

Device registrations may be kept inside a DynamoDb instance by including the following module in the WAR overlay:

{% include casmodule.html group="org.apereo.cas" module="cas-server-support-u2f-dynamodb" %}

{% include casproperties.html
modules="cas-server-support-u2f-dynamodb" %}
