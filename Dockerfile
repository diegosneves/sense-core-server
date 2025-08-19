FROM eclipse-temurin:24.0.1_9-jdk-ubi9-minimal

ENV LANGUAGE='en_US:en'

# Criar usuário se não existir
USER root
RUN id -u 185 &>/dev/null || useradd -u 185 -r -g 0 -d /home/jboss -s /sbin/nologin -c "JBoss user" jboss

# Copiar os arquivos da aplicação
COPY --chown=185 build/quarkus-app/lib/ /deployments/lib/
COPY --chown=185 build/quarkus-app/*.jar /deployments/
COPY --chown=185 build/quarkus-app/app/ /deployments/app/
COPY --chown=185 build/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
USER 185

# Configurações para Quarkus
ENV JAVA_OPTS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"

# Executar diretamente com Java
ENTRYPOINT ["java"]
CMD ["-jar", "/deployments/quarkus-run.jar"]