FROM payara/server-full
MAINTAINER natarajan <natarajanrodrigues@gmail.com>

ENV DOMAIN domain1

ENV LIB /opt/payara41/glassfish/domains/${DOMAIN}/lib/

ENV DEPLOY ${PAYARA_PATH}/glassfish/domains/${DOMAIN}/autodeploy/
ADD target/rhecruta-core/WEB-INF/lib/ ${LIB}
ADD domain.xml ${PAYARA_PATH}/glassfish/domains/${DOMAIN}/config/

ENTRYPOINT $PAYARA_PATH/bin/asadmin start-domain --verbose ${DOMAIN}
ADD target/rhecruta-core.war  ${DEPLOY}
