### Recursos JMS Necessários ao projeto
```
./bin/asadmin create-jms-resource --restype javax.jms.ConnectionFactory jms/dac/dacConnectionFactory

./bin/asadmin create-jms-resource --restype javax.jms.Queue jms/dac/newEmailsQueue

./bin/asadmin create-jms-resource --restype javax.jms.Queue jms/dac/waitingEmailsQueue

./bin/asadmin create-javamail-resource --mailhost="smtp.gmail.com" --mailuser="rhecrutapp" --fromaddress="rhecrutapp@gmail.com" --debug="false" --enabled="true" --description="A new JavaMail Session" --property="mail.smtp.password=rhecrutadac:mail.smtp.auth=true:mail.smtp.port=465:mail.smtp.socketFactory.fallback=false:mail.smtp.socketFactory.port=465:mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory" "dac/rhecruta/javaMailSession"

./bin/asadmin create-jms-resource --restype javax.jms.Queue jms/dac/rhecruta/newOfferQueue

./bin/asadmin create-jms-resource --restype javax.jms.Queue jms/dac/filesToUploadQueue

./bin/asadmin create-jms-resource --restype javax.jms.Queue dac/rhecruta/systemEvaluationQueue

./bin/asadmin create-jms-resource --restype javax.jms.Topic jms/dac/newInterview

./bin/asadmin create-jms-resource --restype javax.jms.Topic jms/dac/cancelInterview


```